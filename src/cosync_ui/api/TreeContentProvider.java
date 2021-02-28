package cosync_ui.api;

import java.util.List;

import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wb.swt.SWTResourceManager;

import cosync_ui.ModelsDisplayView;

public class TreeContentProvider implements ITreeContentProvider, ILabelProvider, IColorProvider {

	@Override
	public Object[] getElements(Object inputElement) {
		List<Conflicts> menus = (List<Conflicts>) inputElement;
		return menus.toArray();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		Conflicts menu = (Conflicts) parentElement;
		List<Conflicts> children = menu.getChildren();
		return children == null ? null : children.toArray();
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		Conflicts menu = (Conflicts) element;
		List<Conflicts> children = menu.getChildren();
		return children != null && !children.isEmpty();
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public Image getImage(Object element) {
		Conflicts conflicts = (Conflicts) element;
		if (conflicts.getConflictName().equals("Conflicts")) {
			return SWTResourceManager.getImage(ModelsDisplayView.class, "/cosync_ui/resources/hierarchicalLayout.png");
		} else if (conflicts.getConflictName().equals("Conflict 1")
				|| conflicts.getConflictName().equals("Conflict 2")) {
			return SWTResourceManager.getImage(ModelsDisplayView.class,
					"/cosync_ui/resources/synchronize-conflict.png");
		}
		return null;
	}

	@Override
	public String getText(Object element) {
		Conflicts conflicts = (Conflicts) element;
		return conflicts.getConflictTitle();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		// ITreeContentProvider.super.dispose();
	}

	@Override
	public Color getForeground(Object element) {
//		Conflicts conflicts = (Conflicts) element;
//		if (conflicts.getConflictName().contains("SRC")) {
//			return new Color(null, 255, 255, 224);
//		}
//		if (conflicts.getConflictName().contains("TRG")) {
//			return Display.getCurrent().getSystemColor(SWT.COLOR_RED);
//		}
		return null;
	}

	@Override
	public Color getBackground(Object element) {
//		Conflicts conflicts = (Conflicts) element;
//		if (conflicts.getConflictName().contains("SRC")) {
//			return new Color(null, 255, 255, 224); // Light Yellow
//		}
//		if (conflicts.getConflictName().contains("TRG")) {
//			return new Color(null, 	255, 228, 225); // Misty Rose
//		}
		return null;
	}

}
