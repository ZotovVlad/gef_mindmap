package com.itemis.gef.tutorial.mindmap.policies;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnClickHandler;
import org.eclipse.gef.mvc.fx.models.HoverModel;
import org.eclipse.gef.mvc.fx.operations.ITransactionalOperation;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IRootPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.policies.DeletionPolicy;

import com.itemis.gef.tutorial.mindmap.JSON.ControllerJSON;
import com.itemis.gef.tutorial.mindmap.model.MindMapNode;
import com.itemis.gef.tutorial.mindmap.operations.SetMindMapNodeColorOperation;
import com.itemis.gef.tutorial.mindmap.operations.SetMindMapNodeDescriptionOperation;
import com.itemis.gef.tutorial.mindmap.operations.SetMindMapNodeEndOperation;
import com.itemis.gef.tutorial.mindmap.operations.SetMindMapNodeFunctionHexFieldOperation;
import com.itemis.gef.tutorial.mindmap.operations.SetMindMapNodeImageOperation;
import com.itemis.gef.tutorial.mindmap.operations.SetMindMapNodeNameOperation;
import com.itemis.gef.tutorial.mindmap.operations.SetMindMapNodeNumberOfInputsOperation;
import com.itemis.gef.tutorial.mindmap.operations.SetMindMapNodeNumberOfOutputsOperation;
import com.itemis.gef.tutorial.mindmap.parts.MindMapConnectionPart;
import com.itemis.gef.tutorial.mindmap.parts.MindMapNodePart;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

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
		if (event.getClickCount() == 2) {
			MindMapNodePart host = (MindMapNodePart) getHost();
			MindMapNode node = host.getContent();

			File file = new File(node.getNodeCode());
			if (!Desktop.isDesktopSupported()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information");
				alert.setHeaderText(null);
				alert.setContentText("Desktop is not supported open txt files");
				alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
				alert.showAndWait();
				return;
			}

			Desktop desktop = Desktop.getDesktop();
			if (file.exists()) {
				try {
					desktop.open(file);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		if (!event.isSecondaryButtonDown()) {
			return; // only listen to secondary buttons
		}

		MenuItem imageNodeItem = createImageNodeItem();
		Menu textMenu = createChangeTextsMenu();
		Menu numberMenu = createChangeNumbersMenu();
		Menu functionMenu = createChangeFunctionsMenu();
		Menu colorMenu = createChangeColorMenu();
		MenuItem deleteNodeItem = createDeleteNodeItem();
		MenuItem getPathSource = getPathSource();

		ContextMenu ctxMenu = new ContextMenu(imageNodeItem, textMenu, numberMenu, functionMenu, colorMenu,
				deleteNodeItem, getPathSource);

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

	private Menu createChangeFunctionsMenu() {
		Menu textsMenu = new Menu("Change Functions");

		MindMapNodePart host = (MindMapNodePart) getHost();

		Menu nameItem = new Menu("Function Hex Field ...");
		MenuItem nameEnter = new MenuItem("Function enter...");
		nameEnter.setOnAction((e) -> {
			try {
				FileChooser fileChooser = new FileChooser();
				FileChooser.ExtensionFilter extFilterHEX = new FileChooser.ExtensionFilter("HEX files (*.hex)",
						"*.hex");
				fileChooser.getExtensionFilters().addAll(extFilterHEX);
				File file = fileChooser.showOpenDialog(null);
				ITransactionalOperation op = new SetMindMapNodeFunctionHexFieldOperation(host, file.getName());
				host.getRoot().getViewer().getDomain().execute(op, null);
			} catch (Exception e1) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setHeaderText(null);
				alert.setContentText("File not selected");
				alert.showAndWait();
			}
		});
		Menu nameExample = new Menu("Function example ...");
		ArrayList<String> names = ControllerJSON.read(host.getContent(), MindMapNode.PROP_FUNCTION_HEX_FIELD);
		for (String string : names) {
			MenuItem nameExampleItem = new MenuItem(string);
			nameExampleItem.setOnAction((e) -> {
				ITransactionalOperation op = new SetMindMapNodeFunctionHexFieldOperation(host,
						nameExampleItem.getText().toString());
				try {
					host.getRoot().getViewer().getDomain().execute(op, null);
				} catch (ExecutionException e1) {
					e1.printStackTrace();
				}
			});
			nameExample.getItems().add(nameExampleItem);
		}
		nameItem.getItems().addAll(nameEnter, nameExample);

		Menu descrItem = new Menu("End ...");
		MenuItem descrEnter = new MenuItem("End enter...");
		descrEnter.setOnAction((e) -> {
			try {
				FileChooser fileChooser = new FileChooser();
				FileChooser.ExtensionFilter extFilterHEX = new FileChooser.ExtensionFilter("HEX files (*.hex)",
						"*.hex");
				fileChooser.getExtensionFilters().addAll(extFilterHEX);
				File file = fileChooser.showOpenDialog(null);
				ITransactionalOperation op = new SetMindMapNodeEndOperation(host, file.getName());
				host.getRoot().getViewer().getDomain().execute(op, null);
			} catch (Exception e1) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setHeaderText(null);
				alert.setContentText("File not selected");
				alert.showAndWait();
			}
		});
		Menu descrExample = new Menu("End example ...");
		ArrayList<String> descriptions = ControllerJSON.read(host.getContent(), MindMapNode.PROP_END);
		for (String string : descriptions) {
			MenuItem descrExampleItem = new MenuItem(string);
			descrExampleItem.setOnAction((e) -> {
				ITransactionalOperation op = new SetMindMapNodeEndOperation(host,
						descrExampleItem.getText().toString());
				try {
					host.getRoot().getViewer().getDomain().execute(op, null);
				} catch (ExecutionException e1) {
					e1.printStackTrace();
				}
			});
			descrExample.getItems().add(descrExampleItem);
		}
		descrItem.getItems().addAll(descrEnter, descrExample);

		textsMenu.getItems().addAll(nameItem, descrItem);

		return textsMenu;
	}

	private Menu createChangeNumbersMenu() {
		Menu textsMenu = new Menu("Change Numbers");

		MindMapNodePart host = (MindMapNodePart) getHost();

		Menu nameItem = new Menu("Number of Input ...");
		MenuItem nameEnter = new MenuItem("Number enter...");
		nameEnter.setOnAction((e) -> {
			try {
				String newName = showDialog(host.getContent().getName(), "Enter new Number of Input...");
				ITransactionalOperation op = new SetMindMapNodeNumberOfInputsOperation(host, newName);
				host.getRoot().getViewer().getDomain().execute(op, null);
			} catch (ExecutionException e1) {
				e1.printStackTrace();
			}
		});
		Menu nameExample = new Menu("Number example ...");
		ArrayList<String> names = ControllerJSON.read(host.getContent(), MindMapNode.PROP_NUMBER_OF_INPUTS);
		for (String string : names) {
			MenuItem nameExampleItem = new MenuItem(string);
			nameExampleItem.setOnAction((e) -> {
				ITransactionalOperation op = new SetMindMapNodeNumberOfInputsOperation(host,
						nameExampleItem.getText().toString());
				try {
					host.getRoot().getViewer().getDomain().execute(op, null);
				} catch (ExecutionException e1) {
					e1.printStackTrace();
				}
			});
			nameExample.getItems().add(nameExampleItem);
		}
		nameItem.getItems().addAll(nameEnter, nameExample);

		Menu descrItem = new Menu("Number of Output ...");
		MenuItem descrEnter = new MenuItem("Output enter...");
		descrEnter.setOnAction((e) -> {
			try {
				String newDescription = showDialog(host.getContent().getDescription(), "Enter new Number of Output...");
				ITransactionalOperation op = new SetMindMapNodeNumberOfOutputsOperation(host, newDescription);
				host.getRoot().getViewer().getDomain().execute(op, null);
			} catch (ExecutionException e1) {
				e1.printStackTrace();
			}
		});
		Menu descrExample = new Menu("Output example ...");
		ArrayList<String> descriptions = ControllerJSON.read(host.getContent(), MindMapNode.PROP_NUMBER_OF_OUTPUTS);
		for (String string : descriptions) {
			MenuItem descrExampleItem = new MenuItem(string);
			descrExampleItem.setOnAction((e) -> {
				ITransactionalOperation op = new SetMindMapNodeNumberOfOutputsOperation(host,
						descrExampleItem.getText().toString());
				try {
					host.getRoot().getViewer().getDomain().execute(op, null);
				} catch (ExecutionException e1) {
					e1.printStackTrace();
				}
			});
			descrExample.getItems().add(descrExampleItem);
		}
		descrItem.getItems().addAll(descrEnter, descrExample);

		textsMenu.getItems().addAll(nameItem, descrItem);

		return textsMenu;
	}

	private Menu createChangeTextsMenu() {
		Menu textsMenu = new Menu("Change Text");

		MindMapNodePart host = (MindMapNodePart) getHost();

		Menu nameItem = new Menu("Name ...");
		MenuItem nameEnter = new MenuItem("Name enter...");
		nameEnter.setOnAction((e) -> {
			try {
				String newName = showDialog(host.getContent().getName(), "Enter new Name...");
				ITransactionalOperation op = new SetMindMapNodeNameOperation(host, newName);
				host.getRoot().getViewer().getDomain().execute(op, null);
			} catch (ExecutionException e1) {
				e1.printStackTrace();
			}
		});
		Menu nameExample = new Menu("Name example ...");
		ArrayList<String> names = ControllerJSON.read(host.getContent(), MindMapNode.PROP_NAME);
		for (String string : names) {
			MenuItem nameExampleItem = new MenuItem(string);
			nameExampleItem.setOnAction((e) -> {
				ITransactionalOperation op = new SetMindMapNodeNameOperation(host,
						nameExampleItem.getText().toString());
				try {
					host.getRoot().getViewer().getDomain().execute(op, null);
				} catch (ExecutionException e1) {
					e1.printStackTrace();
				}
			});
			nameExample.getItems().add(nameExampleItem);
		}
		nameItem.getItems().addAll(nameEnter, nameExample);

		Menu descrItem = new Menu("Description ...");
		MenuItem descrEnter = new MenuItem("Description enter...");
		descrEnter.setOnAction((e) -> {
			try {
				String newDescription = showDialog(host.getContent().getDescription(), "Enter new Description...");
				ITransactionalOperation op = new SetMindMapNodeDescriptionOperation(host, newDescription);
				host.getRoot().getViewer().getDomain().execute(op, null);
			} catch (ExecutionException e1) {
				e1.printStackTrace();
			}
		});
		Menu descrExample = new Menu("Description example ...");
		ArrayList<String> descriptions = ControllerJSON.read(host.getContent(), MindMapNode.PROP_DESCRIPTION);
		for (String string : descriptions) {
			MenuItem descrExampleItem = new MenuItem(string);
			descrExampleItem.setOnAction((e) -> {
				ITransactionalOperation op = new SetMindMapNodeDescriptionOperation(host,
						descrExampleItem.getText().toString());
				try {
					host.getRoot().getViewer().getDomain().execute(op, null);
				} catch (ExecutionException e1) {
					e1.printStackTrace();
				}
			});
			descrExample.getItems().add(descrExampleItem);
		}
		descrItem.getItems().addAll(descrEnter, descrExample);

		textsMenu.getItems().addAll(nameItem, descrItem);

		return textsMenu;
	}

	private MenuItem createDeleteNodeItem() {
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

		return deleteNodeItem;
	}

	private MenuItem createImageNodeItem() {
		MenuItem imageNodeItem = new MenuItem("Change Image");
		imageNodeItem.setOnAction((e) -> {
			MindMapNodePart host = (MindMapNodePart) getHost();
			String newNameImage = null;
			try {
				FileChooser fileChooser = new FileChooser();
				// Set extension filter
				FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)",
						"*.jpg");
				FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)",
						"*.png");
				fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
				// Show open file dialog
				File file = fileChooser.showOpenDialog(null);
				BufferedImage bufferedImage = ImageIO.read(file);
				Image newImage = SwingFXUtils.toFXImage(bufferedImage, null);
//					newNameImage = showDialog(host.getContent().getDescription(), "Enter name new Image...");
//					Image newImage = new Image(new FileInputStream("Icons/" + newNameImage + ".png"));
				ITransactionalOperation op = new SetMindMapNodeImageOperation(host, newImage);
				host.getRoot().getViewer().getDomain().execute(op, null);
			} catch (FileNotFoundException e1) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setHeaderText(null);
				alert.setContentText("File '" + newNameImage + "' not found");
				alert.showAndWait();
			} catch (Exception e1) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setHeaderText(null);
				alert.setContentText("File not selected");
				alert.showAndWait();
			}
		});

		return imageNodeItem;
	}

	private MenuItem getColorMenuItem(String name, Color color) {
		Rectangle graphic = new Rectangle(20, 20);
		graphic.setFill(color);
		graphic.setStroke(Color.BLACK);
		MenuItem item = new MenuItem(name, graphic);
		item.setOnAction((e) -> submitColor(color));
		return item;
	}

	private MenuItem getPathSource() {
		MenuItem getPathSource = new MenuItem("Get code source");
		getPathSource.setOnAction((e) -> {
			MindMapNodePart host = (MindMapNodePart) getHost();
			MindMapNode node = host.getContent();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information");
			alert.setHeaderText(null);
			String pathFile = "File this node locates in the folder: " + node.getNodeCode();
			alert.setContentText(pathFile);
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.showAndWait();
		});

		return getPathSource;
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