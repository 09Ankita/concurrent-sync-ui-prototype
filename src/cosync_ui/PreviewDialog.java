package cosync_ui;

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import cosync_ui.api.Conflict1Changes;
import cosync_ui.api.Conflict1ChangesTreeContentProvider;
import cosync_ui.api.Conflict2Changes;
import cosync_ui.api.Conflict2ChangesTreeContentProvider;

/** This class creates the preview in Pop-up window.
 * @author Ankita Srivastava
*/
public class PreviewDialog {

	private Shell menuShell;
	private ITreeContentProvider contentProvider;
	private IBaseLabelProvider labelProvider;
	private String conflictName;
	private CompareView compareView;
	private Shell parentShell;
	public static ModelsDisplayView mainView;

	public PreviewDialog(String conflictName) {
		this.conflictName = conflictName;
		if(conflictName.equals("Conflict 1")) {
			contentProvider =  new Conflict1ChangesTreeContentProvider();
			labelProvider = new Conflict1ChangesTreeContentProvider();
		}
		else if(conflictName.equals("Conflict 2")) {
			contentProvider = new Conflict2ChangesTreeContentProvider();
			labelProvider = new Conflict2ChangesTreeContentProvider();
		}
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public void build(Shell parentShell) {
		this.parentShell = parentShell;
		menuShell = new Shell(parentShell, SWT.DIALOG_TRIM | SWT.RESIZE);
		menuShell.setText("Resolution Operation");
		menuShell.setLayout(new GridLayout());
		menuShell.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent pEvent) {
				pEvent.doit = false;
				menuShell.setVisible(false);
			}
		});

		Composite panel = new Composite(menuShell, SWT.NONE);
		panel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		panel.setLayout(new GridLayout());

		Label image = new Label(panel, SWT.NONE);
		image.setImage(SWTResourceManager.getImage(PreviewDialog.class, "/cosync_ui/resources/message_info.png"));

		Label messageLabel = new Label(panel, SWT.NONE);
		messageLabel.setText("This operation will have following impact on the models.");
		
		/** Source Changes */
		Group sourceElementGroup = new Group(panel, SWT.NONE);
		sourceElementGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
		sourceElementGroup.setLayout(new GridLayout());
		sourceElementGroup.setText("Source Changes");

		TreeViewer srcElemtreeViewer = new TreeViewer(sourceElementGroup, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY | SWT.SINGLE);
		srcElemtreeViewer.setContentProvider(contentProvider);
		srcElemtreeViewer.setLabelProvider(labelProvider);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 2;
		srcElemtreeViewer.getControl().setLayoutData(gridData);
		srcElemtreeViewer.setAutoExpandLevel(TreeViewer.ALL_LEVELS);
		if(conflictName.equals("Conflict 1")) {
			srcElemtreeViewer.setInput(Conflict1Changes.getSrcElementsList());
		}else if(conflictName.equals("Conflict 2")) {
			srcElemtreeViewer.setInput(Conflict2Changes.getSrcElementsList());
		}
		srcElemtreeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
		    @Override
		    public void selectionChanged(final SelectionChangedEvent event) {
		        if (!event.getSelection().isEmpty()) {
		        	srcElemtreeViewer.setSelection(StructuredSelection.EMPTY);
		        }
		    }
		});
		
		/** Correspondence Changes */
		Group corrElementGroup = new Group(panel, SWT.NONE);
		corrElementGroup.setLayout(new GridLayout());
		corrElementGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
		corrElementGroup.setText("Correspondence Changes");
		
		TreeViewer corrElemtreeViewer = new TreeViewer(corrElementGroup, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.SINGLE);
		corrElemtreeViewer.setContentProvider(contentProvider);
		corrElemtreeViewer.setLabelProvider(labelProvider);
		GridData gridData2 = new GridData(GridData.FILL_BOTH);
		gridData2.horizontalSpan = 2;
		corrElemtreeViewer.getControl().setLayoutData(gridData2);
		corrElemtreeViewer.setAutoExpandLevel(TreeViewer.ALL_LEVELS);
		if(conflictName.equals("Conflict 1")) {
			corrElemtreeViewer.setInput(Conflict1Changes.getCorrElementsList());
		} else if(conflictName.equals("Conflict 2")) {
			corrElemtreeViewer.setInput(Conflict2Changes.getCorrElementsList());
		}
		corrElemtreeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
		    @Override
		    public void selectionChanged(final SelectionChangedEvent event) {
		        if (!event.getSelection().isEmpty()) {
		        	corrElemtreeViewer.setSelection(StructuredSelection.EMPTY);
		        }
		    }
		});
		
		/** Target Changes */
		Group targetElementGroup = new Group(panel, SWT.NONE);
		targetElementGroup.setLayout(new GridLayout());
		targetElementGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
		targetElementGroup.setText("Target Changes");

		TreeViewer trgElemtreeViewer = new TreeViewer(targetElementGroup, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.SINGLE);
		trgElemtreeViewer.setContentProvider(contentProvider);
		trgElemtreeViewer.setLabelProvider(labelProvider);
		GridData gridData1 = new GridData(GridData.FILL_BOTH);
		gridData1.horizontalSpan = 2;
		trgElemtreeViewer.getControl().setLayoutData(gridData1);
		trgElemtreeViewer.setAutoExpandLevel(TreeViewer.ALL_LEVELS);
		if(conflictName.equals("Conflict 1")) {
			trgElemtreeViewer.setInput(Conflict1Changes.getTrgElementsList());
		} else if(conflictName.equals("Conflict 2")) {
			trgElemtreeViewer.setInput(Conflict2Changes.getTrgElementsList());
		}
		trgElemtreeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
		    @Override
		    public void selectionChanged(final SelectionChangedEvent event) {
		        if (!event.getSelection().isEmpty()) {
		        	trgElemtreeViewer.setSelection(StructuredSelection.EMPTY);
		        }
		    }
		});
		
		Composite buttonRow = new Composite(panel, SWT.NONE);
		buttonRow.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		buttonRow.setLayout(new GridLayout(2, false));
		
		Button okButton = new Button(buttonRow, SWT.PUSH);
		okButton.setText("  Ok  ");
		okButton.setSize(new Point(100, 100));
		okButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		okButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent pSelectionEvent) {
				if(conflictName.equals("Conflict 1")) {
					ModelsDisplayView.setCurrentImagePath("/cosync_ui/resources/Conflict1Resolved_GEAdded.png");
					ModelsDisplayView.setModelImage(SWTResourceManager.getImage(ModelsDisplayView.class,
							"/cosync_ui/resources/Conflict1Resolved_GEAdded.png"));
					ModelsDisplayView.setElemStatsImage(SWTResourceManager.getImage(ModelsDisplayView.class,
							"/cosync_ui/resources/ElementsStats_Conflict1Resolved.png"));
					ModelsDisplayView.setNodeStatsImage(SWTResourceManager.getImage(ModelsDisplayView.class,
							"/cosync_ui/resources/NodeStats_Conflict1Resolved.png"));
					ModelsDisplayView.setLegendImage(SWTResourceManager.getImage(ModelsDisplayView.class,
							"/cosync_ui/resources/Legend_With_IndDelta.png"));
					ModelsDisplayView.storeImages(SWTResourceManager.getImage(ModelsDisplayView.class,
							"/cosync_ui/resources/Conflict1Resolved_GEAdded.png"));
				} 
				else if(conflictName.equals("Conflict 2")) {
					ModelsDisplayView.setCurrentImagePath("/cosync_ui/resources/Conflict2Resolved_GEAdded.png");
					ModelsDisplayView.setModelImage(SWTResourceManager.getImage(ModelsDisplayView.class,
							"/cosync_ui/resources/Conflict2Resolved_GEAdded.png"));
					ModelsDisplayView.setElemStatsImage(SWTResourceManager.getImage(ModelsDisplayView.class,
							"/cosync_ui/resources/ElementsStats_Conflict2Resolved.png"));
					ModelsDisplayView.setNodeStatsImage(SWTResourceManager.getImage(ModelsDisplayView.class,
							"/cosync_ui/resources/NodeStats_Conflict2Resolved.png"));
					ModelsDisplayView.setLegendImage(SWTResourceManager.getImage(ModelsDisplayView.class,
							"/cosync_ui/resources/New_Legend_NoDel.png"));
					ModelsDisplayView.storeImages(SWTResourceManager.getImage(ModelsDisplayView.class,
							"/cosync_ui/resources/Conflict2Resolved_GEAdded.png"));
					new ResolvedMessageDialog(parentShell);
				}
				
				menuShell.setVisible(false);
				compareView.dispose();
				CoSync_UI.hideMainView();
				ConflictsListView.enableTreeView();
				parentShell.layout(true, true);
				ConflictsListView.removeTreeElement();
			}
		});
		
		Button cancelButton = new Button(buttonRow, SWT.PUSH);
		cancelButton.setText("Cancel");
		cancelButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		cancelButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent pSelectionEvent) {
				menuShell.setVisible(false);
			}
		});

		panel.pack();
		menuShell.pack();
		menuShell.open();
		menuShell.setVisible(false);
	}

	public void show() {
		menuShell.setVisible(true);
		CoSync_UI.hideMainView();
		compareView = new CompareView(parentShell, SWT.Resize, false, conflictName);
		compareView.setLayoutData(new GridData(GridData.FILL_BOTH));
		compareView.setVisible(true);
		ConflictsListView.disableTreeView();
		parentShell.layout(true, true);
	}
}
