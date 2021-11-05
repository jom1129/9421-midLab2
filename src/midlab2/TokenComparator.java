package midlab2;

import java.util.Comparator;

public class TokenComparator implements Comparator<Token<String>> {
    @Override
    public int compare(Token<String> o1, Token<String> o2) {
        return o1.getData().compareTo(o2.getData());
    }
}
