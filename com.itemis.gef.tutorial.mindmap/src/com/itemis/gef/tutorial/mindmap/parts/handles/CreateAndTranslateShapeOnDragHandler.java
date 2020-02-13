package com.itemis.gef.tutorial.mindmap.parts.handles;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.geometry.planar.Dimension;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.mvc.fx.domain.IDomain;
import org.eclipse.gef.mvc.fx.gestures.ClickDragGesture;
import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnDragHandler;
import org.eclipse.gef.mvc.fx.models.SelectionModel;
import org.eclipse.gef.mvc.fx.operations.DeselectOperation;
import org.eclipse.gef.mvc.fx.parts.IBendableContentPart.BendPoint;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IRootPart;
import org.eclipse.gef.mvc.fx.parts.LayeredRootPart;
import org.eclipse.gef.mvc.fx.policies.CreationPolicy;
import org.eclipse.gef.mvc.fx.viewer.IViewer;
import org.eclipse.gef.mvc.fx.viewer.InfiniteCanvasViewer;

import com.google.common.collect.HashMultimap;
import com.itemis.gef.tutorial.mindmap.parts.MindMapConnectionPart;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class CreateAndTranslateShapeOnDragHandler extends AbstractHandler implements IOnDragHandler {

	private MindMapConnectionPart createdShapePart;
	private Map<AdapterKey<? extends IOnDragHandler>, IOnDragHandler> dragPolicies;

	@Override
	public void abortDrag() {
		if (createdShapePart == null) {
			return;
		}

		// forward event to bend target part
		if (dragPolicies != null) {
			for (IOnDragHandler dragPolicy : dragPolicies.values()) {
				dragPolicy.abortDrag();
			}
		}

		createdShapePart = null;
		dragPolicies = null;
	}

	@Override
	public void drag(MouseEvent event, Dimension delta) {
		if (createdShapePart == null) {
			return;
		}

		// forward drag events to bend target part
		if (dragPolicies != null) {
			for (IOnDragHandler dragPolicy : dragPolicies.values()) {
				dragPolicy.drag(event, delta);
			}
		}
	}

	@Override
	public void endDrag(MouseEvent e, Dimension delta) {
		if (createdShapePart == null) {
			return;
		}

		// forward event to bend target part
		if (dragPolicies != null) {
			for (IOnDragHandler dragPolicy : dragPolicies.values()) {
				dragPolicy.endDrag(e, delta);
			}
		}

		restoreRefreshVisuals(createdShapePart);
		createdShapePart = null;
		dragPolicies = null;
	}

	protected IViewer getContentViewer() {
		return getHost().getRoot().getViewer().getDomain()
				.getAdapter(AdapterKey.get(IViewer.class, IDomain.CONTENT_VIEWER_ROLE));
	}

	@Override
	public MindMapConnectionPart getHost() {
		return (MindMapConnectionPart) super.getHost();
	}

	protected Point getLocation(MouseEvent e) {
		Point2D location = ((InfiniteCanvasViewer) getHost().getRoot().getViewer()).getCanvas().getContentGroup()
				.sceneToLocal(e.getSceneX(), e.getSceneY());
		return new Point(location.getX(), location.getY());
	}

	@Override
	public void hideIndicationCursor() {
	}

	@Override
	public boolean showIndicationCursor(KeyEvent event) {
		return false;
	}

	@Override
	public boolean showIndicationCursor(MouseEvent event) {
		return false;
	}

	@Override
	public void startDrag(MouseEvent event) {
		// find model part
		IRootPart<? extends Node> contentRoot = getContentViewer().getRootPart();
		// copy the prototype
		MindMapConnectionPart copy = getHost();// .getContent();
		// determine coordinates of prototype's origin in model coordinates
		Point2D localToScene = getHost().getVisual().localToScene(0, 0);
		Point2D originInModel = ((LayeredRootPart) getContentViewer().getRootPart()).getContentLayer()
				.sceneToLocal(localToScene.getX(), localToScene.getY());
		// initially move to the originInModel
		List<BendPoint> matrix = copy.getVisualBendPoints();
		// getTransform().getMatrix();

		copy.setVisualBendPoints(matrix);
		// getTransform().setTransform(matrix[0], matrix[1], matrix[2], matrix[3],
		// originInModel.getX(),
//				originInModel.getY());

		// create copy of host's geometry using CreationPolicy from root part
		CreationPolicy creationPolicy = contentRoot.getAdapter(CreationPolicy.class);
		init(creationPolicy);
		createdShapePart = (MindMapConnectionPart) creationPolicy.create(copy, contentRoot,
				HashMultimap.<IContentPart<? extends Node>, String>create());
		commit(creationPolicy);

		// disable refresh visuals for the created shape part
		storeAndDisableRefreshVisuals(createdShapePart);

		// build operation to deselect all but the new part
		List<IContentPart<? extends Node>> toBeDeselected = new ArrayList<>(
				getContentViewer().getAdapter(SelectionModel.class).getSelectionUnmodifiable());
		toBeDeselected.remove(createdShapePart);
		DeselectOperation deselectOperation = new DeselectOperation(getContentViewer(), toBeDeselected);

		// execute on stack
		try {
			getHost().getRoot().getViewer().getDomain().execute(deselectOperation, new NullProgressMonitor());
		} catch (org.eclipse.core.commands.ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// find drag target part
		dragPolicies = createdShapePart.getAdapters(ClickDragGesture.ON_DRAG_POLICY_KEY);
		if (dragPolicies != null) {
			for (IOnDragHandler dragPolicy : dragPolicies.values()) {
				dragPolicy.startDrag(event);
			}
		}
	}

}