package ru.mamsta.matchmaking;

import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class MatchMaking {
	
	public static final int PLAYERS_IN_MATCH = 8;
	
	private AtomicBoolean status = new AtomicBoolean(false);
	private final CopyOnWriteArrayList<MatchUserData> queue = new CopyOnWriteArrayList<>();
	private final ExecutorService exec = Executors.newSingleThreadExecutor();
	
	public void setMatchData(final long userId, final int rank, final long enterTime) {
		final MatchUserData data = new MatchUserData(userId, rank, enterTime);
		getQueue().add(data);
		if (getQueue().size() >= PLAYERS_IN_MATCH && !status.get()) {
			status.getAndSet(true);
			
			exec.submit(() -> {
				final MatchUserData[] matchList = new MatchUserData[PLAYERS_IN_MATCH];
				while(true) {
					matchList[0] = getQueue().remove(0);
					getQueue().forEach(matchUserData -> {
						for(int i = 0; i < matchList.length; i++) {
							final MatchUserData matchUserDataInTeam = matchList[i];
							if(matchUserDataInTeam == null) {
								matchList[i] = matchUserData;
								break;
							}
							final long time = new Date().getTime();
							final long waitingTimeA = time - matchUserDataInTeam.getEnterTime();
							final long waitingTimeB = time - matchUserData.getEnterTime();
							final double index = Math.abs(matchUserDataInTeam.getUserRank() - matchUserData.getUserRank());
							if(index > (waitingTimeA / 5000) || index > (waitingTimeB / 5000))  {
								break;
							}
						}
						if (matchList[matchList.length - 1] != null) {
							return;
						}
					});
					if (matchList[matchList.length - 1] != null) {
						for(int i = 1; i < matchList.length; i++) {
							getQueue().remove(matchList[i]);
						}
						System.out.println("The team was formed at " + MatchUserData.dateFormat.format(new Date()) + ", users: " + Arrays.toString(matchList) + "\n");
						if (getQueue().size() < PLAYERS_IN_MATCH) {
							status.getAndSet(false);
							break;
						}
					} else {
						getQueue().add(matchList[0]);
					}

					Arrays.fill(matchList, null);
				}
			});
		}
	}
	
	public CopyOnWriteArrayList<MatchUserData> getQueue() {
		return queue;
	}
	
	public void stopService() {
		exec.shutdown();
	}
}
