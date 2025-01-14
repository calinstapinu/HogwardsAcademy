import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Map<String, Integer> housePoints = new HashMap<>();

        // Citire din fisier
        try (BufferedReader br = new BufferedReader(new FileReader("punkte.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("&");
                if (parts.length == 5) {
                    String house = parts[2];
                    int points = Integer.parseInt(parts[4]);
                    housePoints.put(house, housePoints.getOrDefault(house, 0) + points);
                }
            }
        } catch (IOException e) {
            System.err.println("Eroare la citirea fisierului: " + e.getMessage());
        }

        // Afisarea studentilor pe baza unei litere introduse de utilizator
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduceti litera pentru a afisa studentii: ");
        String letter = scanner.nextLine().toUpperCase();
        displayStudentsStartingWithLetter(letter);

        // Afisarea studentilor din Gryffindor in ordine alfabetica
        displayGryffindorStudents();

        // Scrierea rezultatului in ergebnis.txt
        writeHouseResultsToFile(housePoints);
    }

    private static void displayStudentsStartingWithLetter(String letter) {
        try (BufferedReader br = new BufferedReader(new FileReader("punkte.txt"))) {
            System.out.println("Studentii care incep cu litera '" + letter + "':");
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("&");
                if (parts.length > 1 && parts[1].toUpperCase().startsWith(letter)) {
                    System.out.println(parts[1]);
                }
            }
        } catch (IOException e) {
            System.err.println("Eroare la citirea fisierului: " + e.getMessage());
        }
    }

    private static void displayGryffindorStudents() {
        List<String> gryffindorStudents = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("punkte.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("&");
                if (parts[2].equalsIgnoreCase("Gryffindor")) {
                    gryffindorStudents.add(parts[1]);
                }
            }
        } catch (IOException e) {
            System.err.println("Eroare la citirea fisierului: " + e.getMessage());
        }

        Collections.sort(gryffindorStudents);
        System.out.println("Studentii din Gryffindor in ordine alfabetica:");
        for (String student : gryffindorStudents) {
            System.out.println(student);
        }
    }

    private static void writeHouseResultsToFile(Map<String, Integer> housePoints) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("ergebnis.txt"))) {
            housePoints.entrySet().stream()
                    .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                    .forEach(entry -> {
                        try {
                            bw.write(entry.getKey() + "#" + entry.getValue());
                            bw.newLine();
                        } catch (IOException e) {
                            System.err.println("Eroare la scrierea in fisier: " + e.getMessage());
                        }
                    });
            System.out.println("Rezultatele au fost scrise in ergebnis.txt.");
        } catch (IOException e) {
            System.err.println("Eroare la scrierea fisierului: " + e.getMessage());
        }
    }
}
