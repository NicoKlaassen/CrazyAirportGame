package games.CrazyAirportGame;

import gameClasses.Game;
import gameClasses.GameState;
import global.FileHelper;
import userManagement.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

import javax.swing.table.TableCellEditor;

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
	private Random rand = new Random();
	private int setTwoChipHelp;
	private int help=0;
	private boolean card23used=false;
	private boolean card11used=false;
	Table table=new Table();
	HashMap<String, BiConsumer<User, JsonObject>> reactionMethods = new HashMap<>();
	Gson gsonFormatter = new Gson();
	JsonParser jParser = new JsonParser();
	private String messageToSend = null;
	//Just needed for lobby
	private ArrayList<User> players = new ArrayList<>();
	private ArrayList<User> spectators = new ArrayList<>();
	private ArrayList<AI> bots = new ArrayList<>();
	private ArrayList<Integer> standardVIDs = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,9,10,13,18,19,21,22,24));
	private List<String> colors = Arrays.asList("blue", "yellow", "green", "red", "purple");
	private List<String> botNames = Arrays.asList("Andreas", "Thomas", "Martin", "Julian");
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
				for(int x=0; x<aiCount; x++) {
				    AI bot = new AI(new User(botNames.get(x),"PW"), colors.get(i));
					table.addPlayer(bot);
					i++;
				}
				table.startGame();
				startTurn();
			}
		});

		reactionMethods.put("addAI", (User user, JsonObject message)->{
			if (gState == GameState.SETUP && user.equals(getGameCreator())) {
				if(getCurrentPlayerAmount()<5){
					aiCount++;
					sendGameDataToClients("USERJOINED");
				}
			}
		});
		
		reactionMethods.put("removeAI", (User user, JsonObject message)->{
			if (gState == GameState.SETUP && user.equals(getGameCreator())) {
				if(aiCount>0){
					aiCount--;
					sendGameDataToClients("USERJOINED");
				}
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
				ErgebnisLOSCard eCard=table.drawECard();
				messageToSend=Integer.toString(eCard.getId());
				sendGameDataToClients("showECard");
				System.out.println(table.getCurrent().getUser().getName() + " eCard " + eCard.getId());
				switch(eCard.getId()) {
				case 3:
					System.out.println("DANIEL");
					sendGameDataToUser(table.getCurrent().getUser(), "askForProjectToSetTwoChips");
					break;
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
					handleVCardCommunication1(vCard.getId());
					break;
				case 49:
					VerantwortungsLOSCard vCard2=table.drawVCard();
					messageToSend=Integer.toString(vCard2.getId());
					sendGameDataToClients("showVCard");
					handleVCardCommunication1(vCard2.getId());
					break;
				case 51:
					sendGameDataToUser(table.getCurrent().getUser(), "choosePlayerToStealFrom");
					break;
				case 53:
					VerantwortungsLOSCard vCard3=table.drawVCard();
					messageToSend=Integer.toString(vCard3.getId());
					sendGameDataToClients("showVCard");
					handleVCardCommunication1(vCard3.getId());
					break;
				default:
					table.processStandardECard(eCard);
					table.endTurn();
					startTurn();
				}
			}
			else {
				sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjects");
				System.out.println(table.getCurrent().getUser().getName() + " Chip setzen");
			}
		});

		//In allen Methoden tableStatus
		reactionMethods.put("chosenProject", (User user, JsonObject message)->{
			setTwoChipHelp=0;
			int chosenProject=message.get("projectID").getAsInt();
			System.out.println("chosen project");
			if(table.setChipOnProject(table.getActiveProjectByID(chosenProject))) {
				VerantwortungsLOSCard vCard=table.drawVCard();
				messageToSend=Integer.toString(vCard.getId());
				sendGameDataToClients("showVCard");
				sendGameDataToClients("tableStatus");
				switch(vCard.getId()) {
				case 8:
					if(table.getCurrent().getChips().size()==0) {
						sendGameDataToClients("tableStatus");
						table.endTurn();
						startTurn();
						break;
					}
					sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjectsTwiceBurn");
					break;
				case 12:
					if(table.getCurrent().getChips().size()==1) {
						table.getCurrent().raiseScore(50);
						sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjects");
						break;
					}
					else if(table.getCurrent().getChips().size()==0) {
						table.getCurrent().raiseScore(50);
						sendGameDataToClients("tableStatus");
						table.endTurn();
						startTurn();
						break;
					}
					else if(table.getCurrent().getChips().size()>=2){
						table.getCurrent().raiseScore(50);
						sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjectTwoSelection");
						break;
					}
				case 15:
					int i=0;
					int z=0;
					table.getCurrent().raiseScore(100);
					for(Subproject p:table.getActiveProjects()) {
						if(p.twoChipsCanBeRemoved()) {
							i++;
						}
					}
					for(Subproject p:table.getActiveProjects()) {
						if(p.chipCanBeRemoved()) {
							z++;
						}
					}
					if(i==0 && z==1){
						System.out.println("OneChip");
						sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjectsTakeOneChip");
						break;
					}
					else if(i>=1){
						System.out.println("twoChips1");
						sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjectTwoSelectionTakeChips");
						break;
					}
					else if(z>=2){
						System.out.println("twoChips2");
						sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjectTwoSelectionTakeChips");
						break;
					}
					else {
						table.endTurn();
						startTurn();
						break;
					}
				case 16:
					table.getCurrent().raiseScore(100);
					if(table.getCurrent().getChips().size()>=2) {
						sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjectsPlaceTwoChips");
						break;
					}
					else if(table.getCurrent().getChips().size()==1) {
						sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjects");
						break;
					}
					else if(table.getCurrent().getChips().size()==0) {
						sendGameDataToClients("tableStatus");
						table.endTurn();
						startTurn();
						break;
					}
					break;
				case 17:
					if(table.getCurrent().getChips().size()>=2) {
						sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjectTwoSelection");
						break;
						
					}
					else if(table.getCurrent().getChips().size()==1) {
						sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjects");
						break;
					}
				case 20:
					if(table.getCurrent().getChips().size()>=1) {
					sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjectsExtraDice");
					break;
					}
					else if(table.getCurrent().getChips().size()==0) {
						sendGameDataToClients("tableStatus");
						table.endTurn();
						startTurn();
					}
				default:
					table.processStandardVCard(vCard);
					sendGameDataToClients("tableStatus");
					table.endTurn();
					startTurn();
				}
			}
			else {
				sendGameDataToClients("tableStatus");
				table.endTurn();
				startTurn();
			}});
		
		reactionMethods.put("chosenProject1", (User user, JsonObject message)->{
			setTwoChipHelp=1;
			int chosenProject=message.get("projectID").getAsInt();
			System.out.println("chosen project1");
			if(table.setChipOnProject(table.getActiveProjectByID(chosenProject))) {
				VerantwortungsLOSCard vCard=table.drawVCard();
				if(vCard.getId()==20) {
					table.addVCard(20);
					vCard=table.drawVCard();
				}
				System.out.println(table.getCurrent().getUser().getName() + " vCard " + vCard.getId());
				messageToSend=Integer.toString(vCard.getId());
				sendGameDataToClients("showVCard");
				sendGameDataToClients("tableStatus");
				switch(vCard.getId()) {
				case 8:
					if(table.getCurrent().getChips().size()==0) {
						sendGameDataToClients("tableStatus");
						table.endTurn();
						startTurn();
						break;
					}
					sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjectsTwiceBurn");
					break;
				case 12:
					if(table.getCurrent().getChips().size()==1) {
						table.getCurrent().raiseScore(50);
						sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjects");
						break;
					}
					else if(table.getCurrent().getChips().size()==0) {
						table.getCurrent().raiseScore(50);
						sendGameDataToClients("tableStatus");
						table.endTurn();
						startTurn();
						break;
					}
					else if(table.getCurrent().getChips().size()>=2){
						table.getCurrent().raiseScore(50);
						sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjectTwoSelection");
						break;
					}
				case 15:
					int i=0;
					int z=0;
					table.getCurrent().raiseScore(100);
					for(Subproject p:table.getActiveProjects()) {
						if(p.twoChipsCanBeRemoved()) {
							i++;
						}
					}
					for(Subproject p:table.getActiveProjects()) {
						if(p.chipCanBeRemoved()) {
							z++;
						}
					}
					if(i==0 && z==1){
						System.out.println("OneChip");
						sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjectsTakeOneChip");
						break;
					}
					else if(i>=1){
						System.out.println("twoChips1");
						sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjectTwoSelectionTakeChips");
						break;
					}
					else if(z>=2){
						System.out.println("twoChips2");
						sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjectTwoSelectionTakeChips");
						break;
					}
					else {
						table.endTurn();
						startTurn();
						break;
					}
				case 16:
					table.getCurrent().raiseScore(100);
					if(table.getCurrent().getChips().size()>=2) {
						sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjectTwoSelection");
						break;
					}
					else if(table.getCurrent().getChips().size()==1) {
						sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjects");
						break;
					}
					else if(table.getCurrent().getChips().size()==0) {
						sendGameDataToClients("tableStatus");
						table.endTurn();
						startTurn();
						break;
					}
					break;
				case 17:
					if(table.getCurrent().getChips().size()>=2) {
						sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjectTwoSelection");
						break;
						
					}
					else if(table.getCurrent().getChips().size()==1) {
						sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjects");
						break;
					}
					else if(table.getCurrent().getChips().size()==0) {
						sendGameDataToClients("tableStatus");
						table.endTurn();
						startTurn();
						break;
					}
					break;
				case 20:
					if(table.getCurrent().getChips().size()>=1) {
					sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjectsExtraDice");
					break;
					}
					else if(table.getCurrent().getChips().size()==0) {
						sendGameDataToClients("tableStatus");
						startTurn();
					}
				default:
					table.processStandardVCard(vCard);
					sendGameDataToClients("tableStatus");
					if(table.getCurrent().getChips().isEmpty()) {
						table.endTurn();
						startTurn();
					}
					else {
						startTurn();
					}
					
				}
			}
			else {
				sendGameDataToClients("tableStatus");
			}});

		reactionMethods.put("specialCardAnswer11", (User user, JsonObject message)->{
			boolean answer=message.get("answer").getAsBoolean();
			if(answer) {
				card11used=true;
				sendGameDataToUser(table.getCurrent().getUser(), "aksForInAndOutProject");
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
				card23used=true;
				sendGameDataToUser(table.getCurrent().getUser(), "takeAwayChipFromPlayer");
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
			if(help==0) {
			help++;
			System.out.println("richtige Reaction");
			Subproject answerProject=table.getActiveProjectByID(message.get("projectID").getAsInt());
			System.out.println("IdNextFreeField"+answerProject.getIdNextFreeField());
			System.out.println("nextFreeField "+answerProject.getNextFreeField().isVField());
			System.out.println("IdNextFreeField"+answerProject.getIdSecondNextFreeField());
			System.out.println("SecondnextFreeField "+answerProject.getSecondNextFreeField().isVField());
			if(answerProject.getFields().get(answerProject.getIdNextFreeField()).isVField() || answerProject.getFields().get(answerProject.getIdNextFreeField()+1).isVField()) {
				System.out.println("zwei in Eins + V");
				Collections.shuffle(this.standardVIDs);
				int id=standardVIDs.get(0);
				standardVIDs.remove(0);
				System.out.println(id);
				table.setTwoChipsInOneProject(answerProject);
				sendGameDataToClients("tableStatus");
				VerantwortungsLOSCard vCard=table.getVCardByID(id);
				System.out.println("ID VKarte Standard"+vCard.getId());
				messageToSend=Integer.toString(vCard.getId());
				System.out.println(messageToSend);
				sendGameDataToClients("showVCard");
				handleVCardCommunication1(vCard.getId());
				sendGameDataToClients("tableStatus");
				table.endTurn();
				startTurn();
			}
			else if(!answerProject.getFields().get(answerProject.getIdNextFreeField()).isVField() && !answerProject.getFields().get(answerProject.getIdNextFreeField()+1).isVField()) {
				System.out.println("zwei in Eins - V");
				table.setTwoChipsInOneProject(answerProject);
				System.out.println("tableStatus nach setTwoChips");
				sendGameDataToClients("tableStatus");
				table.endTurn();
				startTurn();
			}
			}
		});

		//TODO
		reactionMethods.put("removeChipFromProjectAndPutItIntoAnotherAnswer",(User user, JsonObject message)->{
			Subproject fromProject=table.getActiveProjectByID(message.get("fromProjectID").getAsInt());
			Subproject toProject=table.getActiveProjectByID(message.get("toProjectID").getAsInt());
			SubprojectField toField=toProject.getNextFreeField();
			System.out.println("FromProject"+fromProject.getId());
			System.out.println("ToProject"+toProject.getId());
			table.removeChipFromProjectAndPutItIntoAnother(fromProject, toProject);
			sendGameDataToClients("tableStatus");
			if(toField.isVField()) {
				System.out.println("Bevor standard erzeugt");
				Collections.shuffle(this.standardVIDs);
				int id=standardVIDs.get(0);
				standardVIDs.remove(0);
				VerantwortungsLOSCard vCard=table.getVCardByID(id);
				messageToSend=Integer.toString(vCard.getId());
				sendGameDataToClients("showVCard");
				handleVCardCommunication1(vCard.getId());
				sendGameDataToClients("tableStatus");
			}
			if(setTwoChipHelp==1) {
				sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjects");
				setTwoChipHelp=0;
			}
			else if(card11used) {
				table.getCurrent().setHasVCard11(false);
				card11used=false;
				sendGameDataToUser(table.getCurrent().getUser(), "showDiceButton");
				return;
			}
			else {
			table.endTurn();
			startTurn();
			}
		});

		reactionMethods.put("removeChipFromProjectAndPutItIntoAnotherAnswerExtraDice",(User user, JsonObject message)->{
			Subproject fromProject=table.getActiveProjectByID(message.get("fromProjectID").getAsInt());
			Subproject toProject=table.getActiveProjectByID(message.get("toProjectID").getAsInt());
			SubprojectField toField=toProject.getNextFreeField();
			System.out.println("FromProject"+fromProject.getId());
			System.out.println("ToProject"+toProject.getId());
			table.removeChipFromProjectAndPutItIntoAnother(fromProject, toProject);
			sendGameDataToClients("tableStatus");
			if(toField.isVField()) {
				System.out.println("Bevor standard erzeugt");
				Collections.shuffle(this.standardVIDs);
				int id=standardVIDs.get(0);
				standardVIDs.remove(0);
				VerantwortungsLOSCard vCard=table.getVCardByID(id);
				messageToSend=Integer.toString(vCard.getId());
				sendGameDataToClients("showVCard");
				handleVCardCommunication1(vCard.getId());
				sendGameDataToClients("tableStatus");
			}
			if(setTwoChipHelp==1) {
				sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjects");
				setTwoChipHelp=0;
			}
			else {
			startTurn();
			}
		});
		
		reactionMethods.put("stealChipFromPlayer",(User user, JsonObject message)->{
			Player player=table.getPlayerByName(message.get("playerName").getAsString());
			table.takeChipFromAnotherPlayer(player);
			table.getCurrent().setHasVCard23(false);
			sendGameDataToClients("tableStatus");
			sendGameDataToUser(table.getCurrent().getUser(), "showDiceButton");
		});

		reactionMethods.put("add2ChipsOnExistingProjects", (User user, JsonObject message)-> {
			System.out.println("waaaaaaaarum");
			Subproject answerProject1=table.getActiveProjectByID(message.get("projectID1").getAsInt());
			Subproject answerProject2=table.getActiveProjectByID(message.get("projectID2").getAsInt());
			if(answerProject1.getId()==answerProject2.getId()) {
				table.add2ChipsOnTheSameProject(answerProject1);
			}
			else if(answerProject1.getId()!=answerProject2.getId()) {
				table.add2ChipsOnExistingProjects(answerProject1, answerProject2);
			}
			if(setTwoChipHelp==1) {
				sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjects");
				setTwoChipHelp=0;
			}
			else {
			table.endTurn();
			startTurn();
			}
		});

		reactionMethods.put("chipOnFieldBurnSZTTwice", (User user, JsonObject message)-> {
			Subproject answerProject=table.getActiveProjectByID(message.get("projectID").getAsInt());
			table.setChipOnExistingProjectBurnSZTTwice(answerProject);
			sendGameDataToClients("tableStatus");
			if(setTwoChipHelp==1) {
				sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjects");
				setTwoChipHelp=0;
			}
			else {
			table.endTurn();
			startTurn();
			}
		});

		reactionMethods.put("removeSecondChipFromProjectAndAddToPlayer", (User user, JsonObject message)-> {
			System.out.println("remove Second Chip");
			Subproject answerProject=table.getActiveProjectByID(message.get("projectID").getAsInt());
			table.remove1ChipFromProjectAndAddToPlayer(answerProject);
			sendGameDataToClients("tableStatus");
			if(setTwoChipHelp==1) {
				System.out.println("Why tho?!");
				sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjects");
				setTwoChipHelp=0;
			}
			else {
			table.endTurn();
			startTurn();
			}
		});
		
		reactionMethods.put("remove1ChipFromProjectAndAddToPlayer", (User user, JsonObject message)-> {
			Subproject answerProject=table.getActiveProjectByID(message.get("projectID").getAsInt());
			table.remove1ChipFromProjectAndAddToPlayer(answerProject);
			sendGameDataToClients("tableStatus");
			if(setTwoChipHelp==1) {
				sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjects");
				setTwoChipHelp=0;
			}
			else {
			table.endTurn();
			startTurn();
			}
		});
		
		reactionMethods.put("removeFirstChipFromProjectAndAddToPlayer",(User user, JsonObject message)-> {
			Subproject answerProject=table.getActiveProjectByID(message.get("projectID").getAsInt());
			table.remove1ChipFromProjectAndAddToPlayer(answerProject);
			sendGameDataToClients("tableStatus");
			if(setTwoChipHelp==1) {
				sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjects");
				setTwoChipHelp=0;
			}
			else {
				int i=0;
				for(Subproject p:table.getActiveProjects()) {
					if(p.chipCanBeRemoved()) {
						i++;
					}
				}
				if(i>=1){
					sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjectsTakeOneChip");
				}
				else {
					table.endTurn();
					startTurn();
				}
			}
		});

		reactionMethods.put("twoChipsInOneProject", (User user, JsonObject message)-> {
			Subproject answerProject=table.getActiveProjectByID(message.get("projectID").getAsInt());
			table.setTwoChipsInOneProject(answerProject);
			sendGameDataToClients("tableStatus");
			if(setTwoChipHelp==1) {
				sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjects");
				setTwoChipHelp=0;
			}
			else {
			table.endTurn();
			startTurn();
			}
		});

		reactionMethods.put("takeAway20SZTFromPlayer", (User user, JsonObject message)-> {
			Player player=table.getPlayerByName(message.get("player").getAsString());
			table.takeAway20SZTFromPlayer(player);
			sendGameDataToClients("tableStatus");
			if(setTwoChipHelp==1) {
				sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjects");
				setTwoChipHelp=0;
			}
			else {
			table.endTurn();
			startTurn();
			}
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

	//TODO card23
	public void startTurn() {
		System.out.println("start");
		if(!checkGameEnd()) {
			if(!table.hasToSkipNextRound()) {
				if(table.getCurrent() instanceof AI) {
					sendGameDataToClients("tableStatus");
					pause();
					processAIMove();
				}
			else {
				sendGameDataToClients("tableStatus");
				if(table.getCurrent().isHasVCard11()) {
					if(table.getCurrent().isHasVCard23()) {
						sendGameDataToUser(table.getCurrent().getUser(), "useSpecialCard11");
						sendGameDataToUser(table.getCurrent().getUser(), "useSpecialCard23");
					}
					else {
						sendGameDataToUser(table.getCurrent().getUser(), "useSpecialCard11");
					}
				}
				if(table.getCurrent().isHasVCard23()) {
					sendGameDataToUser(table.getCurrent().getUser(), "useSpecialCard23");
				}
				else {
					System.out.println("Dice Button next Player");
					sendGameDataToUser(table.getCurrent().getUser(), "showDiceButton");
				}
			}
			}
			else {
				table.endTurn();
				startTurn();
			}
		}
		else {
			System.out.println("Zu Ende: Gewinner");
			sendGameDataToClients("Winner");
			pause();
            gState = GameState.FINISHED;
		}
	}
		
	
	public static void pause(){
		try {
			java.util.concurrent.TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int random(int max) {
		int random = rand.nextInt((max - 0));
		System.out.println("Zufallszahl: " + random);
		return random;
	}
	
	//TODO 
	public void processAIMove() {
		if(table.getCurrent().hasVCard11) {
			if(table.projectWithMoreThanOneChippedFieldAvailable()) {
				ArrayList<Subproject> projectsArrTo = table.getActiveProjects();
				ArrayList<Subproject> projectsArrFrom = new ArrayList<Subproject>();
				for(Subproject project:table.getActiveProjects()) {
					if(project.chipCanBeRemoved()) {
						projectsArrFrom.add(project);
					}
				}
				int randomFrom = random(projectsArrFrom.size());
				int randomTo = random(projectsArrTo.size());
				Subproject fromProject = projectsArrFrom.get(randomFrom);
				Subproject toProject = projectsArrTo.get(randomTo);
				table.removeChipFromProjectAndPutItIntoAnother(fromProject, toProject);
				sendGameDataToClients("tableStatus");
			}
		}
		if(table.getCurrent().hasVCard23) {
			ArrayList<Player> players = table.getPlayersExceptCurrent();
			Player stealPlayer = null;
			for(Player p: players) {
				if(p.isChipStealable()) {
					stealPlayer = p;
				}
			}
			if(stealPlayer!=null) {
				table.takeChipFromAnotherPlayer(stealPlayer);
			}
		}
		boolean diceResult=table.rollTheDice();
		if(diceResult) {
			pause();
			ErgebnisLOSCard eCard=table.drawECard();
			if(eCard.getId()==3) {eCard=table.drawECard();}
			System.out.println(table.getCurrent().getUser().getName() + " eCard " + eCard.getId());
			messageToSend=Integer.toString(eCard.getId());
			sendGameDataToClients("showECard");
			switch(eCard.getId()) {
			case 3:
				table.setTwoChipsInOneProject(table.getActiveProjects().get(random(table.getActiveProjectsMoreThenOneFreeField().size())));
				sendGameDataToClients("tableStatus");
				table.endTurn();
				startTurn();
				break;
			case 24:
				handleVCardForAIAfterChipSet(
						table.setChipOnProject(
						table.getActiveProjects().get(random(
						table.getActiveProjects().size()))));
				sendGameDataToClients("tableStatus");
				table.endTurn();
				startTurn();
				break;
			case 42:
				if(table.projectWithMoreThanOneChippedFieldAvailable()) {
					ArrayList<Subproject> projectsArrTo = table.getActiveProjects();
					ArrayList<Subproject> projectsArrFrom = new ArrayList<Subproject>();
					for(Subproject project:table.getActiveProjects()) {
						if(project.chipCanBeRemoved()) {
							projectsArrFrom.add(project);
						}
					}
					int randomFrom = random(projectsArrFrom.size());
					int randomTo = random(projectsArrTo.size());
					Subproject fromProject = projectsArrFrom.get(randomFrom);
					Subproject toProject = projectsArrTo.get(randomTo);
					table.removeChipFromProjectAndPutItIntoAnother(fromProject, toProject);
					sendGameDataToClients("tableStatus");
				}
				else {
					System.out.println("Kein Projekt mit mehr als Startchip vorhanden");
				}
				table.endTurn();
				startTurn();
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
				ArrayList<Player> players = table.getPlayersExceptCurrent();
				Player player = players.get(random(players.size()));
				table.takeAway20SZTFromPlayer(player);
				sendGameDataToClients("tableStatus");
				table.endTurn();
				startTurn();
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
			System.out.println(table.getCurrent().getUser().getName() + " Chip setzen");
			handleVCardForAIAfterChipSet(
					table.setChipOnProject(
					table.getActiveProjects().get(random(
					table.getActiveProjects().size()))));
		}
	}
	
	public void handleVCardForAIAfterChipSet(boolean vCardOnField) {
		if(vCardOnField) {
			pause();
			VerantwortungsLOSCard vCard=table.drawVCard();
			System.out.println("VCard nach Chip Platzierung: " + vCard.getId());
			messageToSend=Integer.toString(vCard.getId());
			sendGameDataToClients("showVCard");
			pause();
			handleVCardCommunication(vCard.getId());
			sendGameDataToClients("tableStatus");
		}
		else {
			table.endTurn();
			startTurn();
			sendGameDataToClients("tableStatus");
		}
	}

	private void handleVCardCommunication(int id) {
		switch(id) {
		case 8:
			if(table.getCurrent().getChips().size()==0) {
				sendGameDataToClients("tableStatus");
				table.endTurn();
				startTurn();
				break;
			}
			System.out.println(table.getCurrent().getUser().getName() + " Chip setzen");
			table.setChipOnExistingProjectBurnSZTTwice(table.getActiveProjects().get(random(table.getActiveProjects().size())));
			table.endTurn();
			startTurn();
			sendGameDataToClients("tableStatus");
			break;
		case 12:
			if(table.getCurrent().getChips().size()==1) {
				table.getCurrent().raiseScore(50);
				handleVCardForAIAfterChipSet(
						table.setChipOnProject(
						table.getActiveProjects().get(random(
						table.getActiveProjects().size()))));
				break;
			}
			else if(table.getCurrent().getChips().size()==0) {
				table.getCurrent().raiseScore(50);
				sendGameDataToClients("tableStatus");
				table.endTurn();
				startTurn();
				break;
			}
			else if(table.getCurrent().getChips().size()>=2){
				table.getCurrent().raiseScore(50);
				Subproject projectA = table.getActiveProjects().get(random(table.getActiveProjects().size()));
				Subproject projectB = table.getActiveProjects().get(random(table.getActiveProjects().size()));
				table.add2ChipsOnExistingProjects(projectA, projectB);
				break;
			}
		case 15:
			int i=0;
			int z=0;
			table.getCurrent().raiseScore(100);
			ArrayList<Subproject> arr = new ArrayList<Subproject>();
			for(Subproject p:table.getActiveProjects()) {
				if(p.twoChipsCanBeRemoved()) {
					arr.add(p);
					i++;
				}
			}
			ArrayList<Subproject> arr2 = new ArrayList<Subproject>();
			for(Subproject p:table.getActiveProjects()) {
				if(p.chipCanBeRemoved()) {
					arr2.add(p);
					z++;
				}
			}
			if(i==0 && z==1){
				System.out.println("OneChip");
				table.remove1ChipFromProjectAndAddToPlayer(arr2.get(0));
				table.endTurn();
				startTurn();
				break;
			}
			else if(i>=1){
				System.out.println("twoChips1");
				table.remove2ChipsFromProjectsAndAddToPlayer(arr.get(0), arr.get(1));
				table.endTurn();
				startTurn();
				break;
			}
			else if(z>=2){
				System.out.println("twoChips2");
				table.remove2ChipsFromProjectsAndAddToPlayer(arr2.get(0), arr2.get(1));
				table.endTurn();
				startTurn();
				break;
			}
			else {
				table.endTurn();
				startTurn();
				break;
			}
		case 16:
			table.getCurrent().raiseScore(100);
			if(table.getCurrent().getChips().size()>=2) {
				handleVCardForAIAfterChipSet(
						table.setChipOnProject(
						table.getActiveProjects().get(random(
						table.getActiveProjects().size()))));
				handleVCardForAIAfterChipSet(
						table.setChipOnProject(
						table.getActiveProjects().get(random(
						table.getActiveProjects().size()))));
				break;
			}
			else if(table.getCurrent().getChips().size()==1) {
				handleVCardForAIAfterChipSet(
						table.setChipOnProject(
						table.getActiveProjects().get(random(
						table.getActiveProjects().size()))));
				break;
			}
			else if(table.getCurrent().getChips().size()==0) {
				sendGameDataToClients("tableStatus");
				table.endTurn();
				startTurn();
				break;
			}
			break;
		case 17:
			if(table.getCurrent().getChips().size()>=2) {
				Subproject projectA = table.getActiveProjects().get(random(table.getActiveProjects().size()));
				Subproject projectB = table.getActiveProjects().get(random(table.getActiveProjects().size()));
				table.add2ChipsOnExistingProjects(projectA, projectB);
				break;
			}
			else if(table.getCurrent().getChips().size()==1) {
				handleVCardForAIAfterChipSet(
						table.setChipOnProject(
						table.getActiveProjects().get(random(
						table.getActiveProjects().size()))));
				break;
			}
		case 20:
			if(table.projectWithMoreThanOneChippedFieldAvailable()) {
				ArrayList<Subproject> projectsArrTo = table.getActiveProjects();
				ArrayList<Subproject> projectsArrFrom = new ArrayList<Subproject>();
				for(Subproject project:table.getActiveProjects()) {
					if(project.chipCanBeRemoved()) {
						projectsArrFrom.add(project);
					}
				}
				int randomFrom = random(projectsArrFrom.size());
				int randomTo = random(projectsArrTo.size());
				Subproject fromProject = projectsArrFrom.get(randomFrom);
				Subproject toProject = projectsArrTo.get(randomTo);
				table.removeChipFromProjectAndPutItIntoAnother(fromProject, toProject);
				sendGameDataToClients("tableStatus");
			}
			startTurn();
			break;
			
		default:
			table.processStandardVCard(table.getVCardFromCurrentByID(id));
			table.endTurn();
			startTurn();
		}
		//table.endTurn();
		//startTurn();
		sendGameDataToClients("tableStatus");
	}
	
	
	
	
	
	
	
	
	public void handleVCardCommunication1(int id) {
	System.out.println("handleID"+id);
	switch(id) {
	case 8:
		if(table.getCurrent().getChips().size()==0) {
			sendGameDataToClients("tableStatus");
			table.endTurn();
			startTurn();
			break;
		}
		sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjectsTwiceBurn");
		break;
	case 12:
		if(table.getCurrent().getChips().size()==1) {
			table.getCurrent().raiseScore(50);
			sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjects");
			break;
		}
		else if(table.getCurrent().getChips().size()==0) {
			table.getCurrent().raiseScore(50);
			sendGameDataToClients("tableStatus");
			table.endTurn();
			startTurn();
			break;
		}
		else if(table.getCurrent().getChips().size()>=2){
			table.getCurrent().raiseScore(50);
			sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjectTwoSelection");
			break;
		}
	case 15:
		int i=0;
		int z=0;
		table.getCurrent().raiseScore(100);
		for(Subproject p:table.getActiveProjects()) {
			if(p.twoChipsCanBeRemoved()) {
				i++;
			}
		}
		for(Subproject p:table.getActiveProjects()) {
			if(p.chipCanBeRemoved()) {
				z++;
			}
		}
		if(i==0 && z==1){
			System.out.println("OneChip");
			sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjectsTakeOneChip");
			break;
		}
		else if(i>=1){
			System.out.println("twoChips1");
			sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjectTwoSelectionTakeChips");
			break;
		}
		else if(z>=2){
			System.out.println("twoChips2");
			sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjectTwoSelectionTakeChips");
			break;
		}
		else {
			table.endTurn();
			startTurn();
			break;
		}
	case 16:
		table.getCurrent().raiseScore(100);
		if(table.getCurrent().getChips().size()>=2) {
			sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjectsPlaceTwoChips");
			break;
		}
		else if(table.getCurrent().getChips().size()==1) {
			sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjects");
			break;
		}
		else if(table.getCurrent().getChips().size()==0) {
			sendGameDataToClients("tableStatus");
			table.endTurn();
			startTurn();
			break;
		}
		break;
	case 17:
		if(table.getCurrent().getChips().size()>=2) {
			sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjectTwoSelection");
			break;
			
		}
		else if(table.getCurrent().getChips().size()==1) {
			sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjects");
			break;
		}
	case 20:
		if(table.getCurrent().getChips().size()>=1) {
		sendGameDataToUser(table.getCurrent().getUser(), "showAvailableProjectsExtraDice");
		break;
		}
		else if(table.getCurrent().getChips().size()==0) {
			sendGameDataToClients("tableStatus");
			table.endTurn();
			startTurn();
		}
	default:
		table.processStandardVCard(table.getVCardByID(id));
		sendGameDataToClients("tableStatus");
//		table.endTurn();
//		startTurn();
	}
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
				if(project.getFreeFieldsLeft()>=2) {
					projects2.add(project.toJson());
				}
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
			JsonObject playersToStealFrom=new JsonObject();
			for(Player p:table.getPlayersExceptCurrent()) {
				players.add(p.toJson());
			}
			System.out.println(""+players.toString());
			playersToStealFrom.add("players", players);
			System.out.println(playersToStealFrom.toString());
			return playersToStealFrom.toString();
		//Sends the available projects. Player needs to select to, for removing chip from and adding chip into	
		case ("showAvailableProjectsTwiceBurn"):
			JsonArray projects8= new JsonArray();
			JsonObject twiceBurnProjects=new JsonObject();
			for(Subproject project:table.getActiveProjects()) {
				projects8.add(project.toJson());
			}
			twiceBurnProjects.add("availableProjects", projects8);
			return twiceBurnProjects.toString();
		//Sends the available projects. Player needs to select two (can be the same)
		case ("showAvailableProjectTwoSelection"):
			JsonArray projects3= new JsonArray();
			JsonObject twoSelectionProjects=new JsonObject();
			for(Subproject project:table.getActiveProjects()) {
				projects3.add(project.toJson());
			}
			twoSelectionProjects.add("availableProjects", projects3);
			return twoSelectionProjects.toString();
		//Sends the available projects. Player needs to select two (can be the same)
		case ("showAvailableProjectTwoSelectionTakeChips"):
			JsonArray projects4= new JsonArray();
			JsonObject twiceSelectionProjects=new JsonObject();
			for(Subproject project:table.getActiveProjects()) {
				if(project.chipCanBeRemoved()) {
					projects4.add(project.toJson());
				}
			}
			twiceSelectionProjects.add("availableProjects", projects4);
			return twiceSelectionProjects.toString();
		//Sends the available projects. Player needs to select two (can be the same)
		case ("showAvailableProjectsPlaceTwoChips"):
			JsonArray projects5= new JsonArray();
			JsonObject placeToChipsProjects=new JsonObject();
			for(Subproject project:table.getActiveProjects()) {
				projects5.add(project.toJson());
			}
			placeToChipsProjects.add("availableProjects", projects5);
			return placeToChipsProjects.toString();
		//Sends the available projects and expects one selection. After placing the chip on the project the player gets an extra Turn by dicing again
		case ("showAvailableProjectsExtraDice"):
			JsonObject availableProjectsExtraDice = new JsonObject();
			JsonArray outProjects1= new JsonArray();
			JsonArray inProjects1= new JsonArray();
			for(Subproject project:table.getActiveProjects()) {
				if(project.chipCanBeRemoved()) {
					outProjects1.add(project.toJson());
				}
			}
			for(Subproject project:table.getActiveProjects()) {
				inProjects1.add(project.toJson());
			}
			
			availableProjectsExtraDice.add("outProjects", outProjects1);
			availableProjectsExtraDice.add("inProjects", inProjects1);
			System.out.println(availableProjectsExtraDice.toString());
			return availableProjectsExtraDice.toString();
		//You need to ask the player whether he wants to use his card 11 or not and send a boolean to server
		case ("useSpecialCard11"):
			JsonObject card11=new JsonObject();
			card11.addProperty("player", table.getCurrent().getUser().getName().toString());
			return card11.toString();
		//You need to ask the player whether he wants to use his card 23 or not and send a boolean to server
		case ("useSpecialCard23"):
			JsonObject card23=new JsonObject();
			card23.addProperty("player", table.getCurrent().getUser().getName().toString());
			return card23.toString();
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
				user1.addProperty("role", "Spieler"); 
				users.add(user1);
			}
			object.add("users", users);
			for(int x=0; x<aiCount; x++) {
			    JsonObject bot=new JsonObject();
			    bot.addProperty("name", botNames.get(x)); 
			    bot.addProperty("role", "Bot"); 
			    users.add(bot);
			}
			System.out.println(object.toString());
			return object.toString();
		case("showAvailableProjectsTakeOneChip"):
			JsonArray projects10= new JsonArray();
			JsonObject singleSelectionProjects=new JsonObject();
			for(Subproject project:table.getActiveProjects()) {
				if(project.chipCanBeRemoved()) {
					projects10.add(project.toJson());
				}
			}
			singleSelectionProjects.add("availableProjects", projects10);
			return singleSelectionProjects.toString();
		case ("Winner"):
			JsonObject winner = new JsonObject();
			winner.addProperty("name", table.determineWinner().getUser().getName());
			return winner.toString();
		case ("takeAwayChipFromPlayer"):
			JsonArray players2=new JsonArray();
			JsonObject playersToStealChipFrom=new JsonObject();
			for(Player p:table.getPlayersExceptCurrent()) {
				players2.add(p.toJson());
			}
			System.out.println(""+players2.toString());
			playersToStealChipFrom.add("players", players2);
			System.out.println(playersToStealChipFrom.toString());
			return playersToStealChipFrom.toString();
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
		return players.size() + aiCount;
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
	
	private boolean checkGameEnd() {
		boolean toContinue=true;
		if(table.getPlayers().isEmpty()) {
			toContinue=false;
		}
		for(Player p:table.getPlayers()) {
			if(!p.getChips().isEmpty()) {
				toContinue=false;
			}
		}
		if(table.airportIsBuilt() || toContinue) {
			return true;
		}
		else {
			return false;
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
