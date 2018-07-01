package games.CrazyAirportGame;

import java.awt.Color;

public class Chip {

	Player realOwner;
	Player currentOwner;
	Color color;
	boolean isPlaced;
	
	public Chip(Player owner, Color color) {
		this.realOwner=owner;
		this.currentOwner=owner;
		this.color=color;
		this.isPlaced=false;
	}
}
