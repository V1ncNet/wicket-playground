package de.vinado.function;

import org.danekja.java.util.function.serializable.SerializableFunction;

import java.io.Serializable;

public interface SerializableThrowingFunction<T, R, E extends Exception> extends Serializable {

    R apply(T argument) throws E;

    static <T, R> SerializableFunction<T, R> sneaky(SerializableThrowingFunction<? super T, ? extends R, ?> function) {
        return self -> {
            try {
                return function.apply(self);
            } catch (Exception e) {
                return sneakyThrow(e);
            }
        };
    }

    private static <T extends Exception, R> R sneakyThrow(Exception t) throws T {
        throw (T) t;
    }
}
