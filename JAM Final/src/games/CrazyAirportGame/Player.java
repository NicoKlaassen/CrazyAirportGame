package games.CrazyAirportGame;

import java.util.ArrayList;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import userManagement.User;

public class Player implements Comparable<Player> {

	private int score;
	private ArrayList<Chip> chips = new ArrayList<Chip>();
	private ArrayList<VerantwortungsLOSCard> vCards = new ArrayList<VerantwortungsLOSCard>();
	private ArrayList<EreginisLOSCard> eCards = new ArrayList<EreginisLOSCard>();
	private boolean skipNextRound;
	private boolean hasVCard11;
	private boolean hasVCard23;
	private User user;
	private String color;


	public Player(User user, String color){
		score=0;
		this.skipNextRound=false;
		this.hasVCard11=false;
		this.hasVCard23=false;
		this.user=user;
		this.color=color;
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

	public boolean isHasVCard23() {
		return hasVCard23;
	}

	public void setHasVCard23(boolean hasVCard24) {
		this.hasVCard23 = hasVCard24;
	}
	
	public ArrayList<VerantwortungsLOSCard> getvCards() {
		return vCards;
	}

	public User getUser() {
		return user;
	}

	public ArrayList<Chip> getChips() {
		return chips;
	}

	public String getColor() {
		return color;
	}

	public JsonObject toJson() {
		JsonObject player=new JsonObject();
		player.addProperty("name", this.getUser().getName());
		player.addProperty("score", this.getScore());
		player.addProperty("color", this.getColor());
		return player;
	}

/*	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Player) {
			if(obj.get)
		}
		else return false;
	}*/
	
	

}
