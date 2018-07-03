package games.CrazyAirportGame;

public class SubprojectField {
	
	int amountSZT;
	boolean isVField;
	boolean isChipped;
	Chip chip;

	public SubprojectField(int amountSZT, boolean isVField) {
		this.amountSZT = amountSZT;
		this.isVField = isVField;
		this.isChipped=false;;
	}
	
	public boolean isChipped() {
		return isChipped;
	}


	public void setChipped(boolean isChipped) {
		this.isChipped = isChipped;
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

	
}
