package water.of.cup.boardgames.game;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;

import water.of.cup.boardgames.BoardGames;
import water.of.cup.boardgames.game.inventories.GameInventory;
import water.of.cup.boardgames.game.maps.GameMap;
import water.of.cup.boardgames.game.maps.MapData;
import water.of.cup.boardgames.game.maps.MapManager;
import water.of.cup.boardgames.game.maps.Screen;
import water.of.cup.boardgames.game.storage.GameStorage;
import water.of.cup.boardgames.game.teams.TeamManager;
import water.of.cup.boardgames.game.wagers.WagerManager;

public abstract class Game {
	private static NamespacedKey gameIdKey;
	private static NamespacedKey gameNameKey;

	protected int gameId;
	protected String gameName;
	protected MapManager mapManager;
	protected ArrayList<Screen> screens;
//	protected HashMap<Player, GamePlayer> players;
	private int turn;
	private boolean ingame;
	protected GameImage gameImage;
	protected ArrayList<Button> buttons;
	protected WagerManager wagerManager;
	protected Clock clock;
	protected GameInventory gameInventory;
	protected TeamManager teamManager;
	protected GameStorage gameStorage;

	protected ArrayList<GameMap> gameMaps; // game maps for the game
	// public HashMap<Integer, Integer> mapValMapIds; // <mapval, mapid>
	protected int[][] mapStructure; // the structure of mapVals, 0 for missing map
	protected int placedMapVal; // the value of the map at the placed board location

	private HashMap<String, Object> gameData;

	protected abstract void setMapInformation(int rotation); // set mapStructure and placedMapVal

	private void setGameId() {
		gameId = BoardGames.getInstance().getGameManager().nextGameId();
	}

	protected abstract void startGame();

	protected abstract void setGameName(); // set gameName

	protected abstract void setBoardImage(); // set board Image

	protected abstract void startClock();

	protected abstract GameInventory getGameInventory();

	protected abstract GameStorage getGameStorage();

	public abstract ArrayList<String> getTeamNames();

	public Game(int rotation) {
		screens = new ArrayList<Screen>();
		setMapInformation(rotation);
		assert placedMapVal != 0;
		createMapManager(rotation);
		setGameId();
		assert gameId != 0;
		setGameName();
		assert gameName != null;
		setBoardImage();
		// assert boardImage != null;
		wagerManager = new WagerManager();
		turn = 0;
		buttons = new ArrayList<Button>();
		teamManager = new TeamManager(this);

		// mapValMapIds = new HashMap<Integer, Integer>();
		gameMaps = new ArrayList<GameMap>();

		gameInventory = getGameInventory();
		gameStorage = getGameStorage();

		gameData = new HashMap<>();
	}

	abstract public void click(Player player, double[] loc, ItemStack map);

	public boolean canPlaceBoard(Location loc, int rotation) {
		int[] centerLoc = mapManager.getMapValsLocationOnRotatedBoard(placedMapVal);
		int[] mapDimensions = mapManager.getRotatedDimensions();

		// calculate map bounds
		int t1X = -centerLoc[0];
		int t2X = mapDimensions[0] + t1X;

		int t1Y = 0;
		int t2Y = 0; // for future changes

		int t1Z = -centerLoc[1];
		int t2Z = mapDimensions[1] + t1Z;

		// calculate min and max bounds
		int maxX = Math.max(t1X, t2X);
		int minX = Math.min(t1X, t2X);

		int maxY = Math.max(t1Y, t2Y);
		int minY = Math.min(t1Y, t2Y);

		int maxZ = Math.max(t1Z, t2Z);
		int minZ = Math.min(t1Z, t2Z);

		// check if blocks are empty
		for (int x = minX; x <= maxX; x++) {
			for (int y = minY; y <= maxY; y++) {
				for (int z = minZ; z <= maxZ; z++) {
					if (!loc.getWorld().getBlockAt(loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z)
							.isEmpty())
						return false;
				}
			}
		}

		return true;
	}

	public void placeBoard(Location loc, int rotation) {
		// can place board should always be called first

		World world = loc.getWorld();

		int[] centerLoc = mapManager.getMapValsLocationOnRotatedBoard(placedMapVal);
		int[] mapDimensions = mapManager.getRotatedDimensions();

		// calculate map bounds
		int t1X = -centerLoc[0];
		int t2X = mapDimensions[0] + t1X;

		int t1Y = 0;
		int t2Y = 0; // for future changes

		int t1Z = -centerLoc[1];
		int t2Z = mapDimensions[1] + t1Z;

		// calculate min and max bounds
		int maxX = Math.max(t1X, t2X);
		int minX = Math.min(t1X, t2X);

		int maxY = Math.max(t1Y, t2Y);
		int minY = Math.min(t1Y, t2Y);

		int maxZ = Math.max(t1Z, t2Z);
		int minZ = Math.min(t1Z, t2Z);

//		// place barriers:
//		for (int x = minX; x <= maxX; x++)
//			for (int y = minY; y <= maxY; y++)
//				for (int z = minZ; z <= maxZ; z++) {
//					Location barrierLoc = new Location(loc.getWorld(), loc.getBlockX() + x, loc.getBlockY() + y,
//							loc.getBlockZ() + z);
//					barrierLoc.getBlock().setType(Material.BARRIER);
//				}

		// spawn ItemFrames
		for (int x = minX; x <= maxX; x++) {
			for (int y = minY; y <= maxY; y++) {
				for (int z = minZ; z <= maxZ; z++) {
					Bukkit.getLogger().info("placed");
					for (MapData mapData : mapManager.getMapDataAtLocationOnRotatedBoard(x - t1X, z - t1Z, y - t1Y)) {

						int mapVal = mapData.getMapVal();
						// skip maps with value 0
						if (mapVal <= 0)
							continue;

						// create the map
						GameMap map = new GameMap(this, mapVal, new ItemStack(Material.FILLED_MAP, 1));

						// set the mapView
						MapView mapView = Bukkit.createMap(world);
						MapMeta mapMeta = (MapMeta) map.getItemMeta();
						mapMeta.setMapView(mapView);
						map.setItemMeta(mapMeta);
						// mapValMapIds.put(mapVal, mapView.getId());
						gameMaps.add(map);

						// spawn itemFrame
						Location frameLoc = new Location(loc.getWorld(), loc.getBlockX() + x, loc.getBlockY() + y,
								loc.getBlockZ() + z);

						// set world preconditions so the item frame can spawn
						frameLoc.getBlock().setType(Material.AIR);
						Block placedOn = frameLoc.getBlock().getRelative(mapData.getBlockFace().getOppositeFace());
						if (placedOn.getType() == Material.AIR)
							placedOn.setType(Material.BARRIER);
						
						ItemFrame frame = world.spawn(frameLoc, ItemFrame.class);
						frame.setItem(map);
						frame.setFacingDirection(mapData.getBlockFace(), true);
						frame.setInvulnerable(true);
						frame.setFixed(true);
						frame.setVisible(true);
						
						frameLoc.getBlock().setType(Material.BARRIER);
					}

				}
			}
		}

		// Debug
		if(!hasGameInventory()) {
			startGame();
		} else {
			renderInitial();
		}
	}

	public void renderInitial() {
		mapManager.renderBoard();
	}

	public void endGame(GamePlayer winner) {
		ingame = false;
		wagerManager.completeWagers(winner);
		clearGamePlayers();

		// Ensure map gets cleared
		renderInitial();
	}

	public void setInGame() {
		ingame = true;
	}

	public boolean isIngame() {
		return ingame;
	}

	protected Button getClickedButton(GamePlayer gamePlayer, int[] loc) { // returns null if no button is clicked
		for (Button button : buttons) {
			if (button.clicked(gamePlayer, loc))
				return button;
		}
		return null;
	}
	
	public boolean hasPlayer(Player player) {
		return teamManager.getGamePlayer(player) != null;
	}

	public ArrayList<Game> getPlayerQueue() {
		return null;
	}

	public ArrayList<Game> getPlayerDecideQueue() {
		return null;
	}

	public int getGameId() {
		return gameId;
	}

	public GamePlayer getGamePlayer(Player player) {
		return teamManager.getGamePlayer(player);
	}

	public ArrayList<GamePlayer> getGamePlayers() {
		return teamManager.getGamePlayers();
	}

	public GamePlayer addPlayer(Player player, String team) {
		if(teamManager.getGamePlayer(player) != null) {
			return teamManager.getGamePlayer(player);
		}

		GamePlayer newPlayer = new GamePlayer(player);
		if(team == null) {
			teamManager.addTeam(newPlayer);
		} else {
			teamManager.addTeam(newPlayer, team);
		}

		return newPlayer;
	}

	public GamePlayer addPlayer(Player player) {
		return addPlayer(player, null);
	}

	public void removePlayer(Player player) {
		teamManager.removeTeamByPlayer(player);
	}

	public void clearGamePlayers() {
		teamManager.resetTeams();
	}

	public GamePlayer getTurn() {
		return teamManager.getTurnPlayer();
	}

	protected abstract void gamePlayerOutOfTime(GamePlayer turn);

	public static NamespacedKey getGameIdKey() {
		return gameIdKey;
	}

	public static void setGameIdKey(NamespacedKey gameIdKey) {
		Game.gameIdKey = gameIdKey;
	}

	protected void createMapManager(int rotation) {
		mapManager = new MapManager(mapStructure, rotation, this);
	}

	public boolean destroy(ItemFrame gameFrame) {
		if (!GameMap.isGameMap(gameFrame.getItem()))
			return false;

		// destroy board
		GameMap gameMap = new GameMap(gameFrame.getItem());
		int mapVal = gameMap.getMapVal();
		destroyBoard(gameFrame.getLocation(), mapVal);

		delete();
		return true;
	}

	private void destroyBoard(Location loc, int startMapVal) {
		int[] centerLoc = mapManager.getMapValsLocationOnRotatedBoard(startMapVal);
		int[] mapDimensions = mapManager.getRotatedDimensions();

		// calculate map bounds
		int t1X = -centerLoc[0];
		int t2X = mapDimensions[0] + t1X;

		int t1Y = 0;
		int t2Y = 0; // for future changes

		int t1Z = -centerLoc[1];
		int t2Z = mapDimensions[1] + t1Z;

		// calculate min and max bounds
		int maxX = Math.max(t1X, t2X);
		int minX = Math.min(t1X, t2X);

		int maxY = Math.max(t1Y, t2Y);
		int minY = Math.min(t1Y, t2Y);

		int maxZ = Math.max(t1Z, t2Z);
		int minZ = Math.min(t1Z, t2Z);

		// destroy blocks
		for (int x = minX; x <= maxX; x++) {
			for (int y = minY; y <= maxY; y++) {
				for (int z = minZ; z <= maxZ; z++) {
					Block block = loc.getWorld().getBlockAt(loc.getBlockX() + x, loc.getBlockY() + y,
							loc.getBlockZ() + z);

					block.setType(Material.AIR);
				}
			}
		}
		int maxD = Math.max(Math.max(maxX - minX, maxY - minY), maxZ - minZ);

		// destroy item frames
		for (Entity entity : loc.getWorld().getNearbyEntities(loc, maxD, maxD, maxD)) {
			if (entity instanceof ItemFrame && GameMap.isGameMap(((ItemFrame) entity).getItem())) {
				ItemFrame frame = (ItemFrame) entity;
				if ((new GameMap(frame.getItem())).getGameId() != gameId)
					continue;
				frame.remove();
			}
		}
	}

	public void delete() {
		// TODO: game deletion code
	}

//	public int getMapValsId(int mapVal) {
//		if (mapValMapIds.containsKey(mapVal))
//			return mapValMapIds.get(mapVal);
//		return 0;
//				
//	}
	public String getGameName() {
		return gameName;
	}

	public ArrayList<GameMap> getGameMaps() {
		return gameMaps;
	}

	public GameMap getGameMapByMapVal(int mapVal) {
		for (GameMap map : gameMaps) {
			if (map.getMapVal() == mapVal)
				return map;
		}
		return null;
	}

	public GameImage getGameImage() {
		return gameImage;
	}

	public abstract ItemStack getBoardItem();

	public ArrayList<Button> getButtons() {
		return buttons;
	}

	public int getRotation() {
		return mapManager.getRotation();
	}

	public String getName() {
		return gameName + "";
	}

	public static NamespacedKey getGameNameKey() {
		return gameNameKey;
	}

	public static void setGameNameKey(NamespacedKey gameNameKey) {
		Game.gameNameKey = gameNameKey;
	}

	public ArrayList<Screen> getScreens() {
		return screens;
	}

	public WagerManager getWagerManager() {
		return wagerManager;
	}

	public void displayGameInventory(Player player) {
		if(gameInventory != null) {
			gameInventory.build(player);
		}
	}

	public boolean hasGameInventory() {
		return gameInventory != null;
	}

	public boolean hasGameStorage() {
		return gameStorage != null && BoardGames.getInstance().getStorageManager() != null;
	}

	public void setGameData(HashMap<String, Object> gameData) {
		this.gameData = new HashMap<>(gameData);
	}

	public boolean hasGameData(String key) {
		return this.gameData.containsKey(key);
	}

	public Object getGameData(String key) {
		return this.gameData.get(key);
	}

	public GameStorage getGameStore() {
		return this.gameStorage;
	}
}