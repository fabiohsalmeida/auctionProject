package com.fhsa.auction.domain;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.fhsa.auction.exception.bid.BidIllegalArgumentException;

public class BidTest {
	private User fabio;
	
	/*
	 * [1] Isn't possible to create a bid without an user bound
	 * [1] Isn't possible to create a bid without a value
	 * [1] Isn't possible to create a bid with negative value
	 * [1] Isn't possible to create a bid with zero value
	 */	
	@Before
	public void setUp() {
		fabio = new User(1, "Fábio");
	}
	
	@Test(expected=BidIllegalArgumentException.class)
	public void notPossibleToCreateBidWithoutUser() {
		new Bid(BigDecimal.valueOf(1), null);
	}
	
	@Test(expected=BidIllegalArgumentException.class)
	public void notPossibleToCreateBidWithouValue() {
		new Bid(null, fabio);
	}
	
	@Test(expected=BidIllegalArgumentException.class)
	public void notPossibleToProposeBidWithNegativeValue() {
		new Bid(BigDecimal.valueOf(-1000), fabio);
	}
	
	@Test(expected=BidIllegalArgumentException.class)
	public void notPossibleToProposeBidWithoutUser() {
		new Bid(BigDecimal.valueOf(0), fabio);
	}
}
