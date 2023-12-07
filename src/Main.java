import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Map<String, List<String>> data;

        try {
            data = readInputFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            writeOutputFile(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Чтение информации о выплаченных чеках
     *
     * @return Данные из файла в виде коллекции Map
     */
    private static Map<String, List<String>> readInputFile() throws FileNotFoundException {
        File file = new File("чеки.txt");
        Scanner scanner = new Scanner(file);
        Map<String, List<String>> map = new HashMap<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split("_");
            String month = parts[1].split("\\.")[0];

            if (!map.containsKey(month)) {
                map.put(month, new ArrayList<>());
            }
            map.get(month).add(line);
        }

        return map;
    }

    /**
     * Запись в папки информации о выплаченных чеках, а также вывод чеков,
     * которые не были оплачены в определенных месяцах
     *
     * @param map Данные из исходного файла в виде коллекции Map
     */
    private static void writeOutputFile(Map<String, List<String>> map) throws IOException {
        File outputFile = new File("чеки_по_папкам.txt");
        FileWriter writer = new FileWriter(outputFile);

        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            String month = entry.getKey();
            writer.write("/" + month + ":\n");

            for (String fileName : entry.getValue()) {
                writer.write("/" + month + "/" + fileName + "\n");
            }
        }

        writer.write("не оплачены:\n");
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            if (entry.getValue().size() < 12) {
                writer.write(entry.getKey() + ":\n");
                Set<String> services = new HashSet<>(Arrays.asList(
                        "газоснабжение", "теплоснабжение", "гвс", "xвс", "электроснабжение"
                ));
                for (String fileName : entry.getValue()) {
                    String service = fileName.split("_")[0];
                    services.remove(service);
                }
                for (String service : services) {
                    writer.write(service + "\n");
                }
            }
        }

        writer.close();
    }
}