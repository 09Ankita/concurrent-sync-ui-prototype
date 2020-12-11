package cosync_ui.api;

import java.util.ArrayList;
import java.util.List;

public class DataModel {

	private static List<Conflicts> conflictsList;

	public static List<Conflicts> getConflictsList() {
		List<Conflicts> mainList = new ArrayList<>();
		List<Conflicts> childrens = new ArrayList<>();
		Conflicts child;
		if (conflictsList == null) {
			conflictsList = new ArrayList<Conflicts>();

			child = new Conflicts("Conflict 1 Children", "m6 : Method (Delete Delta)", null);
			childrens.add(child);
			child = new Conflicts("Conflict 1 Children", "C1 --methods--> m6 (Delete Delta)", null);
			childrens.add(child);
			child = new Conflicts("Conflict 1 Children", "e6 --gEntries--> Entry (Create Delta)", null);
			childrens.add(child);
			Conflicts conflict1 = new Conflicts("Conflict 1", "CreateDeleteConflict", childrens);

			childrens = new ArrayList<>();
			child = new Conflicts("Conflict 2 Children", "C2 --methods--> f7 (Delete Delta)", null);
			childrens.add(child);
			child = new Conflicts("Conflict 2 Children", "C3 --methods--> f7 (Create Delta)", null);
			childrens.add(child);
			child = new Conflicts("Conflict 2 Children", "D2 --methods--> e7 (Delete Delta)", null);
			childrens.add(child);
			child = new Conflicts("Conflict 2 Children", "D1 --methods--> e7 (Create Delta)", null);
			childrens.add(child);
			Conflicts conflict2 = new Conflicts("Conflict 2", "MoveMoveConflict", childrens);

			// Conflicts appMenu1 = new Conflicts("Window", "Window", list2);

			conflictsList.add(conflict1);
			conflictsList.add(conflict2);
			Conflicts allConflicts = new Conflicts("Conflicts", "Conflicts", conflictsList);
			mainList.add(allConflicts);
		}
		return mainList;
	}

}
