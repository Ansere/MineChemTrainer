import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class MineChemTrainer {

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Mineral> minerals = DataReader.loadMinerals();
        HashSet<Mineral> mineralsSet = new HashSet<>();
        Scanner sc = new Scanner(System.in);
        String guess = "";
        while (!guess.equalsIgnoreCase("back")) {
            if (mineralsSet.size() >= minerals.size()){
                mineralsSet.clear();
            }
            Mineral m = minerals.get((int) (Math.random() * minerals.size()));
            while (mineralsSet.contains(m)) {
                m = minerals.get((int) (Math.random() * minerals.size()));
            }
            mineralsSet.add(m);
            System.out.println(m.getRandomFormula());
            guess = sc.nextLine();
            if (guess.isBlank()){
                System.out.println("Incorrect, the correct answer was " + m.getFormattedNames());
                continue;
            }
            guess = Arrays.stream(guess.split(" ")).map(String::toLowerCase).map(w -> w.substring(0, 1).toUpperCase(Locale.ROOT) + w.substring(1)).collect(Collectors.joining());
            if (m.getNames().contains(guess)){
                System.out.println("Correct!");
            } else {
                System.out.println("Incorrect, the correct answer was " + m.getFormattedNames());
            }
        }
    }

}

