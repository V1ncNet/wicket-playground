package de.vinado.app.playground.wicket;

import com.yahoo.platform.yui.compressor.CssCompressor;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.css.ICssCompressor;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class YuiCssCompressor implements ICssCompressor {

    @Override
    public String compress(String original) {
        try {
            StringReader reader = new StringReader(original);
            CssCompressor compressor = new CssCompressor(reader);
            StringWriter writer = new StringWriter(original.length() / 2);
            compressor.compress(writer, Integer.MAX_VALUE);
            return writer.toString();
        } catch (IOException e) {
            throw new WicketRuntimeException(e);
        }
    }
}
