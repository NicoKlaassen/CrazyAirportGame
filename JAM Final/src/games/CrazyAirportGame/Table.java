package games.CrazyAirportGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Table {

	private ArrayList<Subproject> projectsAvailable;
	private ArrayList<Subproject> projectsActive;
	private ArrayList<Subproject> projectsFinished;
	private ArrayList<Player> players;
	private ArrayList<ErgebnisLOSCard> eCards;
	private ArrayList<VerantwortungsLOSCard> vCards;
	private Player current;
	
	public Table() {
		this.projectsActive=new ArrayList<Subproject>();
		this.projectsAvailable=new ArrayList<Subproject>();
		this.projectsFinished=new ArrayList<Subproject>();
		this.players=new ArrayList<Player>();
		this.eCards=new ArrayList<ErgebnisLOSCard>();
		this.vCards=new ArrayList<VerantwortungsLOSCard>();
		this.current=null;
		this.initSubprojects();
		this.fillEreignisLOSArray();
		this.fillVerantworungsLOSArray();
		Collections.shuffle(vCards);
		Collections.shuffle(eCards);
		Collections.shuffle(projectsAvailable);
	}

	//Create all subprojects with their Field-ArrayLists
	public void initSubprojects(){
		ArrayList<SubprojectField> fieldsFirestation=new ArrayList<SubprojectField>(Arrays.asList(
				new SubprojectField(10, false, false),
				new SubprojectField(10, false, false),
				new SubprojectField(50, true, false),
				new SubprojectField(10, false, false),
				new SubprojectField(20, false, false),
				new SubprojectField(30, false, false),
				new SubprojectField(40, true, false),
				new SubprojectField(20, false, false),
				new SubprojectField(50, false, true)));
		projectsAvailable.add(new Subproject("Feuerwache",fieldsFirestation,0));
		ArrayList<SubprojectField> fieldsNorthStreet=new ArrayList<SubprojectField>(Arrays.asList(
				new SubprojectField(10, false, false),
				new SubprojectField(30, false, false),
				new SubprojectField(10, false, false),
				new SubprojectField(20, false, false),
				new SubprojectField(50, false, false),
				new SubprojectField(20, true, false),
				new SubprojectField(10, false, false),
				new SubprojectField(30, true, false),
				new SubprojectField(30, false, true)));
		projectsAvailable.add(new Subproject("Landebahn Nord",fieldsNorthStreet,1));
		ArrayList<SubprojectField> fieldsSouthStreet=new ArrayList<SubprojectField>(Arrays.asList(
				new SubprojectField(20, false, false),
				new SubprojectField(10, false, false),
				new SubprojectField(20, true, false),
				new SubprojectField(10, false, false),
				new SubprojectField(50, false, false),
				new SubprojectField(50, false, false),
				new SubprojectField(10, true, false),
				new SubprojectField(20, false, false),
				new SubprojectField(40, false, true)));
		projectsAvailable.add(new Subproject("Landebahn Sued",fieldsSouthStreet,2));
		ArrayList<SubprojectField> fieldsTerminalA=new ArrayList<SubprojectField>(Arrays.asList(
				new SubprojectField(20, false, false),
				new SubprojectField(20, false, false),
				new SubprojectField(10, true, false),
				new SubprojectField(30, false, false),
				new SubprojectField(10, true, false),
				new SubprojectField(50, false, true)));
		projectsAvailable.add(new Subproject("Terminal A",fieldsTerminalA,3));
		ArrayList<SubprojectField> fieldsTerminalB=new ArrayList<SubprojectField>(Arrays.asList(
				new SubprojectField(20, false, false),
				new SubprojectField(10, false, false),
				new SubprojectField(30, true, false),
				new SubprojectField(10, false, false),
				new SubprojectField(10, false, false),
				new SubprojectField(50, true, false),
				new SubprojectField(20, false, false),
				new SubprojectField(50, false, true)));
		projectsAvailable.add(new Subproject("Terminal B",fieldsTerminalB,4));
		ArrayList<SubprojectField> fieldsMainTerminal=new ArrayList<SubprojectField>(Arrays.asList(
				new SubprojectField(20, false, false),
				new SubprojectField(10, true, false),
				new SubprojectField(30, false, false),
				new SubprojectField(10, false, false),
				new SubprojectField(10, false, false),
				new SubprojectField(20, true, false),
				new SubprojectField(50, true, false),
				new SubprojectField(10, false, false),
				new SubprojectField(50, false, true)));
		projectsAvailable.add(new Subproject("Hauptterminal",fieldsMainTerminal,5));
		ArrayList<SubprojectField> fieldsParkStation=new ArrayList<SubprojectField>(Arrays.asList(
				new SubprojectField(50, false, false),
				new SubprojectField(50, false, false),
				new SubprojectField(50, true, false),
				new SubprojectField(20, false, false),
				new SubprojectField(20, true, false),
				new SubprojectField(20, false, false),
				new SubprojectField(50, false, false),
				new SubprojectField(20, false, true)));
		projectsAvailable.add(new Subproject("Parkplatz",fieldsParkStation,6));
		ArrayList<SubprojectField> fieldsPreStation=new ArrayList<SubprojectField>(Arrays.asList(
				new SubprojectField(20, false, false),
				new SubprojectField(10, false, false),
				new SubprojectField(50, true, false),
				new SubprojectField(20, false, false),
				new SubprojectField(50, false, false),
				new SubprojectField(20, false, true)));
		projectsAvailable.add(new Subproject("Vorfeld",fieldsPreStation,7));
	}
	
	//Fills deck of VerantwortungLOS-Cards by setting ID's
	public void fillVerantworungsLOSArray() {
	for(int i=1; i<=24; i++) {
		VerantwortungsLOSCard c=new VerantwortungsLOSCard(i);
		vCards.add(c);
		}
	}
	
	//Fills deck of EreignisLOS-Cards by setting ID's
	public void fillEreignisLOSArray() {
		for(int i=1; i<=55; i++) {
			if(i!=10) {
				ErgebnisLOSCard c= new ErgebnisLOSCard(i);
				eCards.add(c);
			}
		}
	}
	
	public ErgebnisLOSCard getECardByID(int id) {
		ErgebnisLOSCard result=null;
		for (ErgebnisLOSCard e:eCards) {
			if(e.getId()==id) {
				result=e;
			}
		}
		return result;
	}
	
	public VerantwortungsLOSCard getVCardByID(int id) {
		VerantwortungsLOSCard result=null;
		for (VerantwortungsLOSCard v:vCards) {
			if(v.getId()==id) {
				result=v;
			}
		}
		return result;
	}
	
	public boolean projectWithMoreThanOneChippedFieldAvailable() {
		boolean result=false;
		for(Subproject p:projectsActive) {
			if(p.getFields().get(1).isChipped()) {
				result=true;
			}
		}
		return result;
	}
	
	//Gives every player 7 chips
	public void initChips() {
		for(Player p:players) {
			for(int i=0; i<7; i++) {
				p.addChip(new Chip(p));
			}			
		}
	}
	
	//Initializes everything needed to start a game
	public void startGame() {
		initChips();
		assignAndStartInitialProjects();
	}
	
	//Adds player to game. If he is the first player to join a match he automatically is the current/starting player
	public void addPlayer(Player player) {
		if(players.isEmpty()) {
			current=player;
			players.add(current);
		}
		else {
			players.add(player);
		}
	}
	
	public void replacePlayerWithAI(Player player) {
		//ToDo
	}
	
	//If result==true --> pull card from EreignisLOSes | If result==false place chip on existing project 
	public boolean rollTheDice() {
		boolean result;
		int randomDiceRoll = (int)(Math.random() * 6) + 1;
		if(randomDiceRoll>=5) {
			result=true;
		}
		else {
			result=false;
		}
		return result;
	}
	
	public Subproject getAvailableProjectByID(int id) {
		for(Subproject p:projectsAvailable) {
			if(p.getId()==id) return p;
		}
		return null;
	}
	
	public Subproject getActiveProjectByID(int id) {
		for(Subproject p:projectsActive) {
			if(p.getId()==id) return p;
		}
		return null;
	}
	
	public Player getPlayerByName(String name) {
		for(Player p:players) {
			if(p.getUser().getName().equals(name)) return p;
		}
		return null;
	}
	
	public ArrayList<Player> getPlayersExceptCurrent(){
		ArrayList<Player> players=new ArrayList<Player>();
		for(Player p:this.players) {
			if(p.getUser().getName()!=current.getUser().getName()) {
				players.add(p);
			}
		}
		return players;
	}
	
	public VerantwortungsLOSCard getVCardFromCurrentByID(int id) {
		for(VerantwortungsLOSCard vCard:current.getvCards()) {
			if(vCard.getId()==id) {
				return vCard;
			}
		}
		return null;
	}
	
	
	//Assign exactly one project to every player and directly opens it up
	public void assignAndStartInitialProjects() {
		for(Player p:players) {
			Subproject sp=drawProject();
			SubprojectField result=sp.setChip(p.removeChip());
			p.raiseScore(result.getAmountSZT());
		}
	}
	
	public void endTurn() {
		current=players.get((players.indexOf(current)+1)%players.size());
	}
	
	public Player getCurrent() {
		return current;
	}

	//Checks if player can use the special card with ID 11
	public boolean checkForSpecialCard11() {
		return current.isHasVCard11();
	}
	
	//Checks if player can use the special card with ID 23
	public boolean checkForSpecialCard23() {
		return current.isHasVCard23();
	}
	
	//Picks the first (due to shuffle random) available project and puts it into the active stack
	public Subproject drawProject() {
		Subproject result=projectsAvailable.get(0);
		projectsAvailable.remove(result);
		projectsActive.add(result);
		return result;
	}
	
	//Opens up exactly that project in the active stack that has its first field not chipped and sets a chip of the current player to its first field
	public void openUpProject(Subproject project) {
		SubprojectField field=project.setChip(current.removeChip());
		current.raiseScore(field.getAmountSZT());
	}
	
	//Checks if the airport is built (when there are no available and active projects are left)
	public boolean airportIsBuilt() {
		if(projectsAvailable.isEmpty() && projectsActive.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	//Added Player as parameter to assign Card to players deck
	// To add: Process E-Card
	public ErgebnisLOSCard drawECard() {
		ErgebnisLOSCard result=eCards.get(0);
		eCards.remove(result);
		current.addeCard(result);
		return result;
	}
	
	/*Draws the first (due to shuffle random) card from the VerantwortungsLOS-Deck and adds it to the player who draw it
	*ToDo
	*	- processVCard-Method
	*/
	public VerantwortungsLOSCard drawVCard() {
		VerantwortungsLOSCard result=vCards.get(0);
		vCards.remove(result);
		current.addvCard(result);
		return result;
	}
	
	//Performances VCard with actions that don't need any input by the player
	public void processStandardVCard(VerantwortungsLOSCard card) {
		switch(card.getId()) {
		case (1):
			current.raiseScore(100);
			break;
		case (2):
			current.raiseScore(50);
			getRightNeigbour().raiseScore(100);
			break;
		case (3):
			getLeftNeighbor().raiseScore(50);
			break;
		case (4):
			current.raiseScore(100);
			break;
		case (5):
			current.setSkipNextRound(true);
			break;
		case (6):
			current.raiseScore(70);
			if(!projectsAvailable.isEmpty()) {
				openUpProject(drawProject());
			}
			break;
		case (7):
			break;
		case (9):
		case (10):
		case (13):
			break;
		case (18):
			raiseScoreOfEveryPlayer(50);
			break;
		case (19):
			current.raiseScore(100);
			secondLeftNeighborGivesChipToCurrent();
			break;
		case (21):
			secondLeftNeighborGivesSZTToCurrent(50);
			break;
		case (22):
			SZTFromEveryOtherPlayer(30);
			break;
		case (24):
			current.raiseScore(50);
			secondLeftNeighborGivesChipToCurrent();
			break;
		}
	}
	
	//Performances ECard with actions that don't need any input by the player
	public void processStandardECard(ErgebnisLOSCard card) {
		switch(card.getId()) {
		case 1:
			current.raiseScore(20);
			break;
		case 2:
			current.raiseScore(10);
			getLeftNeighbor().raiseScore(20);
			break;
		case 4:
			raiseScoreOfEveryPlayer(10);
			break;
		case 5:
			current.raiseScore(10);
			break;
		case 6:
			break;
		case 7:
			current.raiseScore(30);
			break;
		case 8:
			break;
		case 9:
			current.raiseScore(20);
			break;
		case 11:
			getLeftNeighbor().raiseScore(10);
			getRightNeigbour().raiseScore(10);
			break;
		case (12):
		case (13):
		case (14):
			current.raiseScore(10);
			break;
		case (15):
		case (16):
		case (17):
			break;
		case (18):
			current.raiseScore(30);
			break;
		case (19):
			current.raiseScore(20);
			break;
		case (20):
			current.raiseScore(50);
			break;
		case (21):
			if(getRightNeigbour().getScore()<50) {
				int score=getRightNeigbour().getScore();
				getRightNeigbour().lowerScore(score);
				current.raiseScore(score);
				break;
			}
			else {
				getRightNeigbour().lowerScore(50);
				current.raiseScore(50);
				break;
			}
		case (22):
			current.raiseScore(20);
			break;
		case (23):
			break;
		case (25):
			current.raiseScore(20);
			break;
		case (26):
		case (27):
		case (28):
		case (29):
		case (30):
		case (31):
			current.raiseScore(10);
			break;
		case (32):
			current.raiseScore(30);
			break;
		case (33):
			current.raiseScore(20);
			break;
		case (34):
			current.raiseScore(20);
			getRightNeigbour().raiseScore(10);
			break;
		case (35):
			current.raiseScore(20);
			break;
		case (36):{
			if(!projectsAvailable.isEmpty()) {
				openUpProject(drawProject());
				break;
			}
			else current.raiseScore(30);
			}
			break;
		case (37):
			current.raiseScore(20);
			break;
		case (38):
			current.raiseScore(10);
			break;
		case (39):
			current.raiseScore(10);
			raiseScoreSecondRightNeighbor(10);
			break;
		case (40):
			current.raiseScore(20);
			break;
		case (41):
			current.raiseScore(10);
			break;
		case (43):
			current.lowerScore(20);
			getRightNeigbour().raiseScore(20);
			break;
		case (44):
		case (45):
			current.raiseScore(20);
			break;
		case (46):
			raiseScoreOfEveryPlayer(10);
			break;
		case (48):
			current.raiseScore(20);
			break;
		case (50):
			break;
		case (52):
			current.raiseScore(20);
			break;
		case (54):
			current.raiseScore(50);
			break;
		case (55):
			current.raiseScore(10);
			break;
		}
	}
	
	/*Sets chip on a field and reacts to the type of the field and adds SZT to players score no matter what
	* 
	*When it's a V-Field
	*	- V-Card gets drawn and action is going to be performed
	*When it's a Finish-Field
	*	- Project gets removed from active ones and added to finished
	*	- Every players score (except the current ones) gets lowered by SZT of the field
	*	- Score of current player gets raised every time another players score gets lowered
	*When its a Regular-Field
	*	- Current players amount gets raised by SZT of the field
	*/
	public boolean setChipOnProject(Subproject project) {
		SubprojectField field=project.setChip(current.removeChip());
		current.raiseScore(field.getAmountSZT());
		if(field.isVField()) {
			return true;
		}
		if(field.isLastField()) {
			finishProject(project);
		}
		return false;
	}
	
	//Takes chip from a given player (should be chosen from the current player) and adds it to the chip set of the current player by adding
	public void takeChipFromAnotherPlayer(Player FromPlayer) {
		Chip chip=FromPlayer.removeChip();
		current.addChip(chip);
	}
	
	/*Checks whether the current player has to skip the round or not. Two possible reasons for this:
	* 1.
	*	- Players skipNextRound attribute is true (caused by a card)
	*	- After being skipped this round its set on false again so that he can play again in the next round 
	* 2.
	* 	- Player has no chips left. Has to sit out until he has got some chips back from a finished project
	*/
	public boolean hasToSkipNextRound() {
		if(current.isSkipNextRound()) {
			current.setSkipNextRound(false);
			return true;
		}
		if(current.getChips().isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/*Finishes a given project by performing the following tasks
	*	- Lowering every players (except the current ones) score by SZT of field
	*	- Raising the current players score every time the score of another player gets lowered
	*	- Resets every chip in the project (by setting the current owner to the original one)
	*	- Gives back chips to their owners
	* 	- Adds project to finished stack
	* 	- Removes project from active stack
	*/
	public void finishProject(Subproject project) {
		for(Player p:players) {
			if(p!=current) {
				p.lowerScore(project.getFinishField().getAmountSZT());
				current.raiseScore(project.getFinishField().getAmountSZT());
			}
		}
		project.finishProject();
		projectsFinished.add(project);
		projectsActive.remove(project);
		openUpProject(drawProject());
	}

	//Returns the right neighbor of the current player
	public Player getRightNeigbour() {
		return players.get((players.indexOf(current)+1)%players.size());
	}
	
	//Returns the left neighbor of the current player
	public Player getLeftNeighbor() {
		if(players.indexOf(current)==0) {
			return players.get(players.size()-1);
		}
		else {
			return players.get(players.indexOf(current)-1);
		}
	}
	
	//Makes the current player skip the next round
	public void makePlayerSkipNextRound() {
		current.setSkipNextRound(true);
	}
	
	//Removes one non-start-chip from an active project and puts it into another
	public void removeChipFromProjectAndPutItIntoAnother(Subproject fromProject, Subproject toProject) {
		if(fromProject.chipCanBeRemoved()){
			Chip removedChip=fromProject.removeLastChip();
			current.raiseScore(toProject.setChip(removedChip).getAmountSZT());
		}
	}
	
	//Instead of adding one chip (like normal) on one project this methods sets two chips on two projects
	public void add2ChipsOnExistingProjects(Subproject projectA, Subproject projectB) {
		current.raiseScore(projectA.setChip(current.removeChip()).getAmountSZT());
		current.raiseScore(projectB.setChip(current.removeChip()).getAmountSZT());
	}
	
	//sets two chips on one project
	public void add2ChipsOnTheSameProject(Subproject project) {
		current.raiseScore(project.setChip(current.removeChip()).getAmountSZT());
		current.raiseScore(project.setChip(current.removeChip()).getAmountSZT());
	}
	
	//Sets chip on existing project and burns the SZT twice
	public void setChipOnExistingProjectBurnSZTTwice(Subproject project) {
		current.raiseScore(project.setChip(current.removeChip()).getAmountSZT()*2);
	}
	
	//removes two chips from active projects and adds it to chip-list of current player
	public void remove2ChipsFromProjectsAndAddToPlayer(Subproject projectA, Subproject projectB) {
		current.addChip(projectA.removeLastChip());
		current.addChip(projectB.removeLastChip());
	}
	
	//raises score of every player by specific amount
	public void SZTToEveryOtherPlayer(int value) {
		for(Player p:players) {
			if(p!=current) {
				p.raiseScore(value);
			}
		}
	}
	
	//gives current player specific SZT amount from other players
	public void SZTFromEveryOtherPlayer(int value) {
		for(Player p:players) {
			if(p!=current) {
				p.lowerScore(value);
				current.raiseScore(value);
			}
		}
	}
	
	//current player gets a chip from its second left neighbor
	public void secondLeftNeighborGivesChipToCurrent() {
		if(players.indexOf(current)<2) {
			current.addChip(players.get(players.size()-players.indexOf(current)-1).removeChip());
		}
		else {
			current.addChip(players.get(players.indexOf(current)-2).removeChip());
		}
	}
	
	//current player gets specific SZT ampunt from his second left neighbor
	public void secondLeftNeighborGivesSZTToCurrent(int value) {
		if(players.indexOf(current)<2) {
			players.get(players.size()-players.indexOf(current)-1).lowerScore(value);
			current.raiseScore(value);
		}
		else {
			players.get(players.indexOf(current)-2).lowerScore(value);
			current.raiseScore(value);
		}
	}
	
	//left neighbor of current player gets its score raised by a specified value
	public void leftNeighborRaiseScore(int value) {
		if(players.indexOf(current)<2) {
			players.get(players.size()-players.indexOf(current)-1).raiseScore(value);
		}
		else {
			players.get(players.indexOf(current)-2).raiseScore(value);
		}
	}
	
	//instead of putting one chip in one project the current player sets two chips in one project
	public void setTwoChipsInOneProject(Subproject project) {
		current.raiseScore(project.setChip(current.removeChip()).getAmountSZT());
		current.raiseScore(project.setChip(current.removeChip()).getAmountSZT());
	}
	
	//raises score of every player including the current one
	public void raiseScoreOfEveryPlayer(int value) {
		for(Player p:players) {
			p.raiseScore(value);
		}
	}
	
	//current gets specified amount of SZT from current player
	public void takeSZTFromRightNeighbor(int value) {
		if(getRightNeigbour().getScore()<value) {
			int score=getRightNeigbour().getScore();
			getRightNeigbour().lowerScore(score);
			current.raiseScore(score);
		}
		else {
			getRightNeigbour().lowerScore(value);
			current.raiseScore(value);
		}
	}
	
	//second right neighbor of current player gets his score raised by a specified amount
	public void raiseScoreSecondRightNeighbor(int value) {
		players.get((players.indexOf(current)+2)%players.size()).raiseScore(value);
	}
	
	//right neighbor of the current player gets his score raised by specified amount
	public void giveSZTToRightNeighbor(int value) {
		current.lowerScore(value);
		getRightNeigbour().raiseScore(value);
	}
	
	//current player gets 20 million SZT from a player of his choice
	public void takeAway20SZTFromPlayer(Player player) {
		player.lowerScore(20);
		current.raiseScore(20);
	}
	
	public JsonObject toJson() {
		JsonObject result=new JsonObject();
		JsonArray active=new JsonArray();
		JsonArray available=new JsonArray();
		JsonArray finished=new JsonArray();
		JsonArray players=new JsonArray();
		for(Subproject project:projectsAvailable) {
			available.add(project.toJson());
		}
		for(Subproject project:projectsActive) {
			active.add(project.toJson());
		}
		for(Subproject project:projectsFinished) {
			finished.add(project.toJson());
		}
		result.add("active", active);
		result.add("available", available);
		result.add("finished", finished);
		for(Player p:this.players) {
			players.add(p.toJson());
		}
		result.add("players", players);
		result.add("currentPlayer", current.toJson());
		return result;
	}
	
	public void addVCard(int id) {
		this.vCards.add(new VerantwortungsLOSCard(id));
	}
	
	public ArrayList<Subproject> getActiveProjectsMoreThenOneFreeField(){
		ArrayList<Subproject> projects=new ArrayList<Subproject>();
		for(Subproject project:projectsActive) {
			if(project.getFreeFieldsLeft()>=2) {
				projects.add(project);
			}
		}
		return projects;
	}
	
	public ArrayList<Subproject> getActiveProjects(){
		ArrayList<Subproject> projects=new ArrayList<Subproject>();
		for(Subproject project:projectsActive) {
				projects.add(project);
		}
		return projects;
	}
	
	public ArrayList<Player> getPlayers(){
		ArrayList<Player> players=new ArrayList<Player>();
		for(Player p:players) {
			players.add(p);
		}
		return players;
	}
    
    public Player getPlayerByUser(userManagement.User user) {
	for (Player player : players) {
	    if(player.getUser().equals(user))
		return player;
	}
		return null;
    }
	

	
	
	
	
	
	
	
	
	
}
