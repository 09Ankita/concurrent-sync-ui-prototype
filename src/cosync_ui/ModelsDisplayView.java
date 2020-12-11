package cosync_ui;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import cosync_ui.api.ToolTips;
import cosync_ui.api.UserOptionsManager;

public class ModelsDisplayView extends Composite {

	private ScrolledComposite imageScroller;
	private static Label imageContainer;
	private Button resolveUCEditsButton;
	private UserOptionsManager userOptionsManager;
	private static Image previousStateImage; 
	private static String currentImagePath;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ModelsDisplayView(Composite parent, int style) {
		super(parent, style);
		userOptionsManager = new UserOptionsManager();
		build();
	}
	
	public static void setImage(Image image) {
		previousStateImage = imageContainer.getImage();
		imageContainer.setImage(image);
	}
	
	public static Image getPreviousImage() {
		return previousStateImage;
	}
	
	public static void setCurrentImagePath(String imagePath) {
		currentImagePath = imagePath;
	}
	
	public static String getCurrentImagePath() {
		return currentImagePath;
	}
	
	private ModelsDisplayView build() {		
		setLayout(new GridLayout());

		imageScroller = new ScrolledComposite(this, SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.SINGLE);
		imageScroller.setLayoutData(new GridData(GridData.FILL_BOTH));
		imageScroller.setExpandHorizontal(true);
		imageScroller.setExpandVertical(true);
		imageScroller.setAlwaysShowScrollBars(true);

		imageContainer = new Label(imageScroller, SWT.BORDER | SWT.CENTER);
		imageContainer.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		imageContainer.setLayoutData(new GridData(GridData.FILL_BOTH));
		Image image = SWTResourceManager.getImage(ModelsDisplayView.class, "/cosync_ui/resources/Conflicting.png");
		setCurrentImagePath("/cosync_ui/resources/Conflicting.png");
		imageScroller.setMinSize(image.getBounds().width, image.getBounds().height);
		imageContainer.setImage(image);
		//imageContainer.pack();

		imageScroller.setContent(imageContainer);
		Composite buttonRow = new Composite(this, SWT.NONE);
		buttonRow.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		buttonRow.setLayout(new GridLayout(4, false));
		
		resolveUCEditsButton = new Button(buttonRow, SWT.PUSH);
		resolveUCEditsButton.setText("Resolve Uncontroversial Edits");
		new Label(buttonRow, SWT.NONE);
		new Label(buttonRow, SWT.NONE);
		new Label(buttonRow, SWT.NONE);
		resolveUCEditsButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent pSelectionEvent) {
				setCurrentImagePath("/cosync_ui/resources/UCEditsResolved.png");
				setImage(SWTResourceManager.getImage(ModelsDisplayView.class, "/cosync_ui/resources/UCEditsResolved.png"));
				ConflictsListView.enableTreeView();
				resolveUCEditsButton.setEnabled(false);
			}
		});
		
		this.updateToolTips();
		pack();
		return this;
	}

	protected void saveModels(String[] pLocations) throws IOException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd-HH_mm_ss");
		Date date = new Date(System.currentTimeMillis());
		String time = dateFormat.format(date).toString();
		String path = "C:\\WS_CS_UIPrototype\\cosync_ui\\src\\" + getCurrentImagePath(); 
		File source = new File(path);
		File dest = new File("C:\\WS_CS_UIPrototype\\cosync_ui\\src\\cosync_ui\\instances\\current_state_" + time +".png");
		try {
		    FileUtils.copyFile(source, dest);
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	public void updateToolTips() {
		imageContainer.setToolTipText(
				ToolTips.MATCHDISPLAY_IMAGECONTAINER.getDescription(userOptionsManager.getToolTipSetting()));
		resolveUCEditsButton.setToolTipText(
				ToolTips.MODEL_UCEDITRESOLVE_BUTTON.getDescription(userOptionsManager.getToolTipSetting()));
		
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	public void refreshCorrCheckSelection(boolean selection) {
		if(selection == false) {
			setCurrentImagePath("/cosync_ui/resources/Conflicting_WithoutCorrs_SRCFirst.png");
			setImage(SWTResourceManager.getImage(ModelsDisplayView.class, "/cosync_ui/resources/Conflicting_WithoutCorrs_SRCFirst.png"));
		}else {
			setCurrentImagePath("/cosync_ui/resources/Conflicting.png");
			setImage(SWTResourceManager.getImage(ModelsDisplayView.class, "/cosync_ui/resources/Conflicting.png"));
		}		
	}

}
