package com.mac286.project;

//import project.Stacks.ourVectorW;

import java.util.Vector;

/*
Tester will receive a file and path as well as a riskFactor or how many days until exit
How many days until exit

1- in the constructor create a vector of all symbols (the vector should be a member variable of the class)
2- in the constructor, create a vector of trades, to collect all trades

Have a method run() that will do the following:
1- if the vector of symbols or Trades is empty create again or exit
2- go through the Vector of symbols one by one and test the symbol using a SymbolTester class, that you would have modified
3- collect the trades from the SymbolTester each time a symbol is tested
4- have a method reset() that resets the Vector of symbols and trades
 */
public class Tester {
    private Vector<String> vSymbols;
    private Vector<Trade> mTrades;
    private String mPath, mFile;
    //private float mRisk;
    public Tester (String path, String file, float risk) {
        //set path and file
        //create a Vector of trades
        //set risk to risk
        //open the file and create a Vector of symbols. Using Helper
    }
    //    public void setRisk(float r) {
//        this.mRisk = r;
//    }
    public void setPath (String p) {
        this.mPath = p;
    }
    public void setFile (String f) {
        this.mFile = f;
    }
    public boolean run() {
        //if mSymbols is empty or null, create a new one
        //if mTrades is empty or null, create a new one
        //go through the vSymbols , for each symbol
        //create a symbol Tester with appropriate parameters
        //test the symbol (calling .test() method)
        //collect the trades by calling getTrades() method of the SymbolTester
        return true;
    }

    public Vector<Trade> getTrades() {
        return mTrades;
    }
    public void reset() {
        vSymbols = null;
        mTrades = null;
    }
}
