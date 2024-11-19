package main;

import data.StockDataAutomator;

public class StockPredictorApp {
	//api key=M2WILJVN48U2MICM
	public static void main(String[] args) {
		 String apiKey = "M2WILJVN48U2MICM";
	     String symbol = "AAPL";

	        StockDataAutomator.fetchAndSaveStockData(symbol, apiKey);
	}

}
