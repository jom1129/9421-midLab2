package midlab2;

/**
 * Provided StackNode Reference class by
 * the problem specification
 * @param <T> StackNode content
 */
public class StackNode <T> {
    private T info; // StackNode content
    private StackNode<T> link;   // links to the next StackNode

    /**
     * The default constructor does nothing
     */
    public StackNode() { }

    /**
     * A constructor that receives a parameter
     * to be stored and a reference to the next StackNode
     * @param info data to be stored inside the StackNode
     * @param link contains a reference to the next StackNode
     */
    public StackNode(T info, StackNode<T> link) {
        this.info = info;
        this.link = link;
    }

    /**
     * Holds the element to be stored
     * @param info element to be stored within the StackNode
     */
    public void setInfo(T info) {
        this.info = info;
    }

    /**
     * Contains a memory reference (pointer) to the
     * next StackNode
     * @param link
     */
    public void setLink(StackNode<T> link) {
        this.link = link;
    }

    /**
     * Returns the contents of the StackNode
     * @return element stored within the StackNode
     */
    public T getInfo() {
        return info;
    }

    /**
     * Returns the next StackNode
     * @return reference to the next StackNode
     */
    public StackNode<T> getLink() {
        return link;
    }
}