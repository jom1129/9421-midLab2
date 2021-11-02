package midlab2;

public class TreeNode<T> {
    private TreeNode<T> left;
    private T data;
    private TreeNode<T> right;
    private int nodeWeight = 0; // node weight is not always
                                // equal to the frequency
                                // of the token
    private boolean hasVisited = false;
    public TreeNode(T data) {
        this.data = data;
        left = null;
        right = null;
    }

    public void setLeft(TreeNode<T> left) {
        this.left = left;
    }

    public void setRight(TreeNode<T> right) {
        this.right = right;
    }

    public void setData(T data) {
        this.data = data;
    }

    public TreeNode<T> getLeft() {
        return left;
    }

    public TreeNode<T> getRight() {
        return right;
    }

    public T getData() {
        return data;
    }

    /**
     * Note about compareTo:
     *      if this < other, then compareTo returns < 0
     *      if this > other, then compareTo returns > 0
     * @param element
     */
    public void insert(T element) {
        /* if new element is less than the element stored in the node
               store it to the left node
                   if left is null
                        create a new node with that element stored in it
            if
         */
        if (element.toString().compareTo(data.toString()) < 0) {
            if (left == null) left = new TreeNode<>(element);
            else left.insert(element);
        } else if (element.toString().compareTo(data.toString()) > 0) {
            if (right == null) right = new TreeNode<>(element);
            else right.insert(element);
        }
    }

    public void setNodeWeight(int nodeWeight) {
        this.nodeWeight = nodeWeight;
    }


    public boolean isLeaf() {
        return getLeft() == null && getRight() == null;
    }

    public void setHasVisited(boolean hasVisited) {
        this.hasVisited = hasVisited;
    }

    public boolean hasVisited() {
        return hasVisited;
    }
}
