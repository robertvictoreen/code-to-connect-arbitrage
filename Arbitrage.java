/**
 */

import java.util.*;

class Arbitrage {

	Queue<Quote> bbg;
	Queue<Quote> ebs;
	Queue<Quote> reu;


  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);

    bbg = new LinkedList<>();
    ebs = new LinkedList<>();
    reu = new LinkedList<>();
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
