package ru.tcen.pak.workarea.operations;

import java.util.ArrayList;
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
 * operation to change the OutputsName property of a MindMapNode
 *
 * @author bajurus
 *
 */
public class SetMindMapNodeOutputsNameOperation extends AbstractOperation implements ITransactionalOperation {

	private final MindMapNodePart nodePart;
	private final HashMap<String, ArrayList<String>> oldOutputsName;
	private final HashMap<String, ArrayList<String>> newOutputsName;

	public SetMindMapNodeOutputsNameOperation(MindMapNodePart nodePart,
			HashMap<String, ArrayList<String>> newOutputsName) {
		super("Change color");
		this.nodePart = nodePart;
		this.newOutputsName = newOutputsName;
		this.oldOutputsName = nodePart.getContent().getOutputsName();
	}

	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		nodePart.getContent().setOutputsName(newOutputsName);
		return Status.OK_STATUS;
	}

	@Override
	public boolean isContentRelevant() {
		// yes we change the model
		return true;
	}

	@Override
	public boolean isNoOp() {
		return newOutputsName.equals(oldOutputsName);
	}

	@Override
	public IStatus redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		return execute(monitor, info);
	}

	@Override
	public IStatus undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		nodePart.getContent().setOutputsName(oldOutputsName);
		return Status.OK_STATUS;
	}
}