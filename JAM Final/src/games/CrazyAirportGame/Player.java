package games.CrazyAirportGame;

import java.util.ArrayList;

public class Player implements Comparable<Player> {

	String name;
	int score;
	ArrayList<Chip> chips = new ArrayList<Chip>();
	ArrayList<VerantwortungsLOSCard> vCards = new ArrayList<VerantwortungsLOSCard>();
	ArrayList<EreginisLOSCard> eCards = new ArrayList<EreginisLOSCard>();
	boolean skipNextRound;
	boolean hasVCard11;
	boolean hasVCard23;
	
	public Player(String name){
		this.name=name;
		score=0;
		this.skipNextRound=false;
		this.hasVCard11=false;
		this.hasVCard23=false;
	}
	
	public boolean isChipStealable() {
		return chips.size()>=2;
	}
	
	public Chip removeChip() {
		Chip result=chips.get(0);
		chips.remove(result);
		return result;
	}
	
    @Override
    public int compareTo(Player arg0) {
	return arg0.getScore() - this.getScore();
    }

	public int getScore() {
		return score;
	}

	public void raiseScore(int score) {
		this.score += score;
	}
	
	public void lowerScore(int score) {
		this.score -= score;
	}

	public void addChip(Chip chip) {
		chip.setCurrentOwner(this);
		chips.add(chip);
	}

	public void addvCard(VerantwortungsLOSCard vCard) {
		this.vCards.add(vCard);
	}

	public void addeCard(EreginisLOSCard eCard) {
		this.eCards.add(eCard);
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
		return hasVCard23;
	}

	public void setHasVCard24(boolean hasVCard24) {
		this.hasVCard23 = hasVCard24;
	}

	public String getName() {
		return name;
	}

/*	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Player) {
			if(obj.get)
		}
		else return false;
	}*/
	
	

}
