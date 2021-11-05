package midlab2;

import java.util.Comparator;
/*
    Alphabetizes the list of tokens based on the character
 */
public class TokenComparator implements Comparator<Token<String>> {
    @Override
    // getData returns the stored character in the Token instance
    public int compare(Token<String> o1, Token<String> o2) {
        return o1.getData().compareTo(o2.getData());
    }
}
