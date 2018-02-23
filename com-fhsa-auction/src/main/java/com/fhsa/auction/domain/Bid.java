package com.fhsa.auction.domain;

import java.math.BigDecimal;

import com.fhsa.auction.exception.bid.BidIllegalArgumentException;
import com.fhsa.auction.exception.util.ExceptionMessages;

public class Bid {
	private BigDecimal value;
	private User user;
	
	public Bid(BigDecimal value, User user) {
		validBidsParameters(value, user);
		this.value = value;
		this.user = user;
	}

	private void validBidsParameters(BigDecimal value, User user) {
		if(user == null) {
			throw new BidIllegalArgumentException(ExceptionMessages.BID_EXCEPTION_WITHOUT_USER);
		} else if (value == null) {
			throw new BidIllegalArgumentException(ExceptionMessages.BID_EXCEPTION_WITHOUT_VALUE);
		} else if (value.compareTo(BigDecimal.ZERO)<0) {
			throw new BidIllegalArgumentException(ExceptionMessages.BID_EXCEPTION_NEGATIVE_VALUE);
		} else if (value.compareTo(BigDecimal.ZERO)==0) {
			throw new BidIllegalArgumentException(ExceptionMessages.BID_EXCEPTION_ZERO_VALUE);
		} else {
			return;
		}
	}
	
	public BigDecimal getValue() {
		return value;
	}

	public User getUser() {
		return user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bid other = (Bid) obj;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
}
