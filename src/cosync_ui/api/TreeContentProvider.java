package cosync_ui.api;

import java.util.List;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.graphics.Image;

public class TreeContentProvider implements ITreeContentProvider, ILabelProvider {

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
		// TODO Auto-generated method stub
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

}
