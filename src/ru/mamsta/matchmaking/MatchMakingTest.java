package ru.mamsta.matchmaking;

import java.util.Date;

public class MatchMakingTest {

	private static final int MIN = 1;
	private static final int MAX = 30;
	private static final int COUNT_MATCH = 2;
	private static long id = 0;

	private static long getId() {
		return ++id;
	}

	private static long getTime() {
		return new Date().getTime();
	}

	private static int generateRank() {
		int m = MAX - MIN;
		return (int) ((Math.random() * ++m) + MIN);
	}

	public static void main(final String[] args) throws InterruptedException {
		final MatchMaking matchMaking = new MatchMaking();
		for (int i = 0; i < (COUNT_MATCH * MatchMaking.PLAYERS_IN_MATCH); i++) {
			matchMaking.setMatchData(getId(), generateRank(), getTime());
		}
		Thread.sleep(140000);
		for (int i = 0; i < (COUNT_MATCH * MatchMaking.PLAYERS_IN_MATCH); i++) {
			matchMaking.setMatchData(getId(), generateRank(), getTime());
		}
		Thread.sleep(30000);
		for (int i = 0; i < (COUNT_MATCH * MatchMaking.PLAYERS_IN_MATCH); i++) {
			matchMaking.setMatchData(getId(), generateRank(), getTime());
		}
		Thread.sleep(30000);
		for (int i = 0; i < (COUNT_MATCH * MatchMaking.PLAYERS_IN_MATCH); i++) {
			matchMaking.setMatchData(getId(), generateRank(), getTime());
		}
		for (int i = 0; i < 4; i++) {
			matchMaking.setMatchData(getId(), generateRank(), getTime());
		}
		Thread.sleep(140000);
		for (int i = 0; i < 4; i++) {
			matchMaking.setMatchData(getId(), generateRank(), getTime());
		}
		matchMaking.stopService();
	}
}
