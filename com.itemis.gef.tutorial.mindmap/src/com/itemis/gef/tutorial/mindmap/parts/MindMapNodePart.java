package com.itemis.gef.tutorial.mindmap.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.gef.geometry.planar.Dimension;
import org.eclipse.gef.geometry.planar.Rectangle;
import org.eclipse.gef.mvc.fx.parts.AbstractContentPart;
import org.eclipse.gef.mvc.fx.parts.IResizableContentPart;
import org.eclipse.gef.mvc.fx.parts.ITransformableContentPart;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import com.itemis.gef.tutorial.mindmap.model.MindMapNode;
import com.itemis.gef.tutorial.mindmap.visuals.MindMapNodeVisual;

import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Translate;

/**
 * the {@link MindMapNodePart} is responsible to create and update the
 * {@link MindMapNodeVisual} for a instance of the {@link MindMapNode}.
 *
 */
public class MindMapNodePart extends AbstractContentPart<MindMapNodeVisual> implements
		ITransformableContentPart<MindMapNodeVisual>, IResizableContentPart<MindMapNodeVisual>, PropertyChangeListener {

	private static Set<MindMapNode> mindMapNode = new HashSet<>();
	private static Set<MindMapNodeVisual> mindMapNodeVisual = new HashSet<>();

	private static Color greenNode = Color.GREENYELLOW;
	private static Color redNode = Color.PALEVIOLETRED;

	public int quantityRectangleConnection;
	public boolean connectionOnlyRight;

	public void deleteColorContent() {
		Set<String> titlesIncomingConnectionNode = null;
		Set<String> titlesOutgoingConnectionNode = null;
		for (MindMapNode mindMapNode2 : MindMapNodePart.mindMapNode) {
			if (this.getVisual().getTitleText().getText().toString().equals(mindMapNode2.getTitle())) {
				MindMapNode mindMapNodeNext = null;
				while (true) {
					mindMapNode2.getIncomingConnections().get(0).getSource().deleteTitleAtOutgoingConnection();
				}
//				while (true) {
//					mindMapNode2.getOutgoingConnections().get(0).getTarget().deleteTitleAtIncomingConnection();
//				}
			}
		}
		refreshVisual();
	}

	@Override
	protected void doActivate() {
		super.doActivate();
		getContent().addPropertyChangeListener(this);
	}

	@Override
	protected MindMapNodeVisual doCreateVisual() {
		propertySet();
		return new MindMapNodeVisual(quantityRectangleConnection, connectionOnlyRight);
	}

	@Override
	protected void doDeactivate() {
		getContent().removePropertyChangeListener(this);
		super.doDeactivate();
	}

	@Override
	protected SetMultimap<? extends Object, String> doGetContentAnchorages() {
		// Nothing to anchor to
		return HashMultimap.create();
	}

	@Override
	protected List<? extends Object> doGetContentChildren() {
		// we don't have any children.
		return Collections.emptyList();
	}

	@Override
	protected void doRefreshVisual(MindMapNodeVisual visual) {

		MindMapNode node = getContent();

		visual.setTitle(node.getTitle());
		visual.setDescription(node.getDescription());
		if (node.getImage() != null) {
			visual.setImage(node.getImage());
		}

		for (MindMapNode mindMapNode : MindMapNodePart.mindMapNode) {
			if (mindMapNode.isStarted() && mindMapNode.isFinished()) {
				mindMapNode.setColor(greenNode);
				for (MindMapNodeVisual mindMapNodeVisual : MindMapNodePart.mindMapNodeVisual) {
					if (mindMapNodeVisual.getTitleText().getText().toString().equals(mindMapNode.getTitle())) {
						mindMapNodeVisual.setColor(greenNode);
					}
				}
			} else {
				mindMapNode.setColor(redNode);
				for (MindMapNodeVisual mindMapNodeVisual : MindMapNodePart.mindMapNodeVisual) {
					if (mindMapNodeVisual.getTitleText().getText().toString().equals(mindMapNode.getTitle())) {
						mindMapNodeVisual.setColor(redNode);
					}
				}
			}
		}

		MindMapNodePart.mindMapNode.add(node);
		MindMapNodePart.mindMapNodeVisual.add(visual);

		// use the IResizableContentPart API to resize the visual
		setVisualSize(getContentSize());

		// use the ITransformableContentPart API to position the visual
		setVisualTransform(getContentTransform());
	}

	@Override
	public MindMapNode getContent() {
		return (MindMapNode) super.getContent();
	}

	@Override
	public Dimension getContentSize() {
		return getContent().getBounds().getSize();
	}

	@Override
	public Affine getContentTransform() {
		Rectangle bounds = getContent().getBounds();
		return new Affine(new Translate(bounds.getX(), bounds.getY()));
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		String prop = event.getPropertyName();
		if (MindMapNode.PROP_COLOR.equals(prop) || MindMapNode.PROP_DESCRIPTION.equals(prop)
				|| MindMapNode.PROP_TITLE.equals(prop) || MindMapNode.PROP_IMAGE.equals(prop)) {
			refreshVisual();
		}
	}

	private void propertySet() {
		MindMapNode node = getContent();
		switch (node.getTitle()) {
		case "START":
			quantityRectangleConnection = 1;
			connectionOnlyRight = true;
			break;
		case "FINISH":
			quantityRectangleConnection = 1;
			connectionOnlyRight = false;
			break;
		case "New Node":
			quantityRectangleConnection = 0;
			break;
		case "New Connection":
			quantityRectangleConnection = 0;
			break;
		default:
			quantityRectangleConnection = 6;
			break;
		}
	}

	@Override
	public void setContentSize(Dimension totalSize) {
		// storing the new size
		getContent().getBounds().setSize(totalSize);
	}

	@Override
	public void setContentTransform(Affine totalTransform) {
		// storing the new position
		Rectangle bounds = getContent().getBounds().getCopy();
		bounds.setX(totalTransform.getTx());
		bounds.setY(totalTransform.getTy());
		getContent().setBounds(bounds);
	}

	@Override
	public void setVisualSize(Dimension totalSize) {
		IResizableContentPart.super.setVisualSize(totalSize);
		// perform layout pass to apply size
		getVisual().getParent().layout();
	}
}