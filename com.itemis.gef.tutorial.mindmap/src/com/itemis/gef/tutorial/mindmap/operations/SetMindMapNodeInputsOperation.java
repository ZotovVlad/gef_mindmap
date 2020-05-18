package com.itemis.gef.tutorial.mindmap.operations;

import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.gef.mvc.fx.operations.ITransactionalOperation;

import com.itemis.gef.tutorial.mindmap.parts.MindMapNodePart;

/**
 * operation to change the Inputs property of a MindMapNode
 *
 * @author bajurus
 *
 */
public class SetMindMapNodeInputsOperation extends AbstractOperation implements ITransactionalOperation {

	private final MindMapNodePart nodePart;
	private final Map<String, String> oldInputs;
	private final Map<String, String> newInputs;

	public SetMindMapNodeInputsOperation(MindMapNodePart nodePart, Map<String, String> newInputs) {
		super("Change color");
		this.nodePart = nodePart;
		this.newInputs = newInputs;
		this.oldInputs = nodePart.getContent().getInputs();
	}

	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		nodePart.getContent().setInputs(newInputs);
		return Status.OK_STATUS;
	}

	@Override
	public boolean isContentRelevant() {
		// yes we change the model
		return true;
	}

	@Override
	public boolean isNoOp() {
		return newInputs.equals(oldInputs);
	}

	@Override
	public IStatus redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		return execute(monitor, info);
	}

	@Override
	public IStatus undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		nodePart.getContent().setInputs(oldInputs);
		return Status.OK_STATUS;
	}
}