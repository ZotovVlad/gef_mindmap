package com.itemis.gef.tutorial.mindmap.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.geometry.planar.Dimension;
import org.eclipse.gef.geometry.planar.Rectangle;
import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnDragHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnHoverHandler;

import com.itemis.gef.tutorial.mindmap.JSON.ControllerJSON;
import com.itemis.gef.tutorial.mindmap.model.MindMapNode;
import com.itemis.gef.tutorial.mindmap.parts.MindMapNodePart;

import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class MindMapMovingHandler extends AbstractHandler implements IOnHoverHandler, IOnDragHandler {

	static MindMapNode mindMapNodeTopMoved = new MindMapNode();
	static MindMapNode mindMapNodeBottomMoved = new MindMapNode();

	static List<MindMapNode> mindMapNodesAtField = new ArrayList<>();

	@Override
	public void abortDrag() {
		// TODO Auto-generated method stub
	}

	@Override
	public void drag(MouseEvent e, Dimension delta) {
		// TODO Auto-generated method stub
	}

	@Override
	public void endDrag(MouseEvent e, Dimension delta) {
		if (mindMapNodeBottomMoved.getBounds() == null) {
			MindMapMovingHandler.mindMapNodeBottomMoved = mindMapNodeTopMoved;
		}
		Rectangle rectangle = mindMapNodeBottomMoved.getBounds();
		((MindMapNodePart) getHost()).getContent().setBounds(rectangle);
		MindMapMovingHandler.mindMapNodeTopMoved = new MindMapNode();
		MindMapMovingHandler.mindMapNodeBottomMoved = new MindMapNode();
	}

	@Override
	public void hideIndicationCursor() {
		// TODO Auto-generated method stub
	}

	@Override
	public void hover(MouseEvent e) {
		MindMapMovingHandler.mindMapNodesAtField = (List<MindMapNode>) (List<?>) ControllerJSON.mindMapNodesAtField;
		verifyAndMoveCoordinatesAtNodesAtField(e);
	}

	@Override
	public void hoverIntent(Node hoverIntent) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean showIndicationCursor(KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean showIndicationCursor(MouseEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void startDrag(MouseEvent e) {
		MindMapMovingHandler.mindMapNodeTopMoved = ((MindMapNodePart) getHost()).getContent();
	}

	private void verifyAndMoveCoordinatesAtNodesAtField(MouseEvent e) {
		for (MindMapNode mindMapNode : mindMapNodesAtField) {
			Rectangle bounds = mindMapNode.getBounds();
			if (/*
				 * mindMapNode.getTitle() != ((MindMapNodePart)
				 * getHost()).getContent().getTitle() &&
				 */
			e.getX() >= bounds.getX() && e.getX() <= bounds.getX() + bounds.getWidth() && e.getY() >= bounds.getY()
					&& e.getY() <= bounds.getY() + bounds.getHeight()) {
				MindMapMovingHandler.mindMapNodeBottomMoved = mindMapNode;
				System.out.println(mindMapNode.getTitle() + " bottom");
				break;
			}
		}
//		MindMapNodePart host = (MindMapNodePart) getHost();
//		MindMapNode node = host.getContent();
////		oldmindMapNode = node;
////		oldmindMapNodePart = host;
//		node.setBounds(new Rectangle(500, 500, 500, 500));
//		System.out.println();
	}

}
