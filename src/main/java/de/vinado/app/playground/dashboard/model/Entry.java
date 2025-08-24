package de.vinado.app.playground.dashboard.model;

import java.util.Optional;
import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
@Accessors(fluent = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Entry {

    @NonNull
    @Getter
    @EqualsAndHashCode.Include
    private final Id id;

    @NonNull
    private final State state;

    public Entry(@NonNull Id id, String text) {
        this(id, new State().text(text));
    }

    public State state() {
        return new State(state);
    }

    public Optional<String> text() {
        return Optional.ofNullable(state.text());
    }

    public Mutator mutate() {
        return new Mutator();
    }


    public class Mutator {

        public Mutator text(String text) {
            state.text(text);
            return this;
        }
    }


    @Data
    @RequiredArgsConstructor
    @ToString(doNotUseGetters = true)
    public static class State {

        private String text;

        State(State blueprint) {
            this.text = blueprint.text();
        }
    }

    public record Id(@NonNull UUID value) {

        @Override
        public String toString() {
            return value.toString();
        }
    }
}
