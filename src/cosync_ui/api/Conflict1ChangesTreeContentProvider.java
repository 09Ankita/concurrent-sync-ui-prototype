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

public class Conflict1ChangesTreeContentProvider implements ITreeContentProvider, ILabelProvider, IColorProvider {

	@Override
	public Object[] getElements(Object inputElement) {
		List<Conflict1Changes> menus = (List<Conflict1Changes>) inputElement;
		return menus.toArray();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		Conflict1Changes menu = (Conflict1Changes) parentElement;
		List<Conflict1Changes> children = menu.getAction();
		return children == null ? null : children.toArray();
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		Conflict1Changes menu = (Conflict1Changes) element;
		List<Conflict1Changes> children = menu.getAction();
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
		Conflict1Changes changes = (Conflict1Changes) element;
		if (changes.getElement().contains("Edge")) {
			return SWTResourceManager.getImage(ModelsDisplayView.class, "/cosync_ui/resources/link_obj.png");
		} if (changes.getElement().contains("Node")) {
			return SWTResourceManager.getImage(ModelsDisplayView.class,
					"/cosync_ui/resources/whatsnew16.png");
		} if(changes.getElement().contains("Create")) {
			return SWTResourceManager.getImage(ModelsDisplayView.class,
					"/cosync_ui/resources/add.png");
		} if(changes.getElement().contains("Delete")) {
			return SWTResourceManager.getImage(ModelsDisplayView.class,
					"/cosync_ui/resources/delete.png");
		}
		return null;
	}

	@Override
	public String getText(Object element) {
		Conflict1Changes changes = (Conflict1Changes) element;
		return changes.getElement();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		// ITreeContentProvider.super.dispose();
	}

	@Override
	public Color getForeground(Object element) {
		return null;
	}

	@Override
	public Color getBackground(Object element) {
		return null;
	}

}
