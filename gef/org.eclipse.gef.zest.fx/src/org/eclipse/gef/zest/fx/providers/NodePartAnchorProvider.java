/*******************************************************************************
 * Copyright (c) 2016 itemis AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Alexander Nyßen (itemis AG) - initial API and implementation
 *******************************************************************************/
package org.eclipse.gef.zest.fx.providers;

import org.eclipse.gef.fx.anchors.DynamicAnchor;
import org.eclipse.gef.fx.utils.NodeUtils;
import org.eclipse.gef.geometry.planar.IGeometry;
import org.eclipse.gef.mvc.fx.providers.DefaultAnchorProvider;
import org.eclipse.gef.zest.fx.parts.NodePart;

import javafx.scene.Node;

/**
 * A specific {@link DefaultAnchorProvider} that reflects the node shape as the
 * outline to place anchors at.
 *
 * @author anyssen
 *
 */
public class NodePartAnchorProvider extends DefaultAnchorProvider {

	@Override
	protected IGeometry computeAnchorageReferenceGeometry(DynamicAnchor anchor) {
		final Node shape = ((NodePart) getAdaptable()).getShape();
		if (shape != null) {
			return NodeUtils.localToParent(shape, NodeUtils.getShapeOutline(shape));
		} else {
			return NodeUtils.getShapeOutline(((NodePart) getAdaptable()).getVisual());
		}
	}

}
