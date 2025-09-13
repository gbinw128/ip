package exceptions;

public class DeleteNumberError extends RuntimeException {
    public DeleteNumberError(String message) {
        super(message);
    }
}
