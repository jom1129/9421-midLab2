package midlab2;

/**
 * Required LinkedStack implementation
 * @param <T>
 */
public class LinkedStack <T>  {
    private StackNode<T> top;    // a reference to the StackNode at the
    // top of the stack
    private int numElements = 0;    // holds the number of elements

    /**
     * Returns the number of elements
     * @return number of elements
     */
    public int size() {
        return numElements;
    }

    /**
     * Tests if the stack is empty
     * @return is the stack empty?
     */

    public boolean isEmpty() {
        return top == null;
    }

    /**
     * Returns the contents of the top StackNode
     * without removing the StackNode
     * @return contents of the top StackNode
     * @throws StackException if the stack is empty (stack underflow)
     */

    public T top() throws StackException {
        if (isEmpty()) throw new StackException("Stack is empty.");
        return top.getInfo();
    }

    /**
     * Pops the stack, returns the contents of the top StackNode,
     * removes that StackNode, and sets the bottom StackNode as the new head (top StackNode)
     * @return contents of the top StackNode
     * @throws StackException if the stack is empty (stack underflow)
     */
    public T pop() throws StackException {
        StackNode<T> temp;
        if (isEmpty()) throw new StackException("Stack underflow.");
        temp = top;
        top = top.getLink();
        --numElements;
        return temp.getInfo();
    }

    /**
     * Push an element into the stack
     * This creates a new StackNode, sets the
     * new StackNode as the top StackNode,
     * and links to the previous top StackNode
     * @param item element to be stored inside the new StackNode
     * @throws StackException is the stack is full (StackOverFlow)
     */

    public void push(T item) throws StackException {
        StackNode<T> newStackNode = new StackNode<>();
        newStackNode.setInfo(item);
        newStackNode.setLink(top);
        top = newStackNode;
        ++numElements;
    }

    /**
     * Invokes top()
     * Returns the content of the top StackNode, without
     * removing that StackNode
     * @return contents of the top StackNode
     * @throws StackException if the stack is empty
     */

    public T peek() throws StackException {
        return top();
    }

    /**
     * Empties the stack without
     * deleting the stack instance
     */

    public void clear() {
        for (var top = this.top; !isEmpty(); top = top.getLink(), --numElements)
            top.setLink(null);
    }

    /**
     * Searches for the passed argument if
     * it is in the stack
     * @param item item to lookup
     * @return index of the item (if it exists), else return -1
     */

    public int search(T item) {
        int index;
        StackNode<T> current = top;
        for (index = numElements; current.getLink() != null;
             current = current.getLink(), --index)
            if (current.getInfo().equals(item)) return index;
        return -1;
    }

    /**
     * Return the top StackNode (head StackNode)
     * @return head StackNode
     */
    public StackNode<T> getTop() {
        return top;
    }
}