package midlab2;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class MyComparator implements Comparator<TreeNode> {
    public int compare(TreeNode x, TreeNode y)
    {

        return x.getNodeWeight() - y.getNodeWeight();
    }
}
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

    // TODO: 10/30/2021 @Jerome, Kurt
   public <T> List<Token<T>> huffmanToText(String string, List<Token<T>> tokenList)
            throws ArgumentMismatchException {

        String temp = string;
        int condition = 0;
        Token<T> leastB = new Token<>();
        Token<T> tempB = new Token<>(null, 0, "");
        StringBuilder input = new StringBuilder(string);
        StringBuilder code = new StringBuilder();
        List<String> charList = Arrays.asList(temp.split(","));
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


    /* TODO: 10/30/2021 @Jerome
       Sets up the Forest of Trees
       Similar to that of page 22
     */
     public <T> Tree<String> forestBuilder(List<Token<T>> tokens) {

        int n = tokens.size();
        Collections.sort(tokens);

        PriorityQueue<TreeNode> q = new PriorityQueue<TreeNode>(n, new MyComparator());

         for (int i = 0; i < n; i++) {

             // creating a Huffman node object
             // and add it to the priority queue.
             TreeNode tn = new TreeNode(tokens.get(i).getData());

             tn.setNodeWeight(tokens.get(i).getFrequency());

             tn.setLeft(null);
             tn.setRight(null);

             // add functions adds
             // the huffman node to the queue.
             q.add(tn);
         }

         // create a root node
         TreeNode root = null;

         // Here we will extract the two minimum value
         // from the heap each time until
         // its size reduces to 1, extract until
         // all the nodes are extracted.
         while (q.size() > 1) {

             // first min extract.
             TreeNode x = q.peek();
             q.poll();

             // second min extract.
             TreeNode y = q.peek();
             q.poll();

             // new node f which is equal
             TreeNode f = new TreeNode(x.getNodeWeight() + y.getNodeWeight());

             f.setData('-');

             // first extracted node as left child.
             f.setLeft(x);

             // second extracted node as the right child.
             f.setRight(y);

             // marking the f node as the root node.
             root = f;

             // add this node to the priority-queue.
             q.add(f);
         }

        return new Tree<>(root);
    }

    // TODO: 10/30/2021 @Kurt
    // To be used with forestBuilder
    public <T> void setHuffmanCode(Tree<T> forest, List<Token<T>> forestList) {
        for(Token<T> node : forestList) {
            setHuffmanCode(forest.getRoot(), node.getData());
            var temp = stackToString(treeStack);
            node.setHuffmanCode(temp);
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
        if (stackElement != -1) treeStack.push(stackElement);

        node.setHasVisited(true);

        if (node.isLeaf()) {
            if (!node.getData().equals(element))
                treeStack.pop();
            return;
        }

        //if(!treeStack.isEmpty() && node.getData() != element) treeStack.pop();

        if (node.getData() != null && node.getData().equals(element)) {
            return;
        }


        if (!node.isLeaf() && node.getLeft().hasVisited() && node.getRight().hasVisited() && !node.getLeft().getData().equals(element) && !node.getRight().getData().equals(element)) {
            treeStack.pop();
        }

        setHuffmanCode(node.getLeft(), element, 0);
        setHuffmanCode(node.getRight(), element, 1);
    }

    private String stackToString(LinkedStack<Integer> stack) {
        StringBuilder toInt = new StringBuilder();
        while (!stack.isEmpty() && stack.getTop().getLink() != null && stack.getTop().getInfo() != -1)
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

    public List<Token<String>> parseTokenList (String charText, String bitText) {
        List<String> characterTemp;
        List<String> binTemp;
        List<Token<String>> tokens = new ArrayList<>();

        // Split by comma, strip whitespaces
        characterTemp = Arrays.asList(charText.split("\\s*,\\s*"));
        binTemp = Arrays.asList(bitText.split("\\s*,\\s*"));

        // Eliminate Duplicates
        List<String> characters = characterTemp.stream()
                .distinct()
                .collect(Collectors.toList());
        List<String> binList = binTemp;


        if (characters.size() != binList.size()) {
            throw new ArgumentMismatchException("Inputted parameters are not equal in length.");
        }

        for (int i = 0; i < characters.size(); i++) {
            tokens.add(new Token<>(characters.get(i)));
            tokens.get(i).setNumberOfBits(Integer.parseInt(binTemp.get(i)));
        }
        return tokens;
    }

    public <T> void showHuffmanTable(List<Token<T>> tokenList) {
        for (var tok : tokenList) System.out.println(tok.getData() + " " + tok.getHuffmanCode());
    }
}
