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
 * operation to change the NumberOfHexParameters property of a MindMapNode
 *
 * @author bajurus
 *
 */
public class SetMindMapNodeNumberOfHexParametersOperation extends AbstractOperation implements ITransactionalOperation {

	private final MindMapNodePart nodePart;
	private final String oldNumberOfHexParameters;
	private final String newNumberOfHexParameters;

	public SetMindMapNodeNumberOfHexParametersOperation(MindMapNodePart nodePart, String newInputsName) {
		super("Change NumberOfHexParameters");
		this.nodePart = nodePart;
		this.newNumberOfHexParameters = newInputsName;
		this.oldNumberOfHexParameters = nodePart.getContent().getNumberOfHexParameters();
	}

	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		nodePart.getContent().setNumberOfHexParameters(newNumberOfHexParameters);
		return Status.OK_STATUS;
	}

	@Override
	public boolean isContentRelevant() {
		// yes we change the model
		return true;
	}

	@Override
	public boolean isNoOp() {
		return newNumberOfHexParameters.equals(oldNumberOfHexParameters);
	}

	@Override
	public IStatus redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		return execute(monitor, info);
	}

	@Override
	public IStatus undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		nodePart.getContent().setNumberOfHexParameters(oldNumberOfHexParameters);
		return Status.OK_STATUS;
	}
}