package com.itemis.gef.tutorial.mindmap.model;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import org.eclipse.gef.geometry.planar.Rectangle;

import com.google.common.collect.Lists;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class MindMapNode extends AbstractMindMapItem implements Serializable {

	/**
	 * Generated UUID
	 */
	private static final long serialVersionUID = 8875579454539897410L;

	public static final String userDir = System.getProperty("user.dir");

	public static final String PROP_TITLE = "title";
	public static final String PROP_DESCRIPTION = "description";
	public static final String PROP_COLOR = "color";
	public static final String PROP_BOUNDS = "bounds";
	public static final String PROP_INCOMING_CONNECTIONS = "incomingConnections";
	public static final String PROP_OUTGOGING_CONNECTIONS = "outgoingConnections";
	public static final String PROP_IMAGE = "image";
	public static final String PROP_FILE = "file";
	public static final String PROP_QUANTITYANCHORS = "quantityAnchors";
	public static final String PROP_ANCHORS = "anchors";

	/**
	 * The title of the node
	 */
	private String title;

	/**
	 * he description of the node, which is optional
	 */
	private String description;

	/**
	 * The background color of the node
	 */
	private Color color;

	/**
	 * The file of the node
	 */
	private File file;

	/**
	 * The image of the node
	 */
	private Image image;

	/**
	 * The size and position of the visual representation
	 */
	private Rectangle bounds;

	private List<MindMapConnection> incomingConnections = Lists.newArrayList();

	private List<MindMapConnection> outgoingConnections = Lists.newArrayList();

	public MindMapNode() {
		super();
		File f = new File(userDir + File.separator + "Files" + File.separator + this.hashCode() + ".txt");
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addIncomingConnection(MindMapConnection conn) {
		incomingConnections.add(conn);
		pcs.firePropertyChange(PROP_INCOMING_CONNECTIONS, null, conn);
	}

	public void addOutgoingConnection(MindMapConnection conn) {
		outgoingConnections.add(conn);
		pcs.firePropertyChange(PROP_OUTGOGING_CONNECTIONS, null, conn);
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public Color getColor() {
		return color;
	}

	public String getDescription() {
		return description;
	}

	public File getFile() {
		return file;
	}

	public Image getImage() {
		return image;
	}

	public List<MindMapConnection> getIncomingConnections() {
		return incomingConnections;
	}

	public List<MindMapConnection> getOutgoingConnections() {
		return outgoingConnections;
	}

	public String getTitle() {
		return title;
	}

	public void removeIncomingConnection(MindMapConnection conn) {
		incomingConnections.remove(conn);
		pcs.firePropertyChange(PROP_INCOMING_CONNECTIONS, conn, null);
	}

	public void removeOutgoingConnection(MindMapConnection conn) {
		outgoingConnections.remove(conn);
		pcs.firePropertyChange(PROP_OUTGOGING_CONNECTIONS, conn, null);
	}

	public void setBounds(Rectangle bounds) {
		pcs.firePropertyChange(PROP_BOUNDS, this.bounds, (this.bounds = bounds.getCopy()));
	}

	public void setColor(Color color) {
		pcs.firePropertyChange(PROP_COLOR, this.color, (this.color = color));
	}

	public void setDescription(String description) {
		pcs.firePropertyChange(PROP_DESCRIPTION, this.description, (this.description = description));
	}

	public void setFile(File file) {
		pcs.firePropertyChange(PROP_FILE, this.file, (this.file = file));
	}

	public void setImage(Image image) {
		pcs.firePropertyChange(PROP_IMAGE, this.image, (this.image = image));
	}

	public void setTitle(String title) {
		pcs.firePropertyChange(PROP_TITLE, this.title, (this.title = title));
	}
}