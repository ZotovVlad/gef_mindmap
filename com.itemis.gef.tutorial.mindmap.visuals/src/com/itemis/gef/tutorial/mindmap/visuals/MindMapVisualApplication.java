package com.itemis.gef.tutorial.mindmap.visuals;

import java.io.FileInputStream;

import org.eclipse.gef.fx.anchors.ChopBoxStrategy;
import org.eclipse.gef.fx.anchors.DynamicAnchor;
import org.eclipse.gef.fx.nodes.Connection;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MindMapVisualApplication extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	private String urlImage = "Event-search-icon.png";

	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane root = new Pane();

		// create state visuals
		MindMapNodeVisual node = new MindMapNodeVisual();
		node.setTitle("Test Node");
		node.setDescription("This is just a test node, to see, how it looks :)");
		node.relocate(50, 50);

		node.setImage(new Image(new FileInputStream(urlImage)));

		MindMapNodeVisual node2 = new MindMapNodeVisual();
		node2.setTitle("Test Node 2");
		node2.setDescription("This is just a test node, to see, how it looks :)");
		node2.relocate(150, 250);
		node2.setColor(Color.ALICEBLUE);

		Connection conn = new MindMapConnectionVisual();
		conn.setStartAnchor(new DynamicAnchor(node, new ChopBoxStrategy()));
		conn.setEndAnchor(new DynamicAnchor(node2, new ChopBoxStrategy()));

		root.getChildren().addAll(conn, node, node2);

		primaryStage.setResizable(true);
		primaryStage.setScene(new Scene(root, 1024, 768));
		primaryStage.setTitle("This is an JavaFX Environment Test");
		primaryStage.sizeToScene();
		primaryStage.show();
	}
}