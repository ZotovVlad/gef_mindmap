package com.itemis.gef.tutorial.mindmap.policies;

import java.util.ArrayList;

import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnStrokeHandler;
import org.eclipse.gef.mvc.fx.parts.IRootPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.policies.DeletionPolicy;

import com.itemis.gef.tutorial.mindmap.parts.MindMapConnectionPart;
import com.itemis.gef.tutorial.mindmap.parts.MindMapNodePart;

import javafx.scene.Node;
import javafx.scene.input.KeyEvent;

public class DeleteNodeOnHandleKeyHandler extends AbstractHandler implements IOnStrokeHandler {

	@Override
	public void abortPress() {
		// TODO Auto-generated method stub

	}

	@Override
	public void finalRelease(KeyEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initialPress(KeyEvent event) {
		/*
		 * if (!event..isPrimaryButtonDown()) { return; }
		 */

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
	public void press(KeyEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void release(KeyEvent event) {
		// TODO Auto-generated method stub

	}

}
