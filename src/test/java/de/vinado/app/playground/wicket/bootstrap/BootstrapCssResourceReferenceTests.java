package de.vinado.app.playground.wicket.bootstrap;

import de.vinado.app.playground.wicket.test.SpringMockedWicketTestCase;
import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.request.resource.CssPackageResource;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BootstrapCssResourceReferenceTests extends SpringMockedWicketTestCase {

    private ResourceReference reference;

    @BeforeEach
    void setUp() {
        reference = BootstrapCssResourceReference.asHeaderItem()
            .getReference();
    }

    @Test
    void resourceReference_shouldExtendCssResourceReference() {
        assertInstanceOf(CssResourceReference.class, reference);
    }

    @Test
    void headerItem_shouldReturnSameInstance() {
        CssReferenceHeaderItem headerItem = BootstrapCssResourceReference.asHeaderItem();

        assertSame(reference, headerItem.getReference());
    }

    @Test
    void resourceReference_shouldExist() {
        CssResourceReference reference = (CssResourceReference) tester.startResourceReference(this.reference);

        CssPackageResource resource = reference.getResource();

        assertNotNull(resource.getResourceStream());
    }
}
