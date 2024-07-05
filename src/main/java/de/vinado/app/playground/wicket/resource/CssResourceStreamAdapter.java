package de.vinado.app.playground.wicket.resource;

import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.resource.AbstractResourceStream;
import org.apache.wicket.util.resource.IFixedLocationResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class CssResourceStreamAdapter extends AbstractResourceStream
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
