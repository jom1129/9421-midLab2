package midlab2;

import java.util.List;

public class Utility {
    // TODO: 10/30/2021 @Enrico 
    public StringBuilder acceptInput() {
        return null;
    }

    // TODO: 10/30/2021 @CJ
    // Use downcasting when creating a List instance
    // List<Token<T>> list = new ArrayList<>();
    public <T> List<Token<T>> determineFrequency(StringBuilder userText) {
        return null;
    }

    // TODO: 10/30/2021 @Jerome, Kurt
    public <T> List<Token<T>> huffmanToText() {
        return null;
    }

    // TODO: 10/30/2021 @Jerome 
    public <T> Tree<T> forestBuilder() {
        return null;
    }

    // TODO: 10/30/2021 @Kurt 
    public <T> void setHuffmanCode(Tree<T> forest) {

    }

    public <T> void showHuffmanTable(List<Token<T>> tokenList) {
        for (var tok : tokenList) System.out.print(tok.toString());
    }
}
