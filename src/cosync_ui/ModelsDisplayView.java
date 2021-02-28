package cosync_ui;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import cosync_ui.api.ToolTips;
import cosync_ui.api.UserOptionsManager;

/** This class creates the view to display models, statistics and legend.
 * @author Ankita Srivastava
*/
public class ModelsDisplayView extends Composite {

	private ScrolledComposite modelImageScroller;
	private static Label modelImageContainer;
	private static Button resolveUCEditsButton;
	private UserOptionsManager userOptionsManager;
	private static Image previousStateImage;
	private static String currentImagePath;
	private static List<Image> images = new ArrayList<Image>();
	private static Image currentStateImage;
	private static Label nodeStatsimageContainer;
	private static Label legendImageContainer;
	private static Label elemStatsimageContainer;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public ModelsDisplayView(Composite parent, int style) {
		super(parent, style);
		userOptionsManager = new UserOptionsManager();
		build();
	}

	public static void setModelImage(Image image) {
		previousStateImage = modelImageContainer.getImage();
		modelImageContainer.setImage(image);
		currentStateImage = modelImageContainer.getImage();
		CoSync_UI.enablePreviousMenu();
		if (image
				.equals(SWTResourceManager.getImage(ModelsDisplayView.class, "/cosync_ui/resources/Conflicting.png"))) {
			CoSync_UI.disablePreviousMenu();
			resolveUCEditsButton.setEnabled(true);
		}
	}

	public static void setElemStatsImage(Image image) {
		elemStatsimageContainer.setImage(image);
	}

	public static void setNodeStatsImage(Image image) {
		nodeStatsimageContainer.setImage(image);
	}

	public static void setLegendImage(Image image) {
		legendImageContainer.setImage(image);
	}

	public static Image getPreviousImage() {
		return previousStateImage;
	}

	public static Image getCurrentImage() {
		return currentStateImage;
	}

	public static void setCurrentImagePath(String imagePath) {
		currentImagePath = imagePath;
	}

	public static String getCurrentImagePath() {
		return currentImagePath;
	}

	private ModelsDisplayView build() {
		setLayout(new GridLayout());

		SashForm mainSashForm = new SashForm(this, SWT.HORIZONTAL);
		mainSashForm.setSashWidth(1);
		mainSashForm.setLayoutData(new GridData(GridData.FILL_BOTH));
		mainSashForm.setLayout(new GridLayout());
		mainSashForm.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));

		/**  Model Image Container */
		modelImageScroller = new ScrolledComposite(mainSashForm, SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.SINGLE);
		modelImageScroller.setLayoutData(new GridData(GridData.FILL_BOTH));
		modelImageScroller.setExpandHorizontal(true);
		modelImageScroller.setExpandVertical(true);
		modelImageScroller.setAlwaysShowScrollBars(true);

		modelImageContainer = new Label(modelImageScroller, SWT.BORDER | SWT.CENTER | SWT.H_SCROLL | SWT.V_SCROLL);
		modelImageContainer.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		modelImageContainer.setLayoutData(new GridData(GridData.FILL_BOTH));
		Image image = SWTResourceManager.getImage(ModelsDisplayView.class, "/cosync_ui/resources/Conflicting.png");
		setCurrentImagePath("/cosync_ui/resources/Conflicting.png");
		modelImageScroller.setMinSize(image.getBounds().width, image.getBounds().height);
		modelImageContainer.setImage(image);
		storeImages(image);
		// imageContainer.pack();
		modelImageScroller.setContent(modelImageContainer);
		
		/** Right Panel - Statistics and Legend */
		SashForm rightPanelSashForm = new SashForm(mainSashForm, SWT.VERTICAL);
		rightPanelSashForm.setLayoutData(new GridData(GridData.FILL_BOTH));
		rightPanelSashForm.setLayout(new GridLayout());
		rightPanelSashForm.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));

		Group statsGroup = new Group(rightPanelSashForm, SWT.NONE);
		statsGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
		statsGroup.setLayout(new GridLayout());
		statsGroup.setText("Statistics");

		/** Elements Types Statistics Image Container */
		ScrolledComposite elemStatsimageScroller = new ScrolledComposite(statsGroup,
				SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.SINGLE);
		elemStatsimageScroller.setLayoutData(new GridData(GridData.FILL_BOTH));
		elemStatsimageScroller.setExpandHorizontal(true);
		elemStatsimageScroller.setExpandVertical(true);
		elemStatsimageScroller.setAlwaysShowScrollBars(true);
		elemStatsimageContainer = new Label(elemStatsimageScroller,
				SWT.BORDER | SWT.CENTER | SWT.H_SCROLL | SWT.V_SCROLL);
		elemStatsimageContainer.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		elemStatsimageContainer.setLayoutData(new GridData(GridData.FILL_BOTH));
		Image elemStatsimage = SWTResourceManager.getImage(ModelsDisplayView.class,
				"/cosync_ui/resources/ElementStats_Init.png");
		elemStatsimageScroller.setMinSize(elemStatsimage.getBounds().width, elemStatsimage.getBounds().height);
		elemStatsimageContainer.setImage(elemStatsimage);
		elemStatsimageScroller.setContent(elemStatsimageContainer);

		Label separator = new Label(statsGroup, SWT.SEPARATOR | SWT.HORIZONTAL | SWT.CENTER);
		GridData gd_separator = new GridData(GridData.FILL_BOTH);
		gd_separator.grabExcessVerticalSpace = false;
		gd_separator.grabExcessHorizontalSpace = false;
		separator.setLayoutData(gd_separator);

		/** Node Types Stats Image Container */
		ScrolledComposite nodeStatsImageScroller = new ScrolledComposite(statsGroup,
				SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.SINGLE);
		nodeStatsImageScroller.setLayoutData(new GridData(GridData.FILL_BOTH));
		nodeStatsImageScroller.setExpandHorizontal(true);
		nodeStatsImageScroller.setExpandVertical(true);
		nodeStatsImageScroller.setAlwaysShowScrollBars(true);
		nodeStatsimageContainer = new Label(nodeStatsImageScroller,
				SWT.BORDER | SWT.CENTER | SWT.H_SCROLL | SWT.V_SCROLL);
		nodeStatsimageContainer.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		nodeStatsimageContainer.setLayoutData(new GridData(GridData.FILL_BOTH));
		Image nodeStatsImage = SWTResourceManager.getImage(ModelsDisplayView.class,
				"/cosync_ui/resources/NodeStats_Init.png");
		nodeStatsImageScroller.setMinSize(nodeStatsImage.getBounds().width, nodeStatsImage.getBounds().height);
		nodeStatsimageContainer.setImage(nodeStatsImage);
		nodeStatsImageScroller.setContent(nodeStatsimageContainer);

		/** Legend Image Container */
		ScrolledComposite legendImageScroller = new ScrolledComposite(rightPanelSashForm,
				SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.SINGLE);
		legendImageScroller.setLayoutData(new GridData(GridData.FILL_BOTH));
		legendImageScroller.setExpandHorizontal(true);
		legendImageScroller.setExpandVertical(true);
		legendImageScroller.setAlwaysShowScrollBars(true);
		legendImageContainer = new Label(legendImageScroller, SWT.BORDER | SWT.CENTER | SWT.H_SCROLL | SWT.V_SCROLL);
		legendImageContainer.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		legendImageContainer.setLayoutData(new GridData(GridData.FILL_BOTH));
		Image legendImage = SWTResourceManager.getImage(ModelsDisplayView.class,
				"/cosync_ui/resources/New_Legend_Init.png");
		legendImageScroller.setMinSize(legendImage.getBounds().width, legendImage.getBounds().height);
		legendImageContainer.setImage(legendImage);
		legendImageScroller.setContent(legendImageContainer);

		rightPanelSashForm.setWeights(new int[] { 70, 30 });

		mainSashForm.setWeights(new int[] { 75, 25 });
		
		/** Buttons */
		Composite buttonRow = new Composite(this, SWT.NONE);
		buttonRow.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		buttonRow.setLayout(new GridLayout(4, false));

		resolveUCEditsButton = new Button(buttonRow, SWT.PUSH);
		resolveUCEditsButton.setText("Resolve Uncontroversial Edits");
		resolveUCEditsButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent pSelectionEvent) {
				setCurrentImagePath("/cosync_ui/resources/UCEditsResolved.png");
				setModelImage(SWTResourceManager.getImage(ModelsDisplayView.class,
						"/cosync_ui/resources/UCEditsResolved.png"));
				setElemStatsImage(SWTResourceManager.getImage(ModelsDisplayView.class,
						"/cosync_ui/resources/ElementsStats_UCResolved.png"));
				ModelsDisplayView.setLegendImage(
						SWTResourceManager.getImage(ModelsDisplayView.class, "/cosync_ui/resources/Legend_with_IndDelta.png"));
				storeImages(SWTResourceManager.getImage(ModelsDisplayView.class,
						"/cosync_ui/resources/UCEditsResolved.png"));
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
		File dest = new File(
				"C:\\WS_CS_UIPrototype\\cosync_ui\\src\\cosync_ui\\instances\\current_state_" + time + ".png");
		try {
			FileUtils.copyFile(source, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateToolTips() {
		modelImageContainer.setToolTipText(
				ToolTips.MATCHDISPLAY_IMAGECONTAINER.getDescription(userOptionsManager.getToolTipSetting()));
		resolveUCEditsButton.setToolTipText(
				ToolTips.MODEL_UCEDITRESOLVE_BUTTON.getDescription(userOptionsManager.getToolTipSetting()));
		elemStatsimageContainer.setToolTipText("Statistic of number of Source, Taget and Correspondence elements.");
		nodeStatsimageContainer.setToolTipText("Statistic of type of nodes.");
		legendImageContainer.setToolTipText("Legend - explaining the symbols used.");
	}

	@Override
	protected void checkSubclass() {
	}

	public void refreshCorrCheckSelection(boolean selection) {
		if (selection == false) {
			setCurrentImagePath("/cosync_ui/resources/Conflicting_NoCorrsEdge.png");
			modelImageContainer.setImage(SWTResourceManager.getImage(ModelsDisplayView.class,
					"/cosync_ui/resources/Conflicting_NoCorrsEdge.png"));
			elemStatsimageContainer.setImage(SWTResourceManager.getImage(ModelsDisplayView.class,
					"/cosync_ui/resources/ElementsStats_NoCorrs.png"));
			legendImageContainer.setImage(
					SWTResourceManager.getImage(ModelsDisplayView.class, "/cosync_ui/resources/New_Legend_NoCorrs.png"));
		} else {
			setCurrentImagePath("/cosync_ui/resources/Conflicting.png");
			modelImageContainer.setImage(
					SWTResourceManager.getImage(ModelsDisplayView.class, "/cosync_ui/resources/Conflicting.png"));
			elemStatsimageContainer.setImage(
					SWTResourceManager.getImage(ModelsDisplayView.class, "/cosync_ui/resources/ElementStats_Init.png"));
			legendImageContainer.setImage(
					SWTResourceManager.getImage(ModelsDisplayView.class, "/cosync_ui/resources/New_Legend_Init.png"));
		}
	}

	public void refreshTRGCheckSelection(boolean selection) {
		if (selection == false) {
			setCurrentImagePath("/cosync_ui/resources/Conflicting_NoTRGNode.png");
			modelImageContainer.setImage(SWTResourceManager.getImage(ModelsDisplayView.class,
					"/cosync_ui/resources/Conflicting_NoTRGNode.png"));
		} else {
			setCurrentImagePath("/cosync_ui/resources/Conflicting.png");
			modelImageContainer.setImage(
					SWTResourceManager.getImage(ModelsDisplayView.class, "/cosync_ui/resources/Conflicting.png"));
			elemStatsimageContainer.setImage(
					SWTResourceManager.getImage(ModelsDisplayView.class, "/cosync_ui/resources/ElementStats_Init.png"));
			legendImageContainer.setImage(
					SWTResourceManager.getImage(ModelsDisplayView.class, "/cosync_ui/resources/New_Legend_Init.png"));
		}
	}

	public void refreshSRCCheckSelection(boolean selection) {
		if (selection == false) {
			setCurrentImagePath("/cosync_ui/resources/Conflicting_NoSRCNode.png");
			modelImageContainer.setImage(SWTResourceManager.getImage(ModelsDisplayView.class,
					"/cosync_ui/resources/Conflicting_NoSRCNode.png"));
		} else {
			setCurrentImagePath("/cosync_ui/resources/Conflicting.png");
			modelImageContainer.setImage(
					SWTResourceManager.getImage(ModelsDisplayView.class, "/cosync_ui/resources/Conflicting.png"));
			elemStatsimageContainer.setImage(
					SWTResourceManager.getImage(ModelsDisplayView.class, "/cosync_ui/resources/ElementStats_Init.png"));
			legendImageContainer.setImage(
					SWTResourceManager.getImage(ModelsDisplayView.class, "/cosync_ui/resources/New_Legend_Init.png"));
		}
	}

	public void refreshEdgeLabelSelection(boolean selection) {
		if (!selection) {
			modelImageContainer.setImage(
					SWTResourceManager.getImage(ModelsDisplayView.class, "/cosync_ui/resources/No_EdgeLabels.png"));
		} else {
			modelImageContainer.setImage(
					SWTResourceManager.getImage(ModelsDisplayView.class, "/cosync_ui/resources/Conflicting.png"));
		}
	}

	static public void storeImages(Image image) {
		if (!images.contains(image)) {
			images.add(image);
		}
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

}
