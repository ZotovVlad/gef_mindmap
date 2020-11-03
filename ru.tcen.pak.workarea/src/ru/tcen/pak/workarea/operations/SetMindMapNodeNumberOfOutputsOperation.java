package ru.tcen.pak.workarea.operations;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.gef.mvc.fx.operations.ITransactionalOperation;

import ru.tcen.pak.workarea.parts.MindMapNodePart;

/**
 * operation to change the NumberOfOutputs property of a MindMapNode
 *
 * @author bajurus
 *
 */
public class SetMindMapNodeNumberOfOutputsOperation extends AbstractOperation implements ITransactionalOperation {

	private final MindMapNodePart nodePart;
	private final String oldNumberOfOutputs;
	private final String newNumberOfOutputs;

	public SetMindMapNodeNumberOfOutputsOperation(MindMapNodePart nodePart, String newNumberOfOutputs) {
		super("Change color");
		this.nodePart = nodePart;
		this.newNumberOfOutputs = newNumberOfOutputs;
		this.oldNumberOfOutputs = nodePart.getContent().getNumberOfOutputs();
	}

	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		nodePart.getContent().setNumberOfOutputs(newNumberOfOutputs);
		return Status.OK_STATUS;
	}

	@Override
	public boolean isContentRelevant() {
		// yes we change the model
		return true;
	}

	@Override
	public boolean isNoOp() {
		return newNumberOfOutputs.equals(oldNumberOfOutputs);
	}

	@Override
	public IStatus redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		return execute(monitor, info);
	}

	@Override
	public IStatus undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		nodePart.getContent().setNumberOfOutputs(oldNumberOfOutputs);
		return Status.OK_STATUS;
	}
}