package midlab2;

public class Token<T> implements Comparable<Token<T>> {
    private T data;
    private String huffmanCode;
    private int frequency;

    Token() {
        this(null);
    }

    Token(T data) {
        this(data, 0);
    }

    Token(T data, int frequency) {
        this(data, frequency, null);
    }

    Token(T data, int frequency, String huffmanCode) {
        this.data = data;
        this.frequency = frequency;
        this.huffmanCode = huffmanCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public int compareTo(Token<T> o) {
        return this.getFrequency() - o.getFrequency();
    }

    public void setHuffmanCode(String huffmanCode) {
        this.huffmanCode = huffmanCode;
    }

    public String getHuffmanCode() {
        return huffmanCode;
    }

}
