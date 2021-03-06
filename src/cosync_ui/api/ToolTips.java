package cosync_ui.api;

public enum ToolTips {
	USEROPTION_NEIGHBORHOODSIZE("Set number of connected neighboring elements to show",
			"The neighborhood size slider enables you to define the number of visualized connected neighboring elements with each of the 3 levels of increment"),
	USEROPTION_SHOW_ELEMENTS("Choose which elements of the graphs to show",
			"You can choose whether or not to see the created, source, target and correspondence elements for a match by checking/unchecking the check-boxes corresponding to them"),
	USEROPTION_LABEL_STYLE("Choose whether to show full names, abbreviated names, or none",
			"Choose to see the full label names, an abbreviation of the labels or hide the label names completely for the edges, nodes and correspondence respectively"),
	
	MATCHDISPLAY_USEROPTION_BUTTON("Open menu to select visualization options",
			"Opens a menu in which you can change the graph visualization."),	
	MATCHDISPLAY_TERMINATE_BUTTON("Close the debugger", "Clicking this button will close the debugger"),
	MATCHDISPLAY_RESTART_BUTTON("Close and restart the debugger",
			"Close and restart the debugger to start the process again from the initial models"),
	MATCHDISPLAY_SAVE_MODELS_BUTTON("Save the current state of the models",
			"Save the current state of the model. The saved models are added as (Source, Target and Correspondence) xmi files to the \"Instances\" folder"),
	MATCHDISPLAY_IMAGECONTAINER("Visualization of the models with/without conflicts",
			"Visualization of the models with/without conflicts"),
	
	MATCHLIST_TREE("This panel shows all rules of the TGG as well of the found applicable matches of the rules.",
			"This panel shows all rules of the TGG as well of the found applicable matches of the rules.\nRules that have no matches have a dark background.If they have never been applied they have strike-out text. Rules with available matches have a white background. If they have never been applied before they have bold text."),
	MATCHLIST_APPLY_BUTTON("Applies the selected rule or match.",
			"Applies the selected rule or match. For rules, a random match of the rule is applied. Double-clicking a rule/match applies is an alternative to using this button."),
	MATCHLIST_COLLAPSE_BUTTON("Collapses all tree elements."), 
	MATCHLIST_EXPAND_BUTTON("Expands all tree elements."),
	
	PROTOCOL_VIEW("List of previous rule applications.",
			"This panel shows all previous rule applications. Select one or multiple elements to visualize the rule applications."),
	
	MODEL_UCEDITRESOLVE_BUTTON("Click to resolve uncontroversial edits in the models.",
			"Click to resolve the uncontroversial edits in the models."),
	
	CONFLICTSLIST_TREE("This panel lists all conflicts in the models.", "This panel lists all conflicts in the interrelated models."),
	CONFLICTSLIST_AUTOMATESOL_MENUTITEM("Select to resolve conflict automatically."),
	CONFLICTSLIST_MANUALSOL_MENUTITEM("Select to resolve conflict manually."),
	CONFLICTSLIST_COLLAPSE_MENUTITEM("Collapses all tree elements."), 
	CONFLICTSLIST_EXPAND_MENUITEM("Expands all tree elements."),
	CONFLICTSLIST_HIGHLIGHT_MENUITEM("Highlight this conflict in the graph."),
	
	SAVE_CURRENT_MENU("Save the current state of the models",
			"Save the current state of the model. The saved models are added as (Source, Target and Correspondence) xmi files to the \"Instances\" folder"),
	PREVIOUS_STATE_MENU("Go back to previous state of the models"),
	PREFERENCES_MENU("Set user preferences for graph visualization", "Opens a menu in which you can change the graph visualization."),
	COMPARE_VIEW_MENU("Select to compare with previous state in compare view. Click again to switch back to main view.", "Switch to compare view of models in current state and previous state"),
	;

	private String shortDescription;
	private String longDescription;

	private ToolTips(String shortDescription, String longDescription) {
		this.shortDescription = shortDescription;
		this.longDescription = longDescription;
	}

	private ToolTips(String description) {
		this(description, description);
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public String getDescription(UserOptionsManager.ToolTipOption toolTipSetting) {
		switch (toolTipSetting) {
		case MINIMAL:
			return shortDescription;
		case FULL:
			return longDescription;
		default:
			return "";
		}
	}
}
