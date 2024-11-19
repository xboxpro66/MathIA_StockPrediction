package data;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;

public class StockDataAutomator {

    // Fetch stock data and save it to a file
    public static void fetchAndSaveStockData(String symbol, String apiKey) {
        String urlString = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + symbol + "&apikey=" + apiKey + "&datatype=csv";
        StringBuilder data = new StringBuilder();
        String directory = "stock_data/";

        try {
            // Ensure the directory exists
            File dir = new File(directory);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Fetch data
            URI uri = URI.create(urlString);
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line).append("\n");
            }
            reader.close();

            // Save the data to a file
            String fileName = directory + "daily_" + symbol + ".csv";
            System.out.println("Saving data to: " + fileName);
            saveToFile(data.toString(), fileName);

            // Automatically parse the file
            parseCSVFile(fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save data to a file
    public static void saveToFile(String data, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(data);
            System.out.println("Data successfully saved to: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Parse CSV file and print the data
    public static void parseCSVFile(String filePath) {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] headers = reader.readNext(); // Read header row
            System.out.println("Headers: ");
            for (String header : headers) {
                System.out.print(header + "\t");
            }
            System.out.println("\n");

            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                for (String cell : nextLine) {
                    System.out.print(cell + "\t");
                }
                System.out.println();
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }
}