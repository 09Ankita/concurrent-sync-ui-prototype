package cosync_ui;

import java.util.function.Consumer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ModelLocationDialog {
	private String selectorHints;
	private String defaultLocationData;
	private Consumer<String[]> buttonAction;
	private String defaultName;
	private String fileExtension;

	public ModelLocationDialog(String selectorHints, String defaultLocationData,  String defaultName,
			String fileExtension, String pButtonText, Consumer<String[]> buttonAction) {
		this.selectorHints = selectorHints;
		this.defaultLocationData = defaultLocationData;
		this.buttonAction = buttonAction;
		this.defaultName = defaultName;
		this.fileExtension = fileExtension;
	}

	public void build(Shell parentShell) {
		Shell dialogShell = new Shell(parentShell, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM | SWT.ON_TOP);

		dialogShell.setLayout(new GridLayout());
		dialogShell.setText("Save Models");

		Composite panel = new Composite(dialogShell, SWT.NONE);
		panel.setLayoutData(new GridData(GridData.FILL_BOTH));
		panel.setLayout(new GridLayout());

		LocationSelector srcSelector = new LocationSelector(panel, selectorHints, defaultLocationData,
				defaultName, fileExtension).build(dialogShell);
		srcSelector.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Button actionButton = new Button(panel, SWT.PUSH);
		actionButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		actionButton.setText("Save");
		actionButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent pEvent) {
				buttonAction.accept(new String[] { srcSelector.getFileLocation()});
				dialogShell.close();
				dialogShell.dispose();
			}
		});

		panel.pack();
		dialogShell.pack();

		dialogShell.open();
	}

	private static class LocationSelector extends Composite {

		private String selectorHint;
		private String defaultDirectory;
		private String defaultName;
		private String fileExtension;

		private Text location;

		private LocationSelector(Composite parent, String selectorHint, String defaultDirectory, String defaultName,
				String fileExtension) {
			super(parent, SWT.NONE);
			this.selectorHint = selectorHint;
			this.defaultDirectory = defaultDirectory;
			this.defaultName = defaultName;
			this.fileExtension = fileExtension;
		}

		private LocationSelector build(Shell parentShell) {
			setLayout(new GridLayout(2, false));

			location = new Text(this, SWT.SINGLE);
			location.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			location.setText(defaultDirectory + defaultName + "." + fileExtension);
			location.setMessage(selectorHint);

			Button selectButton = new Button(this, SWT.PUSH);
			selectButton.setText("Change...");
			selectButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent pEvent) {
					FileDialog dialog = new FileDialog(parentShell, SWT.SAVE);
					dialog.setFilterPath(defaultDirectory);
					dialog.setFileName(defaultName);
					dialog.setFilterExtensions(new String[] { fileExtension });
					dialog.setFilterNames(new String[] { fileExtension });
					dialog.setOverwrite(true);

					String result = dialog.open();
					if (result != null)
						location.setText(result + "." + fileExtension);
				}
			});

			return this;
		}

		private String getFileLocation() {
			return location.getText();
		}
	}
}
