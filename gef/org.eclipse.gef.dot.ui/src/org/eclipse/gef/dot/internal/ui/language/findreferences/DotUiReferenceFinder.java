/*******************************************************************************
 * Copyright (c) 2018 itemis AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tamas Miklossy (itemis AG) - initial API and implementation (bug #531049)
 *
 *******************************************************************************/
package org.eclipse.gef.dot.internal.ui.language.findreferences;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.findReferences.IReferenceFinder;
import org.eclipse.xtext.resource.IReferenceDescription;
import org.eclipse.xtext.ui.editor.findrefs.DelegatingReferenceFinder;
import org.eclipse.xtext.util.IAcceptor;

public class DotUiReferenceFinder extends DelegatingReferenceFinder {

	private IReferenceFinder delegate;

	public DotUiReferenceFinder() {
		delegate = DotReferenceFinder.getInstance();
	}

	public void findAllReferences(Iterable<URI> targetURIs,
			ILocalResourceAccess localResourceAccess,
			IAcceptor<IReferenceDescription> acceptor,
			IProgressMonitor monitor) {
		delegate.findAllReferences(getConverter().fromIterable(targetURIs),
				localResourceAccess, getIndexData(), toAcceptor(acceptor),
				monitor);
	}
}
