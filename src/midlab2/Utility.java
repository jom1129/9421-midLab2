package midlab2;

import java.util.List;

public class Utility {
    LinkedStack<Integer> treeStack = new LinkedStack<>();
    // TODO: 10/30/2021 @Enrico 
    /*
        Concatenates the inputted text into a single
        StringBuilder variable
     */
    public StringBuilder acceptInput() {
        return null;
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
    public <T> List<Token<T>> huffmanToText() {
        return null;
    }

    /* TODO: 10/30/2021 @Jerome
       Sets up the Forest of Trees
       Similar to that of page 22
     */
    public <T> Tree<T> forestBuilder() {
        return null;
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
            toInt.append(stack.pop());
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
