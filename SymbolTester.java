package com.mac286.project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class SymbolTester {
    private float riskFactor;
    private String mSymbol;
    private String dataPath; //= "C:\Users\oaith\Courses\MAC286\Fall2023\Data\";
    private Vector<Bar> mData;
    private Vector<Trade> mTrades;
    private boolean loaded = false;

    public SymbolTester(String s, String p, float risk) {
        riskFactor = risk;
        mSymbol = s;
        dataPath = p;
        mData = new Vector<Bar>(3000, 100);
        mTrades = new Vector<Trade>(200, 100);
        loaded = false;
    }

    public Vector<Trade> getTrades() {
        return mTrades;
    }
    public Vector<Bar> getmData() {
        return mData;
    }
    public void loadData() {
        //create file name
        String fileName = dataPath + mSymbol + "_Daily.csv";
        //"C:\Users\oaith\Courses\MAC286\Fall2023\Data\AAPL_Daily.csv"
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while((line = br.readLine()) != null) {
                //create a bar using the constructor that accepts the data as a String
                Bar b = new Bar(line);
                //add the bar to the Vector
                mData.add(b);
            }
            loaded = true;
            br.close();
            fr.close();
        }catch(IOException e) {
            System.out.println("Something is wrong: " + e.getMessage());
            loaded = false;
            return;
        }
    }

    private boolean xDaysLow(int ind, int days) {
        for (int i = ind-1; i > ind-days; i--) {
            if(mData.elementAt(i).getLow() < mData.elementAt(ind).getLow())
                return false;
        }
        return true;
    }
    private boolean xDaysHigh(int ind, int days) {
        for (int i = ind-1; i > ind-days; i--) {
            if(mData.elementAt(i).getHigh() > mData.elementAt(ind).getHigh())
                return false;
        }
        return true;
    }
    public void outcomes(Trade T, int ind) {
        for(int i = ind; i < mData.size(); i++) {
            if(T.getDir() == Direction.LONG) {
                if(mData.elementAt(i).getHigh() > T.getTarget()) { //it is a win
                    //consider a gap day
                    if(mData.elementAt(i).getOpen() > T.getTarget()) {
                        //close at open  a gap day
                        T.close(mData.elementAt(i).getDate(), mData.elementAt(i).getOpen(), i-ind);
                        return;
                    }else {
                        //close the trade at target
                        T.close(mData.elementAt(i).getDate(), T.getTarget(), i-ind);
                        return;
                    }
                } else if(mData.elementAt(i).getLow() < T.getStopLoss()) {
                    //check if there is a gap down
                    if(mData.elementAt(i).getOpen() < T.getStopLoss()) {
                        //get out at the open
                        T.close(mData.elementAt(i).getDate(), mData.elementAt(i).getOpen(), i-ind);
                        return;
                    }else {
                        //get out at stoploss
                        T.close(mData.elementAt(i).getDate(), T.getStopLoss(), i-ind);
                        return;
                    }

                }
            }else {// it is a short trade
                if(mData.elementAt(i).getLow() <= T.getTarget()) { //it is a win
                    //consider a gap day
                    if(mData.elementAt(i).getOpen() < T.getTarget()) {
                        //close at open  a gap down day
                        T.close(mData.elementAt(i).getDate(), mData.elementAt(i).getOpen(), i-ind);
                        return;
                    }else {
                        //close the trade at target
                        T.close(mData.elementAt(i).getDate(), T.getTarget(), i-ind);
                        return;
                    }
                } else if(mData.elementAt(i).getHigh() >= T.getStopLoss()) {
                    //check if there is a gap down
                    if(mData.elementAt(i).getOpen() > T.getStopLoss()) {
                        //get out at the open
                        T.close(mData.elementAt(i).getDate(), mData.elementAt(i).getOpen(), i-ind);
                        return;
                    }else {
                        //get out at stoploss
                        T.close(mData.elementAt(i).getDate(), T.getStopLoss(), i-ind);
                        return;
                    }

                }

            }
        }
        T.close(mData.elementAt(mData.size()-1).getDate(), mData.elementAt(mData.size()-1).getClose(), mData.size()-1-ind);
    }

    public boolean test() {
        if(!loaded) {
            loadData();
            if (!loaded) {
                System.out.println("cannot load data");
                return false;
            }
        }
        /*
         * 1- today makes a 10 days low
         * 2- today is an outside bar (reversal) today's low is smaller than yesterday's low and today's high is larger than yesterday's high.
         * 3- today's close near the high (within less than 10%) (high-close)/(high-low)<0.1;
         * 4- buy at open tomorrow and stop today's low and target factor*risk
         */

		/*
		Long trade:
		If 10 day high is i-2 and:
		- high of i is higher than highs of both i-1 and i-2
		- low of i is lower than lows of both i-1 and i-2
		- Close of i has to be higher than open of i, and also higher than the close of i-1
		Buy the next day (ONLY IF i+1 open is higher than low of i)
		 */

        //TODO: code your pattern here
//		Check if i-1 or i-2 is a 10 day high
//		If 10 day high is i-1 and i is an outside bar:
//		- high of i is larger than high of i-1 and low of i is lower than i-1
//		- close of i is higher than close of i-1 - green)
//		Buy the next day (ONLY IF i+1 open is higher than low of i)

        for (int i = 12; i < mData.size()-2; ++i) {
            if ((xDaysHigh(i-1, 10)) &&
                    (mData.elementAt(i).getHigh() > mData.elementAt(i-1).getHigh()) &&
                    (mData.elementAt(i).getLow() < mData.elementAt(i-1).getLow()) &&
                    (mData.elementAt(i).getClose() > mData.elementAt(i-1).getClose()) &&
                    (mData.elementAt(i).getClose() > mData.elementAt(i).getOpen()) && //erase?
                    (mData.elementAt(i+1).getOpen() > mData.elementAt(i).getLow())){
                float entryprice = mData.elementAt(i+1).getOpen();
                float stoploss = mData.elementAt(i).getLow() - 0.01f;
                float risk = entryprice - stoploss;
                float target = entryprice + riskFactor * risk;
                Trade T = new Trade();
                T.open(mSymbol, mData.elementAt(i+1).getDate(), entryprice, stoploss, target, Direction.LONG);
                outcomes(T, i+1);
                //add the trade to the Trade vector
                mTrades.add(T);
            }else if ((xDaysHigh(i-2, 10)) &&
                    (mData.elementAt(i).getHigh() > mData.elementAt(i-1).getHigh()) &&
                    (mData.elementAt(i).getHigh() > mData.elementAt(i-2).getHigh()) &&
                    (mData.elementAt(i).getLow() < mData.elementAt(i-1).getLow()) &&
                    (mData.elementAt(i).getLow() < mData.elementAt(i-2).getLow()) &&
                    (mData.elementAt(i).getClose() > mData.elementAt(i-1).getClose()) &&
                    (mData.elementAt(i).getClose() > mData.elementAt(i-2).getClose()) && //erase?
                    (mData.elementAt(i).getClose() > mData.elementAt(i).getOpen()) &&
                    (mData.elementAt(i+1).getOpen() > mData.elementAt(i).getLow())){
                float entryprice = mData.elementAt(i+1).getOpen();
                float stoploss = mData.elementAt(i).getLow() - 0.01f;
                float risk = entryprice - stoploss;
                float target = entryprice + riskFactor * risk;
                Trade T = new Trade();
                T.open(mSymbol, mData.elementAt(i+1).getDate(), entryprice, stoploss, target, Direction.LONG);
                outcomes(T, i+1);
                //add the trade to the Trade vector
                mTrades.add(T);
            }
        }
        //short trade
        //Date,Open,High,Low,Close,Adj Close,Volume
        for (int i = 12; i < mData.size()-2; ++i) {
            if ((xDaysLow(i-1, 10)) &&
                    (mData.elementAt(i).getHigh() > mData.elementAt(i-1).getHigh()) && //changed from < to >
                    (mData.elementAt(i).getLow() < mData.elementAt(i-1).getLow()) && //changed from > to <
                    (mData.elementAt(i).getClose() < mData.elementAt(i-1).getClose()) &&
                    (mData.elementAt(i).getClose() < mData.elementAt(i).getOpen()) && //erase?
                    (mData.elementAt(i+1).getOpen() < mData.elementAt(i).getHigh())){ //changed mData.elementAt(i).getLow() to getHigh
                float entryprice = mData.elementAt(i+1).getOpen();
                float stoploss = mData.elementAt(i).getHigh() + 0.01f; //changed - to + and getLow to getHigh
                float risk = stoploss - entryprice; //inverse of long
                float target = entryprice - riskFactor * risk; //changed from + to -
                Trade T = new Trade();
                T.open(mSymbol, mData.elementAt(i+1).getDate(), entryprice, stoploss, target, Direction.SHORT);
                outcomes(T, i+1);
                //add the trade to the Trade vector
                mTrades.add(T);
                //Date,Open,High,Low,Close,Adj Close,Volume
            }else if ((xDaysLow(i-2, 10)) &&
                    (mData.elementAt(i).getHigh() > mData.elementAt(i-1).getHigh()) &&
                    (mData.elementAt(i).getHigh() > mData.elementAt(i-2).getHigh()) &&
                    (mData.elementAt(i).getLow() < mData.elementAt(i-1).getLow()) &&
                    (mData.elementAt(i).getLow() < mData.elementAt(i-2).getLow()) &&
                    (mData.elementAt(i).getClose() < mData.elementAt(i-1).getClose()) &&
                    (mData.elementAt(i).getClose() < mData.elementAt(i-2).getClose()) && //erase?
                    (mData.elementAt(i).getClose() < mData.elementAt(i).getOpen()) &&
                    (mData.elementAt(i+1).getOpen() < mData.elementAt(i).getHigh())){
                float entryprice = mData.elementAt(i+1).getOpen();
                float stoploss = mData.elementAt(i).getHigh() + 0.01f; //changed - to + and getLow to getHigh
                float risk = stoploss - entryprice;
                float target = entryprice - riskFactor * risk;
                Trade T = new Trade();
                T.open(mSymbol, mData.elementAt(i+1).getDate(), entryprice, stoploss, target, Direction.SHORT);
                outcomes(T, i+1);
                //add the trade to the Trade vector
                mTrades.add(T);
            }
        }

        return true;
    }

}
