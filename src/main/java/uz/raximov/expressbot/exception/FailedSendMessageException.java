package uz.raximov.expressbot.exception;

public class FailedSendMessageException extends RuntimeException {

    public FailedSendMessageException() {
    }

    public FailedSendMessageException(String message) {
        super(message);
    }

    public FailedSendMessageException(String message, Throwable cause) {
        super(message, cause);
    }

}

