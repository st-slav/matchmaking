package ru.mamsta.matchmaking;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MatchUserData {

	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yy hh:mm:ss");

	private long userId;
	private int userRank;
	private long enterTime;

	public MatchUserData(final long userId, final int userRank, final long enterTime) {
		super();
		this.userId = userId;
		this.userRank = userRank;
		this.enterTime = enterTime;
	}

	public long getUserId() {
		return userId;
	}

	public int getUserRank() {
		return userRank;
	}

	public long getEnterTime() {
		return enterTime;
	}

	@Override
	public String toString() {
		return "User [Id=" + userId + ", Rank=" + userRank + ", Time=" + dateFormat.format(new Date(enterTime)) + "]";
	}
}
