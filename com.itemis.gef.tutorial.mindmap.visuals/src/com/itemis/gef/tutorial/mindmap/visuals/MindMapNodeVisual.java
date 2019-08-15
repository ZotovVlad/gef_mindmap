package com.itemis.gef.tutorial.mindmap.visuals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.eclipse.gef.fx.nodes.GeometryNode;
import org.eclipse.gef.geometry.planar.RoundedRectangle;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class MindMapNodeVisual extends Region {

	private static final double HORIZONTAL_PADDING = 20d;
	private static final double VERTICAL_PADDING = 10d;
	private static final double VERTICAL_SPACING = 5d;

	private Text titleText;
	private TextFlow descriptionFlow;
	private Text descriptionText;
	private GeometryNode<RoundedRectangle> shape;
	private VBox labelVBox;
	private Image descriptionImage;
	private String urlImage = "Event-search-icon.png";
	private int quantityImage = 0;

	public MindMapNodeVisual() {
		// create background shape
		shape = new GeometryNode<>(new RoundedRectangle(0, 0, 70, 30, 8, 8));
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

		try {
			descriptionImage = new Image(new FileInputStream(urlImage));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
		if (this.quantityImage == 0) {
			ImageView iv1 = new ImageView();
			iv1.setImage(image);
			iv1.setFitWidth(100);
			HBox box = new HBox();
			box.getChildren().add(iv1);
			labelVBox.getChildren().add(box);
			this.descriptionImage = image;
			this.quantityImage = 1;
		} else {

		}
	}

	public void setTitle(String title) {
		this.titleText.setText(title);
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}
}