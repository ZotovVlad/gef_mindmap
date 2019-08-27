package com.itemis.gef.tutorial.mindmap.parts.feedback;

import java.util.ArrayList;

import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.fx.anchors.IAnchor;
import org.eclipse.gef.fx.anchors.StaticAnchor;
import org.eclipse.gef.fx.nodes.Connection;
import org.eclipse.gef.geometry.convert.fx.FX2Geometry;
import org.eclipse.gef.geometry.convert.fx.Geometry2FX;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.mvc.fx.parts.AbstractFeedbackPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;

import com.google.common.reflect.TypeToken;
import com.google.inject.Provider;
import com.itemis.gef.tutorial.mindmap.parts.MindMapNodePart;
import com.itemis.gef.tutorial.mindmap.visuals.MindMapConnectionVisual;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * Part representing a connection creation feedback, anchoring to an
 * {@link MindMapNodePart} and the mouseposition.
 *
 */
public class CreateConnectionFeedbackPart extends AbstractFeedbackPart<Connection> {

	private class MousePositionAnchor extends StaticAnchor implements EventHandler<MouseEvent> {

		boolean connectionIsNotStart = false;

		public MousePositionAnchor(Point referencePositionInScene) {
			super(referencePositionInScene);
		}

		public void dispose() {
			// listen to any mouse move and reposition the anchor
			getRoot().getVisual().getScene().removeEventHandler(MouseEvent.MOUSE_MOVED, this);
			getRoot().getVisual().getScene().removeEventHandler(MouseEvent.MOUSE_PRESSED, this);
		}

		@Override
		public void handle(MouseEvent event) {
			Point v = new Point(event.getSceneX(), event.getSceneY());
			referencePositionProperty().setValue(v);
			if (event.isPrimaryButtonDown()) {
				if (connectionIsNotStart) {
					getVisual().addControlPoint(index++,
							FX2Geometry.toPoint(getVisual().sceneToLocal(event.getSceneX(), event.getSceneY())));
					points.add(v);

				}
				connectionIsNotStart = true;
			}
		}

		public void init() {
			// listen to any mouse move and reposition the anchor
			getRoot().getVisual().getScene().addEventHandler(MouseEvent.MOUSE_MOVED, this);
			getRoot().getVisual().getScene().addEventHandler(MouseEvent.MOUSE_PRESSED, this);
		}

	}

	public static ArrayList<Point> points = new ArrayList<>();

	private int index = 0;

	@Override
	public void doAttachToAnchorageVisual(IVisualPart<? extends Node> anchorage, String role) {
		// find a anchor provider, which must be registered in the module
		// be aware to use the right interfaces (Provider is used a lot)
		@SuppressWarnings("serial")
		Provider<? extends IAnchor> adapter = anchorage
				.getAdapter(AdapterKey.get(new TypeToken<Provider<? extends IAnchor>>() {
				}));
		if (adapter == null) {
			throw new IllegalStateException("No adapter for <" + anchorage.getClass() + "> found.");
		}
		// set the start anchor
		IAnchor anchor = adapter.get();
		getVisual().setStartAnchor(anchor);

		MousePositionAnchor endAnchor = new MousePositionAnchor(
				FX2Geometry.toPoint(getVisual().localToScene(Geometry2FX.toFXPoint(getVisual().getStartPoint()))));
		endAnchor.init();
		getVisual().setEndAnchor(endAnchor);
	}

//	@Override
//	protected Node doCreateVisual() {
//		return new MindMapConnectionVisual();
//	}

	@Override
	protected Connection doCreateVisual() {
		return new MindMapConnectionVisual(points);
//		return null;
	}

//	@Override
//	protected void doRefreshVisual(Node visual) {
//	}

	@Override
	protected void doDetachFromAnchorageVisual(IVisualPart<? extends Node> anchorage, String role) {
		getVisual().setStartPoint(getVisual().getStartPoint());
		((MousePositionAnchor) getVisual().getEndAnchor()).dispose();
		getVisual().setEndPoint(getVisual().getEndPoint());

	}

	@Override
	protected void doRefreshVisual(Connection visual) {
		// TODO Auto-generated method stub
		System.out.println();
	}

	@Override
	public MindMapConnectionVisual getVisual() {
		return (MindMapConnectionVisual) super.getVisual();
	}

}