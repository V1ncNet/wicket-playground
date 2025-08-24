package de.vinado.app.playground.dashboard.presentation.ui;

import de.vinado.app.playground.wicket.PlaygroundPage;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.spring.injection.annot.SpringBean;

import jakarta.servlet.ServletContext;
import java.util.Properties;

public class DashboardPage extends PlaygroundPage {

    public static final String PATH = "/dashboard";

    @SpringBean
    private ServletContext servletContext;

    private final transient Properties properties;

    public DashboardPage() {
        this.properties = new Properties();
        properties.put("BASE_PATH", servletContext.getContextPath() + PATH);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setVersioned(false);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);

        response.render(DashboardJavaScriptResourceReference.asHeaderItem(properties));
    }
}
