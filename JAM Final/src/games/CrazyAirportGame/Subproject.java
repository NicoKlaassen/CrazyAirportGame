package games.CrazyAirportGame;

import java.util.ArrayList;

public class Subproject {

	boolean active;
	boolean built;
	String name;
	ArrayList<SubprojectField> fields;
	Player owner;

	public Subproject(String name, ArrayList<SubprojectField> fields) {
		active=false;
		built=false;
		this.name=name;
		this.fields=fields;
	}
	
	public SubprojectField getNextFreeField() {
		SubprojectField nextFreeField = null;
		for(SubprojectField field:this.getFields()) {
			if(!field.isChipped()) {
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
	
	public SubprojectField getLastField() {
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

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

}
