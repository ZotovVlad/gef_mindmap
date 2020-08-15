package com.itemis.gef.tutorial.mindmap.operations;

import java.util.HashMap;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.gef.mvc.fx.operations.ITransactionalOperation;

import com.itemis.gef.tutorial.mindmap.parts.MindMapNodePart;

/**
 * operation to change the Outputs property of a MindMapNode
 *
 * @author bajurus
 *
 */
public class SetMindMapNodeOutputsOperation extends AbstractOperation implements ITransactionalOperation {

	private final MindMapNodePart nodePart;
	private final HashMap<String, HashMap<String, String>> oldOutputs;
	private final HashMap<String, HashMap<String, String>> newOutputs;

	public SetMindMapNodeOutputsOperation(MindMapNodePart nodePart,
			HashMap<String, HashMap<String, String>> newOutputs) {
		super("Change color");
		this.nodePart = nodePart;
		this.newOutputs = newOutputs;
		this.oldOutputs = nodePart.getContent().getOutputs();
	}

	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		nodePart.getContent().setOutputs(newOutputs);
		return Status.OK_STATUS;
	}

	@Override
	public boolean isContentRelevant() {
		// yes we change the model
		return true;
	}

	@Override
	public boolean isNoOp() {
		return newOutputs.equals(oldOutputs);
	}

	@Override
	public IStatus redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		return execute(monitor, info);
	}

	@Override
	public IStatus undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		nodePart.getContent().setOutputs(oldOutputs);
		return Status.OK_STATUS;
	}
}