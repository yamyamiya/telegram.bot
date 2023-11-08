package io.yamyamiya.telegram.bot.utils;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public interface Result<T> {
    class Success<T> implements Result<T> {
        private final T value;

        public Success(T value) {
            this.value = value;
        }
        public T getValue() {
            return value;
        }
    }

    class Failure<T> implements Result<T> {
        @Nullable
        private final Exception exception;

        public Failure(@NotNull Exception exception) {
            this.exception = exception;
        }

        public Failure() {
            exception=null;
        }
        @Nullable
        public Exception getException() {
            return exception;
        }
    }
}
