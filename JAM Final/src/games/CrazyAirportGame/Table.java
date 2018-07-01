package games.CrazyAirportGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class Table {
	
	ArrayList<VerantwortungsLOSCard> verantworungsLOSE=new ArrayList<VerantwortungsLOSCard>();
	ArrayList<VerantwortungsLOSCard> verantworungsLOSEDrawn=new ArrayList<VerantwortungsLOSCard>();
	ArrayList<EreginisLOSCard> ereignisLOSE=new ArrayList<EreginisLOSCard>();
	ArrayList<EreginisLOSCard> ereignisLOSEDrawn=new ArrayList<EreginisLOSCard>();
	ArrayList<Subproject> subProjects=new ArrayList<Subproject>();
	ArrayList<Player> players=new ArrayList<Player>();
	
	public void fillVerantworungsLOSArray() {
	for(int i=1; i<=24; i++) {
		VerantwortungsLOSCard c=new VerantwortungsLOSCard(i);
		verantworungsLOSE.add(c);
		}
	}
	
	public void fillEreignisLOSArray() {
		for(int i=1; i<=55; i++) {
			EreginisLOSCard c= new EreginisLOSCard(i);
			ereignisLOSE.add(c);
		}
	}
	
	//Create all subprojects with their Field-ArrayLists
	public void initSubprojects(){
		ArrayList<SubprojectField> fieldsFirestation=new ArrayList<SubprojectField>(Arrays.asList(
				new SubprojectField(10, false),
				new SubprojectField(10, false),
				new SubprojectField(50, true),
				new SubprojectField(10, false),
				new SubprojectField(20, false),
				new SubprojectField(30, false),
				new SubprojectField(40, true),
				new SubprojectField(20, false),
				new SubprojectField(50, false)));
		subProjects.add(new Subproject("Feuerwache",fieldsFirestation));
		ArrayList<SubprojectField> fieldsNorthStreet=new ArrayList<SubprojectField>(Arrays.asList(
				new SubprojectField(10, false),
				new SubprojectField(30, false),
				new SubprojectField(10, false),
				new SubprojectField(20, false),
				new SubprojectField(50, false),
				new SubprojectField(20, true),
				new SubprojectField(10, false),
				new SubprojectField(30, true),
				new SubprojectField(30, false)));
		subProjects.add(new Subproject("Landebahn Nord",fieldsNorthStreet));
		ArrayList<SubprojectField> fieldsSouthStreet=new ArrayList<SubprojectField>(Arrays.asList(
				new SubprojectField(20, false),
				new SubprojectField(10, false),
				new SubprojectField(20, true),
				new SubprojectField(10, false),
				new SubprojectField(50, false),
				new SubprojectField(50, false),
				new SubprojectField(10, true),
				new SubprojectField(20, false),
				new SubprojectField(40, false)));
		subProjects.add(new Subproject("Landebahn Süd",fieldsSouthStreet));
		ArrayList<SubprojectField> fieldsTerminalA=new ArrayList<SubprojectField>(Arrays.asList(
				new SubprojectField(20, false),
				new SubprojectField(20, false),
				new SubprojectField(10, true),
				new SubprojectField(30, false),
				new SubprojectField(10, true),
				new SubprojectField(50, false)));
		subProjects.add(new Subproject("Terminal A",fieldsTerminalA));
		ArrayList<SubprojectField> fieldsTerminalB=new ArrayList<SubprojectField>(Arrays.asList(
				new SubprojectField(20, false),
				new SubprojectField(10, false),
				new SubprojectField(30, true),
				new SubprojectField(10, false),
				new SubprojectField(10, false),
				new SubprojectField(50, true),
				new SubprojectField(20, false),
				new SubprojectField(50, false)));
		subProjects.add(new Subproject("Terminal B",fieldsTerminalB));
		ArrayList<SubprojectField> fieldsMainTerminal=new ArrayList<SubprojectField>(Arrays.asList(
				new SubprojectField(20, false),
				new SubprojectField(10, true),
				new SubprojectField(30, false),
				new SubprojectField(10, false),
				new SubprojectField(10, false),
				new SubprojectField(20, true),
				new SubprojectField(50, true),
				new SubprojectField(10, false),
				new SubprojectField(50, false)));
		subProjects.add(new Subproject("Hauptterminal",fieldsMainTerminal));
		ArrayList<SubprojectField> fieldsParkStation=new ArrayList<SubprojectField>(Arrays.asList(
				new SubprojectField(50, false),
				new SubprojectField(50, false),
				new SubprojectField(50, true),
				new SubprojectField(20, false),
				new SubprojectField(20, true),
				new SubprojectField(20, false),
				new SubprojectField(50, false),
				new SubprojectField(20, false)));
		subProjects.add(new Subproject("Parkplatz",fieldsParkStation));
		ArrayList<SubprojectField> fieldsPreStation=new ArrayList<SubprojectField>(Arrays.asList(
				new SubprojectField(20, false),
				new SubprojectField(10, false),
				new SubprojectField(50, true),
				new SubprojectField(20, false),
				new SubprojectField(50, false),
				new SubprojectField(20, false)));
		subProjects.add(new Subproject("Vorfeld",fieldsPreStation));
	}
	
	//Shuffles all the decks
	public void shuffleDecks() {
		Collections.shuffle(ereignisLOSE);
		Collections.shuffle(verantworungsLOSE);
		Collections.shuffle(subProjects);
	}
	
	//Add player to playerlist
	public void addPlayer(Player player){
		players.add(player);
	}
	
	//Assign subprojects to players in playerlist ToDo schöner machen
	public void assignStartSubprojects() {
		for(Player p:players) {
			int i=0;
			p.addSubProjectsActive(subProjects.get(i));
			p.subProjectsActive.get(0).active=true;
			i++;
		}
	}
	
	//Get next free field method in subproject
	public void openUpStartProjects() {
		for(Player p:players) {
			p.subProjectsActive.get(0).fields.get(0).isChipped=true;
			p.setScore(p.subProjectsActive.get(0).fields.get(0).getAmountSZT());
		}
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
	
	public void drawVerantwortungsLOS(Player player) {
		VerantwortungsLOSCard drawnCard=verantworungsLOSE.get(0);
		switch(drawnCard.getId()) {
		case 1:
			//+100 Mio. SZT to the player
		case 2:
			//+50 Mio. SZT to the player and +100 Mio. SZT to right neighbor 
		case 3:
			//Left neighbor +50 Mio. SZT
		case 4:
			//+100 Mio. SZT to the player
		case 5:
			//Player loses next turn
		case 6:
			//+70 Mio. SZT to the Player, Player can start a new project if possible
		case 7:
			//nothing happens
		case 8:
			//place a chip on already started project and burn the SZT amount
		case 9:
			//+100 Mio. SZT to the player
		case 10:
			//+100 Mio. SZT to the player
		case 11:
			//remove chip out of existing project (not the Start-Chip) and put it into another project
		case 12:
			//+50 Mio. SZT to the player and 2 chips on already existing projects
		case 13:
			//+100 Mio. SZT to the player
		case 14:
			//+80 Mio. SZT to the player and if possible start new project
		case 15:
			//+100 Mio. SZT to the player and remove last two chips of a project and keep them to use them later
		case 16:
			//+100 Mio. SZT to the player and two chips on non Start-Fields
		case 17:
			//player can! place two chips on existing project
		case 18:
			//50 Mio. SZT to every player
		case 19:
			//+100 Mio. SZT to the player and he gets one chip from the second left player to use it later
		case 20:
			//remove chip from existing project (no starting-chip) and place in other already started project and roll the dice again
		case 21:
			//take away 50 Mio. from your second left neighbor 
		case 22:
			//take away 30 Mio. from every player
		case 23:
			//keep the card and use it whenever you want --> take away one chip from another player if he has more than one chip
		case 24:
			//+50 Mio. SZT to the player and take a chip from the left neighbor to use it later
		}
		verantworungsLOSEDrawn.add(drawnCard);
		player.addvCard(drawnCard);
		verantworungsLOSE.remove(0);
	}

	public void drawEreignisLOS(Player player) {
		EreginisLOSCard drawnCard=ereignisLOSE.get(0); 
		switch(drawnCard.getId()) {
		case 1:
			//+100 Mio. SZT to the player
		case 2:
			//+50 Mio. SZT to the player and +100 Mio. SZT to right neighbor 
		case 3:
			//Left neighbor +50 Mio. SZT
		case 4:
			//+100 Mio. SZT to the player
		case 5:
			//Player loses next turn
		case 6:
			//+70 Mio. SZT to the Player, Player can start a new project if possible
		case 7:
			//nothing happens
		case 8:
			//place a chip on already started project and burn the SZT amount
		case 9:
			//+100 Mio. SZT to the player
		case 10:
			//+100 Mio. SZT to the player
		case 11:
			//remove chip out of existing project (not the Start-Chip) and put it into another project
		case 12:
			//+50 Mio. SZT to the player and 2 chips on already existing projects
		case 13:
			//+100 Mio. SZT to the player
		case 14:
			//+80 Mio. SZT to the player and if possible start new project
		case 15:
			//+100 Mio. SZT to the player and remove last two chips of a project and keep them to use them later
		case 16:
			//+100 Mio. SZT to the player and two chips on non Start-Fields
		case 17:
			//player can! place two chips on existing project
		case 18:
			//50 Mio. SZT to every player
		case 19:
			//+100 Mio. SZT to the player and he gets one chip from the second left player to use it later
		case 20:
			//remove chip from existing project (no starting-chip) and place in other already started project and roll the dice again
		case 21:
			//take away 50 Mio. from your second left neighbor 
		case 22:
			//take away 30 Mio. from every player
		case 23:
			//keep the card and use it whenever you want --> take away one chip from another player if he has more than one chip
		case 24:
			//+50 Mio. SZT to the player and take a chip from the left neighbor to use it later
		}
		ereignisLOSEDrawn.add(drawnCard);
		player.addeCard(drawnCard);
		ereignisLOSE.remove(0);
	}

}
