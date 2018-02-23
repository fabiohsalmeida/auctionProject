package com.fhsa.auction.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fhsa.auction.exception.auction.AuctionRuntimeException;
import com.fhsa.auction.exception.util.ExceptionMessages;

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
		validNewBidPropose(bid);
		
		bidsList.add(bid);
	}
	
	private void validNewBidPropose(Bid bid) {
		if(!bidsList.isEmpty()) {
			User user = bid.getUser();
			if(this.getNumberOfBidByUser(user)==5) {
				throw new AuctionRuntimeException(ExceptionMessages.AUCTION_EXCEPTION_USER_ALREADY_HAS_FIVE_BIDS);
			} else if(this.getLastBid().getUser().equals(user)) {
				throw new AuctionRuntimeException(ExceptionMessages.AUCTION_EXCEPTION_USER_PROPOSE_BIDS_IN_A_ROW);
			}
		}
	}
	
	private int getNumberOfBidByUser(User user) {
		int numberOfBids = 0;
		for(Bid bid : bidsList) {
			if(bid.getUser().equals(user)) {
				numberOfBids += 1;
			}
		}
		
		return numberOfBids;
	}

	public void doubleBid(User user) {
		Bid lastBidOfUser = this.getLastBidOfUser(user);
		
		if(lastBidOfUser==null) {
			throw new AuctionRuntimeException(ExceptionMessages.AUCTION_EXCEPTION_DOUBLE_BID_OF_USER_WITHOUT_BIDS);
		} else {
			this.proposeNewBid(new Bid(lastBidOfUser.getValue().multiply(BigDecimal.valueOf(2)), user));
		}
	}

	public Bid getLastBid() {
		if(bidsList.isEmpty()) {
			throw new AuctionRuntimeException(ExceptionMessages.AUCTION_EXCEPTION_AUCTION_IS_EMTPY);
		} else {
			return bidsList.get(bidsList.size()-1);
		}
	}

	public Bid getLastBidOfUser(User user) {
		Bid lastBidOfUser = null;
		for(Bid bid : bidsList) {
			if(bid.getUser().equals(user)) {
				lastBidOfUser = bid;
			}
		}
		
		return lastBidOfUser;
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
}
