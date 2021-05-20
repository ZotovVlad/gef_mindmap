package ru.tcen.pak.workarea.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
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

	public static final String PROP_COLOR = "color";
	public static final String PROP_BOUNDS = "bounds";
	public static final String PROP_INCOMING_CONNECTIONS = "incomingConnections";
	public static final String PROP_OUTGOGING_CONNECTIONS = "outgoingConnections";
	public static final String PROP_IMAGE = "image";
	public static final String PROP_FILE = "file";
	public static final String PROP_QUANTITYANCHORS = "quantityAnchors";
	public static final String PROP_ANCHORS = "anchors";

	public static final String PROP_NAME = "name"; // for functional node
	public static final String PROP_DESCRIPTION = "description"; // for functional node
	public static final String PROP_NUMBER_OF_HEX_PARAMETERS = "number_of_hex_parameters"; // for functional node
	public static final String PROP_HEX_PARAMETERS = "hex_parameters"; // for functional node
	public static final String PROP_PARAMETERS = "parameters"; // for functional node
	public static final String PROP_FUNCTION_HEX_FIELD = "function_hex_field"; // for functional node

	private String nodeCustomJSON = userDir + File.separator + "files" + File.separator + "nodes" + File.separator;
	private String nodeAllJSON = userDir + File.separator + "files" + File.separator;
	private String nodeCode = userDir + File.separator + "files" + File.separator + "nodes" + File.separator;
	private String nodeDirectory = userDir + File.separator + "files" + File.separator + "nodes" + File.separator;
	private String nodeLogs = userDir + File.separator + "files" + File.separator;
	private Set<String> titlesIncomingConnection = new HashSet<>();
	private Set<String> titlesOutgoingConnection = new HashSet<>();

	private String name; // for functional node
	private String description; // for functional node
	private String number_of_hex_parameters; // for functional node
	private String function_hex_field; // for functional node
	private ArrayList<ArrayList<HashMap<String, String>>> hex_parameters; // for functional node
	private ArrayList<ArrayList<HashMap<String, String>>> parameters; // for functional node

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

	private boolean isStatic;

	public MindMapNode() {
		super();

		this.nodeCustomJSON += this.hashCode() + File.separator + this.hashCode() + ".json";
		this.nodeAllJSON += "properties" + File.separator + "allProperties" + ".json";
		this.nodeCode += this.hashCode() + File.separator + this.hashCode() + ".txt";
//		this.nodeCode += this.hashCode() + File.separator + this.hashCode() + ".json";// for testing
		this.nodeDirectory += this.hashCode();
		this.nodeLogs += "properties" + File.separator + "mindMapNode" + ".log";

		File file = new File(this.nodeDirectory);
		file.mkdir();

		createFile(nodeCustomJSON, "");
		createFile(nodeCode, "//This is the file with code at the node " + this.hashCode() + "\n");
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

	private void createFile(String path, String stringToFile) {
		File file = new File(path);
		FileWriter myWriter;

		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			myWriter = new FileWriter(file);
			myWriter.write(stringToFile);
			myWriter.close();
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

	public String getFunctionHexField() {
		return function_hex_field;
	}

	public ArrayList<ArrayList<HashMap<String, String>>> getHexParameters() {
		return hex_parameters;
	}

	public Image getImage() {
		return image;
	}

	public List<MindMapConnection> getIncomingConnections() {
		return incomingConnections;
	}

	public String getName() {
		return name;
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

	public String getNodeLogs() {
		return nodeLogs;
	}

	public String getNumberOfHexParameters() {
		return number_of_hex_parameters;
	}

	public List<MindMapConnection> getOutgoingConnections() {
		return outgoingConnections;
	}

	public ArrayList<ArrayList<HashMap<String, String>>> getParameters() {
		return parameters;
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

	public boolean isStatic() {
		return isStatic;
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

	public void setFunctionHexField(String function_hex_field) {
		pcs.firePropertyChange(PROP_FUNCTION_HEX_FIELD, this.function_hex_field,
				(this.function_hex_field = function_hex_field));
	}

	public void setHexParameters(ArrayList<ArrayList<HashMap<String, String>>> hex_parameters) {
		pcs.firePropertyChange(PROP_HEX_PARAMETERS, this.hex_parameters, (this.hex_parameters = hex_parameters));
	}

	public void setImage(Image image) {
		pcs.firePropertyChange(PROP_IMAGE, this.image, (this.image = image));
	}

	public void setName(String name) {
		titlesIncomingConnection.add(name);
		titlesOutgoingConnection.add(name);
		pcs.firePropertyChange(PROP_NAME, this.name, (this.name = name));
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

	public void setNodeLogs(String nodeLogs) {
		this.nodeLogs = nodeLogs;
	}

	public void setNumberOfHexParameters(String number_of_hex_parameters) {
		pcs.firePropertyChange(PROP_NUMBER_OF_HEX_PARAMETERS, this.number_of_hex_parameters,
				(this.number_of_hex_parameters = number_of_hex_parameters));
	}

	public void setParameters(ArrayList<ArrayList<HashMap<String, String>>> parameters) {
		pcs.firePropertyChange(PROP_PARAMETERS, this.parameters, (this.parameters = parameters));
	}

	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}

}