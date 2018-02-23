package com.fhsa.auction.domain;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.fhsa.auction.builder.AuctionBuilder;
import com.fhsa.auction.exception.auction.AuctionRuntimeException;

public class AuctionTest {
	private User fabio;
	private User catherine;
	private User anna;
	private User lucas;

	@Before
	public void setUp() {
		fabio = new User(1, "Fábio");
		catherine = new User(2, "Catherine");
		anna = new User(3, "Anna");
		lucas = new User(4, "Lucas");
	}

	/* Condictions List:
	 * [1] Isn't possible for an user to propose more than five bids
	 * [1] Isn't possible for an user to propose more than one bid in a row
	 * [3] It's possible for to propose bid
	 * [1] Isn't possible for an user to double his last bid if that user had never propose a bid
	 * [1] Isn't possible for an user to double his last bid if that user was the last user to propose a bid
	 * [1] Isn't possible for an user to double his last bid if that user has already propose five bids
	 * [2] It's possible to double the last bid from a user
	 * [2] It's possible to get the last bid from a auction
	 * [1] Isn't possible to get the last bid from a empty auction
	 */

	@Test(expected=AuctionRuntimeException.class)
	public void notPossbileToHaveMoreThanFiveBidsPerUser() {
		new AuctionBuilder().createNewAuctionTo("Computer")
		.proposeBid(BigDecimal.valueOf(1000.00), fabio)
		.proposeBid(BigDecimal.valueOf(7000.00), catherine)
		.proposeBid(BigDecimal.valueOf(4000.00), fabio)
		.proposeBid(BigDecimal.valueOf(1000.00), anna)
		.proposeBid(BigDecimal.valueOf(2000.00), fabio)
		.proposeBid(BigDecimal.valueOf(5000.00), lucas)
		.proposeBid(BigDecimal.valueOf(9000.00), fabio)
		.proposeBid(BigDecimal.valueOf(6000.00), catherine)
		.proposeBid(BigDecimal.valueOf(2100.00), fabio)
		.proposeBid(BigDecimal.valueOf(4000.00), catherine)
		.proposeBid(BigDecimal.valueOf(11000.99), fabio);
	}

	@Test(expected=AuctionRuntimeException.class)
	public void notPossibleToHaveMoreThanOneBidFromSameUserInARow() {
		new AuctionBuilder().createNewAuctionTo("Cup")
		.proposeBid(BigDecimal.valueOf(2.50), fabio)
		.proposeBid(BigDecimal.valueOf(7.00), fabio);		
	}

	@Test
	public void isPossibleForAnUserProposeBid() {
		Auction action = new AuctionBuilder().createNewAuctionTo("TV")
				.proposeBid(BigDecimal.valueOf(400.59), fabio)
				.build();

		assertThat(action.getBidsList(), hasItems(
				new Bid(BigDecimal.valueOf(400.59), fabio)
				));
	}

	@Test
	public void isPossibleForManyUsersProposeBid() {
		Auction action = new AuctionBuilder().createNewAuctionTo("TV")
				.proposeBid(BigDecimal.valueOf(400.59), fabio)
				.proposeBid(BigDecimal.valueOf(500.11), anna)
				.proposeBid(BigDecimal.valueOf(600.52), lucas)
				.proposeBid(BigDecimal.valueOf(700.99), catherine)
				.build();
		
		assertThat(action.getBidsList(), hasItems(
				new Bid(BigDecimal.valueOf(400.59), fabio),
				new Bid(BigDecimal.valueOf(500.11), anna),
				new Bid(BigDecimal.valueOf(600.52), lucas),
				new Bid(BigDecimal.valueOf(700.99), catherine)
				));
	}

	@Test
	public void isPossibleForManyUsersProposeManyBids() {
		Auction action = new AuctionBuilder().createNewAuctionTo("Smartphone")
				.proposeBid(BigDecimal.valueOf(800.02), fabio)
				.proposeBid(BigDecimal.valueOf(810.90), anna)
				.proposeBid(BigDecimal.valueOf(825.00), fabio)
				.proposeBid(BigDecimal.valueOf(850.50), anna)
				.proposeBid(BigDecimal.valueOf(890.99), catherine)
				.proposeBid(BigDecimal.valueOf(1110.52), lucas)
				.proposeBid(BigDecimal.valueOf(1500.11), anna)
				.proposeBid(BigDecimal.valueOf(1501.12), fabio)
				.proposeBid(BigDecimal.valueOf(1700.00), catherine)
				.build();
		
		assertThat(action.getBidsList(), hasItems(
				new Bid(BigDecimal.valueOf(800.02), fabio),
				new Bid(BigDecimal.valueOf(810.90), anna),
				new Bid(BigDecimal.valueOf(825.00), fabio),
				new Bid(BigDecimal.valueOf(850.50), anna),
				new Bid(BigDecimal.valueOf(890.99), catherine),
				new Bid(BigDecimal.valueOf(1110.52), lucas),
				new Bid(BigDecimal.valueOf(1500.11), anna),
				new Bid(BigDecimal.valueOf(1501.12), fabio),
				new Bid(BigDecimal.valueOf(1700.00), catherine)
				));
		
	}
	
	@Test(expected=AuctionRuntimeException.class)
	public void notPossibleToDoubleBidOfUserWithoutPreviousBids() {
		new AuctionBuilder().createNewAuctionTo("Cup")
		.doubleBid(fabio);
	}

	@Test(expected=AuctionRuntimeException.class)
	public void notPossibleToDoubleBidFromUserThatGaveLastBid() {
		new AuctionBuilder().createNewAuctionTo("Keyboard")
		.proposeBid(BigDecimal.valueOf(30.00), fabio)
		.doubleBid(fabio);
	}

	@Test(expected=AuctionRuntimeException.class)
	public void notPossibleToDoubleBidFromUserThatAlreadyHaveFiveBids() {
		new AuctionBuilder().createNewAuctionTo("Keyboard")
		.proposeBid(BigDecimal.valueOf(30.00), fabio)
		.proposeBid(BigDecimal.valueOf(30.00), catherine)
		.proposeBid(BigDecimal.valueOf(30.00), fabio)
		.proposeBid(BigDecimal.valueOf(30.00), catherine)
		.proposeBid(BigDecimal.valueOf(30.00), fabio)
		.proposeBid(BigDecimal.valueOf(30.00), catherine)
		.proposeBid(BigDecimal.valueOf(30.00), fabio)
		.proposeBid(BigDecimal.valueOf(30.00), catherine)
		.proposeBid(BigDecimal.valueOf(30.00), fabio)
		.proposeBid(BigDecimal.valueOf(30.00), catherine)
		.doubleBid(fabio);
	}

	@Test
	public void isPossibleToGetLastBidFromAnActionWithOneBid() {
		Auction auction = new AuctionBuilder().createNewAuctionTo("Mouse")
				.proposeBid(BigDecimal.valueOf(40.40), fabio)
				.build();
		
		assertThat(auction.getLastBid(), is(equalTo(new Bid(BigDecimal.valueOf(40.40), fabio))));
	}

	public void isPossibleToDoubleBidFromUserInActionWithOneBid() {
		Auction auction = new AuctionBuilder().createNewAuctionTo("Bottle of Water")
				.proposeBid(BigDecimal.valueOf(1.00), fabio)
				.proposeBid(BigDecimal.valueOf(1.10), anna)
				.doubleBid(fabio)
				.build();
		
		assertThat(auction.getBidsList(), hasItems(
				new Bid(BigDecimal.valueOf(1.00), fabio),
				new Bid(BigDecimal.valueOf(1.10), anna),
				new Bid(BigDecimal.valueOf(2.00), fabio)			
				));
	}

	public void isPossibleToDoubleBidFromUsersInActionWithManyBids() {
		Auction auction = new AuctionBuilder().createNewAuctionTo("Bottle of Water")
				.proposeBid(BigDecimal.valueOf(1.00), fabio)
				.proposeBid(BigDecimal.valueOf(1.15), anna)
				.doubleBid(fabio)
				.proposeBid(BigDecimal.valueOf(1.25), catherine)
				.doubleBid(anna)
				.proposeBid(BigDecimal.valueOf(2.00), lucas)
				.doubleBid(catherine)
				.doubleBid(lucas)
				.build();
		
		assertThat(auction.getBidsList(), hasItems(
				new Bid(BigDecimal.valueOf(1.00), fabio),
				new Bid(BigDecimal.valueOf(1.15), anna),
				new Bid(BigDecimal.valueOf(2.00), fabio),
				new Bid(BigDecimal.valueOf(1.25), catherine),
				new Bid(BigDecimal.valueOf(2.30), anna),
				new Bid(BigDecimal.valueOf(2.00), lucas),
				new Bid(BigDecimal.valueOf(2.50), catherine),
				new Bid(BigDecimal.valueOf(4.00), lucas)
				));
	}
	
	@Test
	public void isPossibleToGetLastBidFromAnActionWithManyBids() {
		Auction auction = new AuctionBuilder().createNewAuctionTo("Mouse")
				.proposeBid(BigDecimal.valueOf(40.40), fabio)
				.proposeBid(BigDecimal.valueOf(45.40), anna)
				.proposeBid(BigDecimal.valueOf(50.40), catherine)
				.proposeBid(BigDecimal.valueOf(51.00), fabio)
				.proposeBid(BigDecimal.valueOf(52.50), lucas)
				.proposeBid(BigDecimal.valueOf(53.00), fabio)
				.build();
		
		assertThat(auction.getLastBid(), is(equalTo(new Bid(BigDecimal.valueOf(53.00), fabio))));
	}

	@Test(expected=AuctionRuntimeException.class)
	public void notPossibleToGetLastBidFromAnActionWithoutBids() {
		Auction auction = new AuctionBuilder().createNewAuctionTo("Cap")
				.build();
		
		auction.getLastBid();
	}

}
