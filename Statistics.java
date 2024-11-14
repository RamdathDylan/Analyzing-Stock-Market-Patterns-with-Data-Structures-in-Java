public class Statistics {
    double numberTrades;
    double averageProfit;
    double averageHoldingPeriod;
    double averageProfitPerDay; //most important
    //take average profit / average holding period
    double winningPercent;

    //for long
    double numberTradesLong;
    double winningPercentLong;
    double averageProfitLong;
    double averageHoldingPeriodLong;
    double averageProfitPerDayLong;

    //do the same for short
    double numberTradesShort;
    double averageProfitShort;
    double averageHoldingPeriodShort;
    double averageProfitPerDayShort;
    double winningPercentShort;

    @Override
    public String toString() {
        return /*"Statistics: \n" +*/
                "numberTrades = " + numberTrades + " trades" +
                "\naverageProfit = " + averageProfit + "%" +
                "\naverageHoldingPeriod = " + averageHoldingPeriod + " days" +
                "\naverageProfitPerDay = " + averageProfitPerDay + "%" +
                "\nwinningPercent = " + winningPercent + "%" +

                "\n\nnumberTradesLong = " + numberTradesLong + " trades" +
                "\naverageProfitLong = " + averageProfitLong + "%" +
                "\naverageHoldingPeriodLong = " + averageHoldingPeriodLong + " days" +
                "\naverageProfitPerDayLong = " + averageProfitPerDayLong + "%" +
                "\nwinningPercentLong = " + winningPercentLong + "%" +

                "\n\nnumberTradesShort = " + numberTradesShort + " trades" +
                "\naverageProfitShort = " + averageProfitShort + "%" +
                "\naverageHoldingPeriodShort = " + averageHoldingPeriodShort + " days" +
                "\naverageProfitPerDayShort = " + averageProfitPerDayShort + "%" +
                "\nwinningPercentShort = " + winningPercentShort + "%";
    }
}
