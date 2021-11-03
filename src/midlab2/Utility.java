package midlab2;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Utility {
    LinkedStack<Integer> treeStack = new LinkedStack<>();
    // TODO: 10/30/2021 @Enrico 
    /*
        Concatenates the inputted text into a single
        StringBuilder variable
     */
    public String acceptInput() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter text");

        StringBuilder string  = new StringBuilder(scan.nextLine());
        string.append(scan.nextLine());
        return string.toString();
    }

    /* TODO: 10/30/2021 @CJ
       Determines the frequency of each character
       and sets the appropriate value for
       frequency in the Token class
       Use downcasting when creating a List instance:
       List<Token<T>> list = new ArrayList<>();
     */

    public List<Token<String>> determineFrequency(String userText) {
        List<Character> characters = new ArrayList<>(); // characters that are
                                                        // already detected
        List<Token<String>> tokens = new ArrayList<>();
        for(int i = 0; i < userText.length(); i++) {

            if(!characters.contains(userText.charAt(i)) || tokens.isEmpty()) {
                characters.add(userText.charAt(i));
                tokens.add(new Token<>(Character.toString(userText.charAt(i))));
            } else {
                int finalI = i;
                int matchingIndex = IntStream.range(0, tokens.size())
                                .filter(e -> tokens.get(e).getData()
                                        .equals(Character.toString(userText.charAt(finalI))))
                                        .findFirst()
                                                .orElse(-1);
                tokens.get(matchingIndex).incrementFrequency();
            }
        }
        return tokens;
}

/*
    // TODO: 10/30/2021 @Jerome, Kurt
   public <T> List<Token<T>> huffmanToText(List<Token<T>> tokenList)
            throws ArgumentMismatchException {

        String temp = "";
        int condition = 0;
        Token<T> leastB = new Token<>();
        Token<T> tempB = new Token<>(null, 0, "");
        StringBuilder input = acceptInput();
        StringBuilder code = new StringBuilder();
        List<String> charList = Arrays.asList(temp.split(","));
        List<String> binaryEquivalentList = Arrays.asList(temp.split(","));
        List<Token<T>> output = new ArrayList<>();
        List<Token<T>> equalB = new ArrayList<>();


        List<Integer> frequencyList = Arrays.stream(temp.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        // Exception block
        if (charList.size() != frequencyList.size()) {
            if (charList.size() < frequencyList.size())
                throw new ArgumentMismatchException("Too many inputted frequencies.");
            else throw new ArgumentMismatchException("Too many inputted tokens.");
        }


        while (input.length() != 0) {
            condition = 0;
            
            // if bits of token is greater than tempB
            // saves the least number of tokens to leastB
            for (Token<T> token : tokenList) {
                if (token.getNumberofBits() > tempB.getNumberofBits()) {
                    if (leastB.getData() == null) {
                        leastB = token;
                    }
                    if ((leastB.getNumberofBits() > token.getNumberofBits())) {
                        leastB = token;
                    }
                }
            }
        
            // saves all tokens that have equal bits to List<Token<T>> equalB
            for (Token<T> token : tokenList){
                if (leastB.getNumberofBits() == token.getNumberofBits()){
                    equalB.add(token);
                }
            }

            // Gets the code of the input based on the least number of bits and saves it to StringBuilder code
            for (int i = 0; i < leastB.getNumberofBits(); i++) {
                code.append(input.charAt(i));
            }

            // Checks if the huffman code of the tokens of equalB if it is equal to the StringBuilder code
            // if the huffman code of the token is equal to the StringBuilder code
            //      add the token to List<Token<T>> output
            //      delete the code in the input
            for (Token<T> token : equalB) {
                if (token.getHuffmanCode().equals(code.toString())) {
                    output.add(token);
                    input.delete(0, leastB.getNumberofBits());
                    leastB = new Token<>();
                    tempB = new Token<>();
                    condition = 1;
                }
            }
            // if there is no equal huffman code 
            // save leastB to tempB (to get the next least bit)
            if (condition == 0){
                tempB = leastB;
                leastB = new Token<>();
            }
            //resets equalB
            equalB = new ArrayList<>();
        }
        return output;
    }

 */

    /* TODO: 10/30/2021 @Jerome
       Sets up the Forest of Trees
       Similar to that of page 22
     */
     public <T> Tree<String> forestBuilder(List<Token<T>> tokens) {
        TreeNode<String> tree;
        TreeNode<String> tree2 = null;
        TreeNode<String> temp;
        TreeNode<String> leftChild;
        TreeNode<String> rightChild;

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
                if (least.compareTo(token) > 0 ) {
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
                if (least2.compareTo(token) >= 0) {
                    if (token.getData() != least.getData()){
                        least2 = token;
                    }
                }
            }
        }

        // initializing 1st tree
        // tree = new TreeNode<>((least.getFrequency() + least2.getFrequency()));
        tree = new TreeNode<>(null);
        tree.setNodeWeight((least.getFrequency() + least2.getFrequency()));
        leftChild = new TreeNode<>(least.getData().toString());
        leftChild.setNodeWeight(least.getFrequency());
        rightChild = new TreeNode<>(least2.getData().toString());
        rightChild.setNodeWeight(least2.getFrequency());
        tree.setLeft(leftChild);
        tree.setRight(rightChild);


        while (condition != 1) {
            c = 0;

            // Saves the next least element to variable least
            least = new Token<>();
            for (Token<T> token : tokens) {
                //if (token.getFrequency() > least2.getFrequency())
                if (token.compareTo(least2) > 0) {
                    if (least.getData() == null) {
                        least = token;
                    } else {
                        //(least.getFrequency() > token.getFrequency())
                        if (least.compareTo(token) > 0) {
                            least = token;
                        }
                    }
                }
            }

            // if next element is greater than the value of the root
            // sets the element as the right child of the tree
            // if (tree.getNodeWeight() < least.getFrequency())
            if (tree.compareTo(least.getFrequency()) < 0) {
                //temp = new TreeNode<>((tree.getData() + least.getFrequency()));
                temp = new TreeNode<>(null);
                temp.setNodeWeight((tree.getNodeWeight() + least.getFrequency()));
                rightChild = new TreeNode<>(least.getData().toString());
                temp.setLeft(tree);
                temp.setRight(rightChild);
                tree = temp;
                least2 = least;
                for (Token<T> token : tokens){
                    //if (token.getFrequency() > least2.getFrequency())
                    if (token.compareTo(least2) > 0){
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
                // if (tree2.getNodeWeight() < least.getFrequency())
                if (tree2.compareTo(least.getFrequency()) < 0){
                    //temp = new TreeNode<>((tree2.getData() + least.getFrequency()));
                    temp = new TreeNode<>(null);
                    temp.setNodeWeight((tree2.getNodeWeight() + least.getFrequency()));
                    rightChild = new TreeNode<>(least.getData().toString());
                    temp.setLeft(tree2);
                    temp.setRight(rightChild);
                    tree2 = temp;
                    least2 = least;
                    for (Token<T> token : tokens){
                        // if (token.getFrequency() > least2.getFrequency())
                        if (token.compareTo(least2) > 0){
                            c++;
                        }
                    }
                    if (c==0){
                        condition = 1;
                    }
                    // if all elements are used
                    // sets the parent of tree and tree2 to temp and save temp to tree
                    if (condition == 1){
                        //temp = new TreeNode<>((tree.getData() + tree2.getData()));
                        temp = new TreeNode<>(null);
                        temp.setNodeWeight((tree.getNodeWeight()) + tree2.getNodeWeight());
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
                    // if (token.getFrequency() > least.getFrequency())
                    if (token.compareTo(least) > 0) {
                        if (least2.getData() == null) {
                            least2 = token;
                        } else {
                            // if (least2.getFrequency() > token.getFrequency())
                            if (least2.compareTo(token) > 0) {
                                least2 = token;
                            }
                        }
                    }
                }
                //tree2 = new TreeNode<>((least.getFrequency() + least2.getFrequency()));
                tree2 = new TreeNode<>(null);
                tree2.setNodeWeight((least.getFrequency() + least2.getFrequency()));
                leftChild = new TreeNode<>(least.getData().toString());
                leftChild.setNodeWeight(least.getFrequency());
                rightChild = new TreeNode<>(least2.getData().toString());
                rightChild.setNodeWeight(least2.getFrequency());
                tree2.setLeft(leftChild);
                tree2.setRight(rightChild);
                for (Token<T> token : tokens){
                    // if (token.getFrequency() > least2.getFrequency())
                    if (token.compareTo(least2) > 0){
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
