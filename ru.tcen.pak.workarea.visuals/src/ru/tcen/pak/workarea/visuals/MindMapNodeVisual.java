package ru.tcen.pak.workarea.visuals;

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
	private static final int SIZERECTANGLEBOX = 10;

	private double NODE_WIDTH = 170;
	private double NODE_HEIGH = 170;

	private TextFlow descriptionFlow;
	private Text descriptionText;
	private Text nameText;
	private GeometryNode<RoundedRectangle> shape;
	private VBox labelVBox;
	private Image descriptionImage;
	private String urlImage = "null.png";
	private int quantityImage = 0;
	private List<Point> points = new ArrayList<>();
	private List<Rectangle> pointsBox = new ArrayList<>();
	private Color color;
	private boolean isStatic;
	private boolean connectionOnlyRight;
	private int quantityRectangleConnection;

	public List<Point> pointConnection = new ArrayList<>();

	public MindMapNodeVisual(int quantityRectangleConnection, boolean connectionOnlyRight, boolean isStatic) {

		// create background shape
		shape = new GeometryNode<>(new RoundedRectangle(0, 0, NODE_WIDTH, NODE_HEIGH, 8, 8));
		shape.setFill(Color.PALEVIOLETRED);
		shape.setStroke(Color.BLACK);

		// create vertical box for title and description
		labelVBox = new VBox(VERTICAL_SPACING);
		labelVBox.setPadding(new Insets(VERTICAL_PADDING, HORIZONTAL_PADDING, VERTICAL_PADDING, HORIZONTAL_PADDING));

		// ensure shape and labels are resized to fit this visual
		shape.prefWidthProperty().bind(widthProperty());
		shape.prefHeightProperty().bind(heightProperty());
		labelVBox.prefWidthProperty().bind(widthProperty());
		labelVBox.prefHeightProperty().bind(heightProperty());

		// create name text
		nameText = new Text();
		nameText.setTextOrigin(VPos.TOP);

		// create description text
		descriptionText = new Text();
		descriptionText.setTextOrigin(VPos.TOP);

		// use TextFlow to enable wrapping of the description text within the
		// label bounds
		descriptionFlow = new TextFlow(descriptionText);
		// only constrain the width, so that the height is computed in
		// dependence on the width
		descriptionFlow.maxWidthProperty().bind(shape.widthProperty().subtract(HORIZONTAL_PADDING * 2));

		// vertically lay out title and description
		labelVBox.getChildren().addAll(nameText, descriptionFlow);

		// ensure title is always visible (see also #computeMinWidth(double) and
		// #computeMinHeight(double) methods)
		setMinSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);

		// wrap shape and VBox in Groups so that their bounds-in-parent is
		// considered when determining the layout-bounds of this visual
		getChildren().addAll(new Group(shape), new Group(labelVBox));

		this.setQuantityRectangleConnection(quantityRectangleConnection);
		this.setConnectionOnlyRight(connectionOnlyRight);
		this.setStatic(isStatic);

	}

	@Override
	public double computeMinHeight(double height) {
		// ensure title is always visible
		// descriptionFlow.minHeight(width) +
		// titleText.getLayoutBounds().getHeight() + VERTICAL_PADDING * 2;
		// return labelVBox.minHeight(width);
		this.NODE_HEIGH = labelVBox.minHeight(height);
		return labelVBox.minHeight(height);// + nameText.minHeight(width) +
											// descriptionText.minHeight(width);
	}

	@Override
	public double computeMinWidth(double width) {
		// ensure title is always visible
		this.NODE_WIDTH = labelVBox.minWidth(width);
		return labelVBox.minWidth(width);
	}

	@Override
	protected double computePrefHeight(double width) {
		return minHeight(width);
	}

	@Override
	protected double computePrefWidth(double height) {
		return minWidth(height);
	}

	public Color getColor() {
		return color;
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

	public Text getNameText() {
		return nameText;
	}

	public List<Point> getPoints() {
		return points;
	}

	public List<Rectangle> getPointsBox() {
		return pointsBox;
	}

	public int getQuantityRectangleConnection() {
		return quantityRectangleConnection;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public boolean isConnectionOnlyRight() {
		return connectionOnlyRight;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public void paintingRectangleConnection() {
		Rectangle rec = null;

		if (quantityRectangleConnection == 6) {
			points.addAll(
					Arrays.asList(new Point(0, 0), new Point(NODE_WIDTH, 0), new Point(NODE_WIDTH, NODE_HEIGH / 2),
							new Point(NODE_WIDTH, NODE_HEIGH), new Point(0, NODE_HEIGH), new Point(0, NODE_HEIGH / 2)));
			for (int i = 0; i < points.size(); i++) {
				switch (i) {
				case 0:
					rec = new Rectangle(points.get(i).x, points.get(i).y, SIZERECTANGLEBOX, SIZERECTANGLEBOX);
					rec.setOnMouseEntered((event) -> {
						pointConnection.add(new Point(event.getX(), event.getY()));
					});
					break;
				case 1:
					rec = new Rectangle(points.get(i).x - SIZERECTANGLEBOX, points.get(i).y, SIZERECTANGLEBOX,
							SIZERECTANGLEBOX);
					rec.setOnMouseEntered((event) -> {
						pointConnection.add(new Point(event.getX(), event.getY()));
					});
					break;
				case 2:
					rec = new Rectangle(points.get(i).x - SIZERECTANGLEBOX, points.get(i).y - SIZERECTANGLEBOX / 2,
							SIZERECTANGLEBOX, SIZERECTANGLEBOX);
					rec.setOnMouseEntered((event) -> {
						pointConnection.add(new Point(event.getX(), event.getY()));
					});
					break;
				case 3:
					rec = new Rectangle(points.get(i).x - SIZERECTANGLEBOX, points.get(i).y - SIZERECTANGLEBOX,
							SIZERECTANGLEBOX, SIZERECTANGLEBOX);
					rec.setOnMouseEntered((event) -> {
						pointConnection.add(new Point(event.getX(), event.getY()));
					});
					break;
				case 4:
					rec = new Rectangle(points.get(i).x, points.get(i).y - SIZERECTANGLEBOX, SIZERECTANGLEBOX,
							SIZERECTANGLEBOX);
					rec.setOnMouseEntered((event) -> {
						pointConnection.add(new Point(event.getX(), event.getY()));
					});
					break;
				case 5:
					rec = new Rectangle(points.get(i).x, points.get(i).y - SIZERECTANGLEBOX / 2, SIZERECTANGLEBOX,
							SIZERECTANGLEBOX);
					rec.setOnMouseEntered((event) -> {
						pointConnection.add(new Point(event.getX(), event.getY()));
					});
					break;
				default:
					break;
				}
				pointsBox.add(rec);
			}
			getChildren().addAll(pointsBox);
		} else if (quantityRectangleConnection == 1) {
			points.addAll(
					Arrays.asList(new Point(0, 0), new Point(NODE_WIDTH, 0), new Point(NODE_WIDTH, NODE_HEIGH / 2),
							new Point(NODE_WIDTH, NODE_HEIGH), new Point(0, NODE_HEIGH), new Point(0, NODE_HEIGH / 2)));
			if (connectionOnlyRight) {
				rec = new Rectangle(points.get(2).x - SIZERECTANGLEBOX, points.get(2).y - SIZERECTANGLEBOX / 2,
						SIZERECTANGLEBOX, SIZERECTANGLEBOX);
				rec.setOnMouseEntered((event) -> {
					pointConnection.add(new Point(event.getX(), event.getY()));
				});
				pointsBox.add(rec);
			} else {
				rec = new Rectangle(points.get(5).x, points.get(5).y - SIZERECTANGLEBOX / 2, SIZERECTANGLEBOX,
						SIZERECTANGLEBOX);
				rec.setOnMouseEntered((event) -> {
					pointConnection.add(new Point(event.getX(), event.getY()));
				});
				pointsBox.add(rec);
			}
			getChildren().addAll(pointsBox);
		} else if (quantityRectangleConnection == -1) {
			points.addAll(Arrays.asList(new Point(0, 0), new Point(NODE_WIDTH, 0),
					new Point(NODE_WIDTH, NODE_HEIGH / 3 * 1), new Point(NODE_WIDTH, NODE_HEIGH / 3 * 2),
					new Point(NODE_WIDTH, NODE_HEIGH), new Point(0, NODE_HEIGH)));
			for (int i = 0; i < points.size(); i++) {
				switch (i) {
				case 0:
					rec = new Rectangle(points.get(i).x, points.get(i).y, SIZERECTANGLEBOX, SIZERECTANGLEBOX);
					rec.setOnMouseEntered((event) -> {
						pointConnection.add(new Point(event.getX(), event.getY()));
					});
					break;
				case 1:
					rec = new Rectangle(points.get(i).x - SIZERECTANGLEBOX, points.get(i).y, SIZERECTANGLEBOX,
							SIZERECTANGLEBOX);
					rec.setOnMouseEntered((event) -> {
						pointConnection.add(new Point(event.getX(), event.getY()));
					});
					break;
				case 2:
					rec = new Rectangle(points.get(i).x - SIZERECTANGLEBOX, points.get(i).y - SIZERECTANGLEBOX / 2,
							SIZERECTANGLEBOX, SIZERECTANGLEBOX);
					rec.setOnMouseEntered((event) -> {
						pointConnection.add(new Point(event.getX(), event.getY()));
					});
					break;
				case 3:
					rec = new Rectangle(points.get(i).x - SIZERECTANGLEBOX, points.get(i).y - SIZERECTANGLEBOX,
							SIZERECTANGLEBOX, SIZERECTANGLEBOX);
					rec.setOnMouseEntered((event) -> {
						pointConnection.add(new Point(event.getX(), event.getY()));
					});
					break;
				case 4:
					rec = new Rectangle(points.get(i).x - SIZERECTANGLEBOX, points.get(i).y - SIZERECTANGLEBOX,
							SIZERECTANGLEBOX, SIZERECTANGLEBOX);
					rec.setOnMouseEntered((event) -> {
						pointConnection.add(new Point(event.getX(), event.getY()));
					});
					break;
				case 5:
					rec = new Rectangle(points.get(i).x, points.get(i).y - SIZERECTANGLEBOX, SIZERECTANGLEBOX,
							SIZERECTANGLEBOX);
					rec.setOnMouseEntered((event) -> {
						pointConnection.add(new Point(event.getX(), event.getY()));
					});
					break;
				default:
					break;
				}
				pointsBox.add(rec);
			}
			getChildren().addAll(pointsBox);
		}
	}

	public void setColor(Color color) {
		this.color = color;
		shape.setFill(color);
	}

	public void setConnectionOnlyRight(boolean connectionOnlyRight) {
		this.connectionOnlyRight = connectionOnlyRight;
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

	public void setName(String name) {
		this.nameText.setText(name);
	}

	public void setQuantityRectangleConnection(int quantityRectangleConnection) {
		this.quantityRectangleConnection = quantityRectangleConnection;
	}

	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}
}