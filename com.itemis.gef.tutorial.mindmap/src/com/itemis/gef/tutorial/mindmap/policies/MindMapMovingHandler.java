package com.itemis.gef.tutorial.mindmap.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnHoverHandler;

import com.itemis.gef.tutorial.mindmap.JSON.ControllerJSON;
import com.itemis.gef.tutorial.mindmap.model.MindMapNode;
import com.itemis.gef.tutorial.mindmap.parts.MindMapNodePart;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class MindMapMovingHandler extends AbstractHandler implements IOnHoverHandler {

	static MindMapNodePart newmindMapNodePart = new MindMapNodePart();
	static MindMapNode newmindMapNode = new MindMapNode();

	static MindMapNodePart oldmindMapNodePart = new MindMapNodePart();
	static MindMapNode oldmindMapNode = new MindMapNode();

	static List<MindMapNode> mindMapNodesAtField = new ArrayList<>();

	@Override
	public void hover(MouseEvent e) {
		MindMapMovingHandler.mindMapNodesAtField = (List<MindMapNode>) (List<?>) ControllerJSON.mindMapNodesAtField;
		verifyAndMoveCoordinatesAtNodesAtField();
	}

	@Override
	public void hoverIntent(Node hoverIntent) {
		// TODO Auto-generated method stub

	}

	private void verifyAndMoveCoordinatesAtNodesAtField() {
		MindMapNodePart host = (MindMapNodePart) getHost();
		MindMapNode node = host.getContent();
		oldmindMapNode = node;
		oldmindMapNodePart = host;
		// node.setBounds(new Rectangle(500, 500, 500, 500));
		System.out.println();
	}

}
