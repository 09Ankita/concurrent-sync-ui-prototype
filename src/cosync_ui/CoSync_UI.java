package cosync_ui;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import cosync_ui.api.UserOptionsManager;

public class CoSync_UI extends Composite {

	private ConflictsListView conflictsListView;
	private ModelsDisplayView mainView;
	private static Display display;	
	private UserOptionsMenu userOptionsMenu;
	private UserOptionsManager userOptionsManager;

	private static final int MIN_WIDTH_LEFT = 350;
	private static final int MIN_WIDTH_RIGHT = 50;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public CoSync_UI(Composite parent, int style) {
		super(parent, style);
		userOptionsManager = new UserOptionsManager();
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
				// System.out.println(width + " " + Arrays.toString(weights));
				mainSashForm.setWeights(weights);
			}
		});
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public static void main(String[] args) {
		display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout());
		shell.setText("Concurrent Synchronization");
		shell.setImage(SWTResourceManager.getImage(CoSync_UI.class, "/cosync_ui/resources/synch_synch.png"));
		CoSync_UI initScreen = new CoSync_UI(shell, SWT.NONE);
		initScreen.createMenu(shell);
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	public void createMenu(Shell shell) {
		Menu menuBar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuBar);
		
		//File Menu
		MenuItem fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		fileMenuHeader.setText("&File");
		Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
		fileMenuHeader.setMenu(fileMenu);

		MenuItem fileSaveCurrentItem = new MenuItem(fileMenu, SWT.PUSH);
		fileSaveCurrentItem.setText("&Save Current State");
		fileSaveCurrentItem.setImage(SWTResourceManager.getImage(CoSync_UI.class, "/cosync_ui/resources/save_edit.png"));
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
		filePreviousItem.setText("&Previous State");
		filePreviousItem.setImage(SWTResourceManager.getImage(CoSync_UI.class, "/cosync_ui/resources/backward_nav.png"));
		filePreviousItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				if(ModelsDisplayView.getPreviousImage() != null) {
					ModelsDisplayView.setImage(ModelsDisplayView.getPreviousImage());
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		MenuItem fileCompareItem = new MenuItem(fileMenu, SWT.PUSH);
		fileCompareItem.setText("&Compare View");
		
		new MenuItem(fileMenu, SWT.SEPARATOR);
		
		MenuItem filePreferencesItem = new MenuItem(fileMenu, SWT.PUSH);
		filePreferencesItem.setText("&Preferences...");
		filePreferencesItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				userOptionsMenu.show();
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
		
		//Resolve Menu
		MenuItem resolveMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		resolveMenuHeader.setText("&Resolve");
		Menu resolveMenu = new Menu(shell, SWT.DROP_DOWN);
		resolveMenuHeader.setMenu(resolveMenu);
		
		MenuItem resolveAcceptItem = new MenuItem(resolveMenu, SWT.PUSH);
		resolveAcceptItem.setText("&Accept Solution");

		MenuItem resolveCheckConsistencyItem = new MenuItem(resolveMenu, SWT.PUSH);
		resolveCheckConsistencyItem.setText("&Check for Consistency");
		
		//Help Menu
		MenuItem helpMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		helpMenuHeader.setText("&Help");
		Menu helpMenu = new Menu(shell, SWT.DROP_DOWN);
		helpMenuHeader.setMenu(helpMenu);

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
}
