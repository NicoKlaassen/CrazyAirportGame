package games.CrazyAirportGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Table {

	private ArrayList<Subproject> projectsAvailable;
	private ArrayList<Subproject> projectsActive;
	private ArrayList<Subproject> projectsFinished;
	private ArrayList<Player> players;
	private ArrayList<EreginisLOSCard> eCards;
	private ArrayList<VerantwortungsLOSCard> vCards;
	private Player current;
	
	public Table() {
		this.projectsActive=new ArrayList<Subproject>();
		this.projectsAvailable=new ArrayList<Subproject>();
		this.projectsFinished=new ArrayList<Subproject>();
		this.players=new ArrayList<Player>();
		this.eCards=new ArrayList<EreginisLOSCard>();
		this.vCards=new ArrayList<VerantwortungsLOSCard>();
		this.current=null;
		this.initSubprojects();
		this.fillEreignisLOSArray();
		this.fillVerantworungsLOSArray();
		Collections.shuffle(vCards);
		Collections.shuffle(eCards);
		Collections.shuffle(projectsAvailable);
	}
	
	public void startGame() {
		initChips();
		assignAndStartInitialProjects();
	}
	
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
	
	public void assignAndStartInitialProjects() {
		for(Player p:players) {
			Subproject sp=drawProject();
			SubprojectField result=sp.setChip(p.removeChip());
			p.raiseScore(result.getAmountSZT());
		}
	}
	
	public void startTurn() {
		//ToDO
	}
	
	public void endTurn() {
		current=players.get((players.indexOf(current)+1)%players.size());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Subproject drawProject() {
		Subproject result=projectsAvailable.get(0);
		projectsAvailable.remove(result);
		projectsActive.add(result);
		return result;
	}
	
	public EreginisLOSCard drawECard() {
		EreginisLOSCard result=eCards.get(0);
		eCards.remove(result);
		return result;
	}
	
	public VerantwortungsLOSCard drawVCard() {
		VerantwortungsLOSCard result=vCards.get(0);
		vCards.remove(result);
		return result;
	}
	
	public void initChips() {
		for(Player p:players) {
			for(int i=0; i<7; i++) {
				p.addChip(new Chip(p));
			}
		}
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
		projectsAvailable.add(new Subproject("Feuerwache",fieldsFirestation));
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
		projectsAvailable.add(new Subproject("Landebahn Nord",fieldsNorthStreet));
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
		projectsAvailable.add(new Subproject("Landebahn Süd",fieldsSouthStreet));
		ArrayList<SubprojectField> fieldsTerminalA=new ArrayList<SubprojectField>(Arrays.asList(
				new SubprojectField(20, false, false),
				new SubprojectField(20, false, false),
				new SubprojectField(10, true, false),
				new SubprojectField(30, false, false),
				new SubprojectField(10, true, false),
				new SubprojectField(50, false, true)));
		projectsAvailable.add(new Subproject("Terminal A",fieldsTerminalA));
		ArrayList<SubprojectField> fieldsTerminalB=new ArrayList<SubprojectField>(Arrays.asList(
				new SubprojectField(20, false, false),
				new SubprojectField(10, false, false),
				new SubprojectField(30, true, false),
				new SubprojectField(10, false, false),
				new SubprojectField(10, false, false),
				new SubprojectField(50, true, false),
				new SubprojectField(20, false, false),
				new SubprojectField(50, false, true)));
		projectsAvailable.add(new Subproject("Terminal B",fieldsTerminalB));
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
		projectsAvailable.add(new Subproject("Hauptterminal",fieldsMainTerminal));
		ArrayList<SubprojectField> fieldsParkStation=new ArrayList<SubprojectField>(Arrays.asList(
				new SubprojectField(50, false, false),
				new SubprojectField(50, false, false),
				new SubprojectField(50, true, false),
				new SubprojectField(20, false, false),
				new SubprojectField(20, true, false),
				new SubprojectField(20, false, false),
				new SubprojectField(50, false, false),
				new SubprojectField(20, false, true)));
		projectsAvailable.add(new Subproject("Parkplatz",fieldsParkStation));
		ArrayList<SubprojectField> fieldsPreStation=new ArrayList<SubprojectField>(Arrays.asList(
				new SubprojectField(20, false, false),
				new SubprojectField(10, false, false),
				new SubprojectField(50, true, false),
				new SubprojectField(20, false, false),
				new SubprojectField(50, false, false),
				new SubprojectField(20, false, true)));
		projectsAvailable.add(new Subproject("Vorfeld",fieldsPreStation));
	}
	
	public void fillVerantworungsLOSArray() {
	for(int i=1; i<=24; i++) {
		VerantwortungsLOSCard c=new VerantwortungsLOSCard(i);
		vCards.add(c);
		}
	}
	
	public void fillEreignisLOSArray() {
		for(int i=1; i<=55; i++) {
			EreginisLOSCard c= new EreginisLOSCard(i);
			eCards.add(c);
		}
	}
	
}
