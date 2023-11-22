package io.yamyamiya.telegram.bot.utils;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
/**
 * Common interface that represents result of an operation.
 * It consists of Success and Failure.
 * @param <T>  type of expected successful result value.
 */
public interface Result<T> {
    /**
     * represents successful result and holds expected data
     * @param <T> instance of expected data
     */
    class Success<T> implements Result<T> {
        private final T value;

        public Success(T value) {
            this.value = value;
        }
        public T getValue() {
            return value;
        }
    }

    /**
     * represents unsuccessful result and optionally holds exception
     * @param <T> Failure doesn't have any data, but has to have the same T as Success for compatibility.
     */

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
