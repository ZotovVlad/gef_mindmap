package ru.tcen.pak.workarea.parts;

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

import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Translate;
import ru.tcen.pak.workarea.model.MindMapNode;
import ru.tcen.pak.workarea.visuals.MindMapNodeVisual;

/**
 * the {@link MindMapNodePart} is responsible to create and update the
 * {@link MindMapNodeVisual} for a instance of the {@link MindMapNode}.
 *
 */
public class MindMapNodePart extends AbstractContentPart<MindMapNodeVisual> implements
		ITransformableContentPart<MindMapNodeVisual>, IResizableContentPart<MindMapNodeVisual>, PropertyChangeListener {

	public static Set<MindMapNode> mindMapNode = new HashSet<>();
	public static Set<MindMapNodeVisual> mindMapNodeVisual = new HashSet<>();

	private static Color greenNode = Color.GREENYELLOW; // the color of the node if it is in a chain
	private static Color redNode = Color.PALEVIOLETRED; // the color of the node if it is not in a chain

	public int quantityRectangleConnection;
	public boolean connectionOnlyRight;
	public boolean isStatic;

	public void deleteColorContent() {
		MindMapNode mindMapNodeDeleted = null;
		for (MindMapNode mindMapNode2 : MindMapNodePart.mindMapNode) {
			if (this.getVisual().getNameText().getText().toString().equals(mindMapNode2.getName())) {

				mindMapNodeDeleted = mindMapNode2;

				if (!(mindMapNode2.getIncomingConnections().isEmpty())) {
					MindMapNode mindMapNodeNext = mindMapNode2;
					// reset all nodes until node START
					while (true) {
						if (mindMapNodeNext.getName().equals("START")
								|| mindMapNodeNext.getIncomingConnections().isEmpty()) {
							break;
						} else {
							if (!(mindMapNodeNext.getIncomingConnections().isEmpty())) {
								mindMapNodeNext = mindMapNodeNext.getIncomingConnections().get(0).getSource();
							}
							mindMapNodeNext.deleteTitleAtOutgoingConnection();
						}
					}
				}

				if (!(mindMapNode2.getOutgoingConnections().isEmpty())) {
					MindMapNode mindMapNodeNext = mindMapNode2;
					// reset all nodes until node FINISH
					while (true) {
						if (mindMapNodeNext.getName().equals("FINISH")
								|| mindMapNodeNext.getOutgoingConnections().isEmpty()) {
							break;
						} else {
							if (!(mindMapNodeNext.getOutgoingConnections().isEmpty())) {
								mindMapNodeNext = mindMapNodeNext.getOutgoingConnections().get(0).getTarget();
							}
							mindMapNodeNext.deleteTitleAtIncomingConnection();
						}
					}
				}

			}
		}
		mindMapNode.remove(mindMapNodeDeleted); // update collections MindMapNode
		refreshVisual(); // for update all nodes in collections
	}

	@Override
	protected void doActivate() {
		super.doActivate();
		getContent().addPropertyChangeListener(this);
	}

	@Override
	protected MindMapNodeVisual doCreateVisual() {
		propertySet();
		MindMapNodeVisual visual = new MindMapNodeVisual(quantityRectangleConnection, connectionOnlyRight, isStatic);
		visual.rePaintingRectangleConnection();
		return visual;
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
	public void doRefreshVisual(MindMapNodeVisual visual) {

		MindMapNode node = getContent();

		visual.setName(node.getName());
		visual.setDescription(node.getDescription());
		if (node.getImage() != null) {
			visual.setImage(node.getImage());
		}
		visual.setColor(node.getColor());

//		for (MindMapNode mindMapNode : MindMapNodePart.mindMapNode) {
//			if (mindMapNode.isStarted() && mindMapNode.isFinished()) {
//				// if the node is in the chain
//				mindMapNode.setColor(greenNode);
//				for (MindMapNodeVisual mindMapNodeVisual : MindMapNodePart.mindMapNodeVisual) {
//					if (mindMapNodeVisual.getTitleText().getText().toString().equals(mindMapNode.getTitle())) {
//						mindMapNodeVisual.setColor(greenNode);
//					}
//				}
//			} else {
//				// if the node is not in the chain
//				mindMapNode.setColor(redNode);
//				for (MindMapNodeVisual mindMapNodeVisual : MindMapNodePart.mindMapNodeVisual) {
//					if (mindMapNodeVisual.getTitleText().getText().toString().equals(mindMapNode.getTitle())) {
//						mindMapNodeVisual.setColor(redNode);
//					}
//				}
//			}
//		}

		visual.rePaintingRectangleConnection();

		// use the IResizableContentPart API to resize the visual
		setVisualSize(getContentSize());
		// use the ITransformableContentPart API to position the visual
		setVisualTransform(getContentTransform());

		// added each node in collection for update each node
		MindMapNodePart.mindMapNode.add(node);
		// added each node visual in collection for update each node visual
		MindMapNodePart.mindMapNodeVisual.add(visual);
	}

	@Override
	public MindMapNode getContent() {
		return (MindMapNode) super.getContent();
	}

	@Override
	public Dimension getContentSize() {
		for (MindMapNodeVisual mindMapNodeVisual : mindMapNodeVisual) {
			if (mindMapNodeVisual.getNameText().getText().equals(getContent().getName())) {
				mindMapNodeVisual.rePaintingRectangleConnection();
			}
		}
		return getContent().getBounds().getSize();
	}

	@Override
	public Affine getContentTransform() {
		Rectangle bounds = new Rectangle();
		bounds = getContent().getBounds();
		return new Affine(new Translate(bounds.getX(), bounds.getY()));

	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		String prop = event.getPropertyName();
		if (MindMapNode.PROP_COLOR.equals(prop) || MindMapNode.PROP_DESCRIPTION.equals(prop)
				|| MindMapNode.PROP_IMAGE.equals(prop) || MindMapNode.PROP_NAME.equals(prop)
				|| MindMapNode.PROP_BOUNDS.equals(prop)) {
			refreshVisual();
		}
	}

	private void propertySet() {
		MindMapNode node = getContent();
		if (!(node.getName() == null)) {
			switch (node.getName()) {
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
			case "":
				quantityRectangleConnection = -1;
				break;
			default:
				quantityRectangleConnection = 6;
				break;
			}
		}

		if (node.isStatic()) {
			isStatic = true;
		} else {
			isStatic = false;
		}
	}

	@Override
	public void setContentSize(Dimension totalSize) {
		// storing the new size
		getContent().getBounds().setSize(totalSize);
	}

	@Override
	public void setContentTransform(Affine totalTransform) {
		Rectangle bounds = getContent().getBounds().getCopy();
		totalTransform.setTx(bounds.getX());
		totalTransform.setTy(bounds.getY());
		ITransformableContentPart.super.setVisualTransform(totalTransform);
	}

	@Override
	public void setVisualSize(Dimension totalSize) {
		IResizableContentPart.super.setVisualSize(totalSize);
		// perform layout pass to apply size
		getVisual().getParent().layout();
	}
}