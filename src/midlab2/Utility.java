package midlab2;

import java.util.List;

public class Utility {
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

    public <T> void showHuffmanTable(List<Token<T>> tokenList) {
        for (var tok : tokenList) System.out.print(tok.toString());
    }
}
