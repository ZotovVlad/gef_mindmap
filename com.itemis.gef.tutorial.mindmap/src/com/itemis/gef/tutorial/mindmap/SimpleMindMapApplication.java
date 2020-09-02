package com.itemis.gef.tutorial.mindmap;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.mvc.fx.domain.HistoricizingDomain;
import org.eclipse.gef.mvc.fx.domain.IDomain;
import org.eclipse.gef.mvc.fx.viewer.IViewer;

import com.google.inject.Guice;
import com.itemis.gef.tutorial.mindmap.JSON.ControllerJSON;
import com.itemis.gef.tutorial.mindmap.model.AbstractMindMapItem;
import com.itemis.gef.tutorial.mindmap.model.MindMapNode;
import com.itemis.gef.tutorial.mindmap.model.SimpleMindMap;
import com.itemis.gef.tutorial.mindmap.model.SimpleMindMapExampleFactory;
import com.itemis.gef.tutorial.mindmap.models.ItemCreationModel;
import com.itemis.gef.tutorial.mindmap.models.ItemCreationModel.Type;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Entry point for our Simple Mind Map Editor, creating and rendering a JavaFX
 * Window.
 *
 */
public class SimpleMindMapApplication extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	private Stage primaryStage;

	private HistoricizingDomain domain;

	/**
	 * Creates the undo/redo buttons
	 *
	 * @return
	 */
	private Node createButtonBar() {
		Button undoButton = new Button("Undo");
		undoButton.setDisable(true);
		undoButton.setOnAction((e) -> {
			undo();
		});

		Button redoButton = new Button("Redo");
		redoButton.setDisable(true);
		redoButton.setOnAction((e) -> {
			redo();
		});

		// add listener to the operation history of our domain
		// to enable/disable undo/redo buttons as needed
		domain.getOperationHistory().addOperationHistoryListener((e) -> {
			IUndoContext ctx = domain.getUndoContext();
			undoButton.setDisable(!e.getHistory().canUndo(ctx));
			redoButton.setDisable(!e.getHistory().canRedo(ctx));
		});

		return new HBox(10, undoButton, redoButton);
	}

	/**
	 * Creates the tooling buttons to create new elements
	 *
	 * @return
	 */
	private Node createToolPalette() {
		ItemCreationModel creationModel = getContentViewer().getAdapter(ItemCreationModel.class);

		VBox vBox = new VBox(20);

		// the toggleGroup makes sure, we only select one
		ToggleGroup toggleGroup = new ToggleGroup();

		ToggleButton createNode = new ToggleButton("New Node");
		createNode.setToggleGroup(toggleGroup);
		createNode.setMaxWidth(Double.MAX_VALUE);
		createNode.setMinHeight(50);
		createNode.selectedProperty().addListener((e, oldVal, newVal) -> {
			creationModel.setType(newVal ? Type.Node : Type.None);
		});
		vBox.getChildren().add(createNode);

		ToggleButton createConn = new ToggleButton("New Connection");
		createConn.setToggleGroup(toggleGroup);
		createConn.setMaxWidth(Double.MAX_VALUE);
		createConn.setMinHeight(50);
		createConn.selectedProperty().addListener((e, oldVal, newVal) -> {
			creationModel.setType(newVal ? Type.Connection : Type.None);
		});
		vBox.getChildren().add(createConn);

		Text text = new Text("Nodes in library:");
		vBox.getChildren().add(text);
		VBox.setMargin(vBox.getChildren().get(2), new Insets(50, 0, 0, 0));

		List<MindMapNode> nodeLib = ControllerJSON.readMindMapNodeLib();
		List<ToggleButton> nodeLibButton = new ArrayList<>();
		for (int i = 0; i < nodeLib.size(); i++) {
			ToggleButton oneNodeLibButton = new ToggleButton(nodeLib.get(i).getName());
			oneNodeLibButton.setToggleGroup(toggleGroup);
			oneNodeLibButton.setMaxWidth(Double.MAX_VALUE);
			oneNodeLibButton.setMinHeight(50);
			oneNodeLibButton.setOnAction(new EventHandler<ActionEvent>() {

				boolean flag = true;

				@Override
				public void handle(ActionEvent arg0) {
					Type.Node.setString(((ToggleButton) arg0.getSource()).getText().toString());
					flag = !flag;
					// creationModel.setType(arg0 ? Type.Node : Type.None);
					// TODO Auto-generated method stub
				}
			});

			oneNodeLibButton.selectedProperty().addListener((e, oldVal, newVal) -> {
				creationModel.setType(newVal ? Type.Node : Type.None);
			});

			vBox.getChildren().add(oneNodeLibButton);
			nodeLibButton.add(oneNodeLibButton);
		}

		if (nodeLib.size() == 0) {
			Text textEmptyLib = new Text("Not library!");
			vBox.getChildren().add(textEmptyLib);
			VBox.setMargin(vBox.getChildren().get(3), new Insets(-20, 0, 0, 0));
		}

		// ListView list = new ListView();
		// VBox.setVgrow(list, Priority.ALWAYS);
		// vBox.getChildren().add(list);

		// now listen to changes in the model, and deactivate buttons, if
		// necessary
		creationModel.getTypeProperty().addListener((e, oldVal, newVal) -> {
			if (Type.None == newVal) {
				// unselect the toggle button
				Toggle selectedToggle = toggleGroup.getSelectedToggle();
				if (selectedToggle != null) {
					selectedToggle.setSelected(false);
				}
			}
		});

		return vBox;
	}

	/**
	 * Add hot keys actions
	 */
	private void defineHotKeys() {

		primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.isControlDown()) {
					switch (event.getCode()) {
					case Z: {
						// - Undo (Ctrl+z)
						System.out.println("// - Undo (Ctrl+z)");
						undo();
						break;
					}
					case R: {
						// - Redo (Ctrl+r)
						System.out.println("// - Redo (Ctrl+r)");
						redo();
						break;
					}
					default:
						break;
					}
				}
			}

		});
	}

	/**
	 * Returns the content viewer of the domain
	 *
	 * @return
	 */
	private IViewer getContentViewer() {
		return domain.getAdapter(AdapterKey.get(IViewer.class, IDomain.CONTENT_VIEWER_ROLE));
	}

	/**
	 * Creating JavaFX widgets and set them to the stage.
	 */
	private void hookViewers() {
		// creating parent pane for Canvas and button pane
		BorderPane pane = new BorderPane();

		pane.setTop(createButtonBar());
		pane.setCenter(getContentViewer().getCanvas());
		pane.setRight(createToolPalette());

		pane.setMinWidth(1300);
		pane.setMinHeight(700);

//		HotKeysHandler keyboard = new HotKeysHandler();
//		keyboard.init();

		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);
	}

	/**
	 * Creates the example mind map and sets it as content to the viewer.
	 */
	private void populateViewerContents() {
		SimpleMindMapExampleFactory fac = new SimpleMindMapExampleFactory();

		SimpleMindMap mindMap = fac.createComplexExample();

		ControllerJSON controllerJSON = new ControllerJSON();
		for (AbstractMindMapItem abstractMindMapItem : mindMap.getChildElements()) {
			abstractMindMapItem.addPropertyChangeListener(controllerJSON);
			ControllerJSON.mindMapNodesAtField.add(abstractMindMapItem);
			// mindMap.addChildElement(abstractMindMapItem);
		}

		// ControllerJSON.writeAllPropertiesJSON();

		IViewer viewer = getContentViewer();
		viewer.getContents().setAll(mindMap);
	}

	/**
	 * Redo event
	 */
	private void redo() {
		try {
			domain.getOperationHistory().redo(domain.getUndoContext(), null, null);
		} catch (ExecutionException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		SimpleMindMapModule module = new SimpleMindMapModule();
		this.primaryStage = primaryStage;
		// create domain using guice
		this.domain = (HistoricizingDomain) Guice.createInjector(module).getInstance(IDomain.class);

		// update, refresh source and data
		updateSource();

		// create viewers
		hookViewers();

		// activate domain
		domain.activate();

		// load contents
		populateViewerContents();

		defineHotKeys();

		// set-up stage
		primaryStage.setResizable(true);
		primaryStage.setTitle("GEF Simple Mindmap");
		primaryStage.sizeToScene();
		primaryStage.show();
	}

	/**
	 * Undo event
	 */
	private void undo() {
		try {
			domain.getOperationHistory().undo(domain.getUndoContext(), null, null);
		} catch (ExecutionException e1) {
			e1.printStackTrace();
		}
	}

	private void updateSource() {
		String path = System.getProperty("user.dir") + File.separator + "files";

		for (File file : new File(path).listFiles()) {
			if (file.isFile()) {
				file.delete();
			}
		}
	}
}