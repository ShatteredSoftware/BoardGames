package water.of.cup.boardgames.config;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import water.of.cup.boardgames.BoardGames;
import water.of.cup.boardgames.game.storage.StorageType;

import java.util.HashMap;

public enum ConfigUtil {

    // PLUGIN SETTINGS
    PERMISSIONS_ENABLED("settings.permissions", "true"),
    WAGERS_ENABLED("settings.wagers", "true"),
    RECIPE_ENABLED("settings.recipe.enabled", "true"),
    DB_HOST("settings.database.host", "localhost"),
    DB_PORT("settings.database.port", "3306"),
    DB_NAME("settings.database.database", "boardgames"),
    DB_USERNAME("settings.database.username", "root"),
    DB_PASS("settings.database.password", " "),
    DB_ENABLED("settings.database.enabled", "false"),

    // GUI MESSAGES
    GUI_NEXT_PAGE("settings.messages.gui.nextpage", "&aNext Page"),
    GUI_CREATE_GAME("settings.messages.gui.creategame", "&aCreate Game"),
    GUI_ACCEPT_PLAYER("settings.messages.gui.acceptplayer", "&2LEFT CLICK - ACCEPT"),
    GUI_DECLINE_PLAYER("settings.messages.gui.declineplayer", "&4RIGHT CLICK - DECLINE"),
    GUI_START_GAME_WITH("settings.messages.gui.startgamewith", "&aStart game with %num% players"),
    GUI_FORFEIT_GAME("settings.messages.gui.forfeitgame", "&cForfeit Game"),
    GUI_WAIT_CREATOR("settings.messages.gui.waitcreator", "Waiting for game creator"),
    GUI_WAIT_PLAYERS("settings.messages.gui.waitplayers", "Waiting for more players"),
    GUI_JOIN_GAME("settings.messages.gui.joingame", "&aJoin Game"),
    GUI_LEAVE_GAME("settings.messages.gui.leavegame", "&cLeave Game"),
    GUI_READY_TEXT("settings.messages.gui.readytext", "&a&lREADY"),
    GUI_UNREADY_TEXT("settings.messages.gui.unreadytext", "&c&lCLICK TO READY"),
    GUI_NOT_READY_TEXT("settings.messages.gui.notreadytext", "&c&lNOT READY"),
    GUI_WAGER_NEXT("settings.messages.gui.wagernext", "Next"),
    GUI_WAGER_BACK("settings.messages.gui.wagerback", "Back"),
    GUI_WAGER_CANCEL("settings.messages.gui.wagercancel", "&cCancel Wager"),
    GUI_WAGER_CREATE("settings.messages.gui.wagercreate", "&aCreate Wager"),
    GUI_WAGER_ACCEPT("settings.messages.gui.wageraccept", "&aAccept Wager"),
    GUI_WAGER_DECLINE("settings.messages.gui.wagerdecline", "&cDecline Wager"),
    GUI_WAGER_NO_MONEY_CREATE("settings.messages.gui.wagernomoneycreate", "&cNot enough money to create wager"),
    GUI_WAGER_NO_MONEY_ACCEPT("settings.messages.gui.wagernomoneyaccept", "&cNot enough money to accept wager"),
    GUI_WAGER_INCREASE("settings.messages.gui.wagerincrease", "&aIncrease Wager"),
    GUI_WAGER_DECREASE("settings.messages.gui.wagerdecrease", "&aDecrease Wager"),
    GUI_WAGER_BETTINGON("settings.messages.gui.bettingon", "&aBetting on %player%"),
    GUI_WAGER_TEXT("settings.messages.gui.wagertext", "&aWagers"),
    GUI_UP_ARROW("settings.messages.gui.uparrow", "&a/\\"),
    GUI_DOWN_ARROW("settings.messages.gui.downarrow", "&a\\/"),

    // GUI GAME OPTIONS
    GUI_WAGER_LABEL("settings.messages.gui.wagerlabel", "&2Wager: "),
    GUI_TEAM_LABEL("settings.messages.gui.teamlabel", "&2Team: "),
    GUI_RANKED_OPTION_TEXT("settings.messages.gui.rankedoption", "ranked"),
    GUI_UNRANKED_OPTION_TEXT("settings.messages.gui.unrankedoption", "unranked"),

    GUI_TEAM_RED_TEXT("settings.messages.gui.teamredtext", "RED"),
    GUI_TEAM_BLACK_TEXT("settings.messages.gui.teamblacktext", "BLACK"),
    GUI_TEAM_WHITE_TEXT("settings.messages.gui.teamwhitetext", "WHITE"),
    GUI_TEAM_BLUE_TEXT("settings.messages.gui.teambluetext", "BLUE"),

    // GUI CHAT MESSAGES
    CHAT_GUI_GAME_ALREADY_CREATED("settings.messages.gui.gamealreadycreated", "Game has already been created."),
    CHAT_GUI_GAME_NO_MONEY_CREATE("settings.messages.gui.gamenomoneycreate", "&cNot enough money to create game."),
    CHAT_GUI_GAME_NO_MONEY_ACCEPT("settings.messages.gui.gamenomoneyaccept", "&cPlayer no longer has enough money.."),
    CHAT_GUI_GAME_NO_MONEY_JOIN("settings.messages.gui.gamenomoneyjoin", "&cYou do not have enough money!"),
    CHAT_GUI_GAME_ACCEPT("settings.messages.gui.gameacceptchat", "Accepting %player%"),
    CHAT_GUI_GAME_DECLINE("settings.messages.gui.gamedeclinechat", "Declining %player%"),
    CHAT_GUI_GAME_OWNER_LEFT("settings.messages.gui.gameownerleft", "Game owner has left"),
    CHAT_GUI_GAME_PLAYER_LEFT("settings.messages.gui.gameplayerleft", "Player left ready screen. Game cancelled."),
    CHAT_GUI_GAME_NO_AVAIL_GAME("settings.messages.gui.noavailgame", "No available game to join."),
    CHAT_GUI_GAME_FULL_QUEUE("settings.messages.gui.fullqueue", "Too many players are queuing!"),
    CHAT_GUI_WAGER_ACCEPT("settings.messages.gui.chatwageraccept", "%player% has accepted your wager!"),
    CHAT_GUI_WAGER_ACCEPTED("settings.messages.gui.chatwageraccept", "You have accepted %player%'s wager!"),

    // GAME CHAT MESSAGES
    CHAT_GAME_PLAYER_WIN("settings.messages.chat.playerwin", "%player% has won the game!"),
    CHAT_GAME_PLAYER_LOSE("settings.messages.chat.playerlose", "&aYou lost!"),
    CHAT_GAME_TIE("settings.messages.chat.gametie", "&aTie game!"),
    CHAT_GAME_FORCE_JUMP("settings.messages.chat.forcejump", "You must select a piece that can jump if a jump is possible."),
    CHAT_GAME_UNO_FORCE_2("settings.messages.chat.unoforce2", "You were forced to draw 2 cards."),
    CHAT_GAME_UNO_FORCE_4("settings.messages.chat.unoforce4", "You were forced to draw 4 cards."),
    CHAT_GAME_UNO_SKIPPED("settings.messages.chat.unoskipped", "You were skipped."),
    CHAT_GAME_UNO_NOT_YOUR_TURN("settings.messages.chat.unonotyourturn", "It is not your turn."),
    CHAT_GAME_UNO_SELECT_COLOR("settings.messages.chat.unoselectcolor", "Select a color."),
    CHAT_GAME_UNO_FORCE_DRAW("settings.messages.chat.unoforcedraw", "You have no playable cards and were forced to draw a card."),
    CHAT_GAME_UNO_INVALID_CARD("settings.messages.chat.unoinvalidcard", "You can not play that card."),
    CHAT_GAME_PLAYER_LEAVE("settings.messages.chat.gameplayerleave", "%player% has left %game%."),

    // CHAT MESSAGES
    CHAT_NO_DB("settings.messages.chat.nodb", "Database must be enabled to view stats."),
    CHAT_NO_GAME("settings.messages.chat.nogame", "No game found with that name."),
    CHAT_NO_PLAYER("settings.messages.chat.noplayer", "No player found with that name."),
    CHAT_DB_ERROR("settings.messages.chat.dberror", "Error calling to database."),
    CHAT_RELOAD("settings.messages.chat.reload", "Reloaded board games config."),
    CHAT_GAME_NAMES("settings.messages.chat.gamenames", "Game Names: "),
    CHAT_STATS_HEADER("settings.messages.chat.statsheader", "%player%'s stats"),
    CHAT_LEADERBOARD_HEADER("settings.messages.chat.leaderboardheader", "%game% leaderboard sorting by %sort%"),
    CHAT_AVAIL_COMMANDS("settings.messages.chat.availcommands", "Available commands\n/bg games - lists games\n/bg board [game name] - gives you the game's item\n/bg stats [game name] [player name]\n/bg leaderboard [game name] [order by]\n/bg reload"),
    CHAT_PLAYER_INGAME("settings.messages.chat.playeringame", "You must finish your game before joining another."),
    CHAT_PLACED_BOARD("settings.messages.chat.placedboard", "Placed board."),
    CHAT_NO_BOARD_ROOM("settings.messages.chat.noboardroom", "No room to place board."),
    CHAT_WELCOME_GAME("settings.messages.chat.welcomegame", "Welcome to %game%!");

    private final String path;
    private final String defaultValue;
    private static final BoardGames instance = BoardGames.getInstance();
    private static final HashMap<String, ConfigUtil> teamNameMap = new HashMap<>();

    static {
        teamNameMap.put("RED", ConfigUtil.GUI_TEAM_RED_TEXT);
        teamNameMap.put("BLACK", ConfigUtil.GUI_TEAM_BLACK_TEXT);
        teamNameMap.put("WHITE", ConfigUtil.GUI_TEAM_WHITE_TEXT);
        teamNameMap.put("BLUE", ConfigUtil.GUI_TEAM_BLUE_TEXT);
    }

    ConfigUtil(String path, String defaultValue) {
        this.path = path;
        this.defaultValue = defaultValue;
    }

    @Override
    public String toString() {
        String configString = instance.getConfig().getString(this.path);

        if(configString == null) return "";

        return ChatColor.translateAlternateColorCodes('&', configString);
    }

    public String toRawString() {
        return ChatColor.stripColor(this.toString());
    }

    public boolean toBoolean() {
        return this.toString().equals("true");
    }

    public String getPath() {
        return this.path;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public static boolean getBoolean(String path) {
        String configString = instance.getConfig().getString(path);

        if(configString == null) return false;

        return configString.equals("true");
    }

    public static String translateTeamName(String teamName) {
        if(teamNameMap.containsKey(teamName)) {
            return teamNameMap.get(teamName).toString();
        }

        return teamName;
    }

    public String buildString(String replaceWith) {
        String formatted = this.toString();

        formatted = formatted.replace("%player%", replaceWith).replace("%game%", replaceWith);

        return formatted;
    }

    public String buildStringLeaveGame(String playerName, String gameName) {
        String formatted = this.toString();

        formatted = formatted.replace("%player%", playerName).replace("%game%", gameName);

        return formatted;
    }

    public String buildString(String game, String sort) {
        String formatted = this.toString();

        formatted = formatted.replace("%game%", game).replace("%sort%", sort);

        return formatted;
    }

    public String buildString(int num) {
        String formatted = this.toString();

        formatted = formatted.replace("%num%", num + "");

        return formatted;
    }
}
