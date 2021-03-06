package ru.tcen.pak.workarea.parts;

import org.eclipse.gef.common.adapt.IAdaptable;
import org.eclipse.gef.fx.anchors.IAnchor;
import org.eclipse.gef.fx.anchors.StaticAnchor;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;

import com.google.inject.Provider;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.Node;
import ru.tcen.pak.workarea.visuals.MindMapConnectionVisual;
import ru.tcen.pak.workarea.visuals.MindMapNodeVisual;

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
		if (mindMapNodeVisual.getNameText().getText().toString().equals("START")) {
			mindMapNodeVisual.pointConnection.add(new Point(170, 170 / 2));
		} else if (mindMapNodeVisual.getNameText().getText().toString().equals("FINISH")) {
			mindMapNodeVisual.pointConnection.add(new Point(0, 170 / 2));
		}

		// return staticAnchor
		if (mindMapNodeVisual.pointConnection.size() == 0) {
			// return default-staticAnchor
			staticAnchor = new StaticAnchor(mindMapNodeVisual, new Point(5, 5));
			return staticAnchor;
		} else {
			// return point-staticAnchor
			staticAnchor = new StaticAnchor(mindMapNodeVisual,
					mindMapNodeVisual.pointConnection.get(mindMapNodeVisual.pointConnection.size() - 1));
			return staticAnchor;
		}
	}
}