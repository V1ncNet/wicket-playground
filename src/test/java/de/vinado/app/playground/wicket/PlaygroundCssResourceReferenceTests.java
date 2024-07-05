package de.vinado.app.playground.wicket;

import de.vinado.app.playground.wicket.test.SpringEnabledWicketTestCase;
import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.request.resource.ResourceReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlaygroundCssResourceReferenceTests extends SpringEnabledWicketTestCase {

    private ResourceReference reference;

    @BeforeEach
    void setUp() {
        reference = PlaygroundCssResourceReference.asHeaderItem()
            .getReference();
    }

    @Test
    void resourceReference_shouldExtendResourceReference() {
        assertInstanceOf(ResourceReference.class, reference);
    }

    @Test
    void headerItem_shouldReturnSameInstance() {
        CssReferenceHeaderItem headerItem = PlaygroundCssResourceReference.asHeaderItem();

        assertSame(reference, headerItem.getReference());
    }
}
