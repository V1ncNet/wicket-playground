package de.vinado.app.playground.wicket.bootstrap.home;

import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.tempusdominus.TempusDominusBehavior;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.tempusdominus.TempusDominusConfig;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.tempusdominus.TempusDominusDisplayConfig.ButtonType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.tempusdominus.TempusDominusDisplayConfig.ThemeType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.tempusdominus.TempusDominusLocalizationConfig;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeSettings;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.feedback.FencedFeedbackPanel;
import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.convert.converter.LocalDateTimeConverter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class TestForm extends GenericPanel<Bean> {

    private final Form<Bean> form;
    private final FormComponent<LocalDateTime> startTextField;
    private final FormComponent<LocalDateTime> endTextField;
    private final FeedbackPanel endFeedbackPanel;
    private final FeedbackPanel feedbackPanel;

    public TestForm(String id, IModel<Bean> model) {
        super(id, model);

        this.form = form("form");
        this.startTextField = startTextField("start");
        this.endTextField = endTextField("end");
        this.endFeedbackPanel = endFeedbackPanel("endFeedback", endTextField);
        this.feedbackPanel = feedbackPanel("feedback");
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        form.setOutputMarkupId(true);
        startTextField.setOutputMarkupId(true);
        endTextField.setOutputMarkupId(true);
        endFeedbackPanel.setOutputMarkupId(true);
        feedbackPanel.setOutputMarkupId(true);

        queue(form);
        queue(range("range"));
        queue(startTextField);
        queue(endTextField);
        queue(endFeedbackPanel);
        queue(feedbackPanel);
    }

    protected Form<Bean> form(String wicketId) {
        BootstrapForm<Bean> form = new BootstrapForm<>(wicketId, getModel());
        return form;
    }

    protected FeedbackPanel feedbackPanel(String wicketId) {
        return new NotificationPanel(wicketId);
    }

    protected FormComponent<LocalDateTime> startTextField(String wicketId) {
        IModel<LocalDateTime> model = LambdaModel.of(getModel(), Bean::getEnd, Bean::setEnd);
        TempusDominusConfig config = createTempusDominusConfig();
        FormComponent<LocalDateTime> textField = new TempusDominusTextField(wicketId, model);
        textField.setRequired(true);
        textField.add(new TempusDominusBehavior(config) {

            @Override
            public void renderHead(Component component, IHeaderResponse response) {
                super.renderHead(component, response);

                response.render(OnDomReadyHeaderItem.forScript(""
                    + "const " + component.getMarkupId() + " = document.getElementById('" + component.getMarkupId() + "');\n"
                    + "const " + endTextField.getMarkupId() + " = document.getElementById('" + endTextField.getMarkupId() + "');\n"
                    + component.getMarkupId() + ".datetimepicker.subscribe(tempusDominus.Namespace.events.hide, e => {\n"
                    + "  " + endTextField.getMarkupId() + ".datetimepicker.updateOptions({\n"
                    + "    restrictions: {\n"
                    + "      minDate: e.date,\n"
                    + "    },\n"
                    + "  });\n"
                    + "  " + endTextField.getMarkupId() + ".datetimepicker.clear();\n"
                    + "});\n"));
            }
        });
        textField.add(new AjaxFormSubmitBehavior("hide.td") {

            @Override
            protected void onError(AjaxRequestTarget target) {
                super.onError(target);

                target.add(feedbackPanel);
            }
        });
        return textField;
    }

    protected FormComponent<LocalDateTime> endTextField(String wicketId) {
        IModel<LocalDateTime> model = LambdaModel.of(getModel(), Bean::getEnd, Bean::setEnd);
        TempusDominusConfig config = createTempusDominusConfig()
            .withUseCurrent(false);
        FormComponent<LocalDateTime> textField = new TempusDominusTextField(wicketId, model) {

            @Override
            protected void onValid() {
                add(AttributeAppender.replace("class", "form-control is-valid"));
            }

            @Override
            protected void onInvalid() {
                add(AttributeAppender.replace("class", "form-control is-invalid"));
            }
        };
        textField.setRequired(true);
        textField.add(new TempusDominusBehavior(config));
        textField.add(new AjaxFormSubmitBehavior("hide.td") {

            @Override
            protected void onError(AjaxRequestTarget target) {
                super.onError(target);

                target.add(endFeedbackPanel);
                target.add(endTextField);
            }
        });
        return textField;
    }

    protected FeedbackPanel endFeedbackPanel(String wicketId, Component fence) {
        return new FencedFeedbackPanel(wicketId, fence.getParent());
    }

    private FormComponent<Range> range(String wicketId) {
        IModel<Range> model = LambdaModel.of(getModel(), Bean::getRange, Bean::setRange);
        RangeTextField textField = new RangeTextField(wicketId, model, createTempusDominusConfig());
        textField.add(new RangeValidator());
        textField.add(new AjaxFormComponentUpdatingBehavior("hide.td") {

            @Override
            protected void onUpdate(AjaxRequestTarget target) {
            }
        });
        return textField;
    }

    private static TempusDominusConfig createTempusDominusConfig() {
        Locale locale = Session.get().getLocale();
        return new TempusDominusConfig()
            .withClass(LocalDateTime.class)
            .withStepping(30)
            .withRestrictions(restrictions -> restrictions
                .withMinDate(LocalDate.now().atStartOfDay()))
            .withLocalization(localization -> localization
                .withDateFormat(TempusDominusLocalizationConfig.DateFormatType.L, getPattern(SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale)))
                .withFormat(TempusDominusLocalizationConfig.DateFormatType.L.name()))
            .withDisplay(display -> display
                .withTheme(ThemeType.LIGHT)
                .withButton(ButtonType.TODAY, true)
                .withButton(ButtonType.CLEAR, true)
                .withButton(ButtonType.CLOSE, true))
            ;
    }

    private static String getPattern(DateFormat format) {
        return ((SimpleDateFormat) format).toLocalizedPattern();
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);

        CssResourceReference fontAwesomeReference = FontAwesomeSettings.get(Application.get()).getCssResourceReference();
        response.render(CssReferenceHeaderItem.forReference(fontAwesomeReference));
    }


    private static class TempusDominusTextField extends TextField<LocalDateTime> {

        public TempusDominusTextField(String id, IModel<LocalDateTime> model) {
            super(id, model, LocalDateTime.class);
        }

        @Override
        @SuppressWarnings("unchecked")
        public <C> IConverter<C> getConverter(Class<C> type) {
            return (IConverter<C>) TempusDominusConverter.getInstance();
        }
    }

    private static class TempusDominusConverter extends LocalDateTimeConverter {

        private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);

        private TempusDominusConverter() {
        }

        @Override
        protected DateTimeFormatter getDateTimeFormatter() {
            return DATE_TIME_FORMATTER;
        }

        public static TempusDominusConverter getInstance() {
            return Holder.INSTANCE;
        }


        private static class Holder {

            private static final TempusDominusConverter INSTANCE = new TempusDominusConverter();
        }
    }
}
