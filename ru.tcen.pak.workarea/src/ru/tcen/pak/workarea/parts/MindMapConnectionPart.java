package ru.tcen.pak.workarea.parts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.fx.anchors.IAnchor;
import org.eclipse.gef.fx.nodes.Connection;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.mvc.fx.parts.AbstractContentPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import com.google.common.reflect.TypeToken;
import com.google.inject.Provider;

import javafx.scene.Node;
import ru.tcen.pak.workarea.model.MindMapConnection;
import ru.tcen.pak.workarea.parts.feedback.CreateConnectionFeedbackPart;
import ru.tcen.pak.workarea.visuals.MindMapConnectionVisual;

/**
 * The mind map connection part is used the controller for th
 * {@link MindMapConnection}. It create the {@link MindMapConnectionVisual}
 * including the anchors for the connection.
 *
 */
public class MindMapConnectionPart extends AbstractContentPart<Connection> {

	private static final String START_ROLE = "START";
	private static final String END_ROLE = "END";

	ArrayList<Point> points_t = new ArrayList<>();

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

		if (role.equals(START_ROLE)) {
			getVisual().setStartAnchor(anchor);
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
		return new MindMapConnectionVisual(points_t);

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
		// nothing to do here
	}

	@Override
	public MindMapConnection getContent() {
		return (MindMapConnection) super.getContent();
	}

	@Override
	public MindMapConnectionVisual getVisual() {
		return (MindMapConnectionVisual) super.getVisual();
	}
}