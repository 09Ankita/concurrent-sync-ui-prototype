package cosync_ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;

import cosync_ui.api.ToolTips;
import cosync_ui.api.UserOptionsManager;
import cosync_ui.api.UserOptionsManager.VisualizationLabelOptions;

public class UserOptionsMenu {

	private UserOptionsManager userOptionsManager;
	private ModelsDisplayView modelDisplayView;

	private Shell menuShell;
	private Button displayCreatedElementsButton;
	private Button displayCorrButton;
	private Button displaySrcButton;
	private Button displayTrgButton;

	private Button edgeLabelCeckBox;
	private Button edgeLabelAbbrRadioButton;
	private Button edgeLabelFullRadioButton;

	private Button corrLabelCeckBox;
	private Button corrLabelAbbrRadioButton;
	private Button corrLabelFullRadioButton;

	private Button nodeLabelCeckBox;
	private Button nodeLabelAbbrRadioButton;
	private Button nodeLabelFullRadioButton;

	private Scale neighborhoodScale;

	public UserOptionsMenu(UserOptionsManager userOptionsManager, ModelsDisplayView modelDisplayView) {
		this.userOptionsManager = userOptionsManager;
		this.modelDisplayView = modelDisplayView;
	}

	public void build(Shell parentShell) {
		menuShell = new Shell(parentShell, SWT.DIALOG_TRIM | SWT.ON_TOP);
		menuShell.setText("User Options");
		menuShell.setLayout(new GridLayout());
		menuShell.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent pEvent) {
				pEvent.doit = false;
				menuShell.setVisible(false);
			}
		});

		Composite panel = new Composite(menuShell, SWT.NONE);
		panel.setLayoutData(new GridData(GridData.FILL_BOTH));
		panel.setLayout(new GridLayout());

		Group graphElementsGroup = new Group(panel, SWT.NONE);
		graphElementsGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
		graphElementsGroup.setLayout(new GridLayout());
		graphElementsGroup.setText("Show Graph Elements");

		displayCreatedElementsButton = new Button(graphElementsGroup, SWT.CHECK);
		displayCreatedElementsButton.setText("Created Elements");
		displayCreatedElementsButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		displayCreatedElementsButton.setSelection(userOptionsManager.displayFullRuleForMatches());
		displayCreatedElementsButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent pSelectionEvent) {
				userOptionsManager.setDisplayFullRuleForMatches(displayCreatedElementsButton.getSelection());
				//modelDisplayView.refresh();
			}
		});

		displaySrcButton = new Button(graphElementsGroup, SWT.CHECK);
		displaySrcButton.setText("SRC Elements");
		displaySrcButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		displaySrcButton.setSelection(userOptionsManager.displaySrcContextForMatches());
		displaySrcButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent pSelectionEvent) {
				userOptionsManager.setDisplaySrcContextForMatches(displaySrcButton.getSelection());
				//modelDisplayView.refresh();
			}
		});

		displayTrgButton = new Button(graphElementsGroup, SWT.CHECK);
		displayTrgButton.setText("TRG Elements");
		displayTrgButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		displayTrgButton.setSelection(userOptionsManager.displayTrgContextForMatches());
		displayTrgButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent pSelectionEvent) {
				userOptionsManager.setDisplayTrgContextForMatches(displayTrgButton.getSelection());
				//modelDisplayView.refresh();
			}
		});

		displayCorrButton = new Button(graphElementsGroup, SWT.CHECK);
		displayCorrButton.setText("CORR Elements");
		displayCorrButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		displayCorrButton.setSelection(userOptionsManager.displayCorrContextForMatches());
		displayCorrButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent pSelectionEvent) {
				userOptionsManager.setDisplayCorrContextForMatches(displayCorrButton.getSelection());
				modelDisplayView.refreshCorrCheckSelection(displayCorrButton.getSelection());
			}
		});

		Group edgeLabelGroup = new Group(panel, SWT.NONE);
		edgeLabelGroup.setLayout(new GridLayout());
		edgeLabelGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
		edgeLabelGroup.setText("Edge Labels");

		edgeLabelCeckBox = new Button(edgeLabelGroup, SWT.CHECK);
		edgeLabelCeckBox.setText("Show Edge Labels");
		edgeLabelCeckBox.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		edgeLabelCeckBox.setSelection(userOptionsManager.getEdgeLabelVisualization() != VisualizationLabelOptions.NONE);
		edgeLabelCeckBox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent pSelectionEvent) {
				if (!edgeLabelCeckBox.getSelection()) {
					userOptionsManager.setEdgeLabelVisualization(VisualizationLabelOptions.NONE);
					// disable radio buttons
					edgeLabelAbbrRadioButton.setEnabled(false);
					edgeLabelFullRadioButton.setEnabled(false);
					//modelDisplayView.refresh();
				} else {
					// set to radio option
					edgeLabelAbbrRadioButton.setEnabled(true);
					edgeLabelFullRadioButton.setEnabled(true);
					if (edgeLabelAbbrRadioButton.getSelection()) {
						userOptionsManager.setEdgeLabelVisualization(VisualizationLabelOptions.ABBREVIATED);
					} else if (edgeLabelFullRadioButton.getSelection()) {
						userOptionsManager.setEdgeLabelVisualization(VisualizationLabelOptions.FULLNAME);
					}
					//modelDisplayView.refresh();
				}
			}
		});

		Composite edgeLabelComposite = new Composite(edgeLabelGroup, SWT.NONE);
		edgeLabelComposite.setLayout(new GridLayout(2, false));
		edgeLabelComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

		edgeLabelAbbrRadioButton = new Button(edgeLabelComposite, SWT.RADIO);
		edgeLabelAbbrRadioButton.setText("Abbreviated");
		edgeLabelAbbrRadioButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		edgeLabelAbbrRadioButton
				.setSelection(userOptionsManager.getEdgeLabelVisualization() == VisualizationLabelOptions.ABBREVIATED);
		edgeLabelAbbrRadioButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent pSelectionEvent) {
				if (edgeLabelAbbrRadioButton.getSelection()) {
					userOptionsManager.setEdgeLabelVisualization(VisualizationLabelOptions.ABBREVIATED);
					//modelDisplayView.refresh();
				}
			}
		});

		edgeLabelFullRadioButton = new Button(edgeLabelComposite, SWT.RADIO);
		edgeLabelFullRadioButton.setText("Full");
		edgeLabelFullRadioButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		edgeLabelFullRadioButton
				.setSelection(userOptionsManager.getEdgeLabelVisualization() == VisualizationLabelOptions.FULLNAME);
		edgeLabelFullRadioButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent pSelectionEvent) {
				if (edgeLabelFullRadioButton.getSelection()) {
					userOptionsManager.setEdgeLabelVisualization(VisualizationLabelOptions.FULLNAME);
					//modelDisplayView.refresh();
				}
			}
		});

		Group corrLabelGroup = new Group(panel, SWT.NONE);
		corrLabelGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
		corrLabelGroup.setLayout(new GridLayout());
		corrLabelGroup.setText("Correspondence Edge Labels");

		corrLabelCeckBox = new Button(corrLabelGroup, SWT.CHECK);
		corrLabelCeckBox.setText("Show CORR Labels");
		corrLabelCeckBox.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		corrLabelCeckBox.setSelection(userOptionsManager.getCorrLabelVisualization() != VisualizationLabelOptions.NONE);
		corrLabelCeckBox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent pSelectionEvent) {
				if (!corrLabelCeckBox.getSelection()) {
					userOptionsManager.setCorrLabelVisualization(VisualizationLabelOptions.NONE);
					// disable radio buttons
					corrLabelAbbrRadioButton.setEnabled(false);
					corrLabelFullRadioButton.setEnabled(false);
					//modelDisplayView.refresh();
				} else {
					// set to radio option
					corrLabelAbbrRadioButton.setEnabled(true);
					corrLabelFullRadioButton.setEnabled(true);
					if (corrLabelAbbrRadioButton.getSelection()) {
						userOptionsManager.setCorrLabelVisualization(VisualizationLabelOptions.ABBREVIATED);
					} else if (corrLabelFullRadioButton.getSelection()) {
						userOptionsManager.setCorrLabelVisualization(VisualizationLabelOptions.FULLNAME);
					}
					//modelDisplayView.refresh();
				}
			}
		});

		Composite corrLabelComposite = new Composite(corrLabelGroup, SWT.NONE);
		corrLabelComposite.setLayout(new GridLayout(2, false));
		corrLabelComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

		corrLabelAbbrRadioButton = new Button(corrLabelComposite, SWT.RADIO);
		corrLabelAbbrRadioButton.setText("Abbreviated");
		corrLabelAbbrRadioButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		corrLabelAbbrRadioButton
				.setSelection(userOptionsManager.getCorrLabelVisualization() == VisualizationLabelOptions.ABBREVIATED);
		corrLabelAbbrRadioButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent pSelectionEvent) {
				if (corrLabelAbbrRadioButton.getSelection()) {
					userOptionsManager.setCorrLabelVisualization(VisualizationLabelOptions.ABBREVIATED);
					//modelDisplayView.refresh();
				}
			}
		});

		corrLabelFullRadioButton = new Button(corrLabelComposite, SWT.RADIO);
		corrLabelFullRadioButton.setText("Full");
		corrLabelFullRadioButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		corrLabelFullRadioButton
				.setSelection(userOptionsManager.getCorrLabelVisualization() == VisualizationLabelOptions.FULLNAME);
		corrLabelFullRadioButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent pSelectionEvent) {
				if (corrLabelFullRadioButton.getSelection()) {
					userOptionsManager.setCorrLabelVisualization(VisualizationLabelOptions.FULLNAME);
					//modelDisplayView.refresh();
				}
			}
		});

		Group nodeLabelGroup = new Group(panel, SWT.NONE);
		nodeLabelGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
		nodeLabelGroup.setLayout(new GridLayout());
		nodeLabelGroup.setText("Node Labels");

		nodeLabelCeckBox = new Button(nodeLabelGroup, SWT.CHECK);
		nodeLabelCeckBox.setText("Show Node Labels");
		nodeLabelCeckBox.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		nodeLabelCeckBox.setSelection(userOptionsManager.getNodeLabelVisualization() != VisualizationLabelOptions.NONE);
		nodeLabelCeckBox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent pSelectionEvent) {
				if (!nodeLabelCeckBox.getSelection()) {
					userOptionsManager.setNodeLabelVisualization(VisualizationLabelOptions.NONE);
					// disable radio buttons
					nodeLabelAbbrRadioButton.setEnabled(false);
					nodeLabelFullRadioButton.setEnabled(false);
					//modelDisplayView.refresh();
				} else {
					// set to radio option
					nodeLabelAbbrRadioButton.setEnabled(true);
					nodeLabelFullRadioButton.setEnabled(true);
					if (nodeLabelAbbrRadioButton.getSelection()) {
						userOptionsManager.setNodeLabelVisualization(VisualizationLabelOptions.ABBREVIATED);
					} else if (nodeLabelFullRadioButton.getSelection()) {
						userOptionsManager.setNodeLabelVisualization(VisualizationLabelOptions.FULLNAME);
					}
					//modelDisplayView.refresh();
				}
			}
		});

		Composite nodeLabelComposite = new Composite(nodeLabelGroup, SWT.NONE);
		nodeLabelComposite.setLayout(new GridLayout(2, false));
		nodeLabelComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

		nodeLabelAbbrRadioButton = new Button(nodeLabelComposite, SWT.RADIO);
		nodeLabelAbbrRadioButton.setText("Abbreviated");
		nodeLabelAbbrRadioButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		nodeLabelAbbrRadioButton
				.setSelection(userOptionsManager.getNodeLabelVisualization() == VisualizationLabelOptions.ABBREVIATED);
		nodeLabelAbbrRadioButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent pSelectionEvent) {
				if (nodeLabelAbbrRadioButton.getSelection()) {
					userOptionsManager.setNodeLabelVisualization(VisualizationLabelOptions.ABBREVIATED);
					//modelDisplayView.refresh();
				}
			}
		});

		nodeLabelFullRadioButton = new Button(nodeLabelComposite, SWT.RADIO);
		nodeLabelFullRadioButton.setText("Full");
		nodeLabelFullRadioButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		nodeLabelFullRadioButton
				.setSelection(userOptionsManager.getNodeLabelVisualization() == VisualizationLabelOptions.FULLNAME);
		nodeLabelFullRadioButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent pSelectionEvent) {
				if (nodeLabelFullRadioButton.getSelection()) {
					userOptionsManager.setNodeLabelVisualization(VisualizationLabelOptions.FULLNAME);
					//modelDisplayView.refresh();
				}
			}
		});

		Group neighborhoodOption = new Group(panel, SWT.NONE);
		neighborhoodOption.setLayout(new GridLayout(2, false));
		neighborhoodOption.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		neighborhoodOption.setText("Graph Neighborhood");

		Label neighborhoodLabel = new Label(neighborhoodOption, SWT.NONE);
		neighborhoodLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		neighborhoodLabel.setText("Size:");

		neighborhoodScale = new Scale(neighborhoodOption, SWT.HORIZONTAL);
		neighborhoodScale.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		neighborhoodScale.setMinimum(0);
		neighborhoodScale.setPageIncrement(1);
		neighborhoodScale.setMaximum(3);
		neighborhoodScale.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent pSelectionEvent) {
				userOptionsManager.setNeighborhoodSize(neighborhoodScale.getSelection());
				//modelDisplayView.refresh();
			}
		});

		panel.pack();
		menuShell.pack();

		menuShell.open();
		menuShell.setVisible(false);
	}

	public void show() {
		displayCreatedElementsButton.setToolTipText(
				ToolTips.USEROPTION_SHOW_ELEMENTS.getDescription(userOptionsManager.getToolTipSetting()));
		displayCorrButton.setToolTipText(
				ToolTips.USEROPTION_SHOW_ELEMENTS.getDescription(userOptionsManager.getToolTipSetting()));
		displaySrcButton.setToolTipText(
				ToolTips.USEROPTION_SHOW_ELEMENTS.getDescription(userOptionsManager.getToolTipSetting()));
		displayTrgButton.setToolTipText(
				ToolTips.USEROPTION_SHOW_ELEMENTS.getDescription(userOptionsManager.getToolTipSetting()));
		corrLabelCeckBox.setToolTipText(
				ToolTips.USEROPTION_LABEL_STYLE.getDescription(userOptionsManager.getToolTipSetting()));
		corrLabelAbbrRadioButton.setToolTipText(
				ToolTips.USEROPTION_SHOW_ELEMENTS.getDescription(userOptionsManager.getToolTipSetting()));
		corrLabelFullRadioButton.setToolTipText(
				ToolTips.USEROPTION_LABEL_STYLE.getDescription(userOptionsManager.getToolTipSetting()));
		edgeLabelAbbrRadioButton.setToolTipText(
				ToolTips.USEROPTION_LABEL_STYLE.getDescription(userOptionsManager.getToolTipSetting()));
		edgeLabelCeckBox.setToolTipText(
				ToolTips.USEROPTION_LABEL_STYLE.getDescription(userOptionsManager.getToolTipSetting()));
		edgeLabelFullRadioButton.setToolTipText(
				ToolTips.USEROPTION_LABEL_STYLE.getDescription(userOptionsManager.getToolTipSetting()));
		nodeLabelAbbrRadioButton.setToolTipText(
				ToolTips.USEROPTION_LABEL_STYLE.getDescription(userOptionsManager.getToolTipSetting()));
		nodeLabelCeckBox.setToolTipText(
				ToolTips.USEROPTION_LABEL_STYLE.getDescription(userOptionsManager.getToolTipSetting()));
		nodeLabelFullRadioButton.setToolTipText(
				ToolTips.USEROPTION_LABEL_STYLE.getDescription(userOptionsManager.getToolTipSetting()));
		neighborhoodScale.setToolTipText(
				ToolTips.USEROPTION_NEIGHBORHOODSIZE.getDescription(userOptionsManager.getToolTipSetting()));
		menuShell.setVisible(true);
	}
}
