import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        HashMap<String, String> services = new HashMap<>();
        services.put("газоснабжение", "газоснабжение");
        services.put("теплоснабжение", "теплоснабжение");
        services.put("гвс", "гвс");
        services.put("xвс", "xвс");
        services.put("электроснабжение", "электроснабжение");

        String[] months = {"январь", "февраль", "март", "апрель", "май", "июнь", "июль",
                "август", "сентябрь", "октябрь", "ноябрь", "декабрь"};

        try {
            File file = new File("C:\\Users\\Maxim\\IdeaProjects" +
                    "\\2023\\test-task-for-stack\\чеки.txt");
            Scanner scanner = new Scanner(file);

            HashMap<String, StringBuilder> unpaidServices = new HashMap<>();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("_");
                String service = parts[0];
                String month = parts[1].split(" .")[0];

                if (unpaidServices.containsKey(month))
                    unpaidServices.get(month).append("").append(line);
                else
                    unpaidServices.put(month, new StringBuilder(line));
            }

            File outputFile = new File("чеки_по_папкам.txt");
            FileWriter writer = new FileWriter(outputFile);

            // .pdf dobavit
            for (String month : months) {
                if (unpaidServices.containsKey(month + ".pdf")) {
                    writer.write("/" + month + "/" + unpaidServices.get(month).toString() + "");
                }
            }

            writer.write("не оплачены:");
            for (String month : months) {
                if (unpaidServices.containsKey(month)) {
                    writer.write(month + ":");

                    for (String key : services.keySet()) {
                        if (!unpaidServices.get(month).toString().contains(key)) {
                            writer.write(services.get(key) + "");
                        }
                    }
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}