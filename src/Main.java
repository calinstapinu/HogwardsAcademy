import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        // Citire fisier
        List<String> liniePuncte = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("punkte.txt"))) {
            String linie;
            while ((linie = br.readLine()) != null) {
                liniePuncte.add(linie);
            }
        } catch (IOException e) {
            System.out.println("Eroare la citirea fisierului!");
        }

        // Afisare studenti dupa litera
        Scanner scanner = new Scanner(System.in);

        System.out.print("Introduceti o litera: ");
        String litera = scanner.nextLine().toUpperCase(); // toLowerCase()

        System.out.println("Studentii care incep cu litera '" + litera + "':");
        for (String linie : liniePuncte) {
            String[] parti = linie.split("&");
            if (parti[1].toUpperCase().startsWith(litera)) {
                System.out.println(parti[1]);
            }
        }

        // Afisare studenti Gryffindor sortati
        List<String> studentiGryffindor = new ArrayList<>();
        for (String linie : liniePuncte) {
            String[] parti = linie.split("&");
            if (parti[2].equals("Gryffindor")) {
                studentiGryffindor.add(parti[1]);
            }
        }
        Collections.sort(studentiGryffindor);
        System.out.println("\nStudentii din Gryffindor sortati:");
        for (String student : studentiGryffindor) {
            System.out.println(student);
        }

        // Calcul puncte pe case
        Map<String, Integer> puncteCase = new HashMap<>();
        for (String linie : liniePuncte) {
            String[] parti = linie.split("&");
            String casa = parti[2];
            int puncte = Integer.parseInt(parti[4]);

            if (puncteCase.containsKey(casa)) {
                puncteCase.put(casa, puncteCase.get(casa) + puncte);
            } else {
                puncteCase.put(casa, puncte);
            }

        }

        // Scriere fisier rezultat
//        try (BufferedWriter bw = new BufferedWriter(new FileWriter("ergebnis.txt"))) {
//            puncteCase.entrySet().stream()
//                    .sorted((e1, e2) -> e2.getValue() - e1.getValue())
//                    .forEach(entry -> {
//                        try {
//                            bw.write(entry.getKey() + "#" + entry.getValue());
//                            bw.newLine();
//                        } catch (IOException e) {
//                            System.out.println("Eroare la scrierea fisierului!");
//                        }
//                    });
//        } catch (IOException e) {
//            System.out.println("Eroare la scrierea fisierului rezultat!");
//        }


        try (BufferedWriter bw = new BufferedWriter(new FileWriter("ergebnis.txt"))) {
            List<Map.Entry<String, Integer>> listaPuncte = new ArrayList<>(puncteCase.entrySet());

            // Sortare lista in ordine descrescatoare
//            listaPuncte.sort((e1, e2) -> e2.getValue() - e1.getValue());

            // Sortare folosind un Comparator clasic
            listaPuncte.sort(new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
                    return e2.getValue() - e1.getValue(); // Sortare descrescatoare dupa valoare
                }
            });

            // Scriere in fisier
            for (Map.Entry<String, Integer> entry : listaPuncte) {
                bw.write(entry.getKey() + "#" + entry.getValue());
                bw.newLine();
            }

            System.out.println("Rezultatul a fost scris in fisierul 'ergebnis.txt'.");
        } catch (IOException e) {
            System.out.println("Eroare la scrierea fisierului rezultat!");
        }



        System.out.println("\nRezultatul a fost scris in fisierul 'ergebnis.txt'.");
    }
}
