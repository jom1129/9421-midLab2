package midlab2;
public class Tree<T> implements Comparable<Tree<T>> {
    private TreeNode<T> root;
    private int size = 0;
    private int weight = 0;

    public Tree() {
        root = null;
    }

    public Tree(T element) { root = new TreeNode<>(element); }

    public Tree(TreeNode<T> root) { this.root = root; }

    public int getSize() {
        size = getSize(root);
        return size;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public TreeNode<T> getRoot() {
        return root;
    }

    public void setRoot(TreeNode<T> root) {
        this.root = root;
    }

    public boolean isEmpty() { return root == null; }

    private int getSize(TreeNode<T> node) {
        if (node == null) return 0; // pre-test for null, fail early
        /*
            Algorithm:
                1. Get the size of the left subtree
                2. Increment by 1 for the root node
                3. Get the size of the right subtree
                4. Return total
         */
        else return (getSize(node.getLeft()) + 1 + getSize(node.getRight()));
    }
    public void insert(T element) {
        if (root == null) root = new TreeNode<>(element);
        else root.insert(element);
    }

    public void traversePreOrder() {
        preOrderHelper(root);
    }

    private void preOrderHelper(TreeNode<T> node) {
        if (node == null) return;
        System.out.print(node.getData().toString() + " ");
        preOrderHelper(node.getLeft());
        preOrderHelper(node.getRight());
    }

    public void traverseInOrder() {
        inOrderHelper(root);
    }

    public void inOrderHelper(TreeNode<T> node) {
        if (node == null) return;
        inOrderHelper(node.getLeft());
        System.out.print(node.getData().toString() + " ");
        inOrderHelper(node.getRight());
    }

    public void clearHasVisited() { clearHasVisited(root); }

    private void clearHasVisited(TreeNode<T> node) {
        if (node == null) return;
        node.setHasVisited(false);
        clearHasVisited(node.getLeft());
        clearHasVisited(node.getRight());
    }

    public int compareTo(Tree<T> other) {
        return this.getWeight() - other.getWeight();
    }

    // TODO: 10/30/2021 @Enrico 
    public int computeWeight() {
        return -1;
    }
}
