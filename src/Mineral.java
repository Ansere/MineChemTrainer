import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.StringJoiner;

public class Mineral {

    private final ArrayList<ElementGroup> formula;
    private Hydrate hydrate;
    private ArrayList<String> names;

    public Mineral(String name, Hydrate hydrate, ElementGroup... eg) {
        this.formula = new ArrayList<>(Arrays.asList(eg));
        this.hydrate = hydrate;
        this.names = new ArrayList<>();
        names.add(name);
    }

    public Mineral(ArrayList<String> names){
        this.names = names;
        this.formula = new ArrayList<>();
        this.hydrate = null;
    }

    public Mineral(String name){
        this.names = new ArrayList<>();
        names.add(name);
        this.formula = new ArrayList<>();
        this.hydrate = null;
    }

    public void addElementGroup(ElementGroup... eg){
        formula.addAll(Arrays.asList(eg));
    }

    public void setHydrate(Hydrate hydrate){
        this.hydrate = hydrate;
    }

    public String getRandomFormula(){
        LinkedHashMap<String, Integer> elementGroups = new LinkedHashMap<>();
        StringBuilder s = new StringBuilder();
        for (ElementGroup eg : formula){
            ElementHolder eh = eg.getRandomSymbol();
            String symbol = eh.getSymbol();
            elementGroups.computeIfPresent(symbol, (K, V) -> V + eh.getNum() * eg.getNum());
            elementGroups.putIfAbsent(symbol, eh.getNum() * eg.getNum());
        }
        for (String key : elementGroups.keySet()) {
            s.append(key).append(elementGroups.get(key) > 1 ? ElementGroupUtils.generateSubscript(elementGroups.get(key)) : "");
        }
        if (hydrate != null)
            s.append("·").append(hydrate.getNum()).append(hydrate.getSymbol());
        return s.toString();
    }

    public String toString(){
        StringBuilder s = new StringBuilder(getFormattedNames() + ": ");
        for (ElementGroup eg : formula){
            s.append("\t").append(eg.toString());
        }
        return s.toString();
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public String getFormattedNames(){
        StringJoiner s = new StringJoiner("/");
        for (String name : names){
            s.add(name);
        }
        return s.toString();
    }
}
