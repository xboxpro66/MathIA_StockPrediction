package main;

import data.StockDataAutomator;
import java.util.List;

public class StockPredictorApp {
	//api key=M2WILJVN48U2MICM
	public static void main(String[] args) {
		 String apiKey = "M2WILJVN48U2MICM";
	     String symbol = "AAPL";

	     StockDataAutomator.fetchStockData(symbol, apiKey);
	     
	     String csvData = StockDataAutomator.fetchStockData(symbol, apiKey);
	     String fileName = "daily_" + symbol + ".csv";
	     StockDataAutomator.saveToFile(csvData, fileName);

	    
	     List<Double> closingPrices = StockDataAutomator.extractClosingPrices(csvData);
	     StockDataAutomator.analyzeStockData(closingPrices);
	}

}
