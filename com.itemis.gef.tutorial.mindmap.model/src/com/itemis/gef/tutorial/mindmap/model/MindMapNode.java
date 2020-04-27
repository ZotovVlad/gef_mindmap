package com.itemis.gef.tutorial.mindmap.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.gef.geometry.planar.Rectangle;

import com.google.common.collect.Lists;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class MindMapNode extends AbstractMindMapItem implements Serializable {

	/**
	 * Generated UUID
	 */
	/**
	 *
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

	private String nodeCustomJSON = userDir + File.separator + "files" + File.separator + "nodes" + File.separator;

	private String nodeAllJSON = userDir + File.separator + "files" + File.separator;

	private String nodeCode = userDir + File.separator + "files" + File.separator + "nodes" + File.separator;

	private String nodeDirectory = userDir + File.separator + "files" + File.separator + "nodes" + File.separator;

	private Set<String> titlesIncomingConnection = new HashSet<>();

	private Set<String> titlesOutgoingConnection = new HashSet<>();

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
	public List<MindMapConnection> incomingConnections = Lists.newArrayList();

	public List<MindMapConnection> outgoingConnections = Lists.newArrayList();

	public MindMapNode() {
		super();

		this.nodeCustomJSON += this.hashCode() + File.separator + this.hashCode() + ".json";
		this.nodeAllJSON += "properties" + File.separator + "allPropertiesJSON" + ".json";
		this.nodeCode += this.hashCode() + File.separator + this.hashCode() + ".txt";
		this.nodeDirectory += this.hashCode();

		File file = new File(this.nodeDirectory);
		file.mkdir();

		createFile(nodeCustomJSON);
		createFile(nodeAllJSON);
		createFileCode(nodeCode);
	}

	public void addIncomingConnection(MindMapConnection conn) {
		incomingConnections.add(conn);
		pcs.firePropertyChange(PROP_INCOMING_CONNECTIONS, null, conn);
	}

	public void addOutgoingConnection(MindMapConnection conn) {
		outgoingConnections.add(conn);
		pcs.firePropertyChange(PROP_OUTGOGING_CONNECTIONS, null, conn);
	}

	public void addTitleIncomingConnection(String titlesIncomingConnection) {
		// add one title connection
		this.titlesIncomingConnection.add(titlesIncomingConnection);
	}

	public void addTitleOutgoingConnection(String titlesOutgoingConnection) {
		// add one title connection
		this.titlesOutgoingConnection.add(titlesOutgoingConnection);
	}

	public void addTitlesIncomingConnection(Set<String> titlesIncomingConnection) {
		// add collection title connection
		this.titlesIncomingConnection.addAll(titlesIncomingConnection);
	}

	public void addTitlesOutgoingConnection(Set<String> titlesOutgoingConnection) {
		// add collection title connection
		this.titlesOutgoingConnection.addAll(titlesOutgoingConnection);
	}

	private void createFile(String path) {
		File file = new File(path);
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createFileCode(String path) {
		File file = new File(path);
		FileWriter myWriter;

		try {
			myWriter = new FileWriter(file);
			myWriter.write("//This is the file with the node code.\n");
			myWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteTitleAtIncomingConnection() {
		// deleting a string START in a collection
		for (String mindMapConnectionString : titlesIncomingConnection) {
			if (mindMapConnectionString.equals("START")) {
				titlesIncomingConnection.remove("START");
				break;
			}
		}
	}

	public void deleteTitleAtOutgoingConnection() {
		// deleting a string FINISH in a collection
		for (String mindMapConnectionString : titlesOutgoingConnection) {
			if (mindMapConnectionString.equals("FINISH")) {
				titlesOutgoingConnection.remove("FINISH");
				break;
			}
		}
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

	public String getNodeAllJSON() {
		return nodeAllJSON;
	}

	public String getNodeCode() {
		return nodeCode;
	}

	public String getNodeCustomJSON() {
		return nodeCustomJSON;
	}

	public String getNodeDirectory() {
		return nodeDirectory;
	}

	public List<MindMapConnection> getOutgoingConnections() {
		return outgoingConnections;
	}

	public String getTitle() {
		return title;
	}

	public Set<String> getTitlesIncomingConnection() {
		return titlesIncomingConnection;
	}

	public Set<String> getTitlesOutgoingConnection() {
		return titlesOutgoingConnection;
	}

	public boolean isFinished() {
		for (Iterator<String> it = this.getTitlesOutgoingConnection().iterator(); it.hasNext();) {
			String f = it.next();
			if (f.equals(new String("FINISH"))) {
				return true;
			}
		}
		return false;
	}

	public boolean isStarted() {
		for (Iterator<String> it = this.getTitlesIncomingConnection().iterator(); it.hasNext();) {
			String f = it.next();
			if (f.equals(new String("START"))) {
				return true;
			}
		}
		return false;
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
		System.out.println(description);
	}

	public void setFile(File file) {
		pcs.firePropertyChange(PROP_FILE, this.file, (this.file = file));
	}

	public void setImage(Image image) {
		pcs.firePropertyChange(PROP_IMAGE, this.image, (this.image = image));
	}

	public void setNodeAllJSON(String nodeAllJSON) {
		this.nodeAllJSON = nodeAllJSON;
	}

	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}

	public void setNodeCustomJSON(String nodeCustomJSON) {
		this.nodeCustomJSON = nodeCustomJSON;
	}

	public void setNodeDirectory(String nodeDirectory) {
		this.nodeDirectory = nodeDirectory;
	}

	public void setTitle(String title) {
		titlesIncomingConnection.add(title);
		titlesOutgoingConnection.add(title);
		pcs.firePropertyChange(PROP_TITLE, this.title, (this.title = title));
	}
}