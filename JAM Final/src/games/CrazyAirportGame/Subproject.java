package games.CrazyAirportGame;

import java.util.ArrayList;

public class Subproject {

	private String name;
	private ArrayList<SubprojectField> fields;
	private int nextFree;
	
	public Subproject(String name, ArrayList<SubprojectField> fields) {
		this.name=name;
		this.fields=fields;
		this.nextFree=0;
	}
	
	public SubprojectField setChip(Chip chip) {
		SubprojectField result=this.getNextFreeField();
		result.setChip(chip);
		nextFree++;
		return result;
	}
	
	public Chip removeLastChip() {
		nextFree--;
		Chip result=this.getNextFreeField().getChip();
		getNextFreeField().setChip(null);
		return result;
	}
	
	public void finishProject() {
		for(SubprojectField f:fields) {
			f.getChip().reset();
			f.setChip(null);
		}
	}
	
	public SubprojectField getNextFreeField() {
		return fields.get(nextFree);
	}
	
	public boolean isNextFreeFieldLast() {
		return nextFree==fields.size()-1;
	}
	
	public boolean chipCanBeRemoved() {
		return nextFree>1;
	}
	
/*	public SubprojectField getLastField() {
		return this.getFields().get(this.getFields().size()-1);
	}
	
	public SubprojectField getLastChippedField() {
		SubprojectField lastChipped=null;
		int i=-1;
		for(SubprojectField field:this.getFields()) {
			i++;
			if(!field.isChipped()) {
				lastChipped=this.getFields().get(i-1);
			}
		}
		return lastChipped;
	}*/

	public String getName() {
		return name;
	}

/*	public ArrayList<SubprojectField> getFields() {
		return fields;
	}*/

}
