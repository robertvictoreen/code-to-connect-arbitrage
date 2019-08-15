/**
 */

import java.util.*;

class Arbitrage {

    LinkedList<Quote> bbg;
    LinkedList<Quote> ebs;
    LinkedList<Quote> reu;

    Queue<String> log;


  public static void main(String[] args) {

    log = new LinkedList<>(); 

    bbg = new LinkedList<>();
    ebs = new LinkedList<>();
    reu = new LinkedList<>();

    TreeSet<Quote> maxAskQuotes = new TreeSet();
    TreeSet<Quote> minBidQuotes = new TreeSet();

    double totalProfit = 0;

    while (true) { //Every second

        //Instert new data from each provider
        Quote newBbg = new Quote("", "", 0, 0);
        bbg.offer(newBbg);
        maxAskQuotes.add(newBbg);
        minBidQuotes.add(newBbg);

        Quote newEbs = new Quote("", "", 0, 0);
        ebs.offer(newEbs);
        maxAskQuotes.add(newEbs);
        minBidQuotes.add(newEbs);

        Quote newReu = new Quote("", "", 0, 0);
        reu.offer(newReu);
        maxAskQuotes.add(newReu);
        minBidQuotes.add(newReu);

        //Remove expired prices
        //if (bbg.size() > 5) {
            Quote q = bbg.poll();
            if (!q.getSymbol().equals("X:XX:XX")) {
                askPrices.remove(q);
                bidPrices.remove(q);
            }
        //}

        //if (ebs.size() > 4) {
            Quote q = ebs.poll();
            if (!q.getSymbol().equals("X:XX:XX")) {
                askPrices.remove(q);
                bidPrices.remove(q);
            }
        //}

        //if (reu.size() > 3) {
            Quote q = reu.poll();
            if (!q.getSymbol().equals("X:XX:XX")) {
                askPrices.remove(q);
                bidPrices.remove(q);
            }
        //}

        //Get arbitrage profit
        Quote askQuote = maxAskQuotes.last();
        Quote bidQuote = minBidQuotes.first();

        double ask = askQuote.getAsk();
        double bid = bidQuote.getBid();

        double profit = bid - ask;

        if (profit > 0) {
            askPrices.pollLast();
            bidPrices.pollFirst();

            totalProfit += profit;
        }

    }


  }

  private fillSampleData() {
    Quote newQuote;

    bbg.push(new Quote("X:XX:XX", "", 0, 0));
    bbg.push(new Quote("X:XX:XX", "", 0, 0));
    bbg.push(new Quote("X:XX:XX", "", 0, 0));
    bbg.push(new Quote("X:XX:XX", "", 0, 0));
    bbg.push(new Quote("X:XX:XX", "", 0, 0));

    ebs.push(new Quote("X:XX:XX", "", 0, 0));
    ebs.push(new Quote("X:XX:XX", "", 0, 0));
    ebs.push(new Quote("X:XX:XX", "", 0, 0));
    ebs.push(new Quote("X:XX:XX", "", 0, 0));

    reu.push(new Quote("X:XX:XX", "", 0, 0));
    reu.push(new Quote("X:XX:XX", "", 0, 0));
    reu.push(new Quote("X:XX:XX", "", 0, 0));

    newQuote = new Quote("0:00:00", "EURUSD", 1.2000, 1.2240);
    bbg.add(newQuote);
    newQuote = new Quote("0:00:01", "EURUSD", 1.2000, 1.2099);
    bbg.add(newQuote);
    newQuote = new Quote("0:00:02", "EURUSD", 1.2050, 1.2090);
    bbg.add(newQuote);
    newQuote = new Quote("0:00:03", "EURUSD", 1.2070, 1.2095);
    bbg.add(newQuote);
    newQuote = new Quote("0:00:04", "EURUSD", 1.2095, 1.2100);
    bbg.add(newQuote);


    //Fill up ebs queue
    newQuote = new Quote("0:00:00", "EURUSD", 1.2050, 1.2200);
    ebs.add(newQuote);
    newQuote = new Quote("0:00:01", "EURUSD", 1.1900, 1.2220);
    ebs.add(newQuote);
    newQuote = new Quote("0:00:02", "EURUSD", 1.2050, 1.2100);
    ebs.add(newQuote);
    newQuote = new Quote("0:00:03", "EURUSD", 1.2060, 1.2100);
    ebs.add(newQuote);

    //Fill up reu queue
    newQuote = new Quote("0:00:00", "EURUSD", 1.2000, 1.2200);
    reu.add(newQuote);
    newQuote = new Quote("0:00:01", "EURUSD", 1.2020, 1.2200);
    reu.add(newQuote);
    newQuote = new Quote("0:00:02", "EURUSD", 1.1140, 1.2150);
    reu.add(newQuote);
  }
}

class Quote {

    private float bid;
    private float ask;
    private String symbol;
    private String time;

    public Quote(String time, String symbol, float bid, float ask) {
        this.time = time;
        this.symbol = symbol;
        this.bid = bid;
        this.ask = ask;
    }

    public String getSymbol() {
        return symbol;
    }

    public float getAsk() {
        return ask;
    }

    public float getBid() {
        return bid;
    }

    public boolean compareAsk(Quote other) {
        if (other.getAsk() > this.getAsk()) {
            return true;
        }
        return false;
    }

    public boolean compareBid(Quote other) {
        if (other.getBid() > this.getBid()) {
            return true;
        }
        return false;
    }
}
