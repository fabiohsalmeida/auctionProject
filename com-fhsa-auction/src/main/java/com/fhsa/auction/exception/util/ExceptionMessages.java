package com.fhsa.auction.exception.util;

public class ExceptionMessages {
	public static String BID_EXCEPTION_WITHOUT_USER = "Can't create a new bid without an user bound to it.";
	public static String BID_EXCEPTION_WITHOUT_VALUE = "Can't create a new bid without a value bound to it.";
	public static String BID_EXCEPTION_NEGATIVE_VALUE = "Can't create a new bid with bid's value negative.";
	public static String BID_EXCEPTION_ZERO_VALUE = "Can't create a new bid with bid's value equal to zero.";
	
	public static String AUCTION_EXCEPTION_USER_ALREADY_HAS_FIVE_BIDS = "Isn't possible for an user to propose more than five bids in the same auction";
	public static String AUCTION_EXCEPTION_USER_PROPOSE_BIDS_IN_A_ROW = "Isn't possible for an user to propose more than one bid in a row.";
	public static String AUCTION_EXCEPTION_DOUBLE_BID_OF_USER_WITHOUT_BIDS = "Isn't possible for an user to double his last bid if there isn't a previous bid from this user.";
	public static String AUCTION_EXCEPTION_AUCTION_IS_EMTPY = "Isn't possible to return the last bid of a empty auction.";
	
	public static String AUCTIONEER_EXCEPTION_NONEXISTENT_AUCTION = "Isn't possible to evaluate an nonexistent auction.";
}
