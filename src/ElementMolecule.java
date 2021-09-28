import java.util.ArrayList;
import java.util.stream.Collectors;

public class ElementMolecule {

    private final ArrayList<ElementHolder> elements;
    private final int num;

    public ElementMolecule(int number){
        elements = new ArrayList<>();
        num = number;
    }

    public ElementMolecule(Element e){
        elements = new ArrayList<>();
        addElement(e);
        num = 1;
    }

    public ElementMolecule(Element e, int num){
        elements = new ArrayList<>();
        addElement(e);
        this.num = num;
    }

    public int getNum(){
        return num;
    }

    public String getSymbol(){
        StringBuilder s = new StringBuilder();
        for (ElementHolder eh : elements)
            s.append(eh);
        return "(" + s + ")";
    }

    public void addElement(Element e){
        elements.add(new ElementHolder(e));
    }

    public void addElementMolecule(ElementMolecule em){
        elements.add(new ElementHolder(em));
    }

    public String toString(){
        return getSymbol() + (num > 1 ? Integer.toString(num).chars().mapToObj(c ->  Character.toString('\u2080' + (c - '0'))).collect(Collectors.joining()) : "");
    }

}
