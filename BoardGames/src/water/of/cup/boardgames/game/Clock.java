package water.of.cup.boardgames.game;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Clock extends BukkitRunnable {
	private Game game;

	private HashMap<GamePlayer, Double> playerTimes;
	private double lastTimeChange;

	private GamePlayer turn;
	private boolean sendAllTimes; // if true players are sent all times, otherwise just their own

	public Clock(int clockTime, Game game, boolean sendAllTimes) {
		this.sendAllTimes = sendAllTimes;
		for (GamePlayer gamePlayer : game.getGamePlayers()) {
			playerTimes.put(gamePlayer, (double) clockTime);
		}
		this.game = game;
		turn = game.getTurn();
		lastTimeChange = System.currentTimeMillis() / 1000;
	}

	@Override
	public void run() {

//		// check if runnable needs to stop
//		if (game.getGameState() != ChessGameState.INGAME || game.getBlackPlayer() == null
//				|| game.getWhitePlayer() == null) {
//			this.cancel();
//			return;
//		}

		// change color timers
		double timeDifference = System.currentTimeMillis() / 1000 - lastTimeChange;
		lastTimeChange = System.currentTimeMillis() / 1000;
		turn = game.getTurn();
		playerTimes.put(turn, playerTimes.get(turn) - timeDifference);

		// check if player is out of time
		if (playerTimes.get(turn) < 0) {
			// player out of time
			game.gamePlayerOutOfTime(turn);
			return;
		}

		// send players clock times
		sendPlayersClockTimes();
	}

	public void sendPlayersClockTimes() {
		if (!sendAllTimes) {
			turn = game.getTurn();
			String timeText = (int) (playerTimes.get(turn) / 60) + ":" + (int) (playerTimes.get(turn) % 60);
			turn.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR,
					TextComponent.fromLegacyText(ChatColor.YELLOW + timeText));
			return;
		}

		String timeText = "";
		for (GamePlayer gamePlayer : game.getGamePlayers()) {
			timeText += gamePlayer.getPlayer().getName() + ": " + (int) (playerTimes.get(gamePlayer) / 60) + ":"
					+ (int) (playerTimes.get(gamePlayer) % 60) + " | ";
		}

		timeText = timeText.substring(0, timeText.length() - 2);

		for (GamePlayer gamePlayer : game.getGamePlayers()) {
			gamePlayer.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR,
					TextComponent.fromLegacyText(ChatColor.YELLOW + timeText));
		}
	}

	public void incementTime(GamePlayer gamePlayer, double amount) {
		playerTimes.put(gamePlayer, playerTimes.get(gamePlayer) + amount);
	}
}
