package com.itemis.gef.tutorial.mindmap.parts;

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
	MindMapNodeVisual anchorage;

	@Override
	public ReadOnlyObjectProperty<IVisualPart<? extends Node>> adaptableProperty() {
		return null;
	}

	@Override
	public IAnchor get() {
		// if (staticAnchor == null) {
		MindMapNodeVisual mindMapNodeVisual = (MindMapNodeVisual) getAdaptable().getVisual();
		if (mindMapNodeVisual.getTitleText().getText().toString().equals("START")) {
			mindMapNodeVisual.pointConnection.add(new Point(170, 170 / 2));
		} else if (mindMapNodeVisual.getTitleText().getText().toString().equals("FINISH")) {
			mindMapNodeVisual.pointConnection.add(new Point(0, 170 / 2));
		}
		if (mindMapNodeVisual.pointConnection.size() == 0) {
			staticAnchor = new StaticAnchor(mindMapNodeVisual, new Point(5, 5));
			return staticAnchor;
		} else {
			staticAnchor = new StaticAnchor(mindMapNodeVisual,
					mindMapNodeVisual.pointConnection.get(mindMapNodeVisual.pointConnection.size() - 1));
			return staticAnchor;
		}
	}
}