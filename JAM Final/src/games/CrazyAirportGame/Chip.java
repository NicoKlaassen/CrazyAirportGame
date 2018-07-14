package games.CrazyAirportGame;

import java.awt.Color;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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

	public JsonObject toJson() {
		JsonObject chip=new JsonObject();
		chip.addProperty("realOwner", this.getRealOwner().getUser().getName());
		chip.addProperty("currentOwner", this.getCurrentOwner().getUser().getName());
		return chip;
	}
}
