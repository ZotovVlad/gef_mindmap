package com.itemis.gef.tutorial.mindmap.visuals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.gef.fx.nodes.GeometryNode;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.geometry.planar.RoundedRectangle;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class MindMapNodeVisual extends Region {

	private static final double HORIZONTAL_PADDING = 20d;
	private static final double VERTICAL_PADDING = 10d;
	private static final double VERTICAL_SPACING = 5d;
	private static final double NODE_WIDTH = 170;
	private static final double NODE_HEIGH = 170;

	private Text titleText;
	private TextFlow descriptionFlow;
	private Text descriptionText;
	private GeometryNode<RoundedRectangle> shape;
	private VBox labelVBox;
	private Image descriptionImage;
	private String urlImage = "null.png";
	private int quantityImage = 0;
	private List<Point> points = new ArrayList<>();
	private List<Rectangle> pointsBox = new ArrayList<>();

	public MindMapNodeVisual() {

		// create background shape
		shape = new GeometryNode<>(new RoundedRectangle(0, 0, NODE_WIDTH, NODE_HEIGH, 8, 8));
		shape.setFill(Color.LIGHTGREEN);
		shape.setStroke(Color.BLACK);

		// create vertical box for title and description
		labelVBox = new VBox(VERTICAL_SPACING);
		labelVBox.setPadding(new Insets(VERTICAL_PADDING, HORIZONTAL_PADDING, VERTICAL_PADDING, HORIZONTAL_PADDING));

		// ensure shape and labels are resized to fit this visual
		shape.prefWidthProperty().bind(widthProperty());
		shape.prefHeightProperty().bind(heightProperty());
		labelVBox.prefWidthProperty().bind(widthProperty());
		labelVBox.prefHeightProperty().bind(heightProperty());

		// create title text
		titleText = new Text();
		titleText.setTextOrigin(VPos.TOP);

		// create description text
		descriptionText = new Text();
		descriptionText.setTextOrigin(VPos.TOP);

//		try {
//			descriptionImage = new Image(new FileInputStream(urlImage));
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		// descriptionImage = new Image(urlImage);
		// descriptionImage

		// use TextFlow to enable wrapping of the description text within the
		// label bounds
		descriptionFlow = new TextFlow(descriptionText);
		// only constrain the width, so that the height is computed in
		// dependence on the width
		descriptionFlow.maxWidthProperty().bind(shape.widthProperty().subtract(HORIZONTAL_PADDING * 2));

		// vertically lay out title and description
		labelVBox.getChildren().addAll(titleText, descriptionFlow);

		// ensure title is always visible (see also #computeMinWidth(double) and
		// #computeMinHeight(double) methods)
		setMinSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);

		// wrap shape and VBox in Groups so that their bounds-in-parent is
		// considered when determining the layout-bounds of this visual
		getChildren().addAll(new Group(shape), new Group(labelVBox));

		points.addAll(Arrays.asList(new Point(0, 0), new Point(NODE_WIDTH / 2, 0), new Point(NODE_WIDTH, 0),
				new Point(NODE_WIDTH, NODE_HEIGH / 2), new Point(NODE_WIDTH, NODE_HEIGH),
				new Point(NODE_WIDTH / 2, NODE_HEIGH), new Point(0, NODE_HEIGH), new Point(0, NODE_HEIGH / 2)));

		for (int i = 0; i < points.size(); i++) {
			pointsBox.add(new Rectangle(points.get(i).x - 10 / 2, points.get(i).y - 10 / 2, 10, 10));
		}

		getChildren().addAll(pointsBox);

	}

	@Override
	public double computeMinHeight(double width) {
		// ensure title is always visible
		// descriptionFlow.minHeight(width) +
		// titleText.getLayoutBounds().getHeight() + VERTICAL_PADDING * 2;
		return labelVBox.minHeight(width);
	}

	@Override
	public double computeMinWidth(double height) {
		// ensure title is always visible
		return titleText.getLayoutBounds().getWidth() + HORIZONTAL_PADDING * 2;
	}

	@Override
	protected double computePrefHeight(double width) {
		return minHeight(width);
	}

	@Override
	protected double computePrefWidth(double height) {
		return minWidth(height);
	}

	@Override
	public Orientation getContentBias() {
		return Orientation.HORIZONTAL;
	}

	public Text getDescriptionText() {
		return descriptionText;
	}

	public GeometryNode<?> getGeometryNode() {
		return shape;
	}

	public Image getImage() {
		return descriptionImage;
	}

	public List<Point> getPoints() {
		return points;
	}

	public Text getTitleText() {
		return titleText;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setColor(Color color) {
		shape.setFill(color);
	}

	public void setDescription(String description) {
		this.descriptionText.setText(description);
	}

	public void setImage(Image image) {
		if (!(this.descriptionImage == image)) {
			ImageView iv1 = new ImageView(image);
			iv1.setFitWidth(100);
			try {
				labelVBox.getChildren().remove(2);
			} catch (Exception e) {

			}
			labelVBox.getChildren().add(iv1);
			this.descriptionImage = image;
		}
	}

	public void setTitle(String title) {
		this.titleText.setText(title);
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}
}