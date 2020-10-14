package com.itemis.gef.tutorial.mindmap.operations;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.gef.geometry.planar.Rectangle;
import org.eclipse.gef.mvc.fx.operations.ITransactionalOperation;

import com.itemis.gef.tutorial.mindmap.parts.MindMapNodePart;

/**
 * operation to change the Bounds property of a MindMapNode
 *
 * @author bajurus
 *
 */
public class SetMindMapNodeBoundsOperation extends AbstractOperation implements ITransactionalOperation {

	private final MindMapNodePart nodePart;
	private final Rectangle newBounds;
	private final Rectangle oldBounds;

	public SetMindMapNodeBoundsOperation(MindMapNodePart nodePart, Rectangle newBounds) {
		super("Change bounds");
		this.nodePart = nodePart;
		this.newBounds = newBounds;
		this.oldBounds = nodePart.getContent().getBounds();
	}

	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		nodePart.getContent().setBounds(newBounds);
		return Status.OK_STATUS;
	}

	@Override
	public boolean isContentRelevant() {
		// yes we change the model
		return true;
	}

	@Override
	public boolean isNoOp() {
		return newBounds.equals(oldBounds);
	}

	@Override
	public IStatus redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		return execute(monitor, info);
	}

	@Override
	public IStatus undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		nodePart.getContent().setBounds(oldBounds);
		return Status.OK_STATUS;
	}

}