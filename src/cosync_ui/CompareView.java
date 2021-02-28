package cosync_ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

/** This class generates comparison view and solution preview in comparison window.
 * @author Ankita Srivastava
*/
public class CompareView extends Composite {

	private static List<Image> images = new ArrayList<Image>();

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public CompareView(Composite parent, int style, boolean isCompare, String conflictName) {
		super(parent, SWT.RESIZE);
		build(isCompare, conflictName);
	}

	private CompareView build(boolean isCompare, String conflictName) {
		setLayout(new GridLayout());

		SashForm mainSashForm = new SashForm(this, SWT.HORIZONTAL);
		mainSashForm.setSashWidth(1);
		mainSashForm.setLayoutData(new GridData(GridData.FILL_BOTH));
		mainSashForm.setLayout(new GridLayout());
		mainSashForm.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));
		
		Group currGroup = new Group(mainSashForm, SWT.NONE);
		currGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
		currGroup.setLayout(new GridLayout());
		currGroup.setText("Current State");
		
		// Current Model Image Container
		ScrolledComposite currImageScroller = new ScrolledComposite(currGroup, SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.SINGLE);
		currImageScroller.setAlwaysShowScrollBars(true);
		currImageScroller.setLayoutData(new GridData(GridData.FILL_BOTH));
		currImageScroller.setExpandHorizontal(true);
		currImageScroller.setExpandVertical(true);

		Label currImageContainer = new Label(currImageScroller, SWT.BORDER | SWT.CENTER | SWT.H_SCROLL | SWT.V_SCROLL);
		currImageContainer.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		currImageContainer.setLayoutData(new GridData(GridData.FILL_BOTH));
		Image currImage;
		if(isCompare) {
			currImage = SWTResourceManager.getImage(ModelsDisplayView.class, "/cosync_ui/resources/UCEditsResolved.png");
		} else if(!isCompare && conflictName != null &&  conflictName != "" && conflictName.equals("Conflict 1")){
			currImage = SWTResourceManager.getImage(ModelsDisplayView.class, "/cosync_ui/resources/UCEditsResolved.png");
		} else {
			currImage = SWTResourceManager.getImage(ModelsDisplayView.class, "/cosync_ui/resources/Conflict1Resolved_GEAdded.png");
		}
		currImageScroller.setMinSize(currImage.getBounds().width, currImage.getBounds().height);
		currImageContainer.setImage(currImage);
		storeImages(currImage);
		currImageScroller.setContent(currImageContainer);
		
		Group prevGroup = new Group(mainSashForm, SWT.NONE);
		prevGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
		prevGroup.setLayout(new GridLayout());
		if(isCompare) {
			prevGroup.setText("Previous State");
		}else {
			prevGroup.setText("Automated Solution Preview");
		}
		
		//Previous Image or Preview Image Container
		ScrolledComposite prevImageScroller = new ScrolledComposite(prevGroup, SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.SINGLE);
		prevImageScroller.setLayoutData(new GridData(GridData.FILL_BOTH));
		prevImageScroller.setExpandHorizontal(true);
		prevImageScroller.setExpandVertical(true);
		prevImageScroller.setAlwaysShowScrollBars(true);

		Label prevImageContainer = new Label(prevImageScroller, SWT.BORDER | SWT.CENTER | SWT.H_SCROLL | SWT.V_SCROLL);
		prevImageContainer.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		prevImageContainer.setLayoutData(new GridData(GridData.FILL_BOTH));
		Image prevImage;
		if(isCompare) {
			prevImage = SWTResourceManager.getImage(ModelsDisplayView.class, "/cosync_ui/resources/Conflicting.png");
		} else if(!isCompare && conflictName != null &&  conflictName != "" && conflictName.equals("Conflict 1")) {
			prevImage = SWTResourceManager.getImage(ModelsDisplayView.class, "/cosync_ui/resources/Conflict1Resolved_GEAdded.png");
		} else {
			prevImage = SWTResourceManager.getImage(ModelsDisplayView.class, "/cosync_ui/resources//Conflict2Resolved_GEAdded.png");
		}
			
		prevImageScroller.setMinSize(prevImage.getBounds().width, prevImage.getBounds().height);
		prevImageContainer.setImage(prevImage);
		storeImages(prevImage);
		prevImageScroller.setContent(prevImageContainer);
		
		mainSashForm.setWeights(new int[] { 50, 50 });

		pack();
		return this;
	}

	@Override
	protected void checkSubclass() {
	}

	static public void storeImages(Image image) {
		if (!images.contains(image)) {
			images.add(image);
		}
	}

}
