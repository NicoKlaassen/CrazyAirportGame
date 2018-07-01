package games.CrazyAirportGame;

import java.util.ArrayList;

public class Player implements Comparable<Player> {

	String name;
	int score;
	ArrayList<Subproject> subProjectsActive = new ArrayList<Subproject>();
	ArrayList<Chip> chips = new ArrayList<Chip>();
	ArrayList<VerantwortungsLOSCard> vCards = new ArrayList<VerantwortungsLOSCard>();
	ArrayList<EreginisLOSCard> eCards = new ArrayList<EreginisLOSCard>();
	boolean skipNextRound;
	boolean hasVCard11;
	boolean hasVCard24;
	
	public Player(String name, int score){
		this.name=name;
		this.score=score;
		this.skipNextRound=false;
		this.hasVCard11=false;
		this.hasVCard24=false;
	}
	
    @Override
    public int compareTo(Player arg0) {
	return arg0.getScore() - this.getScore();
    }

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public ArrayList<Subproject> getSubProjectsActive() {
		return subProjectsActive;
	}

	public void setSubProjectsActive(ArrayList<Subproject> subProjectsActive) {
		this.subProjectsActive = subProjectsActive;
	}

	public ArrayList<Chip> getChips() {
		return chips;
	}

	public void setChips(ArrayList<Chip> chips) {
		this.chips = chips;
	}

	public ArrayList<VerantwortungsLOSCard> getvCards() {
		return vCards;
	}

	public void setvCards(ArrayList<VerantwortungsLOSCard> vCards) {
		this.vCards = vCards;
	}

	public ArrayList<EreginisLOSCard> geteCards() {
		return eCards;
	}

	public void seteCards(ArrayList<EreginisLOSCard> eCards) {
		this.eCards = eCards;
	}

	public boolean isSkipNextRound() {
		return skipNextRound;
	}

	public void setSkipNextRound(boolean skipNextRound) {
		this.skipNextRound = skipNextRound;
	}

	public boolean isHasVCard11() {
		return hasVCard11;
	}

	public void setHasVCard11(boolean hasVCard11) {
		this.hasVCard11 = hasVCard11;
	}

	public boolean isHasVCard24() {
		return hasVCard24;
	}

	public void setHasVCard24(boolean hasVCard24) {
		this.hasVCard24 = hasVCard24;
	}

	public String getName() {
		return name;
	}

}
