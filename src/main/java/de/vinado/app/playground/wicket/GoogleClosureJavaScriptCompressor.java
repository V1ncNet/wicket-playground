package de.vinado.app.playground.wicket;

import com.google.javascript.jscomp.CompilationLevel;
import com.google.javascript.jscomp.Compiler;
import com.google.javascript.jscomp.CompilerOptions;
import com.google.javascript.jscomp.SourceFile;
import org.apache.wicket.javascript.IJavaScriptCompressor;

public class GoogleClosureJavaScriptCompressor implements IJavaScriptCompressor {

    private static final CompilationLevel LEVEL = CompilationLevel.SIMPLE_OPTIMIZATIONS;

    @Override
    public String compress(String original) {
        Compiler compiler = new Compiler();
        CompilerOptions options = new CompilerOptions();
        LEVEL.setOptionsForCompilationLevel(options);

        SourceFile extern = SourceFile.fromCode("externs.js", "function alert(x) {}");
        SourceFile input = SourceFile.fromCode("input.js", original);

        compiler.compile(extern, input, options);
        return compiler.toSource();
    }
}
