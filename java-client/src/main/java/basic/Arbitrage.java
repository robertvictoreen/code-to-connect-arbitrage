package basic;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;
import java.text.*;

public class Arbitrage {

    LinkedList<Quote> bbg;
    LinkedList<Quote> ebs;
    LinkedList<Quote> reu;

    TreeSet<Quote> maxAskQuotes;
    TreeSet<Quote> minBidQuotes;

    JSONObject bbgJSON;
    JSONObject ebsJSON;
    JSONObject reuJSONI;

    double totalProfit;

    public Arbitrage() {

        totalProfit = 0;

        bbg = new LinkedList<>();
        ebs = new LinkedList<>();
        reu = new LinkedList<>();

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

        maxAskQuotes = new TreeSet<Quote>((q1, q2) -> {
            if (q1.getAsk() != q2.getAsk()) {
                return Double.compare(q1.getAsk(), q2.getAsk());
            }

            if (!q1.getTime().equals(q2.getTime())) {
                return q1.getTime().compareTo(q2.getTime());
            }

            return q1.getProvider().compareTo(q2.getProvider());
        });
        minBidQuotes = new TreeSet<Quote>((q1, q2) -> {
            if (q1.getBid() != q2.getBid()) {
                return Double.compare(q1.getBid(), q2.getBid());
            }

            if (!q1.getTime().equals(q2.getTime())) {
                return q1.getTime().compareTo(q2.getTime());
            }

            return q1.getProvider().compareTo(q2.getProvider());

        });

    }

    public void handleBbg(JSONObject bbgJSON) {
        this.bbgJSON = bbgJSON;
        checkIfAllDataArrived();
    }

    public void handleEbs(JSONObject ebsJSON) {
        this.ebsJSON = ebsJSON;
        checkIfAllDataArrived();
    }

    public void handleReu(JSONObject reuJSONI) {
        this.reuJSONI = reuJSONI;
        checkIfAllDataArrived();
    }

    public void checkIfAllDataArrived() {
        if (this.bbgJSON != null && this.ebsJSON != null && this.reuJSONI != null) {
            System.out.println("Checked data");
            handleData(bbgJSON, ebsJSON, reuJSONI);
            this.bbgJSON = null;
            this.ebsJSON = null;
            this.reuJSONI = null;
        }
    }

    public void handleData(JSONObject bbgJSON, JSONObject ebsJSON, JSONObject reuJSONI) {
        double ask;
        double bid;
        //Every second
        try {
            //Instert new data from each provider

            String provider = bbgJSON.getString("provider");
            String time = bbgJSON.getString("0");
            String symbol = bbgJSON.getString("1");
            ask = bbgJSON.getDouble("2");
            bid = bbgJSON.getDouble("3");

            Quote newReu = new Quote(provider, time, symbol, ask, bid);
            reu.offer(newReu);
            maxAskQuotes.add(newReu);
            minBidQuotes.add(newReu);

            provider = ebsJSON.getString("provider");
            time = ebsJSON.getString("0");
            symbol = ebsJSON.getString("1");
            ask = ebsJSON.getDouble("2");
            bid = ebsJSON.getDouble("3");

            Quote newEbs = new Quote(provider, time, symbol, ask, bid);
            ebs.offer(newEbs);
            maxAskQuotes.add(newEbs);
            minBidQuotes.add(newEbs);

            provider = reuJSONI.getString("provider");
            time = reuJSONI.getString("0");
            symbol = reuJSONI.getString("1");
            ask = reuJSONI.getDouble("2");
            bid = reuJSONI.getDouble("3");

            Quote newBbg = new Quote(provider, time, symbol, ask, bid);
            bbg.offer(newBbg);
            maxAskQuotes.add(newBbg);
            minBidQuotes.add(newBbg);

        } catch (JSONException e) {
            System.out.println(e.getStackTrace());
        }


        Quote q = bbg.poll();
        if (!q.getSymbol().equals("X:XX:XX")) {
            maxAskQuotes.remove(q);
            minBidQuotes.remove(q);
        }

        q = ebs.poll();
        if (!q.getSymbol().equals("X:XX:XX")) {
            maxAskQuotes.remove(q);
            minBidQuotes.remove(q);
        }

        q = reu.poll();
        if (!q.getSymbol().equals("X:XX:XX")) {
            maxAskQuotes.remove(q);
            minBidQuotes.remove(q);
        }


        //Get arbitrage profit
        while (true) {
            Quote askQuote = maxAskQuotes.first();
            Quote bidQuote = minBidQuotes.last();

            ask = askQuote.getAsk();
            bid = bidQuote.getBid();

            double profit = bid - ask;

            if (profit > 0) {
                DecimalFormat df = new DecimalFormat("0.0000");
                //socket send
	            /*
				String buyOrder = askQuote.getSymbol() + " B " + df.format(askQuote.getAsk());
				String sellOrder = bidQuote.getSymbol() + " S " + df.format(bidQuote.getBid());
				*/

                maxAskQuotes.pollFirst();
                minBidQuotes.pollLast();

                totalProfit += profit;


                System.out.println(askQuote.getSymbol() + ", S, " + askQuote.getTime() + " " + df.format(askQuote.getAsk()));
            } else {
                break;
            }
        }
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
