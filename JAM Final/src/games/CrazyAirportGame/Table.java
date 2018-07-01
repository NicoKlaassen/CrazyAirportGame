package games.CrazyAirportGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class Table {
	
	
	ArrayList<VerantwortungsLOSCard> verantworungsLOSE=new ArrayList<VerantwortungsLOSCard>();
	ArrayList<Integer> verantworungsLOSEDrawn=new ArrayList<Integer>();
	ArrayList<EreginisLOSCard> ereignisLOSE=new ArrayList<EreginisLOSCard>();
	ArrayList<Integer> ereignisLOSEDrawn=new ArrayList<Integer>();
	
	public ArrayList<VerantwortungsLOSCard> fillVerantworungsLOSArray() {
	for(int i=1; i<=24; i++) {
		VerantwortungsLOSCard c=new VerantwortungsLOSCard(i);
		verantworungsLOSE.add(c);
	}
	return verantworungsLOSE;
	}
	
	public ArrayList<EreginisLOSCard> fillEreignisLOSArray() {
		for(int i=1; i<=55; i++) {
			EreginisLOSCard c= new EreginisLOSCard(i);
			ereignisLOSE.add(c);
		}
	return ereignisLOSE;
	}
	
	public void shuffleDecks() {
		Collections.shuffle(ereignisLOSE);
		Collections.shuffle(verantworungsLOSE);
	}
	
	public ArrayList<Subproject> initSubprojects(){
		ArrayList<Subproject> subprojects=new ArrayList<Subproject>();
		ArrayList<SubprojectField> fieldsFirestation=new ArrayList<SubprojectField>(Arrays.asList(
				new SubprojectField(10, false, false),
				new SubprojectField(10, false, false),
				new SubprojectField(50, true, false),
				new SubprojectField(10, false, false),
				new SubprojectField(20, false, false),
				new SubprojectField(30, false, false),
				new SubprojectField(40, true, false),
				new SubprojectField(20, false, false),
				new SubprojectField(50, false, false)));
		subprojects.add(new Subproject("Feuerwache",fieldsFirestation));
		ArrayList<SubprojectField> fieldsNorthStreet=new ArrayList<SubprojectField>(Arrays.asList(
				new SubprojectField(10, false, false),
				new SubprojectField(30, false, false),
				new SubprojectField(10, false, false),
				new SubprojectField(20, false, false),
				new SubprojectField(50, false, false),
				new SubprojectField(20, true, false),
				new SubprojectField(10, false, false),
				new SubprojectField(30, true, false),
				new SubprojectField(30, false, false)));
		subprojects.add(new Subproject("Landebahn Nord",fieldsNorthStreet));
		ArrayList<SubprojectField> fieldsSouthStreet=new ArrayList<SubprojectField>(Arrays.asList(
				new SubprojectField(20, false, false),
				new SubprojectField(10, false, false),
				new SubprojectField(20, true, false),
				new SubprojectField(10, false, false),
				new SubprojectField(50, false, false),
				new SubprojectField(50, false, false),
				new SubprojectField(10, true, false),
				new SubprojectField(20, false, false),
				new SubprojectField(40, false, false)));
		subprojects.add(new Subproject("Landebahn Süd",fieldsSouthStreet));
		ArrayList<SubprojectField> fieldsTerminalA=new ArrayList<SubprojectField>(Arrays.asList(
				new SubprojectField(20, false, false),
				new SubprojectField(20, false, false),
				new SubprojectField(10, true, false),
				new SubprojectField(30, false, false),
				new SubprojectField(10, true, false),
				new SubprojectField(50, false, false)));
		subprojects.add(new Subproject("Terminal A",fieldsTerminalA));
		ArrayList<SubprojectField> fieldsTerminalB=new ArrayList<SubprojectField>(Arrays.asList(
				new SubprojectField(20, false, false),
				new SubprojectField(10, false, false),
				new SubprojectField(30, true, false),
				new SubprojectField(10, false, false),
				new SubprojectField(10, false, false),
				new SubprojectField(50, true, false),
				new SubprojectField(20, false, false),
				new SubprojectField(50, false, false)));
		subprojects.add(new Subproject("Terminal B",fieldsTerminalB));
		ArrayList<SubprojectField> fieldsMainTerminal=new ArrayList<SubprojectField>(Arrays.asList(
				new SubprojectField(20, false, false),
				new SubprojectField(10, true, false),
				new SubprojectField(30, false, false),
				new SubprojectField(10, false, false),
				new SubprojectField(10, false, false),
				new SubprojectField(20, true, false),
				new SubprojectField(50, true, false),
				new SubprojectField(10, false, false),
				new SubprojectField(50, false, false)));
		subprojects.add(new Subproject("Hauptterminal",fieldsMainTerminal));
		ArrayList<SubprojectField> fieldsParkStation=new ArrayList<SubprojectField>(Arrays.asList(
				new SubprojectField(50, false, false),
				new SubprojectField(50, false, false),
				new SubprojectField(50, true, false),
				new SubprojectField(20, false, false),
				new SubprojectField(20, true, false),
				new SubprojectField(20, false, false),
				new SubprojectField(50, false, false),
				new SubprojectField(20, false, false)));
		subprojects.add(new Subproject("Parkplatz",fieldsParkStation));
		ArrayList<SubprojectField> fieldsPreStation=new ArrayList<SubprojectField>(Arrays.asList(
				new SubprojectField(20, false, false),
				new SubprojectField(10, false, false),
				new SubprojectField(50, true, false),
				new SubprojectField(20, false, false),
				new SubprojectField(50, false, false),
				new SubprojectField(20, false, false)));
		subprojects.add(new Subproject("Vorfeld",fieldsPreStation));
		
		return subprojects;
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
	
	public void drawVerantwortungsLOS(int id) {
		int randomPull = (int)(Math.random() * verantworungsLOSE.size()) + 1;
		while(verantworungsLOSEDrawn.contains(randomPull)){
			randomPull = (int)(Math.random() * verantworungsLOSE.size()) + 1;
		}
		Card drawnCard = new EreginisLOSCard(randomPull);
		verantworungsLOSEDrawn.add(drawnCard.getId());
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
		verantworungsLOSE.remove(randomPull);
	}

	public void drawEreignisLOS(int id) {
		int randomPull = (int)(Math.random() * ereignisLOSE.size()) + 1;
		while(ereignisLOSEDrawn.contains(randomPull)){
			randomPull = (int)(Math.random() * ereignisLOSE.size()) + 1;
		}
		Card drawnCard = new VerantwortungsLOSCard(randomPull);
		ereignisLOSEDrawn.add(drawnCard.getId());
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
		ereignisLOSE.remove(randomPull);
	}

}
