package com.itemis.gef.tutorial.mindmap.operations;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.gef.mvc.fx.operations.ITransactionalOperation;

import com.itemis.gef.tutorial.mindmap.parts.MindMapNodePart;

/**
 * operation to change the NumberOfInputs property of a MindMapNode
 *
 * @author bajurus
 *
 */
public class SetMindMapNodeNumberOfInputsOperation extends AbstractOperation implements ITransactionalOperation {

	private final MindMapNodePart nodePart;
	private final String oldNumberOfInputs;
	private final String newNumberOfInputs;

	public SetMindMapNodeNumberOfInputsOperation(MindMapNodePart nodePart, String newNumberOfInputs) {
		super("Change color");
		this.nodePart = nodePart;
		this.newNumberOfInputs = newNumberOfInputs;
		this.oldNumberOfInputs = nodePart.getContent().getNumberOfInputs();
	}

	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		nodePart.getContent().setNumberOfInputs(newNumberOfInputs);
		return Status.OK_STATUS;
	}

	@Override
	public boolean isContentRelevant() {
		// yes we change the model
		return true;
	}

	@Override
	public boolean isNoOp() {
		return newNumberOfInputs.equals(oldNumberOfInputs);
	}

	@Override
	public IStatus redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		return execute(monitor, info);
	}

	@Override
	public IStatus undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		nodePart.getContent().setNumberOfInputs(oldNumberOfInputs);
		return Status.OK_STATUS;
	}
}