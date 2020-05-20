package com.itemis.gef.tutorial.mindmap.operations;

import java.util.List;
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
 * operation to change the InputsName property of a MindMapNode
 *
 * @author bajurus
 *
 */
public class SetMindMapNodeInputsNameOperation extends AbstractOperation implements ITransactionalOperation {

	private final MindMapNodePart nodePart;
	private final List<Map<String, List<String>>> oldInputsName;
	private final List<Map<String, List<String>>> newInputsName;

	public SetMindMapNodeInputsNameOperation(MindMapNodePart nodePart, List<Map<String, List<String>>> newInputsName) {
		super("Change color");
		this.nodePart = nodePart;
		this.newInputsName = newInputsName;
		this.oldInputsName = nodePart.getContent().getInputsName();
	}

	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		nodePart.getContent().setInputsName(newInputsName);
		return Status.OK_STATUS;
	}

	@Override
	public boolean isContentRelevant() {
		// yes we change the model
		return true;
	}

	@Override
	public boolean isNoOp() {
		return newInputsName.equals(oldInputsName);
	}

	@Override
	public IStatus redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		return execute(monitor, info);
	}

	@Override
	public IStatus undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		nodePart.getContent().setInputsName(oldInputsName);
		return Status.OK_STATUS;
	}
}