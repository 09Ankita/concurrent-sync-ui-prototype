package cosync_ui.api;

import java.util.List;

public class Conflicts {
	
	private String conflictName;
	private String conflictTitle;
	private List<Conflicts> children;
	
	public Conflicts(String conflictName, String conflictTitle, List<Conflicts> children) {
		super();
		this.conflictName = conflictName;
		this.conflictTitle = conflictTitle;
		this.children = children;
	}
	
	public String getConflictName() {
		return conflictName;
	}
	public void setConflictName(String conflictName) {
		this.conflictName = conflictName;
	}
	public String getConflictTitle() {
		return conflictTitle;
	}
	public void setConflictTitle(String conflictTitle) {
		this.conflictTitle = conflictTitle;
	}
	public List<Conflicts> getChildren() {
		return children;
	}
	public void setChildren(List<Conflicts> children) {
		this.children = children;
	}
	
}
