/*package games.CrazyAirportGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class TableOld {
	
	private ArrayList<VerantwortungsLOSCard> verantworungsLOSE=new ArrayList<VerantwortungsLOSCard>();
	private ArrayList<EreginisLOSCard> ereignisLOSE=new ArrayList<EreginisLOSCard>();
	private ArrayList<Subproject> subProjectsAvailable=new ArrayList<Subproject>();
	private ArrayList<Subproject> subProjectsFinished=new ArrayList<Subproject>();
	private ArrayList<Subproject> subProjectsActive=new ArrayList<Subproject>();
	private ArrayList<Player> players=new ArrayList<Player>();
	private Player current;
	
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
				new SubprojectField(10, false, false),
				new SubprojectField(10, false, false),
				new SubprojectField(50, true, false),
				new SubprojectField(10, false, false),
				new SubprojectField(20, false, false),
				new SubprojectField(30, false, false),
				new SubprojectField(40, true, false),
				new SubprojectField(20, false, false),
				new SubprojectField(50, false, true)));
		subProjectsAvailable.add(new Subproject("Feuerwache",fieldsFirestation));
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
		subProjectsAvailable.add(new Subproject("Landebahn Nord",fieldsNorthStreet));
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
		subProjectsAvailable.add(new Subproject("Landebahn Süd",fieldsSouthStreet));
		ArrayList<SubprojectField> fieldsTerminalA=new ArrayList<SubprojectField>(Arrays.asList(
				new SubprojectField(20, false, false),
				new SubprojectField(20, false, false),
				new SubprojectField(10, true, false),
				new SubprojectField(30, false, false),
				new SubprojectField(10, true, false),
				new SubprojectField(50, false, true)));
		subProjectsAvailable.add(new Subproject("Terminal A",fieldsTerminalA));
		ArrayList<SubprojectField> fieldsTerminalB=new ArrayList<SubprojectField>(Arrays.asList(
				new SubprojectField(20, false, false),
				new SubprojectField(10, false, false),
				new SubprojectField(30, true, false),
				new SubprojectField(10, false, false),
				new SubprojectField(10, false, false),
				new SubprojectField(50, true, false),
				new SubprojectField(20, false, false),
				new SubprojectField(50, false, true)));
		subProjectsAvailable.add(new Subproject("Terminal B",fieldsTerminalB));
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
		subProjectsAvailable.add(new Subproject("Hauptterminal",fieldsMainTerminal));
		ArrayList<SubprojectField> fieldsParkStation=new ArrayList<SubprojectField>(Arrays.asList(
				new SubprojectField(50, false, false),
				new SubprojectField(50, false, false),
				new SubprojectField(50, true, false),
				new SubprojectField(20, false, false),
				new SubprojectField(20, true, false),
				new SubprojectField(20, false, false),
				new SubprojectField(50, false, false),
				new SubprojectField(20, false, true)));
		subProjectsAvailable.add(new Subproject("Parkplatz",fieldsParkStation));
		ArrayList<SubprojectField> fieldsPreStation=new ArrayList<SubprojectField>(Arrays.asList(
				new SubprojectField(20, false, false),
				new SubprojectField(10, false, false),
				new SubprojectField(50, true, false),
				new SubprojectField(20, false, false),
				new SubprojectField(50, false, false),
				new SubprojectField(20, false, true)));
		subProjectsAvailable.add(new Subproject("Vorfeld",fieldsPreStation));
	}
	
	//Shuffles all the decks
	public void shuffleDecks() {
		Collections.shuffle(ereignisLOSE);
		Collections.shuffle(verantworungsLOSE);
		Collections.shuffle(subProjectsAvailable);
		Collections.shuffle(players);
	}
	
	//Add player to playerlist
	public void addPlayer(Player player){
		players.add(player);
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
	
	//Assign subprojects to players in playerlist ToDo schöner machen
	public void assignStartSubprojects() {
		for(Player p:players) {
			int i=0;
			subProjectsAvailable.get(i).setOwner(p);
			subProjectsAvailable.get(i).setActive(true);
			i++;
		}
	}
	
	//Assign single project to single player
	public void assignProjectToPlayer(Player player) {
		for(Subproject project:subProjectsAvailable) {
			if(!project.isActive()) {
				project.setOwner(player);
				project.setActive(true);
				break;
			}
		}
	}

	//Opens up the Start-Project of every player. No check "is V-Field" needed
	public void openUpStartProjects() {
		for(Player p:players) {
			for(Subproject sp:subProjectsAvailable) {
				if(sp.getOwner()==p) {
					Chip chip=p.useChip();
					sp.fields.get(0).setChipped(true);
					sp.fields.get(0).setChip(chip);
					p.raiseScore(sp.fields.get(0).getAmountSZT());
				}
			}
		}
	}
	
	//opens up a new project for a specific player 
	public void openUpProject(Player player) {
		for(Subproject project:subProjectsAvailable) {
			if(project.owner==player && project.fields.get(0).isChipped()==false) {
				Chip chip=player.useChip();
				project.fields.get(0).setChipped(true);
				project.fields.get(0).setChip(chip);	
				player.raiseScore(project.getFields().get(0).getAmountSZT());
			}
		}
	}
	
	public boolean airportIsBuilt() {
		boolean isBuilt=true;
		for(Subproject project:subProjectsAvailable) {
			if(project.isBuilt()==false) {
				isBuilt=false;
				break;
			}
		}
		return isBuilt;
	}
	
	Finishes project by:
	 * 	- Setting last field on chipped
	 * 	- Putting chip on field
	 * 	- Reduce score of other players by fieldSZT and up amount of player
	 * 	- setting built=true and active=false
	 
	public void finishProject(Subproject project, Player player) {
		Chip chip=player.useChip();
		SubprojectField lastField=project.getLastField();
		lastField.setChipped(true);
		lastField.setChip(chip);
		player.raiseScore(lastField.getAmountSZT());
		for(Player p:players) {
			if(p!=player) {
				p.raiseScore(lastField.getAmountSZT()*-1); //Fehlerbehandlung wenn spieler betrag nicht hat
				player.raiseScore(lastField.getAmountSZT());
			}
		project.setBuilt(true);
		project.setActive(false);
		if(airportIsBuilt()==true) {
			//method to handle end of game ################ToDo################
		}
		}
	}
	
	//Handles placing chip on a V-Field
	public void setChipOnVField(Subproject project, Player player) {
		Chip chip=player.useChip();
		SubprojectField vField=project.getNextFreeField();
		vField.setChipped(true);
		vField.setChip(chip);
		player.raiseScore(vField.getAmountSZT());
		drawVerantwortungsLOS(player);
	}
	
	//Handles playing chip on a non V- and non End-Field
	public void setChipOnStandardField(Subproject project, Player player) {
		Chip chip=player.useChip();
		SubprojectField vField=project.getNextFreeField();
		vField.setChipped(true);
		vField.setChip(chip);
		player.raiseScore(vField.getAmountSZT());
	}
	
	//Handles placing a chip on either a End-, V- or Normal-Field
	public void setChipOnField(Subproject project, Player player) {
		SubprojectField field=project.getNextFreeField();
		if(field.isVField()==true) {
			setChipOnVField(project, player);
		}
		if(project.getLastField()==field) {
			finishProject(project, player);
		}
		else {
			setChipOnStandardField(project, player);
		}
	}
	
	//not needed
	public Subproject playerSelectProject(Player player) {
		Subproject project=null;
		//############ToDO#############
		return project;
	}
	
	//Process a turn with rolling the dice
	public void processTurn(Player player) {
		if(player.isSkipNextRound()==true) {
			player.setSkipNextRound(false);//hat spieler noch chips
		}
		else {
			boolean dice=rollTheDice();
			if(dice==true) {
				drawEreignisLOS(player);
			}
			else {
				//setChipOnField(project, player); needs a project from player
			}
		}
	}
	
	//Increases score of specific player by specific amount
	public void upScoreOfPlayer(Player player, int SZT) {
		player.raiseScore(SZT);
	}
	
	public Player getRightNeighbor(Player player) {
		Player rightNeighbor=players.get((players.indexOf(player)+1)%players.size());
		return rightNeighbor;
	}
	
	public Player getLeftNeighbor(Player player) {
		Player leftNeighbor=null;
		int i=-1;
		for(Player p:players) {
			i++;
			if(p==player) {
				if (i==0){
					i=players.size()-1;
				}
				leftNeighbor=players.get(i);
				break;
			}
		}
		return leftNeighbor;
	}
	
	public void makePlayerSuspend(Player player) {
		player.setSkipNextRound(true);
	}
	
	public void removeChipFromProjectAndPutItInAnother(Subproject outProject, Subproject inProject, Player player) {
			Chip chip=outProject.getLastChippedField().getChip();
			outProject.getLastChippedField().setChip(null);
			outProject.getLastChippedField().setChipped(false);
			setChipOnField(inProject, player);
	}
	
	public void addTwoChipsOnExisitingProjects(Player player) {
		if(!player.chipsNotPlacedYet().isEmpty()) {
		if(player.chipsNotPlacedYet().size()>=2) {
			Chip chip1=player.useChip();
			setChipOnField(playerSelectProject(player), player);
			Chip chip2=player.useChip();
			setChipOnField(playerSelectProject(player), player);
		}
		else if(player.chipsNotPlacedYet().size()==1) {
			Chip chip=player.useChip();
			setChipOnField(playerSelectProject(player), player);
			}
		}
	}
	
	public void removeTwoChipsFromExistingProject(Subproject project1, Subproject project2, Player player) {
		if(project1.getLastChippedField()!=project1.getFields().get(0) && project2.getLastChippedField()!=project2.getFields().get(0)) {
			Chip chip1=project1.getLastChippedField().getChip();
			project1.getLastChippedField().setChip(null);
			project1.getLastChippedField().setChipped(false);
			player.chips.add(chip1);
			Chip chip2=project2.getLastChippedField().getChip();
			project2.getLastChippedField().setChip(null);
			project2.getLastChippedField().setChipped(false);
			player.chips.add(chip2);
		}
	}
	
	public void SZTToEveryPlayer(int value) {
		for(Player p:players) {
			p.raiseScore(value);
		}
	}
	
	public void getChipFromLeftNeighbor(Player player) {
		int i=-1;
		for(Player p:players) {
			i++;
			if(p==player) {
				if(i==0) {
					for(Chip chip:players.get(players.size()).getChips()) {
						player.addChip(chip);
						players.get(players.size()).getChips().remove(chip);
						break;
					}
				}
				else if(i>0) {
					for(Chip chip:players.get(i-1).getChips()) {
						player.addChip(chip);
						players.get(i-1).getChips().remove(chip);
						break;
					}
				}
			}
		}
	}
	
	public void getChipFromSecondLeftPlayer(Player player) {
		int i=-1;
		for(Player p:players) {
			i++;
			if(p==player) {
				if(i==2) {
					for(Chip chip:players.get(0).getChips()) {
						if(!chip.isPlaced()) {
							player.addChip(chip);
							players.get(0).getChips().remove(chip);
							break;
						}
					}
					
				}
				else if(i==1) {
					for(Chip chip:players.get(players.size()).getChips()) {
						if(!chip.isPlaced()) {
							player.addChip(chip);
							players.get(players.size()-1).getChips().remove(chip);
							break;
						}
					}
				}
				else if(i==0) {
					for(Chip chip:players.get(players.size()-1).getChips()) {
						if(!chip.isPlaced()) {
							player.addChip(chip);
							players.get(players.size()-2).getChips().remove(chip);
							break;
						}
					}
				}
				else if(i>2) {
					for(Chip chip:players.get(i-2).getChips()) {
						if(!chip.isPlaced()) {
							player.addChip(chip);
							players.get(i-2).getChips().remove(chip);
							break;
						}
					}
				}
			}
		}
	}
	
	public void getSZTFromSecondLeftPlayer(Player player, int SZT) {
		int i=-1;
		for(Player p:players) {
			i++;
			if(p==player) {
				if(i==2) {
					players.get(0).raiseScore(SZT*-1);
					player.raiseScore(SZT);
					break;
				}
				else if(i==1) {
					players.get(players.size()-1).raiseScore(SZT*-1);
					player.raiseScore(SZT);
					break;
				}
				else if(i==0) {
					players.get(players.size()-2).raiseScore(SZT*-1);
					player.raiseScore(SZT);
					break;
				}
				else if(i>2) {
					players.get(i-2).raiseScore(SZT*-1);
					player.raiseScore(SZT);
					break;
				}
			}
		}
	}
	
	public void takeAwaySZTFromEveryPlayer(Player player, int SZT) {
		for(Player p:players) {
			if(p!=player) {
				p.raiseScore(SZT*-1);
				player.raiseScore(SZT);
			}
		}
	}
	
	public void setCard11True(Player player) {
		player.setHasVCard11(true);
	}
	
	public void setCard23True(Player player) {
		player.setHasVCard23(true);
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
			//FassungLOS! remove chip out of existing project (not the Start-Chip) and put it into another project
		case 12:
			//+50 Mio. SZT to the player and 2 chips on already existing projects
		case 13:
			//+100 Mio. SZT to the player
		case 14:
			//+80 Mio. SZT to the player and if possible start new project
		case 15:
			//+100 Mio. SZT to the player and remove last chips of two chosen project and keep them to use them later
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
*/