package com.mac286.project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

/*
in this class, you design your main to test All stocks and ETFs
1- create a Tester with the appropriate path and file name and risk factor
2- Call run()
3- get the trades
4- call helper function that will compute all statistics
 */
public class Main {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        //if you are testing a risk based on stop loss and target
        float[] riskFactor = {0.5f, 1f, 2f, 5f, 10f};
        //set path to appropriate path (C:\\...)
        //remember difference between windows and macin terms of path lots of errors
        //set file to appropriate file (stocks.txt)
        //"C:\\Users\\ramda\\OneDrive\\Desktop\\Data\\AAL_Daily.csv"
        String fileName = "/Users/dylan/Downloads/Data/Stocks.txt";
        Vector<String> symbols = Helper.loadSymbols(fileName);

        //loop through your risk array (5 times) and do the following
        //create a Tester with path file and riskFactor[i]
        fileName = "/Users/dylan/Downloads/Data/";

        Vector<Trade> AllStocks = new Vector<>();
        for(int j = 0 ; j < 5 ; j++) {
            for (int i = 0; i < symbols.size(); ++i) {
                SymbolTester Main0 = new SymbolTester(symbols.elementAt(i), fileName, riskFactor[j]);
                Main0.test();
                AllStocks.addAll(Main0.getTrades());
            }
        }
        System.out.println("Stocks:");

        System.out.println(Helper.computeStats(AllStocks));
        //"C:\Users\ramda\OneDrive\Desktop\Data\AAL_Daily.csv"
        //two different paths (apple and windows) Keep in mind
        fileName = "/Users/dylan/Downloads/Data/ETFs.txt";
        symbols = Helper.loadSymbols(fileName);
        fileName = "/Users/dylan/Downloads/Data/";

        Vector<Trade> AllETFs = new Vector<>();
        for(int j = 0 ; j<5 ; j++) {
            for (int i = 0; i < symbols.size(); ++i) {
                SymbolTester Main0 = new SymbolTester(symbols.elementAt(i), fileName, riskFactor[j]);
                Main0.test();
                AllETFs.addAll(Main0.getTrades());
            }
        }
        System.out.println("\nETFs:");
        System.out.println(Helper.computeStats(AllETFs));

        long endTime = System.currentTimeMillis();
        System.out.println("Runtime took: " + (endTime-startTime));


    }
}
