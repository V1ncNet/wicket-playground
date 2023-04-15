package de.vinado.app.playground.wicket;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.request.resource.ResourceStreamResource;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.resource.AbstractResourceStream;
import org.apache.wicket.util.resource.IFixedLocationResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

public class PlaygroundCssResourceReference extends ResourceReference {

    private final Resource resource;

    private PlaygroundCssResourceReference() {
        super("css/playground.css");
        this.resource = new ClassPathResource(getName());
    }

    public static CssReferenceHeaderItem asHeaderItem() {
        return CssHeaderItem.forReference(Holder.INSTANCE);
    }

    @Override
    public IResource getResource() {
        return new ResourceStreamResource(new CssResourceStreamAdapter(resource));
    }


    @RequiredArgsConstructor
    private static class CssResourceStreamAdapter extends AbstractResourceStream
        implements IFixedLocationResourceStream {

        private final Resource resource;

        private InputStream stream;

        @Override
        public InputStream getInputStream() throws ResourceStreamNotFoundException {
            try {
                return stream = resource.getInputStream();
            } catch (IOException e) {
                throw new ResourceStreamNotFoundException(e);
            }
        }

        @Override
        public void close() throws IOException {
            if (null != stream) {
                stream.close();
            }
        }

        @Override
        @SneakyThrows
        public String locationAsString() {
            return resource.getURI().toString();
        }

        @Override
        @SneakyThrows
        public Bytes length() {
            return Bytes.bytes(resource.contentLength());
        }

        @Override
        public String getContentType() {
            return "text/css";
        }
    }

    private static class Holder {

        private static final PlaygroundCssResourceReference INSTANCE = new PlaygroundCssResourceReference();
    }
}
