package com.itemis.gef.tutorial.mindmap.policies;

import java.util.Collections;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.fx.anchors.IAnchor;
import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnClickHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnHoverHandler;
import org.eclipse.gef.mvc.fx.operations.ChangeSelectionOperation;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.policies.CreationPolicy;
import org.eclipse.gef.mvc.fx.viewer.IViewer;

import com.google.common.collect.HashMultimap;
import com.google.common.reflect.TypeToken;
import com.google.inject.Provider;
import com.itemis.gef.tutorial.mindmap.model.MindMapConnection;
import com.itemis.gef.tutorial.mindmap.models.ItemCreationModel;
import com.itemis.gef.tutorial.mindmap.models.ItemCreationModel.Type;
import com.itemis.gef.tutorial.mindmap.parts.MindMapNodePart;
import com.itemis.gef.tutorial.mindmap.parts.SimpleMindMapPart;
import com.itemis.gef.tutorial.mindmap.parts.feedback.CreateConnectionFeedbackPart;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * The policy to create a new node using the {@link ItemCreationModel}
 *
 * @author hniederhausen
 *
 */
public class CreateNewConnectionOnClickHandler extends AbstractHandler implements IOnClickHandler, IOnHoverHandler {

	@Override
	public void click(MouseEvent e) {
		if (!e.isPrimaryButtonDown()) {
			return;
		}

		IViewer viewer = getHost().getRoot().getViewer();
		ItemCreationModel creationModel = viewer.getAdapter(ItemCreationModel.class);

		if (creationModel.getType() != Type.Connection) {
			return; // don't want to create a connection
		}

		MindMapNodePart source = null;
		MindMapNodePart target = null;
		try {
			if (creationModel.getSource() == null) {
				// the host is the source
				creationModel.setSource((MindMapNodePart) getHost());
				return; // wait for the next click
			}

			// okay, we have a pair
			source = creationModel.getSource();
			target = (MindMapNodePart) getHost();

		} catch (Exception e2) {
			// TODO: handle exception
			// System.out.println(123);
		}

		// check if valid
		if (source == target) {
			return;
		}

		IVisualPart<? extends Node> part = getHost().getRoot().getChildrenUnmodifiable().get(0);
		if (part instanceof SimpleMindMapPart) {
			MindMapConnection newConn = new MindMapConnection();
			newConn.connect(source.getContent(), target.getContent(), CreateConnectionFeedbackPart.points);// CreateConnectionFeedbackPart.points);

			// use CreatePolicy to add a new connection to the model
			CreationPolicy creationPolicy = getHost().getRoot().getAdapter(CreationPolicy.class);
			init(creationPolicy);
			creationPolicy.create(newConn, part, HashMultimap.<IContentPart<? extends Node>, String>create());
			commit(creationPolicy);

			// select target node
			// FIXME
			try {
				viewer.getDomain().execute(new ChangeSelectionOperation(viewer, Collections.singletonList(target)),
						null);
			} catch (ExecutionException e1) {
			}
		}

		// reset creation state
		creationModel.setSource(null);
		creationModel.setType(Type.None);
	}

	@Override
	public void hover(MouseEvent e) {
		IViewer viewer = getHost().getRoot().getViewer();
		ItemCreationModel creationModel = viewer.getAdapter(ItemCreationModel.class);
		MindMapNodePart source = null;
		MindMapNodePart target = null;
//		source = creationModel.getSource();
		target = (MindMapNodePart) getHost();
//		if (!(source == null)) {
//			Provider<? extends IAnchor> adapterSource = source
//					.getAdapter(AdapterKey.get(new TypeToken<Provider<? extends IAnchor>>() {
//					}));
//			adapterSource.get();
//		}
		if (!(target == null)) {
			Provider<? extends IAnchor> adapterTarget = target
					.getAdapter(AdapterKey.get(new TypeToken<Provider<? extends IAnchor>>() {
					}));
			adapterTarget.get();
		}

	}

	@Override
	public void hoverIntent(Node hoverIntent) {

	}
}