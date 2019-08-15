package com.itemis.gef.tutorial.mindmap.policies;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Optional;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnClickHandler;
import org.eclipse.gef.mvc.fx.models.HoverModel;
import org.eclipse.gef.mvc.fx.operations.ITransactionalOperation;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IRootPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.policies.DeletionPolicy;

import com.itemis.gef.tutorial.mindmap.operations.SetMindMapNodeColorOperation;
import com.itemis.gef.tutorial.mindmap.operations.SetMindMapNodeDescriptionOperation;
import com.itemis.gef.tutorial.mindmap.operations.SetMindMapNodeImageOperation;
import com.itemis.gef.tutorial.mindmap.operations.SetMindMapNodeTitleOperation;
import com.itemis.gef.tutorial.mindmap.parts.MindMapConnectionPart;
import com.itemis.gef.tutorial.mindmap.parts.MindMapNodePart;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This policy shows a context menu for MindMapNodeParts, providing some editing
 * functionality.
 *
 * @author hniederhausen
 *
 */
public class ShowMindMapNodeContextMenuOnClickHandler extends AbstractHandler implements IOnClickHandler {

	@Override
	public void click(MouseEvent event) {
		if (!event.isSecondaryButtonDown()) {
			return; // only listen to secondary buttons
		}

		MenuItem deleteNodeItem = new MenuItem("Delete Node");
		deleteNodeItem.setOnAction((e) -> {
			// FIXME
			// remove part from hover model (transient model, i.e. changes
			// are not undoable)
			HoverModel hover = getHost().getViewer().getAdapter(HoverModel.class);
			if (getHost() == hover.getHover()) {
				hover.clearHover();
			}

			// query DeletionPolicy for the removal of the host part
			IRootPart<? extends Node> root = getHost().getRoot();
			DeletionPolicy delPolicy = root.getAdapter(DeletionPolicy.class);
			init(delPolicy);

			// delete all anchored connection parts
			for (IVisualPart<? extends Node> a : new ArrayList<>(getHost().getAnchoredsUnmodifiable())) {
				if (a instanceof MindMapConnectionPart) {
					delPolicy.delete((IContentPart<? extends Node>) a);
				}
			}

			// delete the node part
			delPolicy.delete((IContentPart<? extends Node>) getHost());
			commit(delPolicy);
		});

		MenuItem imageNodeItem = new MenuItem("Change Image");
		imageNodeItem.setOnAction((e) -> {
			MindMapNodePart host = (MindMapNodePart) getHost();
			String newNameImage = null;
			try {
				try {
					newNameImage = showDialog(host.getContent().getDescription(), "Enter name new Image...");
					Image newImage = new Image(new FileInputStream("Icons/" + newNameImage + ".png"));
					ITransactionalOperation op = new SetMindMapNodeImageOperation(host, newImage);
					host.getRoot().getViewer().getDomain().execute(op, null);
				} catch (FileNotFoundException e1) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setHeaderText(null);
					alert.setContentText("File '" + newNameImage + "' not found");
					alert.showAndWait();
				}
			} catch (ExecutionException e1) {
				e1.printStackTrace();
			}
		});

		Menu colorMenu = createChangeColorMenu();
		Menu textMenu = createChangeTextsMenu();

		ContextMenu ctxMenu = new ContextMenu(imageNodeItem, textMenu, colorMenu, deleteNodeItem);
		// show the menu at the mouse position
		ctxMenu.show((Node) event.getTarget(), event.getScreenX(), event.getScreenY());
	}

	private Menu createChangeColorMenu() {
		Menu colorMenu = new Menu("Change Color");
		Color[] colors = { Color.ALICEBLUE, Color.BURLYWOOD, Color.YELLOW, Color.RED, Color.CHOCOLATE,
				Color.GREENYELLOW, Color.WHITE };
		String[] names = { "ALICEBLUE", "BURLYWOOD", "YELLOW", "RED", "CHOCOLATE", "GREENYELLOW", "WHITE" };

		for (int i = 0; i < colors.length; i++) {
			colorMenu.getItems().add(getColorMenuItem(names[i], colors[i]));
		}
		return colorMenu;
	}

	private Menu createChangeTextsMenu() {
		Menu textsMenu = new Menu("Change Text");

		MindMapNodePart host = (MindMapNodePart) getHost();

		MenuItem titleItem = new MenuItem("Title ...");
		titleItem.setOnAction((e) -> {
			try {
				String newTitle = showDialog(host.getContent().getTitle(), "Enter new Title...");
				ITransactionalOperation op = new SetMindMapNodeTitleOperation(host, newTitle);
				host.getRoot().getViewer().getDomain().execute(op, null);
			} catch (ExecutionException e1) {
				e1.printStackTrace();
			}

		});

		MenuItem descrItem = new MenuItem("Description ...");
		descrItem.setOnAction((e) -> {
			try {
				String newDescription = showDialog(host.getContent().getDescription(), "Enter new Description...");
				ITransactionalOperation op = new SetMindMapNodeDescriptionOperation(host, newDescription);
				host.getRoot().getViewer().getDomain().execute(op, null);
			} catch (ExecutionException e1) {
				e1.printStackTrace();
			}
		});

		textsMenu.getItems().addAll(titleItem, descrItem);

		return textsMenu;
	}

	private MenuItem getColorMenuItem(String name, Color color) {
		Rectangle graphic = new Rectangle(20, 20);
		graphic.setFill(color);
		graphic.setStroke(Color.BLACK);
		MenuItem item = new MenuItem(name, graphic);
		item.setOnAction((e) -> submitColor(color));
		return item;
	}

	private String showDialog(String defaultValue, String title) {
		TextInputDialog dialog = new TextInputDialog(defaultValue);
		dialog.setTitle(title);
		dialog.setGraphic(null);
		dialog.setHeaderText("");

		Optional<String> result = dialog.showAndWait();
		String entered = defaultValue;

		if (result.isPresent()) {
			entered = result.get();
		}
		return entered;
	}

	private void submitColor(Color color) {
		if (getHost() instanceof MindMapNodePart) {
			MindMapNodePart host = (MindMapNodePart) getHost();
			SetMindMapNodeColorOperation op = new SetMindMapNodeColorOperation(host, color);
			try {
				host.getViewer().getDomain().execute(op, null);
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

}