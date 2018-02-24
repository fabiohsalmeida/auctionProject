package com.fhsa.auction.comparator;

import java.util.Comparator;

import com.fhsa.auction.domain.Bid;

public class BidComparator implements Comparator<Bid> {

	public int compare(Bid o1, Bid o2) {
		return (o2.getValue().compareTo(o1.getValue()));
	}

}
