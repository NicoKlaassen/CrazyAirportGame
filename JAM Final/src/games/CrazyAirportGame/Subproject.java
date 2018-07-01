package games.CrazyAirportGame;

import java.util.ArrayList;

public class Subproject {

	boolean active;
	boolean built;
	String name;
	ArrayList<SubprojectField> fields= new ArrayList<SubprojectField>();
	
	public Subproject(String name, ArrayList<SubprojectField> fields) {
		active=false;
		this.name=name;
		this.fields=fields;
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
