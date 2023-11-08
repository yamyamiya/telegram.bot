package io.yamyamiya.telegram.bot.exception.exceptions;

public class EntityValidationException extends RuntimeException{
    public EntityValidationException(String message) {
        super(message);
    }
}
