import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadCSVUsingGetResources {
    private final String commaseparate = ",";
    private final Path outputFilepath = Paths.get("src/main/resources/", "OutputFile.csv");
    public static void main(String[] args) {
        ReadCSVUsingGetResources readCSVUsingGetResources = new ReadCSVUsingGetResources();
        readCSVUsingGetResources.entryPoint();
    }

    public void extractedMethod(Path newFile, int row, int col, String replace) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(newFile.toFile()), ',');
        List<String[]> csvBody = reader.readAll();
        csvBody.get(row)[col] = replace;
        reader.close();
        CSVWriter writer = new CSVWriter(new FileWriter(newFile.toFile()), ',');
        writer.writeAll(csvBody);
        writer.flush();
        writer.close();
    }

    private List<String> getRecordFromLine(String line) {
        return getStrings(line, commaseparate);
    }
    private List<String> getStrings(String line, String commaseparate) {
        List<String> values = new ArrayList<String>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(commaseparate);
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }

    private void entryPoint() {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("inputFile.csv");
        List<List<String>> records = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        try {
            String line = reader.readLine();
            while (line != null) {
                records.add(getRecordFromLine(line));
                line = reader.readLine();
            }
            for (List a : records) {
                System.out.println(a);
            }

            OutputStream os = new FileOutputStream(outputFilepath.toFile());
            PrintWriter fileWriter = new PrintWriter(os);
            for (List<String> rowData : records) {
                fileWriter.append(String.join(",", rowData));
                fileWriter.append("\n");
            }
            fileWriter.flush();
            fileWriter.close();
            updateCSV(outputFilepath, 4, 4, "outputusingGetResurces");
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void updateCSV(Path newFile, int row, int col, String replace) throws IOException {
        extractedMethod(newFile, row, col, replace);
        return;
    }

}
