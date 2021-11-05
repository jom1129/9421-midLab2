package midlab2;

import java.util.Comparator;

public class Token<T> implements Comparable<Token<T>>{
    private T data;
    private String huffmanCode;
    private int frequency = 0;
    private int numberOfBits = 0;

    Token() {
        this(null);
    }

    Token(T data) {
        this(data, 1);
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
    
     public int getNumberOfBits(){
        int c=0;
        for(int i = 0; i < this.getHuffmanCode().length(); i++) {
            c++;
        }
        setNumberOfBits(c);
        return c;
    }

    public void setNumberOfBits(int numberOfBits) {
        this.numberOfBits = numberOfBits;
    }

    public void incrementFrequency() {
        frequency++;
    }

}
