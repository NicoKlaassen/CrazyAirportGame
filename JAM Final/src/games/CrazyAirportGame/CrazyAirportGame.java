package games.CrazyAirportGame;

import gameClasses.Game;
import gameClasses.GameState;
import global.FileHelper;
import userManagement.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.function.BiConsumer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CrazyAirportGame extends Game{
	
	private static final String JS = "CrazyAirportGame/js/dummy.js";
    private static final String CSS = "CrazyAirportGame/css/CrazyAirportGame.css";
    private static final String HTML = "CrazyAirportGame/CrazyAirportGame.html";
    private static final String LadenFehlgeschlagen = "<h2>Laden fehlgeschlagen</h2>";
    private static final int MaxPlayers = 5;
    Table table=new Table();
    HashMap<String, BiConsumer<User, JsonObject>> reactionMethods = new HashMap<>();
    Gson gsonFormatter = new Gson();
    JsonParser jParser = new JsonParser();
    private String messageToSend = null;
    //Just needed for lobby
    private ArrayList<User> players = new ArrayList<>();
    private ArrayList<User> spectators = new ArrayList<>();
    //Lobby end
    
    public CrazyAirportGame() {
    	
    	reactionMethods.put("addAI", (User user, JsonObject message)->{
    		//ToDo
    	});
    	
    	reactionMethods.put("rollDice", (User user, JsonObject message)->{
       		boolean result=table.rollTheDice();
       		messageToSend=Boolean.toString(result);
    		sendGameDataToClients("diceResult");
    		if(result) {
    			EreginisLOSCard eCard=table.drawECard();
    			messageToSend=Integer.toString(eCard.getId());
    			sendGameDataToClients("showECard");
    			switch(eCard.getId()) {
    			case 3:
    				break;
    			case 10:
    				break;
    			case 24:
    				break;
    			case 42:
    				break;
    			case 47:
    				break;
    			case 49:
    				break;
    			case 51:
    				break;
    			case 53:
    				break;
    			default:
    				table.processStandardECard(eCard);
    				sendGameDataToClients("tableStatus");
    				table.endTurn();
    			}
    		}
    		else {
    			sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjects");
    		}
    	});
    	
    	reactionMethods.put("chosenProject", (User user, JsonObject message)->{
    		int chosenProject=message.get("projectID").getAsInt();
    		if(table.setChipOnProject(table.getAvailableProjectByID(chosenProject))) {
    			VerantwortungsLOSCard vCard=table.drawVCard();
    			messageToSend=Integer.toString(vCard.getId());
    			sendGameDataToClients("showVCard");
    			switch(vCard.getId()) {
    			case 8:
    				break;
    			case 12:
    				break;
    			case 15:
    				break;
    			case 16:
    				break;
    			case 17:
    				break;
    			case 20:
    				break;
    			default:
    				table.processStandardVCard(vCard);
    			}
    		}
    		sendGameDataToClients("tableStatus");
			table.endTurn();
    	});
    	
    	reactionMethods.put("specialCardAnswer11", (User user, JsonObject message)->{
    		boolean answer=message.get("answer").getAsBoolean();
    		if(answer) {
    			//ToDo handle Card 11
    			sendGameDataToUser(table.getCurrent().getUser(), "showCard11Choice");
    		}
    		else {
    			if(table.getCurrent().isHasVCard23()) {
    				sendGameDataToUser(table.getCurrent().getUser(), "useSpecialCard23");
    			}
    			else {
    				sendGameDataToUser(table.getCurrent().getUser(), "showDiceButton");
    			}
    		}
    	});
    	
    	reactionMethods.put("specialCardAnswer23", (User user, JsonObject message)->{
    		boolean answer=message.get("answer").getAsBoolean();
    		if(answer) {
    			//ToDo handle Card 23
    			sendGameDataToUser(table.getCurrent().getUser(), "showCard23Choice");
    		}
    		else {
				sendGameDataToUser(table.getCurrent().getUser(), "showDiceButton");
    		}
    	});
    	
    	reactionMethods.put("handleChoice11", (User user, JsonObject message)->{
    		Subproject fromProject=table.getActiveProjectByID(message.get("fromProject").getAsInt());
    		Subproject toProject=table.getActiveProjectByID(message.get("toProject").getAsInt());
    		table.removeChipFromProjectAndPutItIntoAnother(fromProject, toProject);
    		table.getCurrent().setHasVCard11(false);
    		sendGameDataToClients("tableStatus");
    		if(table.getCurrent().isHasVCard23()) sendGameDataToUser(table.getCurrent().getUser(), "useSpecialCard23");
    		else sendGameDataToUser(table.getCurrent().getUser(), "showDiceButton");
		});
    	
    	reactionMethods.put("handleChoice23", (User user, JsonObject message)->{
    		Player fromPlayer=table.getPlayerByName(message.get("fromPlayer").getAsString());
    		table.takeChipFromAnotherPlayer(fromPlayer);
    		table.getCurrent().setHasVCard23(false);
    		sendGameDataToClients("tableStatus");
    		sendGameDataToUser(table.getCurrent().getUser(), "showDiceButton");
		});
    	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private void sendMessage(User user, String message) {
        messageToSend = message;
        sendGameDataToUser(user, "sendMessage");
    }

    private void sendMessage(String message){
    	messageToSend = message;
        sendGameDataToClients("sendMessage");
    }

    @Override
    public String getSite() {
        try {
            return FileHelper.getFile(HTML);
        } catch (IOException e) {
            e.printStackTrace();
            return LadenFehlgeschlagen;
        }
    }

    @Override
    public String getCSS() {
        try {
            return FileHelper.getFile(CSS);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getJavaScript() {
        try {
            return FileHelper.getFile(JS);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

	@Override
	public int getMaxPlayerAmount() {
		return MaxPlayers;
	}

	@Override
	public int getCurrentPlayerAmount() {
		return players.size();
	}

    @Override
    public void execute(User user, String s) {
        // Splits by white Space for now, this is needed as there will be more
        // information than just the commands in some requests.
        String[] parts = s.split(" ");

        if (reactionMethods.containsKey(parts[0])) {
            BiConsumer<User, JsonObject> method = reactionMethods.get(parts[0]);
            JsonObject data = (parts.length > 1) ? jParser.parse(s.substring(parts[0].length())).getAsJsonObject() : null;
            method.accept(user, data);
        } else sendMessage(user, "Es wurde ein unbekannter Befehl gesendet.");
       

    }

	@Override
	public ArrayList<User> getPlayerList() {
		return players;
	}

	@Override
	public ArrayList<User> getSpectatorList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getGameData(String eventName, User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
    public void addUser(User user) {
        players.add(user);

        for (User player : players)
            if (!player.equals(user))
                sendGameDataToUser(player, "showPlayer");

        sendMessage("Spieler " + user.getName() + " ist beigetreten.");
    }

	@Override
	public void addSpectator(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isJoinable() {
		return players.size()<5;
	}

	@Override
	public void playerLeft(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public GameState getGameState() {
		return this.gState;
	}

}
