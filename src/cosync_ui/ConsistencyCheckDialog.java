package cosync_ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

public class ConsistencyCheckDialog {

	public ConsistencyCheckDialog(Shell parentShell, String message) {
		build(parentShell, message);	
	}

	/**
	 * Create contents of the dialog.
	 */
	private void build(Shell parentShell, String message) {
		Shell dialogShell = new Shell(parentShell, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM | SWT.ON_TOP | SWT.RESIZE);
		dialogShell.setMinimumSize(new Point(180, 120));

		dialogShell.setLayout(new GridLayout());
		dialogShell.setText("Message");
		dialogShell.setImage(SWTResourceManager.getImage(CoSync_UI.class, "/cosync_ui/resources/synch_synch.png"));

		Composite panel = new Composite(dialogShell, SWT.NONE);
		panel.setLayoutData(new GridData(GridData.FILL_BOTH));
		panel.setLayout(new GridLayout());
		
		Label messageLabel = new Label(panel, SWT.NONE);
		//messageLabel.setText("Models are not consistent!");
		messageLabel.setText(message);
		
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
				dialogShell.setVisible(false);
			}
		});
		
		dialogShell.pack();

		dialogShell.open();
	}

}
