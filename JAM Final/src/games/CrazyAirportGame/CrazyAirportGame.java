package games.CrazyAirportGame;

import gameClasses.Game;
import gameClasses.GameState;
import global.FileHelper;
import userManagement.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CrazyAirportGame extends Game{


	private static final String JS = "CrazyAirportGame/js/dummy.js";
	private static final String CSS = "CrazyAirportGame/css/CrazyAirportGame.css";
	private static final String HTML = "CrazyAirportGame/CrazyAirportGame.html";
	private static final String STRINGFehlgeschlagen = "<h2>Laden fehlgeschlagen</h2>";
	private static final int MaxPlayers = 5;
	private static final int MinPlayers =3;
	Table table=new Table();
	HashMap<String, BiConsumer<User, JsonObject>> reactionMethods = new HashMap<>();
	Gson gsonFormatter = new Gson();
	JsonParser jParser = new JsonParser();
	private String messageToSend = null;
	//Just needed for lobby
	private ArrayList<User> players = new ArrayList<>();
	private ArrayList<User> spectators = new ArrayList<>();
	private List<String> colors = Arrays.asList("blue", "yellow", "green", "red", "purple");
	private int aiCount=0;
	//Lobby end

	public CrazyAirportGame() {

		reactionMethods.put("startGame", (User user, JsonObject message)->{
			if(gState==GameState.SETUP && user.equals(getGameCreator())) {
				sendMessage("Das Spiel wurde gestartet!");
				gState=GameState.RUNNING;
				int i=0;
				for(User u:players) {
					table.addPlayer(new Player(u, colors.get(i)));
					i++;
					sendGameDataToUser(u, "startGame");
				}
				table.startGame();
				startTurn();
			}
		});

		reactionMethods.put("addAI", (User user, JsonObject message)->{
			if (gState == GameState.SETUP && user.equals(getGameCreator())) {
				aiCount++;
				addUser(new User("Name","PW"));
				//sendGameDataToClients("USERJOINED");
			}
		});
		
		reactionMethods.put("lobbyJoin",  (User user, JsonObject message)->{
			sendGameDataToClients("USERJOINED");
		});

		reactionMethods.put("rollDice", (User user, JsonObject message)->{
			boolean result=table.rollTheDice();
			messageToSend=Boolean.toString(result);
			sendGameDataToClients("diceResult");
			if(result) {
				ErgebnisLOSCard eCard=table.getECardByID(42);
				messageToSend=Integer.toString(eCard.getId());
				sendGameDataToClients("showECard");
				switch(eCard.getId()) {
				case 3:
					sendGameDataToUser(table.getCurrent().getUser(), "askForProjectToSetTwoChips");
					break;
				//maybe we should skip this shit... :-D
				case 10:
					sendGameDataToUser(table.getLeftNeighbor().getUser(), "burn20ORPlaceChip");
					break;
				case 24:
					sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjects");
					break;
				case 42:
					if(table.projectWithMoreThanOneChippedFieldAvailable()) {
						sendGameDataToUser(table.getCurrent().getUser(), "aksForInAndOutProject");
						break;
					}
					else {
						table.endTurn();
						startTurn();
						break;
					}
				case 47:
					table.getCurrent().raiseScore(20);
					VerantwortungsLOSCard vCard=table.drawVCard();
					messageToSend=Integer.toString(vCard.getId());
					sendGameDataToClients("showVCard");
					handleVCardCommunication(vCard.getId());
					break;
				case 49:
					VerantwortungsLOSCard vCard2=table.drawVCard();
					messageToSend=Integer.toString(vCard2.getId());
					sendGameDataToClients("showVCard");
					handleVCardCommunication(vCard2.getId());
					break;
				case 51:
					sendGameDataToUser(table.getCurrent().getUser(), "choosePlayerToStealFrom");
					break;
				case 53:
					VerantwortungsLOSCard vCard3=table.drawVCard();
					messageToSend=Integer.toString(vCard3.getId());
					sendGameDataToClients("showVCard");
					handleVCardCommunication(vCard3.getId());
					break;
				default:
					table.processStandardECard(eCard);
					table.endTurn();
					startTurn();
				}
			}
			else {
				sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjects");

			}
		});

		//In allen Methoden tableStatus
		reactionMethods.put("chosenProject", (User user, JsonObject message)->{
			int chosenProject=message.get("projectID").getAsInt();
			System.out.println("test");
			if(table.setChipOnProject(table.getActiveProjectByID(chosenProject))) {
				table.endTurn();
				startTurn();
				VerantwortungsLOSCard vCard=table.drawVCard();
				messageToSend=Integer.toString(vCard.getId());
				sendGameDataToClients("showVCard");
				switch(vCard.getId()) {
				case 8:
					sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjectsTwiceBurn");
					break;
				case 12:
					table.getCurrent().raiseScore(50);
					sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjectTwoSelection");
					break;
				case 15:
					table.getCurrent().raiseScore(100);
					sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjectTwoSelectionTakeChips");
					break;
				case 16:
					table.getCurrent().raiseScore(100);
					if(table.getCurrent().getChips().size()>=2) {
						sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjectsPlaceTwoChips");
					}
					else if(table.getCurrent().getChips().size()==1) {
						sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjects");
					}
					break;
				case 17:
					if(table.getCurrent().getChips().size()>=2) {
						sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjectsPlaceTwoChips");
					}
					else if(table.getCurrent().getChips().size()==1) {
						sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjects");
					}
					break;
				case 20:
					sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjectsExtraDice");
					break;
				default:
					table.processStandardVCard(vCard);
					table.endTurn();
					startTurn();
				}
			}
			else {
				sendGameDataToClients("tableStatus");
				table.endTurn();
				startTurn();
			}});

		reactionMethods.put("specialCardAnswer11", (User user, JsonObject message)->{
			boolean answer=message.get("answer").getAsBoolean();
			if(answer) {
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

		reactionMethods.put("subprojectAnswerTwoChipsInOneProject", (User user, JsonObject message)->{
			Subproject answerProject=table.getActiveProjectByID(message.get("projectID").getAsInt());
			table.setTwoChipsInOneProject(answerProject);
			sendGameDataToClients("tableStatus");
			table.endTurn();
			startTurn();
		});

		reactionMethods.put("removeChipFromProjectAndPutItIntoAnotherAnswer",(User user, JsonObject message)->{
			Subproject fromProject=table.getActiveProjectByID(message.get("fromProjectID").getAsInt());
			Subproject toProject=table.getActiveProjectByID(message.get("toProjectID").getAsInt());
			System.out.println("FromProject"+fromProject.getId());
			System.out.println("ToProject"+toProject.getId());
			table.removeChipFromProjectAndPutItIntoAnother(fromProject, toProject);
			sendGameDataToClients("tableStatus");
			table.endTurn();
			startTurn();
		});

		reactionMethods.put("removeChipFromProjectAndPutItIntoAnotherAnswerExtraDice",(User user, JsonObject message)->{
			Subproject fromProject=table.getActiveProjectByID(message.get("fromProjectID").getAsInt());
			Subproject toProject=table.getActiveProjectByID(message.get("toProjectID").getAsInt());
			table.removeChipFromProjectAndPutItIntoAnother(fromProject, toProject);
			sendGameDataToUser(table.getCurrent().getUser(), "showDiceButton");
			startTurn();
		});

		reactionMethods.put("add2ChipsOnExistingProjects", (User user, JsonObject message)-> {
			Subproject answerProject1=table.getActiveProjectByID(message.get("projectID1").getAsInt());
			Subproject answerProject2=table.getActiveProjectByID(message.get("projectID2").getAsInt());
			if(answerProject1.getId()==answerProject2.getId()) {
				table.add2ChipsOnTheSameProject(answerProject1);
			}
			else if(answerProject1.getId()!=answerProject2.getId()) {
				table.add2ChipsOnExistingProjects(answerProject1, answerProject2);
			}
			sendGameDataToClients("tableStatus");
			table.endTurn();
			startTurn();
		});

		reactionMethods.put("chipOnFieldBurnSZTTwice", (User user, JsonObject message)-> {
			Subproject answerProject=table.getActiveProjectByID(message.get("projectID").getAsInt());
			table.setChipOnExistingProjectBurnSZTTwice(answerProject);
			sendGameDataToClients("tableStatus");
			table.endTurn();
			startTurn();
		});

		reactionMethods.put("remove2ChipsFromProjectsAndAddToPlayer", (User user, JsonObject message)-> {
			Subproject answerProject1=table.getActiveProjectByID(message.get("projectID1").getAsInt());
			Subproject answerProject2=table.getActiveProjectByID(message.get("projectID2").getAsInt());
			table.remove2ChipsFromProjectsAndAddToPlayer(answerProject1, answerProject2);
			sendGameDataToClients("tableStatus");
			table.endTurn();
			startTurn();
		});

		reactionMethods.put("twoChipsInOneProject", (User user, JsonObject message)-> {
			Subproject answerProject=table.getActiveProjectByID(message.get("projectID").getAsInt());
			table.setTwoChipsInOneProject(answerProject);
			sendGameDataToClients("tableStatus");
			table.endTurn();
			startTurn();
		});

		reactionMethods.put("takeAway20SZTFromPlayer", (User user, JsonObject message)-> {
			Player player=table.getPlayerByName(message.get("player").getAsString());
			table.takeAway20SZTFromPlayer(player);
			sendGameDataToClients("tableStatus");
			table.endTurn();
			startTurn();
		});

		reactionMethods.put("burnOrPlace", (User user, JsonObject message)-> {
			boolean answer=message.get("decision").getAsBoolean();
			if(answer) {
				table.getCurrent().raiseScore(20);
				sendGameDataToClients("tableStatus");
				table.endTurn();
				startTurn();
			}
			else {
				sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjects");
			}
		});
	}

	//TODO card24
	public void startTurn() {
		if(table.getCurrent() instanceof AI) {
			//processAIMove();
		}
		else {
			sendGameDataToClients("tableStatus");
			sendGameDataToUser(table.getCurrent().getUser(), "rollDice");
			if(table.getCurrent().isHasVCard11()) {
				sendGameDataToUser(table.getCurrent().getUser(), "useSpecialCard11");
			}
			else {
				sendGameDataToUser(table.getCurrent().getUser(), "showDiceButton");
			}
		}	
	}
	
	
	//TODO 
	public void processAIMove() {
		boolean diceResult=table.rollTheDice();
		if(diceResult) {
			ErgebnisLOSCard eCard=table.drawECard();
			messageToSend=Integer.toString(eCard.getId());
			sendGameDataToClients("showECard");
			switch(eCard.getId()) {
			case 3:
				table.setTwoChipsInOneProject(table.getActiveProjects().get((((AI)table.getCurrent()).chooseRandom(table.getActiveProjects().size()))));
				sendGameDataToClients("tableStatus");
				table.endTurn();
				startTurn();
				break;
			case 10:
				sendGameDataToUser(table.getLeftNeighbor().getUser(), "burn20ORPlaceChip");
				break;
			case 24:
				sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjects");
				break;
			case 42:
				sendGameDataToUser(table.getCurrent().getUser(), "aksForInAndOutProject");
				break;
			case 47:
				table.getCurrent().raiseScore(20);
				VerantwortungsLOSCard vCard=table.drawVCard();
				messageToSend=Integer.toString(vCard.getId());
				sendGameDataToClients("showVCard");
				handleVCardCommunication(vCard.getId());
				break;
			case 49:
				VerantwortungsLOSCard vCard2=table.drawVCard();
				messageToSend=Integer.toString(vCard2.getId());
				sendGameDataToClients("showVCard");
				handleVCardCommunication(vCard2.getId());
				break;
			case 51:
				sendGameDataToUser(table.getCurrent().getUser(), "choosePlayerToStealFrom");
				break;
			case 53:
				VerantwortungsLOSCard vCard3=table.drawVCard();
				messageToSend=Integer.toString(vCard3.getId());
				sendGameDataToClients("showVCard");
				handleVCardCommunication(vCard3.getId());
				break;
			default:
				table.processStandardECard(eCard);
				sendGameDataToClients("tableStatus");
				table.endTurn();
				startTurn();
			}
		}
		else {
			sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjects");

		}
	}


	private void handleVCardCommunication(int id) {
		switch(id) {
		case 8:
			sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjectsTwiceBurn");
			break;
		case 12:
			table.getCurrent().raiseScore(50);
			sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjectTwoSelection");
			break;
		case 15:
			table.getCurrent().raiseScore(100);
			sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjectTwoSelectionTakeChips");
			break;
		case 16:
			table.getCurrent().raiseScore(100);
			if(table.getCurrent().getChips().size()>=2) {
				sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjectsPlaceTwoChips");
			}
			else if(table.getCurrent().getChips().size()==1) {
				sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjects");
			}
			break;
		case 17:
			if(table.getCurrent().getChips().size()>=2) {
				sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjectsPlaceTwoChips");
			}
			else if(table.getCurrent().getChips().size()==1) {
				sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjects");
			}
			break;
		case 20:
			sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjectsExtraDice");
			break;
		default:
			table.processStandardVCard(table.getVCardFromCurrentByID(id));
		}
		sendGameDataToClients("tableStatus");
		table.endTurn();
	}



	@Override
	public String getGameData(String eventName, User user) {
		switch(eventName) {
		//sends the dice result as an boolean to the client (if true show EreignisLOS-Dice else show Place-Chip-Dice
		case ("diceResult"):
			JsonObject result=new JsonObject();
			result.addProperty("result", messageToSend);
			return result.toString();
		//Sends the drawn E-Card ID- you need to load the right picture of the E-Card (Suggestion: Use a name convention and concatenate the id to the rest of the name like: "EreignlisLOS_"+id+".jpg")
		case ("showECard"):
			JsonObject eCard=new JsonObject();
			eCard.addProperty("eCardID", messageToSend);
			return eCard.toString();
		//Sends the drawn V-Card ID- you need to load the right picture of the V-Card (Suggestion: Use a name convention and concatenate the id to the rest of the name like: "VerantwortungsLOS_"+id+".jpg")
		case ("showVCard"):
			JsonObject vCard=new JsonObject();
			vCard.addProperty("vCardID", messageToSend);
			return vCard.toString();
		/*Gives back the current table status which means the whole status of projects and players by giving back a JsonObject filled with arrays
		 * 
		 * First there are three arrays filled with projects. Each project is represented in one of those three arrays (you can identify it via the name or the id)
		 * 
		 * 	Array 1:
		 * 		- Array filled with the available (means not started or finished) projects. One Element of this array consists out of the following:
		 * 			> String name, SubprojectField[] fields, int nextFree, int id
		 * 	Array 2:
		 * 		- Array filled with the active (means started but not finished yet) projects. One Element of this array consists out of the following:
		 * 			> String name
		 *			> ArrayList<SubporjectFields> fields
		 *			> int nextFree
		 *			> int id
		 *	Array 3:
		 *		- Array filled with the finished projects. One Element of this array consists out of the following:
		 *			> String name
		 *			> ArrayList<SubporjectFields> fields
		 *			> int nextFree
		 *			> int id
		 * 	Array 4:
		 * 		- Array filled with all the players. One element of the array consists out of the following:
		 * 			> int score
		 * 			> ArrayList<Chip> chips
		 * 				- Chip consists out of
		 * 					> Player realOwner
		 * 					> Player currentOwner
		 * 			> ArrayList<VerantwortungsLOSCard> vCards (only ID)
		 * 			> ArrayList<ErgebnisLOSCard> eCards	(only ID)
		 * 			> boolean skipNextRound
		 * 			> boolean hasVCard11
		 * 			> boolean hasVCard23
		 * 			> User user
		 * 			> String color
		 */	
		case ("tableStatus"):
			System.out.println(table.toJson().toString());
			return table.toJson().toString();
		//Sends the available projects with more than one free field
		case ("askForProjectToSetTwoChips"):
			JsonObject availableProjects1 = new JsonObject();
			JsonArray projects2= new JsonArray();
			for(Subproject project:table.getActiveProjects()) {
				projects2.add(project.toJson());
			}
			availableProjects1.add("availableProjects", projects2);
			return availableProjects1.toString();
		//You have to ask the player whether he wants to place a chip or burn 20 million SZT
		case ("burn20ORPlaceChip"):
			return "";//no additional information needed for client
		//Sends the available projects and expects one project as answer
		case ("showAvailableProjects"):
			JsonObject availableProjects = new JsonObject();
			JsonArray projects1= new JsonArray();
			for(Subproject project:table.getActiveProjects()) {
				projects1.add(project.toJson());
			}
			availableProjects.add("availableProjects", projects1);
			return availableProjects.toString();
		//Sends the available projects. Player needs to select two, for removing chip from and adding chip into
		case ("aksForInAndOutProject"):
			JsonObject availableProjects2 = new JsonObject();
			JsonArray outProjects= new JsonArray();
			JsonArray inProjects= new JsonArray();
			for(Subproject project:table.getActiveProjects()) {
				if(project.chipCanBeRemoved()) {
					outProjects.add(project.toJson());
				}
			}
			for(Subproject project:table.getActiveProjects()) {
				inProjects.add(project.toJson());
			}
			
			availableProjects2.add("outProjects", outProjects);
			availableProjects2.add("inProjects", inProjects);
			System.out.println(availableProjects2.toString());
			return availableProjects2.toString();
		//Sends all players. Player should select one player to steal from
		case ("choosePlayerToStealFrom"):
			JsonArray players=new JsonArray();
			for(Player p:table.getPlayers()) {
				players.add(p.toJson());
			}
		return players.toString();
		//Sends the available projects. Player needs to select to, for removing chip from and adding chip into	
		case ("showAvailableProjectsTwiceBurn"):
			JsonArray projects8= new JsonArray();
			for(Subproject project:table.getActiveProjects()) {
				projects8.add(project.toJson());
			}
			return projects8.toString();
		//Sends the available projects. Player needs to select two (can be the same)
		case ("showAvailableProjectTwoSelection"):
			JsonArray projects3= new JsonArray();
			for(Subproject project:table.getActiveProjects()) {
				projects3.add(project.toJson());
			}
		return projects3.toString();
		//Sends the available projects. Player needs to select two (can be the same)
		case ("showAvailableProjectTwoSelectionTakeChips"):
			JsonArray projects4= new JsonArray();
			for(Subproject project:table.getActiveProjects()) {
				projects4.add(project.toJson());
			}
		return projects4.toString();
		//Sends the available projects. Player needs to select two (can be the same)
		case ("showAvailableProjectsPlaceTwoChips"):
			JsonArray projects5= new JsonArray();
			for(Subproject project:table.getActiveProjects()) {
				projects5.add(project.toJson());
			}
		return projects5.toString();
		//Sends the available projects and expects one selection. After placing the chip on the project the player gets an extra Turn by dicing again
		case ("showAvailableProjectsExtraDice"):
			JsonArray projects6= new JsonArray();
			for(Subproject project:table.getActiveProjects()) {
				projects6.add(project.toJson());
			}
		return projects6.toString();
		//You need to ask the player whether he wants to use his card 11 or not and send a boolean to server
		case ("useSpecialCard11"):
			return "";
		//You need to ask the player whether he wants to use his card 23 or not and send a boolean to server
		case ("useSpecialCard23"):
			return "";
		//Sends the list of available projects and expects TWO selections (in and out project)
		case ("showCard11Choice"):
			JsonArray projects9= new JsonArray();
		for(Subproject project:table.getActiveProjects()) {
			projects9.add(project.toJson());
		}
		return projects9.toString();
		//Sends the list of players. Player needs to select one player to steal a chip from
		case ("showCard23Choice"):
			JsonArray players1=new JsonArray();
			for(Player p:table.getPlayers()) {
				players1.add(p.toJson());
			}
			return players1.toString();
		//Make dice button clickable
		case ("showDiceButton"):
			return "";
		case("USERJOINED"):
			JsonObject object=new JsonObject();
			JsonArray users=new JsonArray();
			for(User u:this.players) {
				JsonObject user1=new JsonObject();
				user1.addProperty("name", u.getName()); 
				users.add(user1);
			}
			object.add("users", users);
			System.out.println(object.toString());
			return object.toString();
		}
		return null;
	}

	private int html = 2;

	@Override
	public String getSite() {
		try {
			if(html==1) {
				return FileHelper.getFile("CrazyAirportGame/lobby.html");
			}
			if(html==2) {
				return FileHelper.getFile("CrazyAirportGame/spiel.html");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void sendMessage(String message){
		messageToSend = message;
		sendGameDataToClients("sendMessage");
	}

	private void sendMessage(User user, String message) {
		messageToSend = message;
		sendGameDataToUser(user, "sendMessage");
	}

	@Override
	public String getCSS() {
		try {
			return global.FileHelper.getFile("CrazyAirportGame/css/CrazyAirportGame.css");
		} catch (IOException e) {
			System.err
			.println("Loading of file CrazyAirportGame/css/CrazyAirportGame.css failed");
		}
		return null;
	}

	@Override
	public String getJavaScript() {
		return "<script src=\"javascript/CrazyAirportGame.js\"></script>";
	}


	@Override
	public int getMaxPlayerAmount() {
		return MaxPlayers;
	}

	public int getMinPlayerAmount() {
		return MinPlayers;
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
		System.out.println(parts.length);
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
		return spectators;
	}

	@Override
	public void addUser(User user) {
		players.add(user);
		
		sendGameDataToClients("USERJOINED");
		System.out.println("called");
		sendMessage("Spieler " + user.getName() + " ist beigetreten.");
	}
	
	@Override
	public void addSpectator(User user) {
		spectators.add(user);
	}

	@Override
	public boolean isJoinable() {
		return this.gState == GameState.SETUP && players.size() < getMaxPlayerAmount();
	}

	@Override
	public void playerLeft(User user) {
		players.remove(user);
		if (getGameState() == GameState.RUNNING) {
            sendMessage(String.format("Spieler %s hat das Spiel verlassen", user.getName()));
		}
		table.replacePlayerWithAI(table.getPlayerByUser(user));
		startTurn();
	}

	@Override
	public GameState getGameState() {
		return this.gState;
	}

}
