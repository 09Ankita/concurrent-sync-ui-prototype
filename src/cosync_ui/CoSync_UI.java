package cosync_ui;

import java.io.IOException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.wb.swt.SWTResourceManager;

import cosync_ui.api.ToolTips;
import cosync_ui.api.UserOptionsManager;

/** This is main class which generates the layout of Prototype and invokes calls to other classes to get the conflicts list and models to display.
 * @author Ankita Srivastava
*/
public class CoSync_UI extends Composite {

	private ConflictsListView conflictsListView;
	private static ModelsDisplayView mainView;
	private static Display display;	
	private UserOptionsMenu userOptionsMenu;
	private UserOptionsManager userOptionsManager;
	private CompareView compareView;

	private static final int MIN_WIDTH_LEFT = 370;
	private static final int MIN_WIDTH_RIGHT = 50;
	private static ToolItem prevToolItem;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public CoSync_UI(Composite parent, int style) {
		super(parent, style);
		userOptionsManager = new UserOptionsManager();
		createToolBar(parent.getShell());
		SashForm mainSashForm = new SashForm(parent, SWT.HORIZONTAL);
		mainSashForm.setSashWidth(1);
		mainSashForm.setLayoutData(new GridData(GridData.FILL_BOTH));
		mainSashForm.setLayout(new GridLayout());
		mainSashForm.setBackground(display.getSystemColor(SWT.COLOR_GRAY));

		SashForm leftPanelSashForm = new SashForm(mainSashForm, SWT.VERTICAL);
		leftPanelSashForm.setLayoutData(new GridData(GridData.FILL_BOTH));
		leftPanelSashForm.setLayout(new GridLayout());
		leftPanelSashForm.setBackground(display.getSystemColor(SWT.COLOR_GRAY));

		conflictsListView = new ConflictsListView(leftPanelSashForm, SWT.NONE);
		conflictsListView.setLayoutData(new GridData(GridData.FILL_BOTH));

		// leftPanelSashForm.setWeights(new int[] { 60, 40 });

		mainView = new ModelsDisplayView(mainSashForm, SWT.NONE);
		mainView.setLayoutData(new GridData(GridData.FILL_BOTH));

		mainSashForm.setWeights(new int[] { 30, 70 });

		Shell shell = parent.getShell();
		shell.addListener(SWT.Resize, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				int width = shell.getClientArea().width;
				int[] weights = mainSashForm.getWeights();

				if (width >= MIN_WIDTH_LEFT + MIN_WIDTH_RIGHT) {
					weights[0] = 1000000 * MIN_WIDTH_LEFT / width;
					weights[1] = 1000000 - weights[0];
				} else {
					weights[0] = 1000000 * MIN_WIDTH_LEFT / (MIN_WIDTH_LEFT + MIN_WIDTH_RIGHT);
					weights[1] = 1000000 * MIN_WIDTH_RIGHT / (MIN_WIDTH_LEFT + MIN_WIDTH_RIGHT);
				}
				mainSashForm.setWeights(weights);
			}
		});
	}

	@Override
	protected void checkSubclass() {
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public static void main(String[] args) {
		display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout());
		shell.setText("Concurrent Synchronization");
		shell.setImage(SWTResourceManager.getImage(CoSync_UI.class, "/cosync_ui/resources/synch_synch.png"));
		CoSync_UI initScreen = new CoSync_UI(shell, SWT.NONE);
		GridData gd_initScreen = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_initScreen.heightHint = 5;
		initScreen.setLayoutData(gd_initScreen);
		initScreen.createMenu(shell);
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	public void createToolBar(Shell shell) {
		Composite toolBarCmp = new Composite(shell, SWT.NONE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).applyTo(toolBarCmp);
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(toolBarCmp);
		toolBarCmp.setSize(200, 50);
		toolBarCmp.setBackground(new Color(null, 220, 220, 220)); 	
		
		final ToolBar bar = new ToolBar(toolBarCmp, SWT.FLAT | SWT.RIGHT | SWT.HORIZONTAL);
        bar.setBackground(new Color(null, 220, 220, 220));
        
        final ToolItem saveToolItem = new ToolItem(bar, SWT.PUSH);
        saveToolItem.setImage(SWTResourceManager.getImage(CoSync_UI.class, "/cosync_ui/resources/save_edit11.png"));
        saveToolItem.setText("Save");
        saveToolItem.setToolTipText("Save Current State");
        saveToolItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				new ModelLocationDialog("model save location", "/cosync_ui/instances/", "current_state", "png", "Save",
						saveLocations -> {
							try {
								mainView.saveModels(saveLocations);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}).build(getDisplay().getActiveShell());
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
        
        new ToolItem(bar, SWT.SEPARATOR);
        
        prevToolItem = new ToolItem(bar, SWT.PUSH);
        prevToolItem.setImage(SWTResourceManager.getImage(CoSync_UI.class, "/cosync_ui/resources/nav_backward.png"));
        prevToolItem.setText("Previous State");
        prevToolItem.setToolTipText("Back to previous state");
        prevToolItem.setEnabled(false);
        prevToolItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				if (mainView.getImages() != null && mainView.getImages().size() > 1) {
					int indexOf = mainView.getImages().indexOf(ModelsDisplayView.getCurrentImage());
					Image image = mainView.getImages().get(indexOf - 1);
					if (image.equals(SWTResourceManager.getImage(ModelsDisplayView.class,
									"/cosync_ui/resources/Highlighted_Conflict1_1.png"))
							|| image.equals(SWTResourceManager.getImage(ModelsDisplayView.class,
											"/cosync_ui/resources/Conflict1Resolved_HighlightedConflict2.png"))
							|| image.equals(SWTResourceManager.getImage(ModelsDisplayView.class,
													"/cosync_ui/resources/Manual_C1Resolved_OP1_Highlighted.png"))) {
						image = mainView.getImages().get(indexOf - 2);
					}
					if (image.equals(SWTResourceManager.getImage(ModelsDisplayView.class,
							"/cosync_ui/resources/UCEditsResolved.png"))) {
						conflictsListView.addTreeElement(0);
					}
					if (image.equals(SWTResourceManager.getImage(ModelsDisplayView.class,
							"/cosync_ui/resources/Conflict1Resolved_GEAdded.png")) || image.equals(SWTResourceManager.getImage(ModelsDisplayView.class,
							"/cosync_ui/resources/Manual_Conflict1Resolved_OP1.png"))) {
						conflictsListView.addTreeElement(1);
					}
					ModelsDisplayView.setModelImage(image);
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
        new ToolItem(bar, SWT.SEPARATOR);
        ToolItem preferencesToolItem = new ToolItem(bar, SWT.PUSH);
        preferencesToolItem.setImage(SWTResourceManager.getImage(CoSync_UI.class, "/cosync_ui/resources/settings.png"));
        preferencesToolItem.setText("Set Preferences");
        preferencesToolItem.setToolTipText("Set User Preferences");
        preferencesToolItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				userOptionsMenu.show();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
        new ToolItem(bar, SWT.SEPARATOR);
        ToolItem acceptSolToolItem = new ToolItem(bar, SWT.PUSH);
        acceptSolToolItem.setImage(SWTResourceManager.getImage(CoSync_UI.class, "/cosync_ui/resources/checkmark.png"));
        acceptSolToolItem.setText("Accept Solution");
        acceptSolToolItem.setToolTipText("Select to accept solution");
        acceptSolToolItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				String currImagePath = ModelsDisplayView.getCurrentImagePath();
				Image image = ModelsDisplayView.getCurrentImage();
				MessageDialog dialog;
				if(currImagePath.equals("/cosync_ui/resources/Conflict2Resolved_GEAdded.png")) {
					dialog = new MessageDialog(shell, "Message", null,
						    "Models are consistent. \nSolution accepted.", MessageDialog.INFORMATION, new String[] { "Ok" }, 0);
					prevToolItem.setEnabled(false);
				} else {
					dialog = new MessageDialog(shell, "Message", null,
						    "Models are not yet consistent!", MessageDialog.WARNING, new String[] { "Ok" }, 0);
				}
				dialog.setDefaultImage(SWTResourceManager.getImage(CoSync_UI.class, "/cosync_ui/resources/synch_synch.png"));
				dialog.open();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
        
        new ToolItem(bar, SWT.SEPARATOR);
        ToolItem consCheckToolItem = new ToolItem(bar, SWT.PUSH);
        consCheckToolItem.setImage(SWTResourceManager.getImage(CoSync_UI.class, "/cosync_ui/resources/sync.ico"));
        consCheckToolItem.setText("Check Consistency");
        consCheckToolItem.setToolTipText("Check if models are consistent.");
        consCheckToolItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Image image = ModelsDisplayView.getCurrentImage();
				MessageDialog dialog;
				if(image != null && image.equals(SWTResourceManager.getImage(ModelsDisplayView.class,
						"/cosync_ui/resources/Conflict2Resolved_GEAdded.png"))) {
					dialog = new MessageDialog(shell, "Message", null,
						    "Models are consistent.", MessageDialog.INFORMATION, new String[] { "Ok" }, 0);
				} else {
					dialog = new MessageDialog(shell, "Message", null,
						    "Models are not consistent!", MessageDialog.WARNING, new String[] { "Ok" }, 0);
				}
				dialog.setDefaultImage(SWTResourceManager.getImage(CoSync_UI.class, "/cosync_ui/resources/synch_synch.png"));
				dialog.open();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
	}
	
	/** Menu bar */
	public void createMenu(Shell shell) {
		Menu menuBar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuBar);
		
		/** File Menu */
		MenuItem fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		fileMenuHeader.setText("&File");
		Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
		fileMenuHeader.setMenu(fileMenu);

		MenuItem fileSaveCurrentItem = new MenuItem(fileMenu, SWT.PUSH);
		fileSaveCurrentItem.setText("&Save Current State \tCtrl+S");
		fileSaveCurrentItem.setAccelerator(SWT.CTRL + 'S');
		fileSaveCurrentItem.setImage(SWTResourceManager.getImage(CoSync_UI.class, "/cosync_ui/resources/save_edit11.png"));
		fileSaveCurrentItem.setToolTipText(ToolTips.SAVE_CURRENT_MENU.getDescription(userOptionsManager.getToolTipSetting()));
		fileSaveCurrentItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				new ModelLocationDialog("model save location", "/cosync_ui/instances/", "current_state", "png", "Save",
						saveLocations -> {
							try {
								mainView.saveModels(saveLocations);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}).build(getDisplay().getActiveShell());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		MenuItem filePreviousItem = new MenuItem(fileMenu, SWT.PUSH);
		filePreviousItem.setText("&Previous State \tAlt+Left");
		filePreviousItem.setAccelerator(SWT.ALT | SWT.ARROW_LEFT);
		filePreviousItem.setImage(SWTResourceManager.getImage(CoSync_UI.class, "/cosync_ui/resources/backward_nav.png"));
		filePreviousItem.setToolTipText(ToolTips.PREVIOUS_STATE_MENU.getDescription(userOptionsManager.getToolTipSetting()));
		filePreviousItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				if(mainView.getImages() != null && mainView.getImages().size() > 1) {
					Image image = mainView.getImages().get(mainView.getImages().size() - 2);
					ModelsDisplayView.setModelImage(image);
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		
		MenuItem fileCompareItem = new MenuItem(fileMenu, SWT.PUSH);
		fileCompareItem.setText("&Compare View");
		fileCompareItem.setToolTipText(ToolTips.COMPARE_VIEW_MENU.getDescription(userOptionsManager.getToolTipSetting()));
		fileCompareItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				if(ModelsDisplayView.getCurrentImagePath().equals("/cosync_ui/resources/Conflicting.png")) {
					MessageDialog dialog = new MessageDialog(shell, "Message", null,
						    "No changes from the initial state. Nothing to compare.", MessageDialog.INFORMATION, new String[] { "Ok" }, 0);
					dialog.open();
					return;
				}
				mainView.setVisible(!mainView.isVisible());
				if(!mainView.isVisible()) {
					compareView = new CompareView(shell, SWT.Resize, true, null);
					compareView.setLayoutData(new GridData(GridData.FILL_BOTH));
					compareView.setVisible(true);
					ConflictsListView.disableTreeView();
				} else if (mainView.isVisible()){
					compareView.dispose();
					ConflictsListView.enableTreeView();
				}
				shell.layout(true, true);
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		new MenuItem(fileMenu, SWT.SEPARATOR);
		MenuItem fileExitItem = new MenuItem(fileMenu, SWT.PUSH);
		fileExitItem.setText("&Exit");
		fileExitItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				shell.dispose();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		/** Help Menu */
		MenuItem helpMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		helpMenuHeader.setText("&Help");
		Menu helpMenu = new Menu(shell, SWT.DROP_DOWN);
		helpMenuHeader.setMenu(helpMenu);
		
		MenuItem helpTutorialItem = new MenuItem(helpMenu, SWT.PUSH);
		helpTutorialItem.setText("&Tutorial");
		
		MenuItem helpAboutItem = new MenuItem(helpMenu, SWT.PUSH);
		helpAboutItem.setText("&About");
		helpAboutItem.setImage(SWTResourceManager.getImage(CoSync_UI.class, "/cosync_ui/resources/message_info.png"));
		helpAboutItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new AboutDialog(shell);				
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		userOptionsMenu = new UserOptionsMenu(userOptionsManager, mainView);
		userOptionsMenu.build(getShell());
	}
	
	public static void enablePreviousMenu() {
		prevToolItem.setEnabled(true);
	}
	
	public static void disablePreviousMenu() {
		prevToolItem.setEnabled(false);
	}
	
	public static void hideMainView() {
		mainView.setVisible(!mainView.isVisible());
	}
}
