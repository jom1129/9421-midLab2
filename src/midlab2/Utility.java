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
    public <T> void setHuffmanCode(Tree<T> forest) {

    }

    private <T> void setHuffmanCode(TreeNode<T> node, T element, int stackElement) {
        treeStack.push(stackElement);
        node.setHasVisited(true);

        if (node.isLeaf()) {
            if (!node.getData().equals(element))
                treeStack.pop();
            return;
        }

        if (node.getData().equals(element)) {
            return;
        }

        if (!node.isLeaf() && node.getLeft().hasVisited() &&
            node.getRight().hasVisited() && !node.getData().equals(element)) {
            treeStack.pop();
        }

        setHuffmanCode(node.getLeft(), element, 0);
        setHuffmanCode(node.getRight(), element, 1);
    }



    public <T> void showHuffmanTable(List<Token<T>> tokenList) {
        for (var tok : tokenList) System.out.print(tok.toString());
    }
}
