package games.CrazyAirportGame;

import java.awt.Color;

public class Chip {

	private final Player realOwner;
	private Player currentOwner;

	public Chip(Player owner) {
		this.realOwner=owner;
		this.currentOwner=owner;
	}
	
	public Player getCurrentOwner() {
		return currentOwner;
	}

	public void setCurrentOwner(Player currentOwner) {
		this.currentOwner = currentOwner;
	}

	public Player getRealOwner() {
		return realOwner;
	}
	
	public void reset() {
		this.currentOwner=this.getRealOwner();
		this.realOwner.addChip(this);
	}
}
