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
	private boolean flag = false;

	@Override
	public ReadOnlyObjectProperty<IVisualPart<? extends Node>> adaptableProperty() {
		return null;
	}

	@Override
	public IAnchor get() {
		// if (staticAnchor == null) {
		MindMapNodeVisual anchorage = (MindMapNodeVisual) getAdaptable().getVisual();
		List<Rectangle> pointBox = anchorage.getPointsBox();
		for (Rectangle rectangle : pointBox) {
			rectangle.setOnMouseEntered((event) -> {
				point.setLocation(event.getX(), event.getY());
				flag = true;
			});
		}
		if (!flag) {
			if (anchorage.getTitleText().getText().toString().equals("START")) {
				point.setLocation(170, 170 / 2);
			} else if (anchorage.getTitleText().getText().toString().equals("FINISH")) {
				point.setLocation(0, 170 / 2);
			}
		}

		staticAnchor = new StaticAnchor(anchorage, point);// anchorage.pointConnection);

		// }
		return staticAnchor;
	}
}