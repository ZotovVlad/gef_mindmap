package com.itemis.gef.tutorial.mindmap.parts;

import java.util.Optional;

import org.eclipse.gef.common.adapt.IAdaptable;
import org.eclipse.gef.fx.anchors.IAnchor;
import org.eclipse.gef.fx.anchors.StaticAnchor;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;

import com.google.inject.Provider;
import com.itemis.gef.tutorial.mindmap.visuals.MindMapConnectionVisual;
import com.itemis.gef.tutorial.mindmap.visuals.MindMapNodeVisual;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.TextInputDialog;

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

		if (flagDefaultAnchor) {
			staticAnchor = new StaticAnchor(anchorage, ((MindMapNodeVisual) anchorage).getPoints().get(0));
		} else {
			Optional<String> result = null;
			do {
				TextInputDialog dialog = new TextInputDialog();
				dialog.setTitle("Укажите номер точки");
				dialog.setGraphic(null);
				dialog.setHeaderText("Укажите номер точки(от 1 до 8):");

				result = dialog.showAndWait();
			} while ((Integer.parseInt(result.get()) > 8) || (Integer.parseInt(result.get()) < 1));

			staticAnchor = new StaticAnchor(anchorage,
					((MindMapNodeVisual) anchorage).getPoints().get(Integer.parseInt(result.get()) - 1));

		}

		// }
		return staticAnchor;
	}
}