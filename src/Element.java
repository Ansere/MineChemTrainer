import java.util.stream.Collectors;

public class Element {

    private final String symbol;
    private final int num;

    public Element(String symbol, int num){
        this.symbol = symbol;
        this.num = num;
    }

    public Element(String symbol){
        this(symbol, 1);
    }

    public String getSymbol() {
        return symbol;
    }

    public int getNum(){
        return num;
    }

    public String toString(){
        if (num == 1)
            return symbol;
        else
            return symbol + Integer.toString(num).chars().mapToObj(c ->  Character.toString('\u2080' + (c - '0'))).collect(Collectors.joining());
    }
}
