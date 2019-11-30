package demo.api.exception;

public class AppError extends RuntimeException {
    public AppError(String message) {
        super(message);
    }
}
