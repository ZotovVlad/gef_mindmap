package ru.tcen.pak.workarea.policies;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.eclipse.gef.geometry.planar.Rectangle;
import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnClickHandler;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.policies.CreationPolicy;
import org.eclipse.gef.mvc.fx.viewer.IViewer;

import com.google.common.collect.HashMultimap;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import ru.tcen.pak.workarea.JSON.ControllerJSON;
import ru.tcen.pak.workarea.model.MindMapNode;
import ru.tcen.pak.workarea.models.ItemCreationModel;
import ru.tcen.pak.workarea.models.ItemCreationModel.Type;
import ru.tcen.pak.workarea.parts.SimpleMindMapPart;

/**
 * Policy, which listens to primary clicks and creates a new node if the
 * {@link ItemCreationModel} is in the right state.
 *
 */
public class CreateNewNodeOnClickHandler extends AbstractHandler implements IOnClickHandler {

	private static int i = 0;

	@Override
	public void click(MouseEvent e) {
		if (!e.isPrimaryButtonDown()) {
			return; // wrong mouse button
		}

		IViewer viewer = getHost().getRoot().getViewer();
		ItemCreationModel creationModel = viewer.getAdapter(ItemCreationModel.class);
		if (creationModel == null) {
			throw new IllegalStateException("No ItemCreationModel bound to viewer!");
		}

		if (creationModel.getType() != Type.Node) {
			if (Type.Node.getString() == "") {
				// don't want to create a node
				return;
			}
		}

		IVisualPart<? extends Node> part = viewer.getRootPart().getChildrenUnmodifiable().get(0);
		if (part instanceof SimpleMindMapPart) {
			// calculate the mouse coordinates
			// determine coordinates of new nodes origin in model coordinates
			Point2D mouseInLocal = part.getVisual().sceneToLocal(e.getSceneX(), e.getSceneY());

			ControllerJSON controllerJSON = new ControllerJSON();

			MindMapNode newNode = new MindMapNode();

			MindMapNode customNode = ControllerJSON.search(Type.Node.getString());
			if (!(customNode == null)) {
				newNode.setTitle("");
				newNode.setName(customNode.getName());
				newNode.setDescription(customNode.getDescription());
				newNode.setNumberOfHexParameters(customNode.getNumberOfHexParameters());
				newNode.setHexParameters(customNode.getHexParameters());
				newNode.setParameters(customNode.getParameters());
				newNode.setFunctionHexField(customNode.getFunctionHexField());
			} else {
				newNode.setTitle("Node" + i++);
				newNode.setDescription("no description");
				try {
					newNode.setImage(new Image(new FileInputStream("Icons/" + "icon0" + ".png")));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			newNode.setColor(Color.GREENYELLOW);
			newNode.setStatic(false);
			newNode.setBounds(new Rectangle(mouseInLocal.getX(), mouseInLocal.getY(), 170, 170));

			ControllerJSON.writeCustomJSON(newNode);
			newNode.addPropertyChangeListener(controllerJSON);
			ControllerJSON.mindMapNodesAtField.add(newNode);

			// GEF provides the CreatePolicy to add a new element to the model
			CreationPolicy creationPolicy = getHost().getRoot().getAdapter(CreationPolicy.class);
			init(creationPolicy);
			// create a IContentPart for our new model. The newly created
			// IContentPart is returned, but we do not need it.
			creationPolicy.create(newNode, part, HashMultimap.<IContentPart<? extends Node>, String>create());
			// commit the creation
			commit(creationPolicy);
		}

		// reset creation state
		Type.Node.setString("");
		creationModel.setType(Type.None);
	}
}