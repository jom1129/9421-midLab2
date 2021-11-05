package midlab2;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Comparator to be used for the PriorityQueue Structure
 */
class MyComparator implements Comparator<TreeNode> {
    public int compare(TreeNode x, TreeNode y)
    {
        return x.getNodeWeight() - y.getNodeWeight();
    }
}
public class Utility {

    /**
     * Creates a tokenList while setting the Data and frequency parameter
     * @param userText input to be parsed
     * @return a tokenList with the Data and Frequency parameter
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

public <T> List<String> textToHuffman(String userInput, List<Token<T>> tokenList) {
        StringBuilder input = new StringBuilder(userInput);
        List<String> output = new ArrayList<>();
        char c;

        for (int i = 0; i < input.length(); i++) {
             c = input.charAt(i);
            for(Token<T> token : tokenList){
                if (token.getData().equals(c)){
                    output.add(token.getHuffmanCode());
                }
            }
        }
        return output;
    }

    // TODO: 10/30/2021 @Jerome, Kurt
   public <T> List<String> huffmanToText(String string, List<Token<T>> tokenList)
            throws ArgumentMismatchException {

        String temp = string;
        int condition = 0;
        Token<T> leastB = new Token<>();
        Token<T> tempB = new Token<>(null, 0, "");
        StringBuilder input = new StringBuilder(string);
        StringBuilder code = new StringBuilder();
        // List<String> charList = Arrays.asList(temp.split(","));
        List<String> output = new ArrayList<>();
        List<Token<T>> equalB = new ArrayList<>();


        while (input.length() != 0) {
            condition = 0;

            // if bits of token is greater than tempB
            // saves the least number of tokens to leastB
            for (Token<T> token : tokenList) {
                if (token.getNumberOfBits() > tempB.getNumberOfBits()) {
                    if (leastB.getData() == null) {
                        leastB = token;
                    }
                    if ((leastB.getNumberOfBits() > token.getNumberOfBits())) {
                        leastB = token;
                    }
                }
            }

            // saves all tokens that have equal bits to List<Token<T>> equalB
            for (Token<T> token : tokenList){
                if (leastB.getNumberOfBits() == token.getNumberOfBits()){
                    equalB.add(token);
                }
            }

            // Gets the code of the input based on the least number of bits and saves it to StringBuilder code
            for (int i = 0; i < leastB.getNumberOfBits(); i++) {
                code.append(input.charAt(i));
            }

            // Checks if the huffman code of the tokens of equalB if it is equal to the StringBuilder code
            // if the huffman code of the token is equal to the StringBuilder code
            //      add the token to List<Token<T>> output
            //      delete the code in the input
            for (Token<T> token : equalB) {
                if (token.getHuffmanCode().equals(code.toString())) {
                    output.add(token.getData().toString());
                    input.delete(0, leastB.getNumberOfBits());
                    leastB = new Token<>();
                    tempB = new Token<>(null, 0, "");
                    condition = 1;
                }
            }
            // if there is no equal huffman code
            // save leastB to tempB (to get the next least bit)
            if (condition == 0){
                tempB = leastB;
                leastB = new Token<>();
            }
            code = new StringBuilder();
            //resets equalB
            equalB = new ArrayList<>();
        }
        return output;
    }


    /**
     * Uses a minheap as a priority Queue
     * for constructing a forest
     * @param tokens contains a tokenList with
     * required parameters for the processing of a forest
     * @param <T> data type of the nodes within the forest
     * @return a structured forest using the minheap data structure
     */
     public <T> Tree<String> forestBuilder(List<Token<T>> tokens) {

        int n = tokens.size();
        Collections.sort(tokens);

        PriorityQueue<TreeNode> q = new PriorityQueue<>(n, new MyComparator());

         for (int i = 0; i < n; i++) {

             // Use the priority queue as a minheap structure
             //
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
             TreeNode f = new TreeNode("-");

             f.setNodeWeight(x.getNodeWeight() + y.getNodeWeight());

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
    public <T> void setHuffmanCode(Tree<T> forest, List<Token<T>> forestList) {
        StringBuilder bits = new StringBuilder();
        int max = 0;
        int c = 0;
        TreeNode<T> root = forest.getRoot();
        TreeNode<T> current = root;

        // Gets the maximum bits
        while(!current.isLeaf()){
            current = current.getLeft();
            max++;
        }
        current = root;

        while(!current.getLeft().hasVisited()) {
            if (max == 0) break;
            current = current.getLeft();
            if (current.isLeaf()) {
                for (Token<T> token : forestList) {
                    if (token.getData() == current.getData()) {
                        while (c < max) {
                            bits.append("0");
                            c++;
                        }
                        token.setHuffmanCode(bits.toString());
                        bits = new StringBuilder();
                        c = 0;
                        current.setHasVisited(true);
                    }
                }
                current = root;
                while (!current.getLeft().hasVisited()) {
                    current = current.getLeft();
                }
                current.setHasVisited(true);
                current = current.getRight();
                for (Token<T> token : forestList) {
                    if (token.getData() == current.getData()) {
                        while (c < max - 1) {
                            bits.append("0");
                            c++;
                        }
                        bits.append("1");
                        token.setHuffmanCode(bits.toString());
                        bits = new StringBuilder();
                        c = 0;
                        max = max - 2;
                    }
                }
                current = root;
            }
            if(current.getLeft().hasVisited()){
                current.setHasVisited(true);
                current = current.getRight();
                for (Token<T> token : forestList) {
                    if (token.getData() == current.getData()) {
                        while (c < max) {
                            bits.append("0");
                            c++;
                        }
                        bits.append("1");
                        token.setHuffmanCode(bits.toString());
                        bits = new StringBuilder();
                        c = 0;
                        max = max - 1;
                    }
                }
                current = root;
            }
        }

        current = root;
        current = current.getRight();
        max = 1;
        if (current.isLeaf()){
            for (Token<T> token : forestList) {
                if (token.getData() == current.getData()) {
                    bits.append("1");
                    token.setHuffmanCode(bits.toString());
                    bits = new StringBuilder();
                }
            }
        }
        else {
            while (!current.isLeaf()) {
                current = current.getLeft();
                max++;
            }
            current = root;
            current = current.getRight();
            while (!current.getLeft().hasVisited()) {
                if (max == 0) break;
                current = current.getLeft();
                if (current.isLeaf()) {
                    for (Token<T> token : forestList) {
                        if (token.getData() == current.getData()) {
                            bits.append("1");
                            while (c < max - 1) {
                                bits.append("0");
                                c++;
                            }
                            token.setHuffmanCode(bits.toString());
                            bits = new StringBuilder();
                            c = 0;
                            current.setHasVisited(true);
                        }
                    }
                    current = root;
                    current = current.getRight();
                    while (!current.getLeft().hasVisited()) {
                        current = current.getLeft();
                    }
                    current.setHasVisited(true);
                    current = current.getRight();
                    for (Token<T> token : forestList) {
                        if (token.getData() == current.getData()) {
                            bits.append("1");
                            while (c < max - 2) {
                                bits.append("0");
                                c++;
                            }
                            bits.append("1");
                            token.setHuffmanCode(bits.toString());
                            bits = new StringBuilder();
                            c = 0;
                            max = max - 2;
                        }
                    }
                    current = root;
                    current = current.getRight();
                    if (current.getLeft().hasVisited()) {
                        current.setHasVisited(true);
                        current = current.getRight();
                        for (Token<T> token : forestList) {
                            if (token.getData() == current.getData()) {
                                bits.append("1");
                                while (c < max - 1) {
                                    bits.append("0");
                                    c++;
                                }
                                bits.append("1");
                                token.setHuffmanCode(bits.toString());
                                bits = new StringBuilder();
                                c = 0;
                                max = max - 1;
                            }
                        }
                        current = root;
                    }
                }
            }
        }
    }

    public <T> void showHuffmanTable(List<Token<String>> tokenList) {
        // Sort tokens in their natural order
        tokenList.sort(new TokenComparator());
        System.out.printf("%-15s %-15s %-15s %-15s\n", "Character", "Frequency",
                "Huffman Code", "Number of Bits");
        for (var tok : tokenList) System.out.printf("%-15s %-15s %-15s %-15s\n", tok.getData(),
                tok.getFrequency(), tok.getHuffmanCode(), tok.getNumberOfBits());
    }

    public <T> String computePercentageSavings(List<Token<T>>tokenList) {
        int totalCharacters = 0;
        int huffmanBits = 0;
        int totalBits;
        double percentage;
        StringBuilder result = new StringBuilder();

        // computes for the total characters
        for (var token : tokenList) totalCharacters += token.getFrequency();

        totalBits = totalCharacters * 7;

        for (var token : tokenList) huffmanBits +=  token.getFrequency() * token.getNumberOfBits();
        percentage = ((totalBits - 0.0 - huffmanBits) / (totalBits * 1.0)) * 100;

        result.append("Percent Savings is: " + percentage + "%\n\n");
        result.append("""
                This computation does not take into count the size of the table.
                Actual savings values may be lesser because the huffman cipher table
                will still need to be transmitted.
                """);
        return result.toString();
    }

    public <T> String showHuffmanToTextOutput(List<String> list) {
        StringBuilder s = new StringBuilder();
        for (var tok : list) {
            s.append(tok);
        }
        return s.toString();
    }
}
