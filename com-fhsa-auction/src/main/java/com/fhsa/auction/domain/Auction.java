package com.fhsa.auction.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Auction {
	private List<Bid> bidsList;
	private String auctionProductName;
	private BigDecimal bidsValueAverage;

	public Auction(String auctionProductName) {
		this.auctionProductName = auctionProductName;
		bidsList = new ArrayList<Bid>();
		bidsValueAverage = BigDecimal.valueOf(0);
	}
	
	public void proposeNewBid(Bid bid) {
		
	}
	
	public List<Bid> getBidsList() {
		return bidsList;
	}

	public String getAuctionProductName() {
		return auctionProductName;
	}

	public BigDecimal getBidsValueAverage() {
		return bidsValueAverage;
	}

	public void doubleBid(User user) {
		// TODO Auto-generated method stub
		
	}

	public Bid getLastBid() {
		// TODO Auto-generated method stub
		return null;
	}
}
