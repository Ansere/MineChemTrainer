import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ElementGroup {
    private final int num;
    private final ArrayList<ElementHolder> elements;

    public ElementGroup(int n, ElementMolecule... em){
        num = n;
        elements = Arrays.stream(em).map(ElementHolder::new).collect(Collectors.toCollection(ArrayList::new));
    }
    public ElementGroup(ElementMolecule... em){
        this(1, em);
    }

    public ElementGroup(int num){
        this.num = num;
        elements = new ArrayList<>();
    }

    public ElementGroup(Element e) {
        this(1);
        addElement(e);
    }

    public void addElementMolecule(ElementMolecule em){
        elements.add(new ElementHolder(em));
    }
    public void addElement(Element e){
        elements.add(new ElementHolder(e));
    }

    public int getNum(){
        return num;
    }
    public ElementHolder getRandomSymbol(){
        return elements.get((int)(Math.random() * elements.size()));
    }

    public String toString(){
        return elements.toString() + ": " + num;
    }

}


