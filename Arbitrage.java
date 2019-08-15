/**
 */

import java.util.*;

public class Arbitrage {


  public static void main(String[] args) {

    LinkedList<Quote> bbg = new LinkedList<>();
    LinkedList<Quote> ebs = new LinkedList<>();
    LinkedList<Quote> reu = new LinkedList<>();

    bbg.push(new Quote("", "X:XX:XX", "", 0, 0));
    bbg.push(new Quote("", "X:XX:XX", "", 0, 0));
    bbg.push(new Quote("", "X:XX:XX", "", 0, 0));
    bbg.push(new Quote("", "X:XX:XX", "", 0, 0));
    bbg.push(new Quote("", "X:XX:XX", "", 0, 0));

    ebs.push(new Quote("", "X:XX:XX", "", 0, 0));
    ebs.push(new Quote("", "X:XX:XX", "", 0, 0));
    ebs.push(new Quote("", "X:XX:XX", "", 0, 0));
    ebs.push(new Quote("", "X:XX:XX", "", 0, 0));

    reu.push(new Quote("", "X:XX:XX", "", 0, 0));
    reu.push(new Quote("", "X:XX:XX", "", 0, 0));
    reu.push(new Quote("", "X:XX:XX", "", 0, 0));

    TreeSet<Quote> maxAskQuotes = new TreeSet<Quote>((q1, q2) -> {
        if (q1.getAsk() != q2.getAsk()) {
            return Double.compare(q1.getAsk(), q2.getAsk());
        }

        if (!q1.getTime().equals(q2.getTime())) {
            return q1.getTime().compareTo(q2.getTime());
        }

        return q1.getProvider().compareTo(q2.getProvider());
    });
    TreeSet<Quote> minBidQuotes = new TreeSet<Quote>((q1, q2) -> {
        if (q1.getBid() != q2.getBid()) {
            return Double.compare(q1.getBid(), q2.getBid());
        }

        if (!q1.getTime().equals(q2.getTime())) {
            return q1.getTime().compareTo(q2.getTime());
        }

        return q1.getProvider().compareTo(q2.getProvider());

    });

    System.out.println("Bbg size: " + bbg.size());

    double totalProfit = 0;

    Scanner scan = new Scanner(System.in);

    while (scan.hasNext()) { //Every second

        //Instert new data from each provider

        String provider = scan.next();
        String time = scan.next();
        String symbol = scan.next();
        double ask = scan.nextDouble();
        double bid = scan.nextDouble();

        //System.out.println();

        scan.nextLine();

        Quote newReu = new Quote(provider, time, symbol, ask, bid);
        reu.offer(newReu);
        maxAskQuotes.add(newReu);
        minBidQuotes.add(newReu);

        System.out.println("TreeSet size: " + maxAskQuotes.size());
        
        provider = scan.next();
        time = scan.next();
        symbol = scan.next();
        ask = scan.nextDouble();
        bid = scan.nextDouble();
        scan.nextLine();

        Quote newEbs = new Quote(provider, time, symbol, ask, bid);
        ebs.offer(newEbs);
        maxAskQuotes.add(newEbs);
        minBidQuotes.add(newEbs);

        System.out.println("TreeSet size: " + maxAskQuotes.size());
        
        provider = scan.next();
        time = scan.next();
        symbol = scan.next();
        ask = scan.nextDouble();
        bid = scan.nextDouble();
        scan.nextLine();

        Quote newBbg = new Quote(provider, time, symbol, ask, bid);
        bbg.offer(newBbg);
        maxAskQuotes.add(newBbg);
        minBidQuotes.add(newBbg);

        System.out.println("Done");

        System.out.println("TreeSet size: " + maxAskQuotes.size());
        

        //Remove expired prices
        //if (bbg.size() > 5) {
            Quote q = bbg.poll();
            if (!q.getSymbol().equals("X:XX:XX")) {
                maxAskQuotes.remove(q);
                minBidQuotes.remove(q);
            }
        //}

        //if (ebs.size() > 4) {
            q = ebs.poll();
            if (!q.getSymbol().equals("X:XX:XX")) {
                maxAskQuotes.remove(q);
                minBidQuotes.remove(q);
            }
        //}

        //if (reu.size() > 3) {
            q = reu.poll();
            if (!q.getSymbol().equals("X:XX:XX")) {
                maxAskQuotes.remove(q);
                minBidQuotes.remove(q);
            }
        //}

        System.out.println("Start profit calculation");


        //Get arbitrage profit
        while (true) {
            Quote askQuote = maxAskQuotes.first();
            Quote bidQuote = minBidQuotes.last();

            ask = askQuote.getAsk();
            bid = bidQuote.getBid();

            System.out.println("Ask: " + ask);
            System.out.println("Bid: " + bid);

            double profit = bid - ask;

            if (profit > 0) {
                maxAskQuotes.pollFirst();
                minBidQuotes.pollLast();

                totalProfit += profit;

                System.out.println("Made profit: " + profit);
            } else {
                break;
            }
        }  

    }


  }

  public void fillSampleData(LinkedList<Quote> bbg, LinkedList<Quote> ebs, LinkedList<Quote> reu) {
    Quote newQuote;

    // bbg.push(new Quote("X:XX:XX", "", 0, 0));
    // bbg.push(new Quote("X:XX:XX", "", 0, 0));
    // bbg.push(new Quote("X:XX:XX", "", 0, 0));
    // bbg.push(new Quote("X:XX:XX", "", 0, 0));
    // bbg.push(new Quote("X:XX:XX", "", 0, 0));

    // ebs.push(new Quote("X:XX:XX", "", 0, 0));
    // ebs.push(new Quote("X:XX:XX", "", 0, 0));
    // ebs.push(new Quote("X:XX:XX", "", 0, 0));
    // ebs.push(new Quote("X:XX:XX", "", 0, 0));

    // reu.push(new Quote("X:XX:XX", "", 0, 0));
    // reu.push(new Quote("X:XX:XX", "", 0, 0));
    // reu.push(new Quote("X:XX:XX", "", 0, 0));

    // newQuote = new Quote("0:00:00", "EURUSD", 1.2000, 1.2240);
    // bbg.add(newQuote);
    // newQuote = new Quote("0:00:01", "EURUSD", 1.2000, 1.2099);
    // bbg.add(newQuote);
    // newQuote = new Quote("0:00:02", "EURUSD", 1.2050, 1.2090);
    // bbg.add(newQuote);
    // newQuote = new Quote("0:00:03", "EURUSD", 1.2070, 1.2095);
    // bbg.add(newQuote);
    // newQuote = new Quote("0:00:04", "EURUSD", 1.2095, 1.2100);
    // bbg.add(newQuote);


    // //Fill up ebs queue
    // newQuote = new Quote("0:00:00", "EURUSD", 1.2050, 1.2200);
    // ebs.add(newQuote);
    // newQuote = new Quote("0:00:01", "EURUSD", 1.1900, 1.2220);
    // ebs.add(newQuote);
    // newQuote = new Quote("0:00:02", "EURUSD", 1.2050, 1.2100);
    // ebs.add(newQuote);
    // newQuote = new Quote("0:00:03", "EURUSD", 1.2060, 1.2100);
    // ebs.add(newQuote);

    // //Fill up reu queue
    // newQuote = new Quote("0:00:00", "EURUSD", 1.2000, 1.2200);
    // reu.add(newQuote);
    // newQuote = new Quote("0:00:01", "EURUSD", 1.2020, 1.2200);
    // reu.add(newQuote);
    // newQuote = new Quote("0:00:02", "EURUSD", 1.1140, 1.2150);
    // reu.add(newQuote);
  }
}

class Quote {

    private double bid;
    private double ask;
    private String provider;
    private String symbol;
    private String time;

    public Quote(String provider, String time, String symbol, double bid, double ask) {
        this.provider = provider;
        this.time = time;
        this.symbol = symbol;
        this.bid = bid;
        this.ask = ask;
    }

    public String getProvider() {
        return provider;
    }

    public String getTime() {
        return time;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getAsk() {
        return ask;
    }

    public double getBid() {
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
