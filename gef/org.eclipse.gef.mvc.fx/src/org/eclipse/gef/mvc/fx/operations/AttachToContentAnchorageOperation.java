/*******************************************************************************
 * Copyright (c) 2014, 2016 itemis AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Matthias Wienand (itemis AG) - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.gef.mvc.fx.operations;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.gef.mvc.fx.parts.IContentPart;

import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;

import javafx.scene.Node;

/**
 * The {@link AttachToContentAnchorageOperation} uses the {@link IContentPart}
 * API to attach an anchored to the given anchorage.
 *
 * @author mwienand
 *
 */
public class AttachToContentAnchorageOperation extends AbstractOperation
		implements ITransactionalOperation {

	private final IContentPart<? extends Node> anchored;
	private final Object contentAnchorage;
	private final String role;

	// initial content anchorages (for no-op test)
	private SetMultimap<Object, String> initialContentAnchorages;

	/**
	 * Creates a new {@link AttachToContentAnchorageOperation} to attach the
	 * given <i>anchored</i> {@link IContentPart} to the given
	 * <i>contentAnchorage</i> under the specified <i>role</i>, so that it will
	 * be returned by subsequent calls to
	 * {@link IContentPart#getContentAnchoragesUnmodifiable()}.
	 *
	 * @param anchored
	 *            The {@link IContentPart} which is to be attached to the given
	 *            <i>contentAnchorage</i>.
	 * @param contentAnchorage
	 *            The content object to which the given <i>anchored</i> is to be
	 *            attached.
	 * @param role
	 *            The role under which the <i>contentAnchorage</i> is anchored.
	 */
	public AttachToContentAnchorageOperation(
			IContentPart<? extends Node> anchored, Object contentAnchorage,
			String role) {
		super("Attach To Content Anchorage");
		this.anchored = anchored;
		this.contentAnchorage = contentAnchorage;
		this.initialContentAnchorages = ImmutableSetMultimap
				.copyOf(anchored.getContentAnchoragesUnmodifiable());
		this.role = role;
	}

	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException {
		// System.out.println("EXEC attach " + anchored + " to content "
		// + contentAnchorage + " with role " + role + ".");
		if (anchored.getContent() != null
				&& !anchored.getContentAnchoragesUnmodifiable()
						.containsEntry(contentAnchorage, role)) {
			anchored.attachToContentAnchorage(contentAnchorage, role);
		}
		return Status.OK_STATUS;
	}

	@Override
	public boolean isContentRelevant() {
		return true;
	}

	@Override
	public boolean isNoOp() {
		return initialContentAnchorages.containsEntry(contentAnchorage, role);
	}

	@Override
	public IStatus redo(IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException {
		return execute(monitor, info);
	}

	@Override
	public IStatus undo(IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException {
		// System.out.println("UNDO attach " + anchored + " to content "
		// + contentAnchorage + " with role " + role + ".");
		if (anchored.getContent() != null
				&& anchored.getContentAnchoragesUnmodifiable()
						.containsEntry(contentAnchorage, role)) {
			anchored.detachFromContentAnchorage(contentAnchorage, role);
		}
		return Status.OK_STATUS;
	}

}