import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Vector;

public class Helper {
    //write a static method that returns a path and a file name
    //it opens the file (file symbols) and reads it and creates a vector
    //of Strings of all files

    //EDITED
//    public static Vector<String> loadSymbols (/*String path, String file*/ String fileName) {
//        //TODO: create a Vector symbols
//        Vector<String> symbols = new Vector<>();
//        //open the file and read line by line, trim the string and add it to Vector symbols
//        //String fileName = "C:\\Users\\junyo\\OneDrive\\Documents\\School\\Second Year\\Spring 2024\\Data Structures\\Data\\Data Files\\stocks.txt";
//        try{
//            FileReader fr = new FileReader(fileName);
//            BufferedReader br = new BufferedReader(fr);
//            String line = br.readLine();
//
//            while (line != null) {
//                symbols.add(line);
//                line = br.readLine();
//            }
//            br.close();
//            fr.close();
//        }catch(Exception e) {
//            System.out.println("Exception: " + e.getMessage());
//        }
//        //return the vector
//        return symbols;
//    }

    public static ArrayList<String> loadSymbols (/*String path, String file*/ String fileName) {
        //TODO: create a Vector symbols
        ArrayList<String> symbols = new ArrayList<>();
        //open the file and read line by line, trim the string and add it to Vector symbols
        //String fileName = "C:\\Users\\junyo\\OneDrive\\Documents\\School\\Second Year\\Spring 2024\\Data Structures\\Data\\Data Files\\stocks.txt";
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

    //public static Statistics computeStats(Vector<Trade> trades) {
    //EDITED
    public static Statistics computeStats(ArrayList<Trade> trades) {
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
        while (i != trades.size() && trades.get(i) != null) {
            stat.averageProfit+= trades.get(i).percentPL(); //positive is winner
            //calculate number of winner for short and long and losers and see the proportion
            holdperiod += trades.get(i).getHoldingPeriod();
            if (trades.get(i).getDir() == Direction.LONG) {
                ++numLongTrades;
                stat.averageProfitLong += trades.get(i).percentPL(); //positive is winner
                holdPeriodLong += trades.get(i).getHoldingPeriod();
                if (trades.get(i).percentPL() > 0)
                    ++numWinsLong;
            }else if (trades.get(i).getDir() == Direction.SHORT) {
                ++numShortTrades;
                stat.averageProfitShort += trades.get(i).percentPL(); //positive is winner
                holdPeriodShort += trades.get(i).getHoldingPeriod();
                if (trades.get(i).percentPL() > 0)
                    ++numWinsShort;
            }
            ++i;
        }
        //is this how to calculate average profit?
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
        //win percentage = number of winning trades/total number of trades * 100
        //stat.winningPercentLong = (numWinsLong/trades.size()) * 100;
        stat.winningPercentLong = (numWinsLong/numLongTrades) * 100;

        stat.averageHoldingPeriodShort = holdPeriodShort/numShortTrades;
        stat.averageProfitPerDayShort = stat.averageProfitShort/stat.averageHoldingPeriodShort;
        //stat.winningPercentShort = (numWinsShort/trades.size()) * 100;
        stat.winningPercentShort = (numWinsShort/numShortTrades) * 100;

        //for long
//        float holdPeriodLong = 0f;
//        while (i != trades.size() && trades.get(i) != null) {
//            if (trades.get(i).getDir() == Direction.LONG) {
//                stat.averageProfitLong += trades.get(i).percentPL(); //positive is winner
//                //calculate number of winner for short and long and losers and see the proportion
//                holdPeriodLong += trades.get(i).getHoldingPeriod();
//            }
//            ++i;
//        }
//        //stat.winningPercentLong = 1;
//        stat.averageHoldingPeriodLong = holdPeriodLong/trades.size();
//        stat.averageProfitPerDayLong = stat.averageProfitLong/stat.averageHoldingPeriodLong;
//
//        //for short
//        float holdPeriodShort = 0f;
//        while (i != trades.size() && trades.get(i) != null) {
//            if (trades.get(i).getDir() == Direction.SHORT) {
//                stat.averageProfitShort += trades.get(i).percentPL(); //positive is winner
//                //calculate number of winner for short and long and losers and see the proportion
//                holdPeriodShort += trades.get(i).getHoldingPeriod();
//            }
//            ++i;
//        }
//        //stat.winningPercentShort = 1;
//        stat.averageHoldingPeriodShort = holdPeriodShort/trades.size();
//        stat.averageProfitPerDayShort = stat.averageProfitShort/stat.averageHoldingPeriodShort;
        //sum of percentPL for each trade

        //check if long or short
        //return statistics as object

        return stat;
    }
}
