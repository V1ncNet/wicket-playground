package de.vinado.app.playground.wicket;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.MarkupException;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.util.lang.Generics;
import org.apache.wicket.util.string.Strings;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public class ExceptionErrorPage extends ErrorPage {

    private final transient Throwable throwable;

    private transient String resource = "";
    private transient String markup = "";
    private boolean showStacktrace = false;

    public ExceptionErrorPage() {
        this(new Exception("There's no error"));
    }

    public ExceptionErrorPage(Throwable throwable) {
        this.throwable = throwable;

        if (throwable instanceof MarkupException markupException) {
            MarkupStream markupStream = markupException.getMarkupStream();
            this.resource = markupStream.getResource().toString();
            this.markup = markupStream.toHtmlDebugString();
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        WebMarkupContainer markupContainer = new WebMarkupContainer("markupContainer") {

            @Override
            protected void onConfigure() {
                super.onConfigure();

                setVisible(showStacktrace && !Strings.isEmpty(resource));
            }
        };
        markupContainer.setOutputMarkupPlaceholderTag(true);
        add(markupContainer);

        WebMarkupContainer stacktraceContainer = new WebMarkupContainer("stacktraceContainer") {

            @Override
            protected void onConfigure() {
                super.onConfigure();

                setVisible(showStacktrace);
            }
        };
        stacktraceContainer.setOutputMarkupPlaceholderTag(true);
        add(stacktraceContainer);

        add(new MultiLineLabel("message", throwable.getLocalizedMessage()));
        add(new AjaxLink<>("showStackTraceButton") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                showStacktrace = !showStacktrace;
                target.add(markupContainer);
                target.add(stacktraceContainer);
            }
        });
        add(homePageLink("homePageLink"));

        markupContainer.add(new Label("resource", resource));
        MultiLineLabel markup = new MultiLineLabel("markup", this.markup);
        markup.setEscapeModelStrings(false);
        markupContainer.add(markup);

        IModel<String> stacktraceModel = new StacktraceModel(throwable);
        stacktraceContainer.add(new MultiLineLabel("stacktrace", stacktraceModel));
    }

    @Override
    protected void setHeaders(WebResponse response) {
        super.setHeaders(response);

        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);

        response.render(CssHeaderItem.forCSS("pre p { margin: 0; }", null));
    }


    private static class StacktraceModel implements IModel<String> {

        private final transient Throwable throwable;

        public StacktraceModel(Throwable throwable) {
            this.throwable = throwable;
        }

        @Override
        public String getObject() {
            List<Throwable> al = convertToList(throwable);
            StringBuilder sb = new StringBuilder(256);

            int length = al.size() - 1;
            Throwable cause = al.get(length);

            sb.append("Root cause:\n\n");
            outputThrowable(cause, sb, false);

            if (length > 0) {
                sb.append("\n\nComplete stack:\n\n");
                for (int i = 0; i < length; i++) {
                    outputThrowable(al.get(i), sb, true);
                    sb.append("\n");
                }
            }

            return sb.toString();
        }

        private List<Throwable> convertToList(final Throwable throwable) {
            List<Throwable> al = Generics.newArrayList();
            Throwable cause = throwable;
            al.add(cause);
            while ((cause.getCause() != null) && (cause != cause.getCause())) {
                cause = cause.getCause();
                al.add(cause);
            }
            return al;
        }

        private void outputThrowable(Throwable cause, StringBuilder sb, boolean stopAtWicketServlet) {
            sb.append(cause);
            sb.append("\n");
            StackTraceElement[] trace = cause.getStackTrace();
            for (int i = 0; i < trace.length; i++) {
                String traceString = trace[i].toString();
                if (i > 1) {
                    sb.append("     at ");
                    sb.append(traceString);
                    sb.append("\n");
                    if (stopAtWicketServlet && traceString.startsWith("org.apache.wicket.protocol.http.WicketFilter")) {
                        return;
                    }
                }
            }
        }
    }
}
