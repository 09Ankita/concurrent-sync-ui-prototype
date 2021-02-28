package cosync_ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.wb.swt.SWTResourceManager;

import cosync_ui.api.Conflicts;
import cosync_ui.api.DataModel;
import cosync_ui.api.ToolTips;
import cosync_ui.api.TreeContentProvider;
import cosync_ui.api.UserOptionsManager;

/** This class generates the Conflicts list view.
 * @author Ankita Srivastava
*/
public class ConflictsListView extends Composite {

	private static TreeViewer treeViewer;
	private MenuItem expandAllItem;
	private MenuItem collapseAllItem;
	private static Conflicts selectedElement;
	private static boolean conflict1Resolved;
	private UserOptionsManager userOptionsManager;
	private MenuItem automatedSolItem;
	private MenuItem manualSolItem;
	private MenuItem manualOp1;
	private MenuItem manualOp2;
	private boolean isC1ResolvedManually = false;
	private static List<Conflicts> removedElems = new ArrayList<Conflicts>();
	private static TreeColumn treeColName;

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
	
	public static void disableTreeView() {
		treeViewer.getTree().setEnabled(false);
	}

	private ConflictsListView build() {
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		TreeColumnLayout tcl_compositeExample = new TreeColumnLayout();
		setLayout(tcl_compositeExample);
		treeViewer = new TreeViewer(this, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL  );
		Tree tree = treeViewer.getTree();
		tree.setEnabled(false);
		tree.setHeaderVisible(true);
		tree.setHeaderBackground(Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));
		tree.setLinesVisible(true);
		
		TreeViewerColumn treeViewerColumn = new TreeViewerColumn(treeViewer, SWT.NONE);
		treeColName = treeViewerColumn.getColumn();
		tcl_compositeExample.setColumnData(treeColName, new ColumnWeightData(100, ColumnWeightData.MINIMUM_WIDTH, true));
		treeColName.setText("Conflicts - 2");
		treeColName.setImage(SWTResourceManager.getImage(ModelsDisplayView.class, "/cosync_ui/resources/hierarchicalLayout.png"));
		
		treeViewer.setContentProvider(new TreeContentProvider());
		treeViewer.setLabelProvider(new TreeContentProvider());
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 2;
		treeViewer.setSelection(null);
		
		treeViewer.setInput(DataModel.getConflictsList());

		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent pEvent) {
				if (pEvent.getSelection() instanceof IStructuredSelection) {
					selectedElement = (Conflicts) pEvent.getStructuredSelection().getFirstElement();
					MenuItem[] menuItems = treeViewer.getTree().getMenu().getItems();
					for (MenuItem menuItem : menuItems) {
						if (menuItem.getText().equals("Compute Automated Solution") || menuItem.getText().equals("Resolve Manually")
								|| menuItem.getText().equals("Highlight Conflict")) {
							if (selectedElement != null && selectedElement.getChildren() != null && selectedElement.getChildren().size() == 1) {
								menuItem.setEnabled(true);
								if(selectedElement.getConflictName().equals("Conflict 1")) {
									ModelsDisplayView.setCurrentImagePath("/cosync_ui/resources/Highlighted_Conflict1_1.png");
									ModelsDisplayView.setModelImage(SWTResourceManager.getImage(ModelsDisplayView.class,
											"/cosync_ui/resources/Highlighted_Conflict1_1.png"));
									ModelsDisplayView.storeImages(SWTResourceManager.getImage(ModelsDisplayView.class,
											"/cosync_ui/resources/Highlighted_Conflict1_1.png"));
									manualOp1.setText("Keep p10 and e6");
									manualOp2.setText("Delete m6, p9, and p10"); //and m8-->p10
								} else if(selectedElement.getConflictName().equals("Conflict 2")) {
									ModelsDisplayView.setCurrentImagePath("/cosync_ui/resources/Highlighted_Conflict2_1.png");
									ModelsDisplayView.setModelImage(SWTResourceManager.getImage(ModelsDisplayView.class,
											"/cosync_ui/resources/Highlighted_Conflict2_1.png"));
									if(conflict1Resolved) {
										if (isC1ResolvedManually) {
											ModelsDisplayView.setCurrentImagePath("/cosync_ui/resources/Manual_C1Resolved_OP1_Highlighted.png");
											ModelsDisplayView.setModelImage(SWTResourceManager.getImage(ModelsDisplayView.class,
													"/cosync_ui/resources/Manual_C1Resolved_OP1_Highlighted.png"));
											ModelsDisplayView.storeImages(SWTResourceManager.getImage(ModelsDisplayView.class,
													"/cosync_ui/resources/Manual_C1Resolved_OP1_Highlighted.png"));
										} else {
											ModelsDisplayView.setCurrentImagePath("/cosync_ui/resources/Conflict1Resolved_HighlightedConflict2.png");
											ModelsDisplayView.setModelImage(SWTResourceManager.getImage(ModelsDisplayView.class,
													"/cosync_ui/resources/Conflict1Resolved_HighlightedConflict2.png"));
											ModelsDisplayView.storeImages(SWTResourceManager.getImage(ModelsDisplayView.class,
													"/cosync_ui/resources/Conflict1Resolved_HighlightedConflict2.png"));
										}
									}
									manualOp1.setText("Move f7 to C3, Move e7 to D1 and D3");
									manualOp2.setText("Create Elements");
								}
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
		expandAllItem.setImage(SWTResourceManager.getImage(ModelsDisplayView.class, "/cosync_ui/resources/expandall.png"));
		expandAllItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent pSelectionEvent) {
				treeViewer.expandAll();
			}
		});
		collapseAllItem = new MenuItem(treeMenu, SWT.PUSH);
		collapseAllItem.setText("Collapse All");
		collapseAllItem.setImage(SWTResourceManager.getImage(ModelsDisplayView.class, "/cosync_ui/resources/collapseall.png"));
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
				if (selectedElement.getConflictName().equals("Conflict 1")) {
					PreviewDialog preview = new PreviewDialog("Conflict 1");
					preview.build(getShell());
					preview.show();
				} else if (selectedElement.getConflictName().equals("Conflict 2")) {
					PreviewDialog preview = new PreviewDialog("Conflict 2");
					preview.build(getShell());
					preview.show();
				}
			}
		});
		manualSolItem = new MenuItem(treeMenu, SWT.CASCADE);
		manualSolItem.setEnabled(false);
		manualSolItem.setText("Resolve Manually");
		
		Menu manualSolMenu = new Menu(treeMenu);
		manualSolItem.setMenu(manualSolMenu);
		
		manualOp1 = new MenuItem(manualSolMenu, SWT.RADIO);		
		manualOp1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent pSelectionEvent) {
				if (selectedElement.getConflictName().equals("Conflict 1")) {
					ModelsDisplayView.setCurrentImagePath("/cosync_ui/resources/Manual_Conflict1Resolved_OP1.png");
					ModelsDisplayView.setModelImage(SWTResourceManager.getImage(ModelsDisplayView.class,
							"/cosync_ui/resources/Manual_Conflict1Resolved_OP1.png"));
					ModelsDisplayView.storeImages(SWTResourceManager.getImage(ModelsDisplayView.class,
							"/cosync_ui/resources/Manual_Conflict1Resolved_OP1.png"));
					ModelsDisplayView.setElemStatsImage(SWTResourceManager.getImage(ModelsDisplayView.class,
							"/cosync_ui/resources/ElementsStats_Conflict1Resolved_ManualOp1.png"));
					ModelsDisplayView.setNodeStatsImage(SWTResourceManager.getImage(ModelsDisplayView.class,
							"/cosync_ui/resources/NodeStats_Conflict1Resolved.png"));
					ModelsDisplayView.setLegendImage(SWTResourceManager.getImage(ModelsDisplayView.class,
							"/cosync_ui/resources/New_Legend_Init.png"));
					isC1ResolvedManually  = true;
					removeTreeElement();
				} else if (selectedElement.getConflictName().equals("Conflict 2")) {
					
				}
			}
		});
		
		manualOp2 = new MenuItem(manualSolMenu, SWT.RADIO);
		
		manualSolItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent pSelectionEvent) {
			}
		});

		this.updateToolTips();
		pack();		
		return this;
	}

	@Override
	protected void checkSubclass() {
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
	
	public static void removeTreeElement() {
		DataModel.removeElement(selectedElement);
		if(!removedElems.contains(selectedElement)) {
			removedElems.add(selectedElement);
		}
		treeViewer.remove(selectedElement);
		if(removedElems.size() == 1) {
			treeColName.setText("Conflicts - 1");
		} if(removedElems.size() == 2) {
			treeColName.setText("Conflicts - 0");
		}
		conflict1Resolved = true;
	}
	
	public void addTreeElement(int index) {
		DataModel.addElement(removedElems.get(index));
		treeViewer.add(treeViewer.getInput(), removedElems.get(index));
		treeViewer.refresh();
		if(index == 0) {
			conflict1Resolved = false;
		}
		if(treeViewer.getTree().getItemCount() == 1) {
			treeColName.setText("Conflicts - 1");
		} if(treeViewer.getTree().getItemCount() == 2) {
			treeColName.setText("Conflicts - 2");
		}
	}
}
