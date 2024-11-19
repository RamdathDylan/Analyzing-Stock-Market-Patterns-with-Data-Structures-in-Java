package com.mac286.project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;

public class Helper {
    /*write a static method that returns a path and a file name
    //it opens the file (file symbols) and reads it and creates a vector
    of Strings of all files
    */


    public static Vector<String> loadSymbols (String fileName) {
        //TODO: create a Vector symbols
        Vector<String> symbols = new Vector<>();
        //open the file and read line by line, trim the string and add it to Vector symbols
        //String fileName = ""/Users/dylan/Downloads/Data/Stocks.txt";
        try{
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();

            while (line != null) {
                symbols.add(line);
                line = br.readLine();
            }
            br.close();
            fr.close();
        }catch(Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        //return the vector
        return symbols;
    }

    //write a static method that accepts a vector of trades and goes
    //through it to compute all statistics, return the statistics as
    //an object

    public static Statistics computeStats(Vector<Trade> trades) {
        //create a Statistics object
        Statistics stat = new Statistics();
        //go through Vector trades one by one and compute all the statistics
        int i = 0;
        double profit = 0d;
        double holdperiod = 0d;
        double holdPeriodLong = 0d;
        double holdPeriodShort = 0d;
        double numWinsTotal = 0d;
        double numWinsLong = 0d;
        double numWinsShort = 0d;
        double numLongTrades = 0d;
        double numShortTrades = 0d;

        //average profit = sum / number of trades
        while (i != trades.size() && trades.elementAt(i) != null) {
            stat.averageProfit+= trades.elementAt(i).percentPL(); //positive is winner
            holdperiod += trades.elementAt(i).getHoldingPeriod();
            if (trades.elementAt(i).getDir() == Direction.LONG) {
                ++numLongTrades;
                stat.averageProfitLong += trades.elementAt(i).percentPL(); //positive is winner
                holdPeriodLong += trades.elementAt(i).getHoldingPeriod();
                if (trades.elementAt(i).percentPL() > 0)
                    ++numWinsLong;
            }else if (trades.elementAt(i).getDir() == Direction.SHORT) {
                ++numShortTrades;
                stat.averageProfitShort += trades.elementAt(i).percentPL(); //positive is winner
                holdPeriodShort += trades.elementAt(i).getHoldingPeriod();
                if (trades.elementAt(i).percentPL() > 0)
                    ++numWinsShort;
            }
            ++i;
        }
        stat.numberTrades = trades.size();
        stat.averageProfit = stat.averageProfit/trades.size();
        numWinsTotal = numWinsLong + numWinsShort;
        stat.winningPercent = (numWinsTotal / trades.size()) * 100;
        stat.averageProfitLong = stat.averageProfitLong/numLongTrades;
        stat.averageProfitShort = stat.averageProfitShort/numShortTrades;
        stat.numberTradesLong = numLongTrades;
        stat.averageHoldingPeriod = holdperiod/trades.size();
        stat.averageProfitPerDay = stat.averageProfit/stat.averageHoldingPeriod;
        stat.numberTradesShort = numShortTrades;
        stat.averageHoldingPeriodLong = holdPeriodLong/numLongTrades;
        stat.averageProfitPerDayLong = stat.averageProfitLong/stat.averageHoldingPeriodLong;
        stat.winningPercentLong = (numWinsLong/numLongTrades) * 100;
        stat.averageHoldingPeriodShort = holdPeriodShort/numShortTrades;
        stat.averageProfitPerDayShort = stat.averageProfitShort/stat.averageHoldingPeriodShort;
        stat.winningPercentShort = (numWinsShort/numShortTrades) * 100;


        return stat;
    }
}
