package com.itemis.gef.tutorial.mindmap.parts;

import org.eclipse.gef.common.adapt.IAdaptable;
import org.eclipse.gef.fx.anchors.DynamicAnchor;
import org.eclipse.gef.fx.anchors.IAnchor;
import org.eclipse.gef.fx.anchors.ProjectionStrategy;
import org.eclipse.gef.fx.anchors.StaticAnchor;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;

import com.google.inject.Provider;
import com.itemis.gef.tutorial.mindmap.visuals.MindMapConnectionVisual;
import com.itemis.gef.tutorial.mindmap.visuals.MindMapNodeVisual;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.Node;

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

	private static final int SIZERECTANGLEBOX = 10;

	public static boolean isStaticAnchor = true;

	private DynamicAnchor dynamicAnchor;
	private StaticAnchor staticAnchor;

	@Override
	public ReadOnlyObjectProperty<IVisualPart<? extends Node>> adaptableProperty() {
		return null;
	}

	@Override
	public IAnchor get() {
		// get current MindMapNodeVisual
		MindMapNodeVisual mindMapNodeVisual = (MindMapNodeVisual) getAdaptable().getVisual();

		// default node visual "settings"
		if (mindMapNodeVisual.getTitleText().getText().toString().equals("START")) {
			mindMapNodeVisual.pointConnection.add(new Point(170, 170 / 2));
		} else if (mindMapNodeVisual.getTitleText().getText().toString().equals("FINISH")) {
			mindMapNodeVisual.pointConnection.add(new Point(0, 170 / 2));
		}

		if (isStaticAnchor) {
			// return staticAnchor
			if (mindMapNodeVisual.pointConnection.size() == 0) {
				// return default-staticAnchor
				// staticAnchor = new StaticAnchor(mindMapNodeVisual, new Point(5, 5));
				// return staticAnchor;
				dynamicAnchor = new DynamicAnchor(mindMapNodeVisual);
				return dynamicAnchor;
			} else {
				// return point-staticAnchor
//				staticAnchor = new StaticAnchor(mindMapNodeVisual,
//						mindMapNodeVisual.pointConnection.get(mindMapNodeVisual.pointConnection.size() - 1));
//				return staticAnchor;

				dynamicAnchor = new DynamicAnchor(mindMapNodeVisual);
				return dynamicAnchor;
			}
		} else {
			// return staticAnchor
			dynamicAnchor = new DynamicAnchor(mindMapNodeVisual, new ProjectionStrategy());
			// , new OrthogonalProjectionStrategy());
			return dynamicAnchor;
		}
	}
}