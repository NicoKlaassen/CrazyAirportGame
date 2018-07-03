package games.CrazyAirportGame;

import java.util.ArrayList;

public class Subproject {

	boolean active;
	boolean built;
	String name;
	ArrayList<SubprojectField> fields;
	ArrayList<Chip> chipsInProject;

	public Subproject(String name, ArrayList<SubprojectField> fields) {
		active=false;
		built=false;
		this.name=name;
		this.fields=fields;
	}
	
	public SubprojectField getNextFreeField(Subproject project) {
		SubprojectField nextFreeField = null;
		for(SubprojectField field:project.getFields()) {
			if(field.isChipped==false) {
				nextFreeField=field;
				break;
			}
			
		}
		return nextFreeField;
	}
	
	public boolean isLastField(Subproject project, SubprojectField field) {
		boolean result=false;
		if(project.getFields().get(project.getFields().size()-1)==field) {
			result=true;
		}
		return result;
	}
	
	public ArrayList<Chip> getChipsInProject() {
		return chipsInProject;
	}

	public void addChip(Chip chip) {
		this.chipsInProject.add(chip);
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isBuilt() {
		return built;
	}

	public void setBuilt(boolean built) {
		this.built = built;
	}

	public String getName() {
		return name;
	}

	public ArrayList<SubprojectField> getFields() {
		return fields;
	}
}
