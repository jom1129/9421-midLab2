package midlab2;

/**
 * Data Structure Tree class
 * @param <T> Type of the Contents to be stored
 */
public class Tree<T> implements Comparable<Tree<T>> {
    private TreeNode<T> root; // holds a reference to the root node
    private int size = 0; // holds the number of nodes
    private int weight = 0; // holds the weight of the tree,
                            // the summation of the weights of the
                            // child node

    /**
     * Creates an empty tree
     */
    public Tree() {
        root = null;
    }

    /**
     * Creates a tree with a root node
     * @param element to be stored
     */
    public Tree(T element) { root = new TreeNode<>(element); }

    /**
     * Creates a tree when passed a node as a parameter
     * @param root node
     */
    public Tree(TreeNode<T> root) { this.root = root; }

    /**
     * Computes for the size recursively
     * and returns the updated size variable
     * @return number of nodes / size of the tree
     */
    public int getSize() {
        size = getSize(root);
        return size;
    }

    /**
     * Returns the weight of the tree
     * @return weight of the tree
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Sets the weight of the tree
     * @param weight weight of the tree
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * Returns a reference to the root node
     * @return reference to the root node
     */
    public TreeNode<T> getRoot() {
        return root;
    }

    /**
     * Sets the root node to the passed instance
     * @param root new root
     */
    public void setRoot(TreeNode<T> root) {
        this.root = root;
    }

    /**
     * Tests if the tree is empty
     * @return
     */
    public boolean isEmpty() { return root == null; }

    /**
     * Recursively computes for the size of the tree
     * Private method invoked by getSize()
     *
     Algorithm:
     1. Get the size of the left subtree
     2. Increment by 1 for the root node
     3. Get the size of the right subtree
     4. Return total
     *
     * @return size of the tree
     */
    private int getSize(TreeNode<T> node) {
        if (node == null) return 0; // pre-test for null, fail early

        else return (getSize(node.getLeft()) + 1 + getSize(node.getRight()));
    }

    /**
     * Inserts an element into the tree
     *      If tree is null, set the element as the root
     *      Else insert it in the tree
     * @param element to be inserted
     */
    public void insert(T element) {
        if (root == null) root = new TreeNode<>(element);
        else root.insert(element);
    }

    /**
     * Wrapper method for traversing the tree in
     * pre-order method
     */
    public void traversePreOrder() {
        preOrderHelper(root);
    }

    /**
     * Recursive helper method invoked by the wrapepr method
     * @param node node
     */
    private void preOrderHelper(TreeNode<T> node) {
        if (node == null) return;
        System.out.print(node.getData().toString() + " ");
        preOrderHelper(node.getLeft());
        preOrderHelper(node.getRight());
    }

    /**
     * Public wrapper method that
     * traverses the tree in order
     */
    public void traverseInOrder() {
        inOrderHelper(root);
    }

    /**
     * Recursive method invoked by the wrapper method
     * that traverses the tree in order
     * @param node node
     */
    private void inOrderHelper(TreeNode<T> node) {
        if (node == null) return;
        inOrderHelper(node.getLeft());
        System.out.print(node.getData().toString() + " ");
        inOrderHelper(node.getRight());
    }

    /**
     * Wrapper method for clearing the visited property of a tree
     */
    public void clearHasVisited() { clearHasVisited(root); }

    /**
     * Helper method for recursively clearing the visited property
     * of a tree
     * @param node
     */
    private void clearHasVisited(TreeNode<T> node) {
        if (node == null) return;
        node.setHasVisited(false);
        clearHasVisited(node.getLeft());
        clearHasVisited(node.getRight());
    }

    /**
     * Compares this tree to another
     * based on the weight
     * @param other tree
     * @return the lexicographic ordering
     */
    public int compareTo(Tree<T> other) {
        return this.getWeight() - other.getWeight();
    }

    /**
     * Computes for the weight of this tree
     * @param node root node
     * @return the weight of this tree
     */
    // TODO: 10/30/2021 @Enrico
    public int computeWeight(TreeNode<T> node) {
        if(root == null)
        return 0;

        int leftHeight = getSize(node.getRight());
        int rightHeight = getSize(node.getLeft());

        return -1 + Math.max(leftHeight, rightHeight);
    }
}
