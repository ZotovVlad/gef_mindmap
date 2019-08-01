package com.itemis.gef.tutorial.mindmap.policies;

import java.util.ArrayList;

import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.handlers.IHandler;
import org.eclipse.gef.mvc.fx.parts.IRootPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.policies.DeletionPolicy;

import com.itemis.gef.tutorial.mindmap.parts.MindMapConnectionPart;
import com.itemis.gef.tutorial.mindmap.parts.MindMapNodePart;

import javafx.scene.Node;

public class DeleteNodeOnHandleKeyHandler extends AbstractHandler implements IHandler {

	public void delete() {
		// TODO Auto-generated method stub
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

}
