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
 * operation to change the FunctionHexField property of a MindMapNode
 *
 * @author bajurus
 *
 */
public class SetMindMapNodeFunctionHexFieldOperation extends AbstractOperation implements ITransactionalOperation {

	private final MindMapNodePart nodePart;
	private final String oldFunctionHexField;
	private final String newFunctionHexField;

	public SetMindMapNodeFunctionHexFieldOperation(MindMapNodePart nodePart, String newFunctionHexField) {
		super("Change color");
		this.nodePart = nodePart;
		this.newFunctionHexField = newFunctionHexField;
		this.oldFunctionHexField = nodePart.getContent().getFunctionHexField();
	}

	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		nodePart.getContent().setFunctionHexField(newFunctionHexField);
		return Status.OK_STATUS;
	}

	@Override
	public boolean isContentRelevant() {
		// yes we change the model
		return true;
	}

	@Override
	public boolean isNoOp() {
		return newFunctionHexField.equals(oldFunctionHexField);
	}

	@Override
	public IStatus redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		return execute(monitor, info);
	}

	@Override
	public IStatus undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		nodePart.getContent().setFunctionHexField(oldFunctionHexField);
		return Status.OK_STATUS;
	}
}