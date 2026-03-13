import java.io.*;
import java.util.*;

public class SkiLiftDataProcessor {

    public static void main(String[] args) {
        String inputFile = "D:\\MyUniversity\\N.U\\M1\\P.D.P\\hw\\A5material\\ski_lift_data.csv";
        String outputFile = "D:\\MyUniversity\\N.U\\M1\\P.D.P\\hw\\A5material\\ski_lift_data_output.csv";

        try {
            processSkiLiftData(inputFile, outputFile);
            System.out.println("Processing complete! Output saved to: " + outputFile);
        } catch (IOException e) {
            System.err.println("Error processing file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void processSkiLiftData(String inputFile, String outputFile) throws IOException {
        // Map to store sum of lifts for each skier
        Map<String, Integer> skierVerticalMap = new HashMap<>();
        List<String[]> allRows = new ArrayList<>();
        String[] headers = null;
        int skierIndex = -1;
        int liftIndex = -1;

        // First pass: read all data and calculate sums
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line = br.readLine();

            if (line != null) {
                headers = line.split(",");
                allRows.add(headers);

                // Find column indices
                for (int i = 0; i < headers.length; i++) {
                    String header = headers[i].trim();
                    if (header.equalsIgnoreCase("skier")) {
                        skierIndex = i;
                    } else if (header.equalsIgnoreCase("lift")) {
                        liftIndex = i;
                    }
                }

                if (skierIndex == -1 || liftIndex == -1) {
                    throw new IOException("Required columns 'skier' and 'lift' not found");
                }
            }

            // Read all data rows and calculate sums
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                allRows.add(values);

                if (values.length > Math.max(skierIndex, liftIndex)) {
                    String skierId = values[skierIndex].trim();
                    try {
                        int liftValue = Integer.parseInt(values[liftIndex].trim());
                        skierVerticalMap.put(skierId,
                                skierVerticalMap.getOrDefault(skierId, 0) + liftValue);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid lift value in row: " + line);
                    }
                }
            }
        }

        // Write output with only SkierID and Vertical columns
        try (PrintWriter pw = new PrintWriter(new FileWriter(outputFile))) {
            // Write header
            pw.println("SkierID,Vertical");

            // Write each unique skier with their total vertical
            for (Map.Entry<String, Integer> entry : skierVerticalMap.entrySet()) {
                pw.println(entry.getKey() + "," + entry.getValue());
            }
        }
    }
}