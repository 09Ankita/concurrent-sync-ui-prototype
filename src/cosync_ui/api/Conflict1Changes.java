package cosync_ui.api;

import java.util.ArrayList;
import java.util.List;

public class Conflict1Changes {
	
	private String element;
	
	private List<Conflict1Changes> children;

	public Conflict1Changes(String element, List<Conflict1Changes> action) {
		this.element = element;
		this.children = action;
	}

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}

	public List<Conflict1Changes> getAction() {
		return children;
	}

	public void setAction(List<Conflict1Changes> action) {
		this.children = action;
	}
	
	public static List<Conflict1Changes> getSrcElementsList() {
		List<Conflict1Changes> changesList = new ArrayList<Conflict1Changes>();
		List<Conflict1Changes> actions = new ArrayList<>();
		Conflict1Changes action;
		Conflict1Changes change;
		
		actions = new ArrayList<>();
		action = new Conflict1Changes("Delete", null);
		actions.add(action);
		change = new Conflict1Changes("Node m6", actions);
		changesList.add(change);
		
		actions = new ArrayList<>();
		action = new Conflict1Changes("Delete", null);
		actions.add(action);
		change = new Conflict1Changes("Edge C1-to-m6", actions);
		changesList.add(change);
		
		actions = new ArrayList<>();
		action = new Conflict1Changes("Delete", null);
		actions.add(action);
		change = new Conflict1Changes("Node p9", actions);
		changesList.add(change);
		
		actions = new ArrayList<>();
		action = new Conflict1Changes("Delete", null);
		actions.add(action);
		change = new Conflict1Changes("Edge m6-to-p9", actions);
		changesList.add(change);
		
		actions = new ArrayList<>();
		action = new Conflict1Changes("Delete", null);
		actions.add(action);
		change = new Conflict1Changes("Edge m6-to-p10", actions);
		changesList.add(change);
		
		return changesList;
	}
	
	public static List<Conflict1Changes> getCorrElementsList() {
		List<Conflict1Changes> changesList = new ArrayList<Conflict1Changes>();
		List<Conflict1Changes> actions = new ArrayList<>();
		Conflict1Changes action;
		Conflict1Changes change;
		
		actions = new ArrayList<>();
		action = new Conflict1Changes("Create", null);
		actions.add(action);
		change = new Conflict1Changes("Corr Edge p10-to-e8", actions);
		changesList.add(change);
		
		return changesList;
	}
	
	public static List<Conflict1Changes> getTrgElementsList() {
		List<Conflict1Changes> changesList = new ArrayList<Conflict1Changes>();
		List<Conflict1Changes> actions = new ArrayList<>();
		Conflict1Changes action;
		Conflict1Changes change;
		
		actions = new ArrayList<>();
		action = new Conflict1Changes("Delete", null);
		actions.add(action);
		change = new Conflict1Changes("Node e6", actions);
		changesList.add(change);
		
		actions = new ArrayList<>();
		action = new Conflict1Changes("Delete", null);
		actions.add(action);
		change = new Conflict1Changes("Edge e6-to-ge12", actions);
		changesList.add(change);
		
		return changesList;
	}
}
