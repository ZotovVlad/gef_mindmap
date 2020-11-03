package ru.tcen.pak.workarea.parts;

import java.util.List;

import org.eclipse.gef.fx.anchors.DynamicAnchor;

import com.google.common.collect.Lists;
import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;

import javafx.scene.Node;

public class MindMapNodeAnchors extends Node {
	/**
	 * quantity of anchors on the sides
	 */
	private int quantityAnchors;

	/**
	 * anchors on the sides
	 */
	private List<DynamicAnchor> anchors = Lists.newArrayList();

	public List<DynamicAnchor> getAnchors() {
		return anchors;
	}

	public int getQuantityAnchors() {
		return quantityAnchors;
	}

	@Override
	protected boolean impl_computeContains(double arg0, double arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BaseBounds impl_computeGeomBounds(BaseBounds arg0, BaseTransform arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected NGNode impl_createPeer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object impl_processMXNode(MXNodeAlgorithm arg0, MXNodeAlgorithmContext arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setAnchors(List<DynamicAnchor> anchors) {
		this.anchors = anchors;
	}

	public void setQuantityAnchors(int quantityAnchors) {
		this.quantityAnchors = quantityAnchors;
	}

}
