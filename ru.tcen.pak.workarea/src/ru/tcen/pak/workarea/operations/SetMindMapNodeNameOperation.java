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
 * operation to change the Name property of a MindMapNode
 *
 * @author bajurus
 *
 */
public class SetMindMapNodeNameOperation extends AbstractOperation implements ITransactionalOperation {

	private final MindMapNodePart nodePart;
	private final String oldName;
	private final String newName;

	public SetMindMapNodeNameOperation(MindMapNodePart nodePart, String newName) {
		super("Change Name");
		this.nodePart = nodePart;
		this.newName = newName;
		this.oldName = nodePart.getContent().getName();
	}

	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		nodePart.getContent().setName(newName);
		return Status.OK_STATUS;
	}

	@Override
	public boolean isContentRelevant() {
		// yes we change the model
		return true;
	}

	@Override
	public boolean isNoOp() {
		return newName.equals(oldName);
	}

	@Override
	public IStatus redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		return execute(monitor, info);
	}

	@Override
	public IStatus undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		nodePart.getContent().setName(oldName);
		return Status.OK_STATUS;
	}
}