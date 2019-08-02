package com.itemis.gef.tutorial.mindmap.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.mvc.fx.gestures.ClickDragGesture;
import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnStrokeHandler;
import org.eclipse.gef.mvc.fx.models.SelectionModel;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IRootPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.policies.DeletionPolicy;
import org.eclipse.gef.mvc.fx.viewer.IViewer;

import com.itemis.gef.tutorial.mindmap.parts.MindMapConnectionPart;
import com.itemis.gef.tutorial.mindmap.parts.MindMapNodePart;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class DeleteNodeOnHandleKeyHandler extends AbstractHandler implements IOnStrokeHandler {

	@Override
	public void abortPress() {

	}

	public void delete(KeyEvent event) {

		// determine the node part for which the delete hover handle is clicked
		MindMapNodePart node = (MindMapNodePart) getHost().getAnchoragesUnmodifiable().keySet().iterator().next();

		// initialize deletion policy
		IRootPart<? extends Node> root = getHost().getRoot();
		DeletionPolicy delPolicy = root.getAdapter(DeletionPolicy.class);
		init(delPolicy);

		// delete connections from/to the part first
		for (IVisualPart<? extends Node> a : new ArrayList<>(node.getAnchoredsUnmodifiable())) {
			if (a instanceof MindMapConnectionPart) {
				delPolicy.delete((MindMapConnectionPart) a);
			}
		}

		// and finally remove the node part
		delPolicy.delete(node);
		commit(delPolicy);
	}

	@Override
	public void finalRelease(KeyEvent event) {

	}

	@Override
	public void initialPress(KeyEvent event) {
		// determine the node part for which the delete hover handle is clicked
		MindMapNodePart node = (MindMapNodePart) getHost().getAnchoragesUnmodifiable().keySet().iterator().next();

		// initialize deletion policy
		IRootPart<? extends Node> root = getHost().getRoot();
		DeletionPolicy delPolicy = root.getAdapter(DeletionPolicy.class);
		init(delPolicy);

		// delete connections from/to the part first
		for (IVisualPart<? extends Node> a : new ArrayList<>(node.getAnchoredsUnmodifiable())) {
			if (a instanceof MindMapConnectionPart) {
				delPolicy.delete((MindMapConnectionPart) a);
			}
		}

		// and finally remove the node part
		delPolicy.delete(node);
		commit(delPolicy);

	}

	protected boolean isDelete(KeyEvent event) {
		// only delete on <DELETE> key
		if (event.getCode() != KeyCode.DELETE) {
			return false;
		}

		// prevent deletion when other drag policies are running
		ClickDragGesture tool = getHost().getRoot().getViewer().getDomain().getAdapter(ClickDragGesture.class);
		if (tool != null && getHost().getRoot().getViewer().getDomain().isExecutionTransactionOpen(tool)) {
			return false;
		}

		return true;
	}

	@Override
	public void press(KeyEvent event) {
		if (!isDelete(event)) {
			return;
		}
		// get current selection
		IViewer viewer = getHost().getRoot().getViewer();
		List<IContentPart<? extends Node>> selected = new ArrayList<>(
				viewer.getAdapter(SelectionModel.class).getSelectionUnmodifiable());

		// if no parts are selected, we do not delete anything
		if (selected.isEmpty()) {
			return;
		}

		// delete selected parts
		DeletionPolicy deletionPolicy = getHost().getRoot().getAdapter(DeletionPolicy.class);
		init(deletionPolicy);
		for (IContentPart<? extends Node> s : selected) {
			deletionPolicy.delete(s);
		}
		commit(deletionPolicy);

	}

	@Override
	public void release(KeyEvent event) {
		if (!isDelete(event)) {
			return;
		}
		// get current selection
		IViewer viewer = getHost().getRoot().getViewer();
		List<IContentPart<? extends Node>> selected = new ArrayList<>(
				viewer.getAdapter(SelectionModel.class).getSelectionUnmodifiable());

		// if no parts are selected, we do not delete anything
		if (selected.isEmpty()) {
			return;
		}

		// delete selected parts
		DeletionPolicy deletionPolicy = getHost().getRoot().getAdapter(DeletionPolicy.class);
		init(deletionPolicy);
		for (IContentPart<? extends Node> s : selected) {
			deletionPolicy.delete(s);
		}
		commit(deletionPolicy);

	}

}
