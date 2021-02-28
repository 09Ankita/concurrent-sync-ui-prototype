package cosync_ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class AboutDialog {

	public AboutDialog(Shell parentShell) {
		build(parentShell);	
	}

	/**
	 * Create contents of the dialog.
	 */
	private void build(Shell parentShell) {
		Shell dialogShell = new Shell(parentShell, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM | SWT.ON_TOP | SWT.RESIZE);
		dialogShell.setMinimumSize(new Point(180, 120));

		dialogShell.setLayout(new GridLayout());
		dialogShell.setText("About");
		dialogShell.setImage(SWTResourceManager.getImage(CoSync_UI.class, "/cosync_ui/resources/synch_synch.png"));

		Composite panel = new Composite(dialogShell, SWT.NONE);
		panel.setLayoutData(new GridData(GridData.FILL_BOTH));
		panel.setLayout(new GridLayout());
		
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date date = new Date(System.currentTimeMillis());
		String time = dateFormat.format(date).toString();
		Text aboutText = new Text(panel, SWT.BORDER | SWT.READ_ONLY | SWT.CENTER | SWT.MULTI);
		aboutText.setEnabled(false);
		aboutText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		aboutText.setLayoutData(new GridData(GridData.FILL_BOTH));
		aboutText.setText("Author: Ankita Srivastava \nRelease Date: " + time + " \nVersion: 1.0");
		aboutText.setMessage("About");
		
//		panel.pack();
		dialogShell.pack();

		dialogShell.open();
	}

}
