package com.itemis.gef.tutorial.mindmap.policies;

import java.util.ArrayList;
import java.util.Map;

import org.eclipse.gef.geometry.planar.Rectangle;
import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnStrokeHandler;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IRootPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.policies.DeletionPolicy;
import org.eclipse.gef.mvc.fx.viewer.IViewer;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.itemis.gef.tutorial.mindmap.model.MindMapConnection;
import com.itemis.gef.tutorial.mindmap.model.MindMapNode;
import com.itemis.gef.tutorial.mindmap.models.ItemCreationModel;
import com.itemis.gef.tutorial.mindmap.models.ItemCreationModel.Type;
import com.itemis.gef.tutorial.mindmap.parts.MindMapConnectionPart;
import com.itemis.gef.tutorial.mindmap.parts.MindMapNodePart;
import com.itemis.gef.tutorial.mindmap.parts.SimpleMindMapAnchorProvider;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class MindMapKeyHandler extends AbstractHandler implements IOnStrokeHandler {

	@Inject
	private Injector injector;

	@Override
	public void abortPress() {
	}

	/**
	 * add connection on field
	 */
	private void addConnection() {
		IViewer viewer = getHost().getRoot().getViewer();
		ItemCreationModel creationModel = viewer.getAdapter(ItemCreationModel.class);

		creationModel.setType(Type.Connection);
	}

	/**
	 * add node on field
	 *
	 * @param primaryStage
	 */
	private void addNode() {
		IViewer viewer = getHost().getRoot().getViewer();
		ItemCreationModel creationModel = viewer.getAdapter(ItemCreationModel.class);
		if (creationModel == null) {
			throw new IllegalStateException("No ItemCreationModel bound to viewer!");
		}

		creationModel.setType(Type.Node);

		/*
		 * IVisualPart<? extends Node> part =
		 * viewer.getRootPart().getChildrenUnmodifiable().get(0); if (part instanceof
		 * SimpleMindMapPart) {
		 *
		 * MindMapNode newNode = createMindMapNode();
		 *
		 * // GEF provides the CreatePolicy to add a new element to the model
		 * CreationPolicy creationPolicy =
		 * getHost().getRoot().getAdapter(CreationPolicy.class); init(creationPolicy);
		 * // create a IContentPart for our new model. The newly created // IContentPart
		 * is returned, but we do not need it. creationPolicy.create(newNode, part,
		 * HashMultimap.<IContentPart<? extends Node>, String>create()); // commit the
		 * creation commit(creationPolicy); }
		 *
		 * // reset creation state creationModel.setType(Type.None);
		 */
	}

	private MindMapNode createMindMapNode() {
		Scene scene = getHost().getVisual().getScene();
		MindMapNode newNode = new MindMapNode();
		newNode.setTitle("New node");
		newNode.setDescription("no description");
		newNode.setColor(Color.PALEVIOLETRED);
		newNode.setBounds(new Rectangle(scene.getWidth() / 2 - 120 / 2, scene.getHeight() / 2 - 80 / 2, 120, 80));

		return newNode;
	}

	private void deleteNode() {
		boolean flag = false;

		((MindMapNodePart) getHost()).deleteColorContent();

		// query DeletionPolicy for the removal of the host part
		IRootPart<? extends Node> root = getHost().getRoot();
		DeletionPolicy delPolicy = root.getAdapter(DeletionPolicy.class);
		init(delPolicy);

		// delete all anchored connection parts
		for (IVisualPart<? extends Node> a : new ArrayList<>(getHost().getAnchoredsUnmodifiable())) {
			if (a instanceof MindMapConnectionPart) {
				delPolicy.delete((IContentPart<? extends Node>) a);
			}
			flag = true;
		}

		// delete the node part
		if (flag) {
			delPolicy.delete((IContentPart<? extends Node>) getHost());
		}
		commit(delPolicy);
	}

	@Override
	public void finalRelease(KeyEvent event) {

		switch (event.getCode()) {
		case DELETE: {
			// - Delete node (Delete)
			deleteNode();
			break;
		}
		case SHIFT: {
			// - Orthogonal connection
			SimpleMindMapAnchorProvider.isStaticAnchor = false;
			break;
		}
		default: {
			break;
		}
		}
	}

	@Override
	public void initialPress(KeyEvent event) {
		if (event.isShiftDown()) {
			// - Orthogonal connection
			SimpleMindMapAnchorProvider.isStaticAnchor = false;
		} else {
			// - Normal connection
			SimpleMindMapAnchorProvider.isStaticAnchor = true;
		}
	}

	@Override
	public void press(KeyEvent event) {

//		if (event.isShiftDown() && event.isControlDown()) {
//			switch (event.getCode()) {
//			case Z: {
//				// - Pressed (Ctrl + Shift + z)
//				System.out.println("// - (Ctrl + Shift + z)");
//				break;
//			}
//			default: {
//				break;
//			}
//			}
//		} else
		if (event.isControlDown()) {
			switch (event.getCode()) {

			case X: {
				// - New node (Ctrl + x)
				System.out.println("// - New node (Ctrl + x)");
				addNode();
				break;
			}
			case C: {
				// - New connection (Ctrl + c)
				addConnection();
				break;
			}
			case A: {
				// - Print all nodes (Ctrl + a)
				printInfoOnAllNodes();
				break;
			}
			default: {
				break;
			}
			}
		}
	}

	private void printInfoOnAllNodes() {
		Map<Object, IContentPart<? extends Node>> mindMap = getHost().getViewer().getContentPartMap();

		StringBuilder builder = new StringBuilder();
		for (Object node : mindMap.keySet()) {
			if (node instanceof MindMapNode) {
				for (MindMapConnection connection : ((MindMapNode) node).getIncomingConnections()) {
					builder.append(connection.getSource().getTitle());
					builder.append(", ");
				}

				System.out.print(builder.toString() + " -> " + ((MindMapNode) node).getTitle() + " -> ");
				builder = new StringBuilder();
				for (MindMapConnection connection : ((MindMapNode) node).getOutgoingConnections()) {
					builder.append(connection.getTarget().getTitle());
					builder.append(", ");
				}
			}

			System.out.println(builder.toString() + "\n");
		}
	}

	@Override
	public void release(KeyEvent event) {
	}

}
