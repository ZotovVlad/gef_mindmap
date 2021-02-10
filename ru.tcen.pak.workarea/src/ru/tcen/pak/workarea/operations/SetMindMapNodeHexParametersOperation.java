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
 * operation to change the HexParameters property of a MindMapNode
 *
 * @author bajurus
 *
 */
public class SetMindMapNodeHexParametersOperation extends AbstractOperation implements ITransactionalOperation {

	private final MindMapNodePart nodePart;
	private final ArrayList<ArrayList<HashMap<String, String>>> oldHexParameters;
	private final ArrayList<ArrayList<HashMap<String, String>>> newHexParameters;

	public SetMindMapNodeHexParametersOperation(MindMapNodePart nodePart,
			ArrayList<ArrayList<HashMap<String, String>>> newHexParameters) {
		super("Change HexParameters");
		this.nodePart = nodePart;
		this.newHexParameters = newHexParameters;
		this.oldHexParameters = nodePart.getContent().getHexParameters();
	}

	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		nodePart.getContent().setHexParameters(newHexParameters);
		return Status.OK_STATUS;
	}

	@Override
	public boolean isContentRelevant() {
		// yes we change the model
		return true;
	}

	@Override
	public boolean isNoOp() {
		return newHexParameters.equals(oldHexParameters);
	}

	@Override
	public IStatus redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		return execute(monitor, info);
	}

	@Override
	public IStatus undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		nodePart.getContent().setHexParameters(oldHexParameters);
		return Status.OK_STATUS;
	}
}