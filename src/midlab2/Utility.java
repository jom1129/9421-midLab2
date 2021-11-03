package midlab2;

import java.util.*;
import java.util.stream.Collectors;

public class Utility {
    LinkedStack<Integer> treeStack = new LinkedStack<>();
    // TODO: 10/30/2021 @Enrico 
    /*
        Concatenates the inputted text into a single
        StringBuilder variable
     */
    public StringBuilder acceptInput() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter text");

        StringBuilder string  = new StringBuilder(scan.nextLine());
        string.append(scan.nextLine());
        return string;
    }

    /* TODO: 10/30/2021 @CJ
       Determines the frequency of each character
       and sets the appropriate value for
       frequency in the Token class
       Use downcasting when creating a List instance:
       List<Token<T>> list = new ArrayList<>();
     */

    public <T> List<Token<T>> determineFrequency(StringBuilder userText) {
        return null;
    }

    // TODO: 10/30/2021 @Jerome, Kurt
    public <T> List<Token<T>> huffmanToText(List<Token<T>> tokenList)
            throws ArgumentMismatchException {
        String temp = "";
        List<String> charList = Arrays.asList(temp.split(","));
        List<String> frequencyList = Arrays.asList(temp.split(","));

        /*
        List<Integer> frequencyList = Arrays.stream(temp.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

         */

        // Exception block
        if (charList.size() != frequencyList.size()) {
            if (charList.size() < frequencyList.size())
            throw new ArgumentMismatchException("Too many inputted frequencies.");
        else throw new ArgumentMismatchException("Too many inputted tokens.");
        }

        return null;
    }

    /* TODO: 10/30/2021 @Jerome
       Sets up the Forest of Trees
       Similar to that of page 22
     */
     public <T> Tree<Integer> forestBuilder() {
        TreeNode<Integer> tree;
        TreeNode<Integer> tree2 = null;
        TreeNode<Integer> temp;
        TreeNode<Integer> leftChild;
        TreeNode<Integer> rightChild;
        StringBuilder text = acceptInput();
        List<Token<T>> tokens = determineFrequency(text);
        int condition = 0;
        int c;
        Token<T> least = new Token<>();
        Token<T> least2 = new Token<>();

        // Saves the least element to variable least
        for (Token<T> token : tokens){
            if (least.getData() == null){
                least = token;
            }
            else {
                if (least.getFrequency() > token.getFrequency()) {
                    least = token;
                }
            }
        }

        // Saves the 2nd least element to variable least2
        for (Token<T> token : tokens){
            if (least2.getData() == null){
                least2 = token;
            }
            else {
                if (least2.getFrequency() >= token.getFrequency()) {
                    if (token.getData() != least.getData()){
                        least2 = token;
                    }
                }
            }
        }

        // initializing 1st tree
        tree = new TreeNode<>((least.getFrequency() + least2.getFrequency()));
        leftChild = new TreeNode<>(least.getFrequency());
        rightChild = new TreeNode<>(least2.getFrequency());
        tree.setLeft(leftChild);
        tree.setRight(rightChild);


        while (condition != 1) {
            c = 0;

            // Saves the next least element to variable least
            least = new Token<>();
            for (Token<T> token : tokens) {
                if (token.getFrequency() > least2.getFrequency()) {
                    if (least.getData() == null) {
                        least = token;
                    } else {
                        if (least.getFrequency() > token.getFrequency()) {
                            least = token;
                        }
                    }
                }
            }

            // if next element is greater than the value of the root
            // sets the element as the right child of the tree
            if (tree.getData() < least.getFrequency()) {
                temp = new TreeNode<>((tree.getData() + least.getFrequency()));
                rightChild = new TreeNode<>(least.getFrequency());
                temp.setLeft(tree);
                temp.setRight(rightChild);
                tree = temp;
                least2 = least;
                for (Token<T> token : tokens){
                    if (token.getFrequency() > least2.getFrequency()){
                        c++;
                    }
                }
                if (c==0){
                    condition = 1;
                }
            }

            // if tree2 is not null
            // sets the element as the right child of tree2
            else if (tree2 != null){
                if (tree2.getData() < least.getFrequency()){
                    temp = new TreeNode<>((tree2.getData() + least.getFrequency()));
                    rightChild = new TreeNode<>(least.getFrequency());
                    temp.setLeft(tree2);
                    temp.setRight(rightChild);
                    tree2 = temp;
                    least2 = least;
                    for (Token<T> token : tokens){
                        if (token.getFrequency() > least2.getFrequency()){
                            c++;
                        }
                    }
                    if (c==0){
                        condition = 1;
                    }
                    // if all elements are used
                    // sets the parent of tree and tree2 to temp and save temp to tree
                    if (condition == 1){
                        temp = new TreeNode<>((tree.getData() + tree2.getData()));
                        temp.setLeft(tree);
                        temp.setRight(tree2);
                        tree = temp;
                    }
                }
            }

            // if next element is lesser than the value of the root
            // Saves the next least element to variable least2
            // initializes tree2
            else {
                least2 = new Token<>();
                for (Token<T> token : tokens) {
                    if (token.getFrequency() > least.getFrequency()) {
                        if (least2.getData() == null) {
                            least2 = token;
                        } else {
                            if (least2.getFrequency() > token.getFrequency()) {
                                least2 = token;
                            }
                        }
                    }
                }
                tree2 = new TreeNode<>((least.getFrequency() + least2.getFrequency()));
                leftChild = new TreeNode<>(least.getFrequency());
                rightChild = new TreeNode<>(least2.getFrequency());
                tree2.setLeft(leftChild);
                tree2.setRight(rightChild);
                for (Token<T> token : tokens){
                    if (token.getFrequency() > least2.getFrequency()){
                        c++;
                    }
                }
                if (c==0){
                    condition = 1;
                }


            }
        }


        return new Tree<>(tree);
    }

    // TODO: 10/30/2021 @Kurt
    // To be used with forestBuilder
    public <T> void setHuffmanCode(Tree<T> forest, List<Token<T>> forestList) {
        for(Token<T> node : forestList) {
            setHuffmanCode(forest.getRoot(), node.getData());
            node.setHuffmanCode(stackToString(treeStack));
            // Empty the stack
            treeStack.clear();
            // Clear hasVisited property
            forest.clearHasVisited();
        }

    }

    private <T> void setHuffmanCode(TreeNode<T> node, T element) {
        setHuffmanCode(node, element, -1);
    }

    private <T> void setHuffmanCode(TreeNode<T> node, T element, int stackElement) {
        treeStack.push(stackElement);
        node.setHasVisited(true);

        if (node.isLeaf()) {
            if (!node.getData().equals(element))
                treeStack.pop();
            return;
        }

        if (node.getData().equals(element)) return;

        if (!node.isLeaf() && node.getLeft().hasVisited() &&
            node.getRight().hasVisited() && !node.getData().equals(element)) {
            treeStack.pop();
        }

        setHuffmanCode(node.getLeft(), element, 0);
        setHuffmanCode(node.getRight(), element, 1);
    }

    private String stackToString(LinkedStack<Integer> stack) {
        StringBuilder toInt = new StringBuilder();
        while (!stack.isEmpty())
            if (stack.peek() != -1) toInt.append(stack.pop());
        return toInt.toString();
    }

    private void reverseStack() {
        if (!treeStack.isEmpty()) {
            Integer temp = treeStack.peek();
            treeStack.pop();
            reverseStack();

            reverseStack(temp);
        }

    }

    private void reverseStack(Integer x) {
        if (treeStack.isEmpty()) treeStack.push(x);
        else {
            Integer temp = treeStack.peek();
            treeStack.pop();
            reverseStack(temp);
        }
    }



    public <T> void showHuffmanTable(List<Token<T>> tokenList) {
        for (var tok : tokenList) System.out.print(tok.toString());
    }
}
