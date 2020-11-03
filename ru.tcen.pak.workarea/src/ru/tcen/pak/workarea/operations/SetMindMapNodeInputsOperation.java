package ru.tcen.pak.workarea.operations;

import java.util.HashMap;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.gef.mvc.fx.operations.ITransactionalOperation;

import ru.tcen.pak.workarea.parts.MindMapNodePart;

/**
 * operation to change the Inputs property of a MindMapNode
 *
 * @author bajurus
 *
 */
public class SetMindMapNodeInputsOperation extends AbstractOperation implements ITransactionalOperation {

	private final MindMapNodePart nodePart;
	private final HashMap<String, HashMap<String, String>> oldInputs;
	private final HashMap<String, HashMap<String, String>> newInputs;

	public SetMindMapNodeInputsOperation(MindMapNodePart nodePart, HashMap<String, HashMap<String, String>> newInputs) {
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