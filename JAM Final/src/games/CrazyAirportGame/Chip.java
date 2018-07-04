package games.CrazyAirportGame;

import java.awt.Color;

public class Chip {

	private Player realOwner;
	private Player currentOwner;
	private Color color;
	private boolean isPlaced;

	public Chip(Player owner, Color color) {
		this.realOwner=owner;
		this.currentOwner=owner;
		this.color=color;
		this.isPlaced=false;
	}
	
	public Player getCurrentOwner() {
		return currentOwner;
	}

	public void setCurrentOwner(Player currentOwner) {
		this.currentOwner = currentOwner;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isPlaced() {
		return isPlaced;
	}

	public void setPlaced(boolean isPlaced) {
		this.isPlaced = isPlaced;
	}

	public Player getRealOwner() {
		return realOwner;
	}

}
