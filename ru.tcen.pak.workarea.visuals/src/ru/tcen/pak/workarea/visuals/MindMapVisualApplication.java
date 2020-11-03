package ru.tcen.pak.workarea.visuals;

import org.eclipse.gef.fx.anchors.ChopBoxStrategy;
import org.eclipse.gef.fx.anchors.DynamicAnchor;
import org.eclipse.gef.fx.nodes.Connection;

import javafx.application.Application;
import javafx.scene.Scene;
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

		// Image image = new Image(new FileInputStream(urlImage));
		// ImageView iv1 = new ImageView(image);

		String userDir = System.getProperty("user.dir");
//		File f = new File(userDir + File.separator + "test" + ".txt");
//		f.createNewFile(); \Files

		// create state visuals

		MindMapNodeVisual node = new MindMapNodeVisual(6, false, false);
		node.setTitle("Test Node");
		node.setName("NAME1");
		node.setDescription("This is just a test node, to see, how it looks :)");
		node.relocate(500, 50);
		// node.setImage(image);

		MindMapNodeVisual node2 = new MindMapNodeVisual(6, false, false);
		node2.setTitle("Test Node 2");
		node2.setName("NAME2");
		node2.setDescription("This is just a test node, to see, how it looks :)");
		node2.relocate(150, 250);
		node2.setColor(Color.ALICEBLUE);
		// node2.setImage(image);
		// node2.getChildrenUnmodifiable().add(iv1);

		System.out.println(node.hashCode());
		System.out.println(node2.hashCode());

		Connection conn = new MindMapConnectionVisual();
		conn.setStartAnchor(new DynamicAnchor(node, new ChopBoxStrategy())); // OrthogonalProjectionStrategy,
																				// ProjectionStrategy
		conn.setEndAnchor(new DynamicAnchor(node2, new ChopBoxStrategy()));

		// root.getChildren().add(iv1);
		root.getChildren().addAll(conn, node, node2);

		primaryStage.setResizable(true);
		primaryStage.setScene(new Scene(root, 1024, 768));
		primaryStage.setTitle("This is an JavaFX Environment Test");
		primaryStage.sizeToScene();
		primaryStage.show();
	}
}