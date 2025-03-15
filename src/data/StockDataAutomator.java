package data;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.net.HttpURLConnection;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.net.URI;
import java.net.URISyntaxException;

public class StockDataAutomator {
    

    public static String fetchStockData(String symbol, String apiKey) {
        String urlString = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + symbol + "&apikey=" + apiKey + "&datatype=csv";
        StringBuilder data = new StringBuilder();

        try {
            URI uri = new URI(urlString);
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line).append("\n");
            }
            reader.close();
        } catch (URISyntaxException e) {
            System.err.println("Invalid URI syntax: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error fetching stock data: " + e.getMessage());
            e.printStackTrace();
        }

       
        
        return data.toString();
    }


    public static void saveToFile(String data, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(data);
            System.out.println("Data saved to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static List<Double> extractClosingPrices(String csvData) {
        List<Double> closingPrices = new ArrayList<>();
        
        try (CSVReader reader = new CSVReader(new StringReader(csvData))) {
            String[] headers = reader.readNext();
            int closeIndex = -1;

            // to identify the "close" column index
            for (int i = 0; i < headers.length; i++) {
                if (headers[i].equalsIgnoreCase("close")) {
                    closeIndex = i;
                    break;
                }
            }

            if (closeIndex == -1) {
                System.out.println("No 'close' column found in CSV data.");
                return closingPrices;
            }

            // Read data rows and extract "close" values
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                closingPrices.add(Double.parseDouble(nextLine[closeIndex]));
            }

        } catch (IOException | CsvValidationException | NumberFormatException e) {
            e.printStackTrace();
        }

        return closingPrices;
    }

    public static void analyzeStockData(List<Double> prices) {
        System.out.println("Analyzing stock data...");

        // Calculate SMA
        List<Double> sma = DataProcessor.calculateSMA(prices, 5);
        System.out.println("5-Day SMA: " + sma);

        // Calculate daily percentage changes
        List<Double> percentageChanges = DataProcessor.calculateDailyPercentageChange(prices);
        System.out.println("Daily Percentage Changes: " + percentageChanges);

        // Calculate volatility
        double volatility = DataProcessor.calculateVolatility(percentageChanges);
        System.out.println("Volatility: " + volatility);
    }
}
