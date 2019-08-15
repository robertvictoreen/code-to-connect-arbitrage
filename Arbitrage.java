/**
 */

import java.util.*;

class Arbitrage {

    LinkedList<Quote> bbg;
    LinkedList<Quote> ebs;
    LinkedList<Quote> reu;


  public static void main(String[] args) {

    bbg = new LinkedList<>();
    ebs = new LinkedList<>();
    reu = new LinkedList<>();

  }

  private fillSampleData() {
    Quote newQuote;

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
