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
	
	public ArrayList<Chip> chipsNotPlacedYet(){
		ArrayList<Chip> chipsNotPlaced=new ArrayList<Chip>();
		for(Chip c:chips) {
			if(!c.isPlaced()) {
				chipsNotPlaced.add(c);
			}
		}
		return chipsNotPlaced;
	}
	
	public Chip useChip() {
		Chip chip=null;
		for(Chip c:this.getChips()) {
			if(!c.isPlaced()) {
				c.setPlaced(true);
				chip=c;
				break;
			}
		}
		return chip;
	}
	
    @Override
    public int compareTo(Player arg0) {
	return arg0.getScore() - this.getScore();
    }

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score += score;
	}

	public ArrayList<Chip> getChips() {
		return chips;
	}

	public void setChips(ArrayList<Chip> chips) {
		this.chips = chips;
	}

	public void addChip(Chip chip) {
		chips.add(chip);
	}
	
	public ArrayList<VerantwortungsLOSCard> getvCards() {
		return vCards;
	}

	public void addvCard(VerantwortungsLOSCard vCard) {
		this.vCards.add(vCard);
	}

	public ArrayList<EreginisLOSCard> geteCards() {
		return eCards;
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
