package ru.tcen.pak.workarea.operations;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.gef.mvc.fx.operations.ITransactionalOperation;

import javafx.scene.image.Image;
import ru.tcen.pak.workarea.parts.MindMapNodePart;

/**
 * operation to change the Image property of a MindMapNode
 *
 * @author bajurus
 *
 */
public class SetMindMapNodeImageOperation extends AbstractOperation implements ITransactionalOperation {

	private final MindMapNodePart nodePart;
	private final Image oldImage;
	private final Image newImage;

	public SetMindMapNodeImageOperation(MindMapNodePart nodePart, Image newImage) {
		super("Change Image");
		this.nodePart = nodePart;
		this.newImage = newImage;
		this.oldImage = nodePart.getContent().getImage();
	}

	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		nodePart.getContent().setImage(newImage);
		return Status.OK_STATUS;
	}

	@Override
	public boolean isContentRelevant() {
		// yes we change the model
		return true;
	}

	@Override
	public boolean isNoOp() {
		return newImage.equals(oldImage);
	}

	@Override
	public IStatus redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		return execute(monitor, info);
	}

	@Override
	public IStatus undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		nodePart.getContent().setImage(oldImage);
		return Status.OK_STATUS;
	}
}