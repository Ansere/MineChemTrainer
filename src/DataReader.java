import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class DataReader {

    public static ArrayList<Mineral> loadMinerals() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("data\\minerals.txt"));
        ArrayList<Mineral> minerals = new ArrayList<>();
        while (sc.hasNext()){
            String mineralData = sc.nextLine();
            if (mineralData.startsWith("//")){
                break;
            }
            if (!mineralData.startsWith("#")){
                Mineral m = new Mineral(new ArrayList<>(List.of(mineralData.split(" - ")[0].split("/"))));
                String formulaString = mineralData.split(" - ")[1];
                boolean inParentheses = false;
                for (int i = 0; i < formulaString.length(); i++){
                    if ("()".contains(formulaString.charAt(i) + "")) {
                        inParentheses = !inParentheses;
                    }
                    if (i == formulaString.length() - 1 || "(·".contains(formulaString.charAt(i + 1) + "") && !inParentheses || Character.isUpperCase(formulaString.charAt(i + 1)) && !inParentheses){
                        String groupString = formulaString.substring(0, i + 1);
                        formulaString = formulaString.substring(i + 1);
                        if (groupString.contains(",")){
                            int j = groupString.indexOf(")") + 1;
                            int num = groupString.substring(j).isBlank() ? 1 : Integer.parseInt(groupString.substring(j));
                            String[] symbols = groupString.substring(0, j).replace(")", "").replace("(", "").split(",");
                            ElementGroup eg = new ElementGroup(num);
                            for (String s : symbols){
                                if (s.chars().mapToObj(Character::isUpperCase).filter(b -> b).count() == 1)
                                    eg.addElement(formatElementMolecule(s));
                                else
                                    eg.addElementMolecule(formatPolyatomicGroup("(" + s + ")1"));
                            }
                            m.addElementGroup(eg);
                            //m.addElementGroup(new ElementGroup(num, Arrays.stream(symbols).map(s -> formatPolyatomicGroup("(" + s + ")1")).toArray(ElementMolecule[]::new)));
                        } else if (!groupString.contains("(") && groupString.chars().mapToObj(Character::isUpperCase).filter(b -> b).count() == 1){
                            m.addElementGroup(new ElementGroup(formatElementMolecule(groupString)));
                        } else {
                            if (!Character.isDigit(groupString.charAt(groupString.length() - 1)))
                                groupString += 1;
                            m.addElementGroup(new ElementGroup(formatPolyatomicGroup(groupString)));
                        }
                        i = -1;
                    } else if ('·' == formulaString.charAt(i)){
                        m.setHydrate(formatHydrate(formulaString.substring(1)));
                        break;
                    }
                }
                minerals.add(m);
            }

        }
        return minerals;
    }

    public static ElementMolecule formatPolyatomicGroup(String s){
        ElementMolecule em = new ElementMolecule(Integer.parseInt(s.substring(s.length() - 1)));
        s = s.substring(1, s.length() - 2);
        int inParentheses = 0;
        for (int i = 0; i < s.length(); i++){
            if ("(".contains(s.substring(i, i + 1))){
                inParentheses++;
            } else if (")".contains(s.substring(i, i + 1))){
                inParentheses--;
            }
            if (i < s.length() - 1 && !((Character.isUpperCase(s.charAt(i + 1)) || (s.charAt(i + 1) == '(')) && inParentheses == 0)) {
               continue;
            }
            String elemMolecule = s.substring(0, i + 1);
            if (elemMolecule.startsWith("(")){
                em.addElementMolecule(formatPolyatomicGroup(elemMolecule));
            } else {
                int j = 0;
                while (j < elemMolecule.length() && !Character.isDigit(elemMolecule.charAt(j))) {
                    j++;
                }
                Element e = new Element(elemMolecule.substring(0, j), !Character.isDigit(elemMolecule.charAt(elemMolecule.length() - 1)) ? 1 : Integer.parseInt(elemMolecule.substring(j)));
                em.addElement(e);
            }
            s = s.substring(i + 1);
            i = -1;
        }
        return em;
    }

    public static Element formatElementMolecule(String s){
        boolean isMultiple = false;
        int i;
        for (i = 0; i < s.length(); i++){
            if (Character.isDigit(s.charAt(i))){
                isMultiple = true;
                break;
            }
        }
        return new Element(s.substring(0, i), isMultiple ? Integer.parseInt(s.substring(i)) : 1);
    }

    public static Hydrate formatHydrate(String s){
        boolean isMultiple = false;
        int i;
        s = s.substring(0, s.length() - 3);
        for (i = s.length() - 1; i > 0; i--){
            if (Character.isDigit(s.charAt(i - 1))){
                isMultiple = true;
                break;
            }
        }
        return new Hydrate(isMultiple ? Integer.parseInt(s.substring(0, i)) : s.startsWith("n") ? -1 : 1);
    }

}

