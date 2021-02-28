package cosync_ui.api;

import java.util.ArrayList;
import java.util.List;

public class Conflict2Changes {
	
	private String element;
	
	private List<Conflict2Changes> children;

	public Conflict2Changes(String element, List<Conflict2Changes> action) {
		this.element = element;
		this.children = action;
	}

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}

	public List<Conflict2Changes> getAction() {
		return children;
	}

	public void setAction(List<Conflict2Changes> action) {
		this.children = action;
	}
	
	public static List<Conflict2Changes> getSrcElementsList() {
		List<Conflict2Changes> changesList = new ArrayList<Conflict2Changes>();
		List<Conflict2Changes> actions = new ArrayList<>();
		Conflict2Changes action;
		Conflict2Changes change;
		
		actions = new ArrayList<>();
		action = new Conflict2Changes("Delete", null);
		actions.add(action);
		change = new Conflict2Changes("Edge C2-to-f7", actions);
		changesList.add(change);
		
		actions = new ArrayList<>();
		action = new Conflict2Changes("Create", null);
		actions.add(action);
		change = new Conflict2Changes("Edge C1-to-f7", actions);
		changesList.add(change);
		
		return changesList;
	}
	
	public static List<Conflict2Changes> getCorrElementsList() {
		List<Conflict2Changes> changesList = new ArrayList<Conflict2Changes>();
		List<Conflict2Changes> actions = new ArrayList<>();
		Conflict2Changes action;
		Conflict2Changes change;
		
		action = new Conflict2Changes("Create", null);
		actions.add(action);
		change = new Conflict2Changes("Corr Edge C3-to-D3", actions);
		changesList.add(change);
		
		return changesList;
	}
	
	public static List<Conflict2Changes> getTrgElementsList() {
		List<Conflict2Changes> changesList = new ArrayList<Conflict2Changes>();
		List<Conflict2Changes> actions = new ArrayList<>();
		Conflict2Changes action;
		Conflict2Changes change;
		
		action = new Conflict2Changes("Delete", null);
		actions.add(action);
		change = new Conflict2Changes("Edge D2-to-e7", actions);
		changesList.add(change);
		
		actions = new ArrayList<>();
		action = new Conflict2Changes("Create", null);
		actions.add(action);
		change = new Conflict2Changes("Node D3", actions);
		changesList.add(change);
		
		actions = new ArrayList<>();
		action = new Conflict2Changes("Create", null);
		actions.add(action);
		change = new Conflict2Changes("Edge D2-to-D3", actions);
		changesList.add(change);
		
		actions = new ArrayList<>();
		action = new Conflict2Changes("Create", null);
		actions.add(action);
		change = new Conflict2Changes("Edge D3-to-e7", actions);
		changesList.add(change);
		
		return changesList;
	}
}
