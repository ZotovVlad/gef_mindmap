package com.itemis.gef.tutorial.mindmap.parts;

import java.util.List;

import org.eclipse.gef.common.adapt.IAdaptable;
import org.eclipse.gef.fx.anchors.IAnchor;
import org.eclipse.gef.fx.anchors.StaticAnchor;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;

import com.google.inject.Provider;
import com.itemis.gef.tutorial.mindmap.visuals.MindMapConnectionVisual;
import com.itemis.gef.tutorial.mindmap.visuals.MindMapNodeVisual;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

/**
 * The {@link SimpleMindMapAnchorProvider} create an anchor for a
 * {@link MindMapConnectionVisual}.
 *
 * It is bound to an {@link MindMapNodePart} and will be called in the
 * {@link MindMapConnectionPart}.
 *
 */
public class SimpleMindMapAnchorProvider extends IAdaptable.Bound.Impl<IVisualPart<? extends Node>>
		implements Provider<IAnchor> {

	public static boolean flagDefaultAnchor;
	public static boolean flagPrimaryHandle;
	private StaticAnchor staticAnchor;
	private Point point = new Point();

	@Override
	public ReadOnlyObjectProperty<IVisualPart<? extends Node>> adaptableProperty() {
		return null;
	}

	@Override
	public IAnchor get() {
		// if (staticAnchor == null) {
		// get the visual from the host (MindMapNodePart)
		Node anchorage = getAdaptable().getVisual();
		// create a new anchor instance2

//		if (flagDefaultAnchor) {
//			staticAnchor = new StaticAnchor(anchorage, ((MindMapNodeVisual) anchorage).getPoints().get(0));
//		} else {
//			Optional<String> result = null;
//			do {
//				TextInputDialog dialog = new TextInputDialog();
//				dialog.setTitle("Укажите номер точки");
//				dialog.setGraphic(null);
//				dialog.setHeaderText("Укажите номер точки(от 1 до 8):");
//
//				result = dialog.showAndWait();
//			} while ((Integer.parseInt(result.get()) > 8) || (Integer.parseInt(result.get()) < 1));
//
//			staticAnchor = new StaticAnchor(anchorage,
//					((MindMapNodeVisual) anchorage).getPoints().get(Integer.parseInt(result.get()) - 1));
//
//		}
		List<Rectangle> pointBox = ((MindMapNodeVisual) anchorage).getPointsBox();
		for (Rectangle rectangle : pointBox) {
//			rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
//
//				@Override
//				public void handle(MouseEvent event) {
//					// TODO Auto-generated method stub
//					point = new Point();
//					point.setLocation(event.getX(), event.getY());
//					// point.setLocation(pointBox.get(5).getX() + 10 / 2, pointBox.get(5).getY() +
//					// 10 / 2);
//				}
//			});
			rectangle.setOnMouseEntered((event) -> {

				point = new Point();
				point.setLocation(event.getX(), event.getY());

			});

		}

		staticAnchor = new StaticAnchor(anchorage, point);

		// }
		return staticAnchor;
	}
}