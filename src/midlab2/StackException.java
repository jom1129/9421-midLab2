package midlab2;

/**
 * Thrown during events of StackOverFlow (if the stack is full)
 * or StackUnderFlow (if the stack is empty)
 */
public class StackException extends RuntimeException {
    public StackException(String err) {
        super(err);
    }
}