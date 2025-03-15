package data;

import java.util.ArrayList;
import java.util.List;

public class DataProcessor {
    
    // Calculate a Simple Moving Average
    public static List<Double> calculateSMA(List<Double> prices, int window) {
        List<Double> movingAverages = new ArrayList<>();
        
        for (int i = 0; i <= prices.size() - window; i++) {
            double sum = 0.0;
            for (int j = 0; j < window; j++) {
                sum += prices.get(i + j);
            }
            movingAverages.add(sum / window);
        }
        
        return movingAverages;
    }
    
    // Calculate Daily Percentage Changes
    public static List<Double> calculateDailyPercentageChange(List<Double> prices) {
        List<Double> percentageChanges = new ArrayList<>();
        
        for (int i = 1; i < prices.size(); i++) {
            double change = ((prices.get(i) - prices.get(i - 1)) / prices.get(i - 1)) * 100;
            percentageChanges.add(change);
        }
        
        return percentageChanges;
    }
    
    // Calculate Volatility 
    public static double calculateVolatility(List<Double> percentageChanges) {
        double sum = 0.0;
        double mean = 0.0;

        // Calculate mean/average
        for (double change : percentageChanges) {
            mean += change;
        }
        mean /= percentageChanges.size();

        // Calculate variance
        for (double change : percentageChanges) {
            sum += Math.pow(change - mean, 2);
        }
        double variance = sum / percentageChanges.size();

        return Math.sqrt(variance);
    }
}