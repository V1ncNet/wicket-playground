package de.vinado.app.playground.upload.adapter.model;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

import static java.util.function.Predicate.not;

@Data
public class Request {

    @NonNull
    @Getter(AccessLevel.NONE)
    private final List<Bundle> bundles;

    public List<Bundle> listPending() {
        return stream(not(Bundle::failed))
            .toList();
    }

    public List<Bundle> listFailed() {
        return stream(Bundle::failed)
            .toList();
    }

    private Stream<Bundle> stream(Predicate<Bundle> predicate) {
        return bundles.stream().filter(predicate);
    }

    public boolean uploaded() {
        return bundles.stream().allMatch(Bundle::completed);
    }
}
