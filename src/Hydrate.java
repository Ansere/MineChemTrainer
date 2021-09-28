public class Hydrate{

    private int num; // -1 is n

    public Hydrate (int num){
        this.num = num;
    }

    public String getNum() {
        if (num < 0)
            return "n";
        return num + "";
    }

    public String getSymbol(){
        return "H" + (char) ('\u2080' + ('2' - '0')) + "O";
    }

}
