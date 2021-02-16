package ru.tcen.pak.workarea.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.geometry.planar.Point;

public class MindMapConnection extends AbstractMindMapItem {

	/**
	 * Generated UUID
	 */
	private static final long serialVersionUID = 6065237357753406466L;

	private MindMapNode source;
	private MindMapNode target;
	private boolean connected;

	private ArrayList<Point> connectionPoints;

	public MindMapConnection() {

	}

	public void connect(MindMapNode source, MindMapNode target, ArrayList<Point> connectionPoints) {
		if (source == null || target == null || source == target) {
			throw new IllegalArgumentException();
		}
		disconnect();
		this.source = source;
		this.target = target;
		this.connectionPoints = connectionPoints;
		reconnect();
		if (!(source == target)) {
			setIncomingOutgoing();
		}
	}

	public void disconnect() {
		if (connected) {
			source.removeOutgoingConnection(this);
			target.removeIncomingConnection(this);
			connected = false;
		}
	}

	public ArrayList<Point> getPoints() {
		return connectionPoints;
	}

	public MindMapNode getSource() {
		return source;
	}

	public MindMapNode getTarget() {
		return target;
	}

	public void reconnect() {
		if (!connected) {
			source.addOutgoingConnection(this);
			target.addIncomingConnection(this);

			connected = true;
		}
	}

	private void setIncomingOutgoing() {
		this.source.addTitlesOutgoingConnection(this.target.getTitlesOutgoingConnection());
		this.target.addTitlesIncomingConnection(this.source.getTitlesIncomingConnection());

		List<MindMapConnection> IncomingConnections = this.source.getIncomingConnections();
		while (!(IncomingConnections.isEmpty())) {
			IncomingConnections.get(0).getSource()
					.addTitlesOutgoingConnection(this.target.getTitlesOutgoingConnection());
			IncomingConnections = IncomingConnections.get(0).getSource().getIncomingConnections();
			if (!(IncomingConnections.isEmpty())) {
				if (IncomingConnections.get(0).getTarget().getName().equals("START")
						|| IncomingConnections.get(0).getTarget().getName().equals("")) {
					break;
				}
			}
		}

		List<MindMapConnection> OutgoingConnections = this.target.getOutgoingConnections();
		while (!(OutgoingConnections.isEmpty())) {

			OutgoingConnections.get(0).getTarget()
					.addTitlesIncomingConnection(this.source.getTitlesIncomingConnection());
			OutgoingConnections = OutgoingConnections.get(0).getTarget().getOutgoingConnections();
			if (!(OutgoingConnections.isEmpty())) {
				if (OutgoingConnections.get(0).getSource().getName().equals("FINISH")
						|| IncomingConnections.get(0).getTarget().getName().equals("")) {
					break;
				}
			}
		}

	}

	public void setSource(MindMapNode source) {
		this.source = source;
	}

	public void setTarget(MindMapNode target) {
		this.target = target;
	}
}