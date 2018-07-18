package games.CrazyAirportGame;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Subproject {

	private String name;
	private ArrayList<SubprojectField> fields;
	private int nextFree;
	private int id;
	
	public Subproject(String name, ArrayList<SubprojectField> fields, int id) {
		this.name=name;
		this.fields=fields;
		this.nextFree=0;
		this.id=id;
	}
	
	public int getId() {
		return id;
	}

	public SubprojectField setChip(Chip chip) {
		SubprojectField result=this.fields.get(nextFree);
		result.setChip(chip);
		nextFree++;
		return result;
	}
	
	public Chip removeLastChip() {
		nextFree--;
		Chip result=this.fields.get(nextFree).getChip();
		fields.get(nextFree).setChip(null);
		return result;
	}
	
	public void finishProject() {
		for(SubprojectField f:fields) {
			f.getChip().reset();
			f.setChip(null);
		}
	}
	
	public SubprojectField getFinishField() {
		return this.fields.get(fields.size()-1);
	}
	
	public int getFreeFieldsLeft() {
		return fields.size()-nextFree;
	}
	
	public ArrayList<SubprojectField> getFields() {
		return fields;
	}

	public int getIdNextFreeField() {
		return fields.indexOf(fields.get(nextFree));
	}
	
	public int getIdSecondNextFreeField() {
		return fields.indexOf(fields.get(nextFree+1));
	}
	
	public SubprojectField getNextFreeField() {
		return fields.get(nextFree);
	}
	
	public SubprojectField getSecondNextFreeField() {
		return fields.get(nextFree+1);
	}
	
	public boolean isNextFreeFieldLast() {
		return nextFree==fields.size()-1;
	}
	
	public boolean chipCanBeRemoved() {
		return nextFree>1;
	}
	
	public boolean twoChipsCanBeRemoved() {
		return nextFree>2;
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

	public JsonObject toJson() {
		JsonObject result=new JsonObject();
		JsonArray array=new JsonArray();
		for(SubprojectField field:this.fields) {
			array.add(field.toJson());
		}
		result.addProperty("id", this.id);
		result.add("fields", array);
		return result;
	}

/*	public ArrayList<SubprojectField> getFields() {
		return fields;
	}*/

}
