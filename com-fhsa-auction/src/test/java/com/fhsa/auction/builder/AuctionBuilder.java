package com.fhsa.auction.builder;

import java.math.BigDecimal;

import com.fhsa.auction.domain.Auction;
import com.fhsa.auction.domain.Bid;
import com.fhsa.auction.domain.User;

public class AuctionBuilder {
	private Auction auction;
	
	public AuctionBuilder createNewAuctionTo(String auctionProductName) {
		this.auction = new Auction(auctionProductName);
		return this;
	}
	
	public AuctionBuilder proposeBid(BigDecimal value, User user) {
		auction.proposeNewBid(new Bid(value, user));
		return this;
	}
	
	public AuctionBuilder doubleBid(User user) {
		auction.doubleBid(user);
		return this;
	}
	
	public Auction build() {
		return auction;
	}
}
