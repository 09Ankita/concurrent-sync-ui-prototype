package cosync_ui;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.wb.swt.SWTResourceManager;

import cosync_ui.api.Conflicts;
import cosync_ui.api.DataModel;
import cosync_ui.api.ToolTips;
import cosync_ui.api.TreeContentProvider;
import cosync_ui.api.UserOptionsManager;

public class ConflictsListView extends Composite {

	private static TreeViewer treeViewer;
	private MenuItem expandAllItem;
	private MenuItem collapseAllItem;
	private Conflicts selectedElement;
	private UserOptionsManager userOptionsManager;
	private MenuItem automatedSolItem;
	private MenuItem manualSolItem;
	private MenuItem higlightConflictItem;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public ConflictsListView(Composite parent, int style) {
		super(parent, style);
		userOptionsManager = new UserOptionsManager();
		build();
	}

	public static void enableTreeView() {
		treeViewer.getTree().setEnabled(true);
	}

	private ConflictsListView build() {
		setLayout(new GridLayout(2, false));
		treeViewer = new TreeViewer(this, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION | SWT.SINGLE);
		treeViewer.setContentProvider(new TreeContentProvider());
		treeViewer.setLabelProvider(new TreeContentProvider());
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 2;
		treeViewer.getControl().setLayoutData(gridData);
		Tree tree = treeViewer.getTree();
		tree.setEnabled(false);
		tree.setLinesVisible(true);
		treeViewer.setInput(DataModel.getConflictsList());

		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent pEvent) {
				if (pEvent.getSelection() instanceof IStructuredSelection) {
					selectedElement = (Conflicts) pEvent.getStructuredSelection().getFirstElement();
					System.out.println(selectedElement.getConflictTitle());
					MenuItem[] menuItems = treeViewer.getTree().getMenu().getItems();
					for (MenuItem menuItem : menuItems) {
						if (menuItem.getText().equals("Compute Automated Solution")
								|| menuItem.getText().equals("Resolve Manually")) {
							if (selectedElement != null && selectedElement.getChildren() == null) {
								menuItem.setEnabled(true);
							} else {
								menuItem.setEnabled(false);
							}
						}
					}
				}
			}
		});

		final Menu treeMenu = new Menu(treeViewer.getTree());
		treeViewer.getTree().setMenu(treeMenu);
		expandAllItem = new MenuItem(treeMenu, SWT.PUSH);
		expandAllItem.setText("Expand All");
		expandAllItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent pSelectionEvent) {
				treeViewer.expandAll();
			}
		});
		collapseAllItem = new MenuItem(treeMenu, SWT.PUSH);
		collapseAllItem.setText("Collapse All");
		collapseAllItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent pSelectionEvent) {
				treeViewer.collapseAll();
			}
		});

		automatedSolItem = new MenuItem(treeMenu, SWT.PUSH);
		automatedSolItem.setText("Compute Automated Solution");
		automatedSolItem.setEnabled(false);
		automatedSolItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent pSelectionEvent) {
				if (selectedElement.getConflictName().equals("Conflict 1 Children")) {
					ModelsDisplayView.setCurrentImagePath("/cosync_ui/resources/Conflict1Resolved_GEAdded.png");
					ModelsDisplayView.setImage(SWTResourceManager.getImage(ModelsDisplayView.class,
							"/cosync_ui/resources/Conflict1Resolved_GEAdded.png"));
				} else if (selectedElement.getConflictName().equals("Conflict 2 Children")) {
					ModelsDisplayView.setCurrentImagePath("/cosync_ui/resources/Conflict2Resolved_GEAdded.png");
					ModelsDisplayView.setImage(SWTResourceManager.getImage(ModelsDisplayView.class,
							"/cosync_ui/resources/Conflict2Resolved_GEAdded.png"));
				}
			}
		});
		manualSolItem = new MenuItem(treeMenu, SWT.PUSH);
		manualSolItem.setText("Resolve Manually");
		manualSolItem.setEnabled(false);
		manualSolItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent pSelectionEvent) {
				// TODO - Add implementation for this
			}
		});

		higlightConflictItem = new MenuItem(treeMenu, SWT.PUSH);
		higlightConflictItem.setText("Highlight Conflict");

		this.updateToolTips();
		pack();
		return this;
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public void updateToolTips() {
		treeViewer.getControl()
				.setToolTipText(ToolTips.CONFLICTSLIST_TREE.getDescription(userOptionsManager.getToolTipSetting()));
		automatedSolItem.setToolTipText(
				ToolTips.CONFLICTSLIST_AUTOMATESOL_MENUTITEM.getDescription(userOptionsManager.getToolTipSetting()));
		manualSolItem.setToolTipText(
				ToolTips.CONFLICTSLIST_MANUALSOL_MENUTITEM.getDescription(userOptionsManager.getToolTipSetting()));
		collapseAllItem.setToolTipText(
				ToolTips.CONFLICTSLIST_COLLAPSE_MENUTITEM.getDescription(userOptionsManager.getToolTipSetting()));
		expandAllItem.setToolTipText(
				ToolTips.CONFLICTSLIST_EXPAND_MENUITEM.getDescription(userOptionsManager.getToolTipSetting()));

	}

}
