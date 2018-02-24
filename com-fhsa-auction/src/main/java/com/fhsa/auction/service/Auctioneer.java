package com.fhsa.auction.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

import com.fhsa.auction.comparator.BidComparator;
import com.fhsa.auction.domain.Auction;
import com.fhsa.auction.domain.Bid;
import com.fhsa.auction.domain.User;
import com.fhsa.auction.exception.auctioneer.AuctioneerRuntimeException;
import com.fhsa.auction.exception.util.ExceptionMessages;

public class Auctioneer {
	private Auction auction;
	private Bid highestBid;
	private Bid lowestBid;
	private List<Bid> threeHighestsBidList;
	private BigDecimal averageBidsValue;

	public void evaluateAuction(Auction auction) {
		validAuctionWhenEvaluating(auction);

		this.auction = auction;

		collectInformationsFromAuction(auction);
		identifyHighestsBids();
	}

	private void collectInformationsFromAuction(Auction auction) {
		List<Bid> bidsList = auction.getBidsList();
		BigDecimal totalBidsValue = BigDecimal.ZERO;

		lowestBid = auction.getBidsList().get(0);
		highestBid = auction.getBidsList().get(0);

		for (Bid bid : bidsList) {
			totalBidsValue = totalBidsValue.add(bid.getValue());
			if (bid.getValue().compareTo(lowestBid.getValue()) < 0) {
				lowestBid = bid;
			}
			if (bid.getValue().compareTo(highestBid.getValue()) > 0) {
				highestBid = bid;
			}
		}

		averageBidsValue = totalBidsValue.divide(BigDecimal.valueOf(auction.getBidsList().size()),
				RoundingMode.HALF_EVEN);
	}

	public void identifyHighestsBids() {
		threeHighestsBidList = auction.getBidsList();

		Collections.sort(threeHighestsBidList, new BidComparator());

		threeHighestsBidList = generateSubListOfHighestsBids();

	}

	private List<Bid> generateSubListOfHighestsBids() {
		return threeHighestsBidList.subList(0, threeHighestsBidList.size() > 3 ? 3 : threeHighestsBidList.size());
	}

	private void validAuctionWhenEvaluating(Auction auction) {
		if (auction == null) {
			throw new AuctioneerRuntimeException(ExceptionMessages.AUCTIONEER_EXCEPTION_NONEXISTENT_AUCTION);
		} else if (auction.getBidsList().isEmpty()) {
			throw new AuctioneerRuntimeException(ExceptionMessages.AUCTIONEER_EXCEPTION_EMPTY_AUCTION);
		} else {
			return;
		}
	}

	private void validAuctionWhenGettingInformations() {
		if (auction == null) {
			throw new AuctioneerRuntimeException(
					ExceptionMessages.AUCTIONEER_EXCEPTION_UNABLE_TO_GET_INFO_BEFORE_EVALUATE);
		} else {
			return;
		}
	}

	private void validUserBid(Bid bid) {
		if (bid == null) {
			throw new AuctioneerRuntimeException(
					ExceptionMessages.AUCTIONEER_EXCEPTION_GET_HIGHEST_BID_OF_USER_WITHOUT_BIDS);
		} else {
			return;
		}
	}

	public Bid getLowestBid() {
		validAuctionWhenGettingInformations();

		return lowestBid;
	}

	public Bid getHighestBid() {
		validAuctionWhenGettingInformations();

		return highestBid;
	}

	public BigDecimal getAverageBidsValue() {
		validAuctionWhenGettingInformations();

		return averageBidsValue;
	}

	public List<Bid> getThreeHighestsBidList() {
		validAuctionWhenGettingInformations();

		return threeHighestsBidList;
	}

	public Bid getHighestsBidByUser(User user) {
		Bid highestBidOfUser = null;

		validAuctionWhenGettingInformations();

		for (Bid bid : auction.getBidsList()) {
			if (bid.getUser().equals(user)) {
				if (highestBidOfUser == null) {
					highestBidOfUser = bid;
				} else {
					if (highestBidOfUser.getValue().compareTo(bid.getValue()) < 0) {
						highestBidOfUser = bid;
					}
				}
			}
		}

		validUserBid(highestBidOfUser);

		return highestBidOfUser;
	}

	@Override
	public String toString() {
		return "Auctioneer [auction=" + auction + ", highestBid=" + highestBid + ", lowestBid=" + lowestBid
				+ ", threeHighestsBidList=" + threeHighestsBidList + ", averageBidsValue=" + averageBidsValue + "]";
	}

}
