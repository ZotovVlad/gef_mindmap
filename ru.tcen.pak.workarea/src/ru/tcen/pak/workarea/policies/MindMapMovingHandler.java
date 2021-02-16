package ru.tcen.pak.workarea.policies;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.gef.geometry.planar.Dimension;
import org.eclipse.gef.geometry.planar.Rectangle;
import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnDragHandler;
import org.eclipse.gef.mvc.fx.parts.IContentPart;

import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import ru.tcen.pak.workarea.model.MindMapNode;
import ru.tcen.pak.workarea.parts.MindMapNodePart;

public class MindMapMovingHandler extends AbstractHandler implements IOnDragHandler {

	static MindMapNode mindMapNodeTopMoved = new MindMapNode();
	static MindMapNode mindMapNodeBottomMoved = new MindMapNode();

	static List<MindMapNode> mindMapNodesAtField = new ArrayList<>();

	private double offsetMenu = 300; // offset generated Tool Palette

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
		if (!mindMapNodeTopMoved.isStatic()) {
			verifyAndMoveCoordinatesAtNodesAtField(e);
			if (mindMapNodeBottomMoved.getBounds() == null) {
				MindMapMovingHandler.mindMapNodeBottomMoved = mindMapNodeTopMoved;
			}

			mindMapNodeBottomMoved.setName(mindMapNodeTopMoved.getName());
			mindMapNodeTopMoved.setBounds(mindMapNodeBottomMoved.getBounds().getCopy());
		}

		MindMapMovingHandler.mindMapNodeTopMoved = new MindMapNode();
		MindMapMovingHandler.mindMapNodeBottomMoved = new MindMapNode();
		MindMapMovingHandler.mindMapNodesAtField = new ArrayList<>();
	}

	private List<MindMapNode> getDataFromScene() {
		List<MindMapNode> dataFromScene = new ArrayList<>();
		Map<Object, IContentPart<? extends Node>> mindMap = getHost().getViewer().getContentPartMap();
		for (Object node : mindMap.keySet()) {
			if (node instanceof MindMapNode) {
				dataFromScene.add((MindMapNode) node);
			}
		}
		return dataFromScene;
	}

	@Override
	public void hideIndicationCursor() {
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
		MindMapMovingHandler.mindMapNodesAtField = getDataFromScene();
		MindMapMovingHandler.mindMapNodeTopMoved = ((MindMapNodePart) getHost()).getContent();
	}

	private void verifyAndMoveCoordinatesAtNodesAtField(MouseEvent e) {
		for (MindMapNode mindMapNode : mindMapNodesAtField) {
			if (mindMapNode.hashCode() != MindMapMovingHandler.mindMapNodeTopMoved.hashCode()) {
				Rectangle bounds = mindMapNode.getBounds();
				if (e.getX() - offsetMenu >= bounds.getX() && e.getX() - offsetMenu <= bounds.getX() + bounds.getWidth()
						&& e.getY() >= bounds.getY() && e.getY() <= bounds.getY() + bounds.getHeight()) {
					MindMapMovingHandler.mindMapNodeBottomMoved = mindMapNode;
//					System.out.println(mindMapNode.getName() + " bottom");
					break;
				}
			}
		}
	}

}
