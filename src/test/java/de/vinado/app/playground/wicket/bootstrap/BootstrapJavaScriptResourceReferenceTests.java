package de.vinado.app.playground.wicket.bootstrap;

import de.vinado.app.playground.wicket.test.SpringMockedWicketTestCase;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.request.resource.JavaScriptPackageResource;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BootstrapJavaScriptResourceReferenceTests extends SpringMockedWicketTestCase {

    private ResourceReference reference;

    @BeforeEach
    void setUp() {
        reference = BootstrapJavaScriptResourceReference.asHeaderItem()
            .getReference();
    }

    @Test
    void resourceReference_shouldExtendJavaScriptResourceReference() {
        assertInstanceOf(JavaScriptResourceReference.class, reference);
    }

    @Test
    void headerItem_shouldReturnSameInstance() {
        JavaScriptReferenceHeaderItem headerItem = BootstrapJavaScriptResourceReference.asHeaderItem();

        assertSame(reference, headerItem.getReference());
    }

    @Test
    void resourceReference_shouldExist() {
        JavaScriptResourceReference reference = (JavaScriptResourceReference) tester.startResourceReference(this.reference);

        JavaScriptPackageResource resource = reference.getResource();

        assertNotNull(resource.getResourceStream());
    }
}
