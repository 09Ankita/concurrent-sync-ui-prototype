package cosync_ui.api;

import java.util.ArrayList;
import java.util.List;

public class DataModel {

	private static List<Conflicts> conflictsList;

	public static List<Conflicts> getConflictsList() {
		List<Conflicts> mainList = new ArrayList<>();
		List<Conflicts> childrens = new ArrayList<>();
		List<Conflicts> elements = new ArrayList<>();
		Conflicts child;
		if (conflictsList == null) {
			conflictsList = new ArrayList<Conflicts>();

			child = new Conflicts("Conflict 1 Children SRC", "m6 : Method (Delete Delta)", null);
			childrens.add(child);
			child = new Conflicts("Conflict 1 Children SRC", "C1 --methods--> m6 (Delete Delta)", null);
			childrens.add(child);
			child = new Conflicts("Conflict 1 Children SRC", "m8 --methods--> p10 (Create Delta)", null);
			childrens.add(child);
			child = new Conflicts("Conflict 1 Children TRG", "e6 --gEntries--> ge12 (Create Delta)", null);
			childrens.add(child);
			child = new Conflicts("Elements1", "Elements", childrens);
			elements.add(child);
			Conflicts conflict1 = new Conflicts("Conflict 1", "CreateDeleteConflict", elements);

			childrens = new ArrayList<>();
			child = new Conflicts("Conflict 2 Children SRC", "C2 --methods--> f7 (Delete Delta)", null);
			childrens.add(child);
			child = new Conflicts("Conflict 2 Children SRC", "C3 --methods--> f7 (Create Delta)", null);
			childrens.add(child);
			child = new Conflicts("Conflict 2 Children TRG", "D2 --methods--> e7 (Delete Delta)", null);
			childrens.add(child);
			child = new Conflicts("Conflict 2 Children TRG", "D1 --methods--> e7 (Create Delta)", null);
			childrens.add(child);
			elements = new ArrayList<>();
			child = new Conflicts("Elements2", "Elements", childrens);
			elements.add(child);
			Conflicts conflict2 = new Conflicts("Conflict 2", "MoveConflict", elements);

			conflictsList.add(conflict1);
			conflictsList.add(conflict2);
			Conflicts allConflicts = new Conflicts("Conflicts", "Conflicts", conflictsList);
			mainList.add(allConflicts);
		}
		return conflictsList;
	}
	
	public static void removeElement(Conflicts element) {
		conflictsList.remove(element);
		System.out.println(conflictsList);
	}
	
	public static void addElement(Conflicts element) {
		if(!conflictsList.contains(element)) {
			if(element.getConflictName().equals("Conflict 1")) {
				conflictsList.add(0, element);
				return;
			}
//			if(element.getConflictName().equals("Conflict 2")) {
//				conflictsList.add(1, element);
//			}
			conflictsList.add(element);
		}
	}
}
