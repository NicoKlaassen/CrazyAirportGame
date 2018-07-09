package games.CrazyAirportGame;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class SubprojectField {
	
	private final int amountSZT;
	private final boolean isVField;
	private Chip chip;
	private final boolean isLastField;

	public SubprojectField(int amountSZT, boolean isVField, boolean isLastField) {
		this.amountSZT = amountSZT;
		this.isVField = isVField;
		this.isLastField=isLastField;
	}

	public boolean isLastField() {
		return isLastField;
	}

	public boolean isChipped() {
		return this.chip!=null;
	}

	public int getAmountSZT() {
		return amountSZT;
	}

	public boolean isVField() {
		return isVField;
	}

	public Chip getChip() {
		return chip;
	}

	public void setChip(Chip chip) {
		this.chip = chip;
	}

	public JsonObject toJson() {
		JsonObject result=new JsonObject();
		Chip chip=this.getChip();
		if(chip!=null) {
			result.addProperty("Chip", chip.getCurrentOwner().getColor());
		}
		else {
			result.addProperty("Chip", "none");
		}
		return result;
	}

	
}
