package com.itemis.gef.tutorial.mindmap.policies;

import org.eclipse.gef.geometry.planar.Rectangle;
import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnHoverHandler;

import com.itemis.gef.tutorial.mindmap.model.MindMapNode;
import com.itemis.gef.tutorial.mindmap.parts.MindMapNodePart;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class MindMapMovingHandler extends AbstractHandler implements IOnHoverHandler {

	static MindMapNodePart newmindMapNodePart = new MindMapNodePart();
	static MindMapNode newmindMapNode = new MindMapNode();

	static MindMapNodePart oldmindMapNodePart = new MindMapNodePart();
	static MindMapNode oldmindMapNode = new MindMapNode();

	@Override
	public void hover(MouseEvent e) {
		MindMapNodePart host = (MindMapNodePart) getHost();
		MindMapNode node = host.getContent();
		oldmindMapNode = node;
		oldmindMapNodePart = host;
		node.setBounds(new Rectangle(500, 500, 500, 500));
		System.out.println();
	}

	@Override
	public void hoverIntent(Node hoverIntent) {
		// TODO Auto-generated method stub

	}

}
