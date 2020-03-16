/*******************************************************************************
 * Copyright (c) 2014, 2017 itemis AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Alexander Nyßen (itemis AG) - initial API and implementation
 *
 *******************************************************************************/
package com.itemis.gef.tutorial.mindmap.testconnection.parts;

import java.util.Map;

import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IContentPartFactory;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.itemis.gef.tutorial.mindmap.testconnection.model.GeometricCurve;
import com.itemis.gef.tutorial.mindmap.testconnection.model.GeometricShape;

import javafx.scene.Node;

public class MvcLogoExampleContentPartFactory implements IContentPartFactory {

	@Inject
	private Injector injector;

	@Override
	public IContentPart<? extends Node> createContentPart(Object content, Map<Object, Object> contextMap) {
		if (content instanceof GeometricShape) {
			return injector.getInstance(GeometricShapePart.class);
		} else if (content instanceof GeometricCurve) {
			return injector.getInstance(GeometricCurvePart.class);
		} else {
			throw new IllegalArgumentException(content.getClass().toString());
		}
	};

}
