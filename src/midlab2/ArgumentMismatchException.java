package midlab2;

/**
 * Exception class thrown
 * When arguments
 */
public class ArgumentMismatchException extends RuntimeException {
    ArgumentMismatchException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
