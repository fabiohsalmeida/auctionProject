package com.fhsa.auction.service;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.fhsa.auction.builder.AuctionBuilder;
import com.fhsa.auction.domain.Auction;
import com.fhsa.auction.domain.Bid;
import com.fhsa.auction.domain.User;
import com.fhsa.auction.exception.auctioneer.AuctioneerRuntimeException;

public class AuctioneerTest {
	private Auctioneer auctioneer;
	private User fabio;
	private User catherine;
	private User anna;
	private User lucas;

	@Before
	public void setUp() {
		auctioneer = new Auctioneer();
		fabio = new User(1, "Fábio");
		catherine = new User(2, "Catherine");
		anna = new User(3, "Anna");
		lucas = new User(4, "Lucas");
	}	

	/*
	 * [1] It's possible to get the average of values from a auction after evaluate an auction
	 * [1] Isn't possible to get average of values from a auction before evaluate an auction
	 * [3] It's possible to get the three highest bids from a auction after evaluate
	 * [1] Isn't possible to get the three highest bids from a auction before evaluate
	 * [2] It's possible to get the highest bids from each user after evaluate
	 * [1] Isn's possible to get the highest bids from each user before evaluate
	 * [1] Isn't possible to evaluate an nonexistent auction
	 * [3] It's possible to evaluate auction (get lowest bid and highest bid)
	 */
	@Test
	public void isPossibleToGetAvarageOfBidsValue() {
		Auction auction = new AuctionBuilder().createNewAuctionTo("Window")
				.proposeBid(BigDecimal.valueOf(100.00), fabio)
				.proposeBid(BigDecimal.valueOf(200.00), catherine)
				.proposeBid(BigDecimal.valueOf(300.00), lucas)
				.build();

		auctioneer.evaluateAuction(auction);

		assertThat(auctioneer.getAverageBidsValue(), is(equalTo(BigDecimal.valueOf(200.00))));
	}

	@Test(expected=AuctioneerRuntimeException.class)
	public void notPossibleToGetAvarageOfBidsValueBeforeEvaluate() {
		auctioneer.getAverageBidsValue();
	}

	@Test
	public void isPossibleToGetThreeHighestBidsFromAuctionWithLessThanThreeBids() {
		Auction auction = new AuctionBuilder().createNewAuctionTo("Xbox 360 Controller")
				.proposeBid(BigDecimal.valueOf(50.00), fabio)
				.build();

		auctioneer.evaluateAuction(auction);

		assertThat(auctioneer.getHighestsBidsFromAuction(), hasItems(
				new Bid(BigDecimal.valueOf(50.00), fabio)
				));
	}

	@Test
	public void isPossibleToGetThreeHighestBidsFromAuctionWithMoreThanThreeBids() {
		Auction auction = new AuctionBuilder().createNewAuctionTo("Xbox 360 Controller")
				.proposeBid(BigDecimal.valueOf(50.00), fabio)
				.proposeBid(BigDecimal.valueOf(55.00), anna)
				.proposeBid(BigDecimal.valueOf(59.00), catherine)
				.proposeBid(BigDecimal.valueOf(60.00), lucas)
				.proposeBid(BigDecimal.valueOf(61.00), fabio)
				.build();

		auctioneer.evaluateAuction(auction);

		assertThat(auctioneer.getHighestsBidsFromAuction(), hasItems(
				new Bid(BigDecimal.valueOf(59.00), catherine),
				new Bid(BigDecimal.valueOf(60.00), lucas),
				new Bid(BigDecimal.valueOf(61.00), fabio)
				));		
	}

	@Test
	public void isPossibleToGetThreeHighestBidsInRandomOrder() {
		Auction auction = new AuctionBuilder().createNewAuctionTo("Xbox 360 Controller")
				.proposeBid(BigDecimal.valueOf(61.00), fabio)
				.proposeBid(BigDecimal.valueOf(55.00), anna)
				.proposeBid(BigDecimal.valueOf(59.00), catherine)
				.proposeBid(BigDecimal.valueOf(60.00), lucas)
				.proposeBid(BigDecimal.valueOf(50.00), fabio)
				.build();

		auctioneer.evaluateAuction(auction);

		assertThat(auctioneer.getHighestsBidsFromAuction(), hasItems(
				new Bid(BigDecimal.valueOf(59.00), catherine),
				new Bid(BigDecimal.valueOf(60.00), lucas),
				new Bid(BigDecimal.valueOf(61.00), fabio)
				));		
	}

	@Test(expected=AuctioneerRuntimeException.class)
	public void notPossibleToGetHighestsBidsBeforeEvaluate() {
		auctioneer.getHighestsBidsFromAuction();
	}
	
	@Test
	public void isPossibleToGetHighestsBidByUserWithOneBid() {
		Auction auction = new AuctionBuilder().createNewAuctionTo("Glasses")
				.proposeBid(BigDecimal.valueOf(20.00), anna)
				.build();
		
		auctioneer.evaluateAuction(auction);
		
		assertThat(auctioneer.getHighestsBidByUser(anna), is(equalTo(new Bid(BigDecimal.valueOf(20.00), anna))));
	}
	
	@Test
	public void isPossibleToGetHighestsBidByUserWithManyBids() {
		Auction auction = new AuctionBuilder().createNewAuctionTo("Glasses")
				.proposeBid(BigDecimal.valueOf(70.00), fabio)
				.proposeBid(BigDecimal.valueOf(55.00), anna)
				.proposeBid(BigDecimal.valueOf(90.00), catherine)
				.build();
		
		auctioneer.evaluateAuction(auction);
		
		assertThat(auctioneer.getHighestsBidByUser(anna), is(equalTo(new Bid(BigDecimal.valueOf(55.00), anna))));
	}

	@Test(expected=AuctioneerRuntimeException.class)
	public void notPossibleToGetHighestsBidByUserBeforeEvaluate() {
		auctioneer.getHighestsBidByUser(fabio);
	}
	
	@Test
	public void isPossibleToEvaluateAuctionWithBidsInAscendingOrder() {
		Auction auction = new AuctionBuilder().createNewAuctionTo("Mirror")
				.proposeBid(BigDecimal.valueOf(30.00), fabio)
				.proposeBid(BigDecimal.valueOf(40.00), anna)
				.proposeBid(BigDecimal.valueOf(50.00), catherine)
				.proposeBid(BigDecimal.valueOf(60.00), lucas)
				.build();
		
		auctioneer.evaluateAuction(auction);

		assertThat(auctioneer.getLowestBid(), is(equalTo(new Bid(BigDecimal.valueOf(30.00), fabio))));
		assertThat(auctioneer.getHighestBid(), is(equalTo(new Bid(BigDecimal.valueOf(60.00), fabio))));
	}
	
	@Test
	public void isPossibleToEvaluateAuctionWithBidsInDescendingOrder() {
		Auction auction = new AuctionBuilder().createNewAuctionTo("Mirror")
				.proposeBid(BigDecimal.valueOf(60.00), lucas)
				.proposeBid(BigDecimal.valueOf(50.00), catherine)
				.proposeBid(BigDecimal.valueOf(40.00), anna)
				.proposeBid(BigDecimal.valueOf(30.00), fabio)
				.build();
		
		auctioneer.evaluateAuction(auction);

		assertThat(auctioneer.getLowestBid(), is(equalTo(new Bid(BigDecimal.valueOf(30.00), fabio))));
		assertThat(auctioneer.getHighestBid(), is(equalTo(new Bid(BigDecimal.valueOf(60.00), fabio))));
		
	}
	
	@Test
	public void isPossibleToEvaluateAuctionWithBidsInRandomOrder() {
		Auction auction = new AuctionBuilder().createNewAuctionTo("Mirror")
				.proposeBid(BigDecimal.valueOf(60.00), lucas)
				.proposeBid(BigDecimal.valueOf(40.00), anna)
				.proposeBid(BigDecimal.valueOf(30.00), fabio)
				.proposeBid(BigDecimal.valueOf(50.00), catherine)
				.build();
		
		auctioneer.evaluateAuction(auction);

		assertThat(auctioneer.getLowestBid(), is(equalTo(new Bid(BigDecimal.valueOf(30.00), fabio))));
		assertThat(auctioneer.getHighestBid(), is(equalTo(new Bid(BigDecimal.valueOf(60.00), fabio))));
		
	}
}
