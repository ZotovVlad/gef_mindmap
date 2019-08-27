package com.itemis.gef.tutorial.mindmap.visuals;

import java.util.List;

import org.eclipse.gef.fx.anchors.IAnchor;
import org.eclipse.gef.fx.anchors.StaticAnchor;
import org.eclipse.gef.fx.nodes.Connection;
import org.eclipse.gef.geometry.planar.Point;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class MindMapConnectionVisual extends Connection {

	public static class ArrowHead extends Polygon {
		public ArrowHead() {
			super(0, 0, 10, 3, 10, -3);
		}
	}

	public MindMapConnectionVisual() {
		ArrowHead endDecoration = new ArrowHead();
		endDecoration.setFill(Color.BLACK);
		setEndDecoration(endDecoration);
	}

	public MindMapConnectionVisual(List<Point> wayPoints) {
		ArrowHead endDecoration = new ArrowHead();
		if (!wayPoints.isEmpty()) {
			for (int i = 0; i < wayPoints.size(); i++) {
				this.addControlPoint(i, wayPoints.get(i));
			}
		}
		endDecoration.setFill(Color.BLACK);
		setEndDecoration(endDecoration);
	}

	@Override
	public void addControlPoint(int index, Point point) {
		if (point != null) {
			IAnchor anchor = new StaticAnchor(this, point);
			super.addControlAnchor(index, anchor);
		}
	}

}