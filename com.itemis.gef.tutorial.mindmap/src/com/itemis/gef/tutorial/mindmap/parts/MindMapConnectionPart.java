package com.itemis.gef.tutorial.mindmap.parts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.common.collections.ObservableSetMultimap;
import org.eclipse.gef.fx.anchors.IAnchor;
import org.eclipse.gef.fx.anchors.StaticAnchor;
import org.eclipse.gef.fx.nodes.Connection;
import org.eclipse.gef.fx.nodes.OrthogonalRouter;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.mvc.fx.parts.AbstractContentPart;
import org.eclipse.gef.mvc.fx.parts.IBendableContentPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import com.google.common.reflect.TypeToken;
import com.google.inject.Provider;
import com.itemis.gef.tutorial.mindmap.model.MindMapConnection;
import com.itemis.gef.tutorial.mindmap.parts.feedback.CreateConnectionFeedbackPart;
import com.itemis.gef.tutorial.mindmap.visuals.MindMapConnectionVisual;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * The mind map connection part is used the controller for th
 * {@link MindMapConnection}. It create the {@link MindMapConnectionVisual}
 * including the anchors for the connection.
 *
 */
public class MindMapConnectionPart extends AbstractContentPart<Connection> implements IBendableContentPart<Connection> {

	private static final String START_ROLE = "START";
	private static final String END_ROLE = "END";

	ArrayList<Point> points_t = new ArrayList<>();

	private Connection visual = null;

	@Override
	protected void doAttachToAnchorageVisual(IVisualPart<? extends Node> anchorage, String role) {
		// find a anchor provider, which must be registered in the module
		// be aware to use the right interfaces (Provider is used a lot)
		@SuppressWarnings("serial")
		Provider<? extends IAnchor> adapter = anchorage
				.getAdapter(AdapterKey.get(new TypeToken<Provider<? extends IAnchor>>() {
				}));
		if (adapter == null) {
			throw new IllegalStateException("No adapter  found for <" + anchorage.getClass() + "> found.");
		}
		IAnchor anchor = adapter.get();

		if (!SimpleMindMapAnchorProvider.isStaticAnchor) {
			getVisual().setRouter(new OrthogonalRouter());
			// getVisual().setInterpolator(new PolyBezierInterpolator());
		}
		if (role.equals(START_ROLE)) {
			getVisual().setStartAnchor(anchor);// , new OrthogonalProjectionStrategy());
		} else if (role.equals(END_ROLE)) {
			getVisual().setEndAnchor(anchor);
		} else {
			throw new IllegalArgumentException("Invalid role: " + role);
		}
	}

	@Override
	protected Connection doCreateVisual() {
		// Anchor anchor = adapter.get();
		for (int i = 0; i < CreateConnectionFeedbackPart.points.size(); i++) {
			points_t.add(CreateConnectionFeedbackPart.points.get(i));
		}
		CreateConnectionFeedbackPart.points.clear();
		MindMapConnectionVisual mmcv = new MindMapConnectionVisual(points_t);
		this.visual = mmcv;
		// mmcv.setRouter(new OrthogonalRouter());

		mmcv.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {

			// System.out.println(getAnchoragesUnmodifiable());

			// get MindMapNodeParts connection
			ObservableSetMultimap<IVisualPart<? extends Node>, String> obs = getAnchoragesUnmodifiable();
			Map<IVisualPart<? extends Node>, Collection<String>> map = obs.asMap();
			String str1 = map.keySet().toString();
			System.out.println(str1);

			System.out.println(mmcv.getAnchor(0));
			System.out.println(mmcv.getAnchor(1));
			List<IAnchor> anchors = new ArrayList<>();
			anchors.add(mmcv.getAnchor(0));
			anchors.add(new StaticAnchor(,new Point(50, 5)));
			mmcv.setAnchors(anchors);
			System.out.println(anchors.toString());

//			System.out.println(getVisualBendPoints());
			// List<BendPoint> bp = getVisualBendPoints();
			// bp.set(0, new BendPoint(bp.get(0).getContentAnchorage(), new Point(500,
			// 500)));
			// System.out.println(bp.toString());

			System.out.println(getVisualBendPoints());

			// not working
			// setVisualBendPoints(getVisualBendPoints());

			System.out.println();
			doRefreshVisual(this.visual);

		});

		return mmcv;

	}

	@Override
	protected void doDetachFromAnchorageVisual(IVisualPart<? extends Node> anchorage, String role) {
		if (role.equals(START_ROLE)) {
			getVisual().setStartPoint(getVisual().getStartPoint());
		} else if (role.equals(END_ROLE)) {
			getVisual().setEndPoint(getVisual().getEndPoint());
		} else {
			throw new IllegalArgumentException("Invalid role: " + role);
		}
	}

	@Override
	protected SetMultimap<? extends Object, String> doGetContentAnchorages() {
		SetMultimap<Object, String> anchorages = HashMultimap.create();

		anchorages.put(getContent().getSource(), START_ROLE);
		anchorages.put(getContent().getTarget(), END_ROLE);

		return anchorages;
	}

	@Override
	protected List<? extends Object> doGetContentChildren() {
		return Collections.emptyList();
	}

	@Override
	protected void doRefreshVisual(Connection visual) {

		this.visual = visual;

		BendPoint bp = new BendPoint(new Point(100, 100));

		// MindMapConnectionPart mmcp = (MindMapConnectionPart) visual;

		// System.out.println(getContent());
		System.out.println();
//		GeometricCurve content = getContent();
//
//		// TODO: extract router code and replace start/end/control point
//		// handling by calling
//		// setVisualBendPoints(getContentBendPoints());
//
//		List<Point> wayPoints = content.getWayPointsCopy();
//
//		// TODO: why is this needed??
//		AffineTransform transform = content.getTransform();
//		if (previousContent == null || (transform != null && !transform.equals(previousContent.getTransform())
//				|| transform == null && previousContent.getTransform() != null)) {
//			if (transform != null) {
//				Point[] transformedWayPoints = transform.getTransformed(wayPoints.toArray(new Point[] {}));
//				wayPoints = Arrays.asList(transformedWayPoints);
//			}
//		}
//
//		if (!getContentAnchoragesUnmodifiable().containsValue(SOURCE_ROLE)) {
//			visual.setStartPoint(wayPoints.remove(0));
//		} else {
//			visual.setStartPointHint(wayPoints.remove(0));
//		}
//
//		if (!getContentAnchoragesUnmodifiable().containsValue(TARGET_ROLE)) {
//			visual.setEndPoint(wayPoints.remove(wayPoints.size() - 1));
//		} else {
//			visual.setEndPointHint(wayPoints.remove(wayPoints.size() - 1));
//		}
//
//		if (!visual.getControlPoints().equals(wayPoints)) {
//			visual.setControlPoints(wayPoints);
//		}
//
//		// decorations
//		switch (content.getSourceDecoration()) {
//		case NONE:
//			if (visual.getStartDecoration() != null) {
//				visual.setStartDecoration(null);
//			}
//			break;
//		case CIRCLE:
//			if (visual.getStartDecoration() == null || !(visual.getStartDecoration() instanceof CircleHead)) {
//				visual.setStartDecoration(START_CIRCLE_HEAD);
//			}
//			break;
//		case ARROW:
//			if (visual.getStartDecoration() == null || !(visual.getStartDecoration() instanceof ArrowHead)) {
//				visual.setStartDecoration(START_ARROW_HEAD);
//			}
//			break;
//		}
//		switch (content.getTargetDecoration()) {
//		case NONE:
//			if (visual.getEndDecoration() != null) {
//				visual.setEndDecoration(null);
//			}
//			break;
//		case CIRCLE:
//			if (visual.getEndDecoration() == null || !(visual.getEndDecoration() instanceof CircleHead)) {
//				visual.setEndDecoration(END_CIRCLE_HEAD);
//			}
//			break;
//		case ARROW:
//			if (visual.getEndDecoration() == null || !(visual.getEndDecoration() instanceof ArrowHead)) {
//				visual.setEndDecoration(END_ARROW_HEAD);
//			}
//			break;
//		}
//
//		Shape startDecorationVisual = (Shape) visual.getStartDecoration();
//		Shape endDecorationVisual = (Shape) visual.getEndDecoration();
//
//		// stroke paint
//		if (((GeometryNode<?>) visual.getCurve()).getStroke() != content.getStroke()) {
//			((GeometryNode<?>) visual.getCurve()).setStroke(content.getStroke());
//		}
//		if (startDecorationVisual != null && startDecorationVisual.getStroke() != content.getStroke()) {
//			startDecorationVisual.setStroke(content.getStroke());
//		}
//		if (endDecorationVisual != null && endDecorationVisual.getStroke() != content.getStroke()) {
//			endDecorationVisual.setStroke(content.getStroke());
//		}
//
//		// stroke width
//		if (((GeometryNode<?>) visual.getCurve()).getStrokeWidth() != content.getStrokeWidth()) {
//			((GeometryNode<?>) visual.getCurve()).setStrokeWidth(content.getStrokeWidth());
//		}
//		if (startDecorationVisual != null && startDecorationVisual.getStrokeWidth() != content.getStrokeWidth()) {
//			startDecorationVisual.setStrokeWidth(content.getStrokeWidth());
//		}
//		if (endDecorationVisual != null && endDecorationVisual.getStrokeWidth() != content.getStrokeWidth()) {
//			endDecorationVisual.setStrokeWidth(content.getStrokeWidth());
//		}
//
//		// dashes
//		List<Double> dashList = new ArrayList<>(content.getDashes().length);
//		for (double d : content.getDashes()) {
//			dashList.add(d);
//		}
//		if (!((GeometryNode<?>) visual.getCurve()).getStrokeDashArray().equals(dashList)) {
//			((GeometryNode<?>) visual.getCurve()).getStrokeDashArray().setAll(dashList);
//		}
//
//		// connection router
//		if (content.getRoutingStyle().equals(RoutingStyle.ORTHOGONAL)) {
//			// re-attach visual in case we are connected to an anchor with
//			// non orthogonal computation strategy
//			if (getVisual().getStartAnchor() != null && getVisual().getStartAnchor() instanceof DynamicAnchor
//					&& !(((DynamicAnchor) getVisual().getStartAnchor())
//							.getComputationStrategy() instanceof OrthogonalProjectionStrategy)) {
//				IVisualPart<? extends Node> anchorage = getViewer().getVisualPartMap()
//						.get(getVisual().getStartAnchor().getAnchorage());
//				doDetachFromAnchorageVisual(anchorage, SOURCE_ROLE);
//				if (anchorage != this) {
//					// connected to anchorage
//					doAttachToAnchorageVisual(anchorage, SOURCE_ROLE);
//				}
//			}
//			if (getVisual().getEndAnchor() != null && getVisual().getEndAnchor() instanceof DynamicAnchor
//					&& !(((DynamicAnchor) getVisual().getEndAnchor())
//							.getComputationStrategy() instanceof OrthogonalProjectionStrategy)) {
//				IVisualPart<? extends Node> anchorage = getViewer().getVisualPartMap()
//						.get(getVisual().getEndAnchor().getAnchorage());
//				doDetachFromAnchorageVisual(anchorage, TARGET_ROLE);
//				if (anchorage != this) {
//					// connected to anchorage
//					doAttachToAnchorageVisual(anchorage, TARGET_ROLE);
//				}
//			}
//			if (!(visual.getInterpolator() instanceof PolylineInterpolator)) {
//				visual.setInterpolator(new PolylineInterpolator());
//			}
//			if (!(visual.getRouter() instanceof OrthogonalRouter)) {
//				visual.setRouter(new OrthogonalRouter());
//			}
//		} else {
//			// re-attach visual in case we are connected to an anchor with
//			// orthogonal computation strategy
//			if (getVisual().getStartAnchor() != null && getVisual().getStartAnchor() instanceof DynamicAnchor
//					&& ((DynamicAnchor) getVisual().getStartAnchor())
//							.getComputationStrategy() instanceof OrthogonalProjectionStrategy) {
//				IVisualPart<? extends Node> anchorage = getViewer().getVisualPartMap()
//						.get(getVisual().getStartAnchor().getAnchorage());
//				doDetachFromAnchorageVisual(anchorage, SOURCE_ROLE);
//				doAttachToAnchorageVisual(anchorage, SOURCE_ROLE);
//			}
//			if (getVisual().getEndAnchor() != null && getVisual().getEndAnchor() instanceof DynamicAnchor
//					&& ((DynamicAnchor) getVisual().getEndAnchor())
//							.getComputationStrategy() instanceof OrthogonalProjectionStrategy) {
//				IVisualPart<? extends Node> anchorage = getViewer().getVisualPartMap()
//						.get(getVisual().getEndAnchor().getAnchorage());
//				doDetachFromAnchorageVisual(anchorage, TARGET_ROLE);
//				doAttachToAnchorageVisual(anchorage, TARGET_ROLE);
//			}
//			if (!(visual.getInterpolator() instanceof PolyBezierInterpolator)) {
//				visual.setInterpolator(new PolyBezierInterpolator());
//			}
//			if (!(visual.getRouter() instanceof StraightRouter)) {
//				visual.setRouter(new StraightRouter());
//			}
//		}
//
//		previousContent = content;
//
//		// apply effect
//		super.doRefreshVisual(visual);
//		// nothing to do here
	}

	@Override
	public MindMapConnection getContent() {
		return (MindMapConnection) super.getContent();
	}

	@Override
	public List<BendPoint> getContentBendPoints() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MindMapConnectionVisual getVisual() {
		return (MindMapConnectionVisual) super.getVisual();
	}

	@Override
	public void setContentBendPoints(List<BendPoint> bendPoints) {
		// TODO Auto-generated method stub

	}

}