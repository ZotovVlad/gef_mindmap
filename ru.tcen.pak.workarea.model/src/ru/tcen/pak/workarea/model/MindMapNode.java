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

	public static final String PROP_TITLE = "title";
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
	public static final String PROP_FUNCTION_HEX_FIELD = "function_hex_field"; // for functional node
	public static final String PROP_NUMBER_OF_INPUTS = "number_of_inputs"; // for functional node
	public static final String PROP_NUMBER_OF_OUTPUTS = "number_of_outputs"; // for functional node
	public static final String PROP_INPUTS_NAME = "inputs_name"; // for functional node
	public static final String PROP_OUTPUTS_NAME = "outputs_name"; // for functional node
	public static final String PROP_INPUTS = "inputs"; // for functional node
	public static final String PROP_OUTPUTS = "outputs"; // for functional node
	public static final String PROP_END = "end"; // for functional node

	private String nodeCustomJSON = userDir + File.separator + "files" + File.separator + "nodes" + File.separator;
	private String nodeAllJSON = userDir + File.separator + "files" + File.separator;
	private String nodeCode = userDir + File.separator + "files" + File.separator + "nodes" + File.separator;
	private String nodeDirectory = userDir + File.separator + "files" + File.separator + "nodes" + File.separator;
	private Set<String> titlesIncomingConnection = new HashSet<>();
	private Set<String> titlesOutgoingConnection = new HashSet<>();
	private String name; // for functional node
	private String description; // for functional node
	private String function_hex_field; // for functional node
	private String number_of_inputs; // for functional node
	private String number_of_outputs; // for functional node
	private HashMap<String, ArrayList<String>> inputs_name = new HashMap<>(); // for functional
																				// node
	private HashMap<String, ArrayList<String>> outputs_name = new HashMap<>(); // for
																				// functional
																				// node
	private HashMap<String, HashMap<String, String>> inputs = new HashMap<>(); // for
																				// functional
																				// node
	private HashMap<String, HashMap<String, String>> outputs = new HashMap<>(); // for
																				// functional
																				// node
	private String end; // for functional node

	/**
	 * The title of the node
	 */
	private String title;

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
		this.nodeAllJSON += "properties" + File.separator + "allProperties" + ".json";
		this.nodeCode += this.hashCode() + File.separator + this.hashCode() + ".txt";
		this.nodeDirectory += this.hashCode();

		File file = new File(this.nodeDirectory);
		file.mkdir();

		createFile(nodeCustomJSON, "");
//		createFile(nodeAllJSON, "[\r\n" + "  {\r\n" + "    \"name\": \"inv1\",\r\n"
//				+ "    \"description\": \"Invertor 1\",\r\n" + "    \"function_hex_field\": \"inv1_field.hex\",\r\n"
//				+ "    \"number_of_inputs\": \"1\",\r\n" + "    \"number_of_outputs\": \"1\",\r\n"
//				+ "    \"inputs_name\": {\r\n" + "      \"input1\": [\r\n" + "        \"PIN_0\",\r\n"
//				+ "        \"PIN_1\"\r\n" + "      ],\r\n" + "      \"input2\": [\r\n" + "        \"\"\r\n"
//				+ "      ]\r\n" + "    },\r\n" + "    \"outputs_name\": {\r\n" + "      \"output1\": [\r\n"
//				+ "        \"PAD_Out0\",\r\n" + "        \"PAD_Out1\",\r\n" + "        \"PAD_Out2\",\r\n"
//				+ "        \"PAD_Out3\"\r\n" + "      ],\r\n" + "      \"output2\": [\r\n" + "        \"\"\r\n"
//				+ "      ],\r\n" + "      \"output3\": [\r\n" + "        \"\"\r\n" + "      ],\r\n"
//				+ "      \"output4\": [\r\n" + "        \"\"\r\n" + "      ]\r\n" + "    },\r\n"
//				+ "    \"inputs\": {\r\n" + "      \"input1\": {\r\n"
//				+ "        \"PIN_0\": \"inv1_pin_0_field.hex\"\r\n" + "      },\r\n" + "      \"input2\": {\r\n"
//				+ "        \"PIN_1\": \"inv1_pin_1_field.hex\"\r\n" + "      }\r\n" + "    },\r\n"
//				+ "    \"outputs\": {\r\n" + "      \"output1\": {\r\n"
//				+ "        \"PAD_Out0\": \"inv1_pad_out0_field.hex\"\r\n" + "      },\r\n" + "      \"output2\": {\r\n"
//				+ "        \"PAD_Out1\": \"inv1_pad_out1_field.hex\"\r\n" + "      },\r\n" + "      \"output3\": {\r\n"
//				+ "        \"PAD_Out2\": \"inv1_pad_out2_field.hex\"\r\n" + "      },\r\n" + "      \"output4\": {\r\n"
//				+ "        \"PAD_Out3\": \"inv1_pad_out3_field.hex\"\r\n" + "      }\r\n" + "    },\r\n"
//				+ "    \"end\": \"inv1_flash_read_end_field.hex\"\r\n" + "  },\r\n" + "  {\r\n"
//				+ "    \"name\": \"and1\",\r\n" + "    \"description\": \"And\",\r\n"
//				+ "    \"function_hex_field\": \"and1_field.hex\",\r\n" + "    \"number_of_inputs\": \"2\",\r\n"
//				+ "    \"number_of_outputs\": \"1\",\r\n" + "    \"inputs_name\": {\r\n" + "      \"input1\": [\r\n"
//				+ "        \"PIN_0\",\r\n" + "        \"PIN_1\"\r\n" + "      ],\r\n" + "      \"input2\": [\r\n"
//				+ "        \"PIN_0\",\r\n" + "        \"PIN_1\"\r\n" + "      ]\r\n" + "    },\r\n"
//				+ "    \"outputs_name\": {\r\n" + "      \"output1\": [\r\n" + "        \"PAD_Out0\",\r\n"
//				+ "        \"PAD_Out1\",\r\n" + "        \"PAD_Out2\",\r\n" + "        \"PAD_Out3\"\r\n"
//				+ "      ],\r\n" + "      \"output2\": [\r\n" + "        \"\"\r\n" + "      ],\r\n"
//				+ "      \"output3\": [\r\n" + "        \"\"\r\n" + "      ],\r\n" + "      \"output4\": [\r\n"
//				+ "        \"\"\r\n" + "      ]\r\n" + "    },\r\n" + "    \"inputs\": {\r\n"
//				+ "      \"input1\": {\r\n" + "        \"PIN_0\": \"and1_pin_0_field.hex\"\r\n" + "      },\r\n"
//				+ "      \"input2\": {\r\n" + "        \"PIN_1\": \"and1_pin_1_field.hex\"\r\n" + "      }\r\n"
//				+ "    },\r\n" + "    \"outputs\": {\r\n" + "      \"output1\": {\r\n"
//				+ "        \"PAD_Out0\": \"and1_pad_out0_field.hex\"\r\n" + "      },\r\n" + "      \"output2\": {\r\n"
//				+ "        \"PAD_Out1\": \"and1_pad_out1_field.hex\"\r\n" + "      },\r\n" + "      \"output3\": {\r\n"
//				+ "        \"PAD_Out2\": \"and1_pad_out2_field.hex\"\r\n" + "      },\r\n" + "      \"output4\": {\r\n"
//				+ "        \"PAD_Out3\": \"and1_pad_out3_field.hex\"\r\n" + "      }\r\n" + "    },\r\n"
//				+ "    \"end\": \"and1_flash_read_end_field.hex\"\r\n" + "  }\r\n" + "]");
		createFile(nodeCode, "//This is the file with the node code.\n");
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

	public String getEnd() {
		return end;
	}

	public File getFile() {
		return file;
	}

	public String getFunctionHexField() {
		return function_hex_field;
	}

	public Image getImage() {
		return image;
	}

	public List<MindMapConnection> getIncomingConnections() {
		return incomingConnections;
	}

	public HashMap<String, HashMap<String, String>> getInputs() {
		return inputs;
	}

	public HashMap<String, ArrayList<String>> getInputsName() {
		return inputs_name;
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

	public String getNumberOfInputs() {
		return number_of_inputs;
	}

	public String getNumberOfOutputs() {
		return number_of_outputs;
	}

	public List<MindMapConnection> getOutgoingConnections() {
		return outgoingConnections;
	}

	public HashMap<String, HashMap<String, String>> getOutputs() {
		return outputs;
	}

	public HashMap<String, ArrayList<String>> getOutputsName() {
		return outputs_name;
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
		// System.out.println(description);
	}

	public void setEnd(String end) {
		pcs.firePropertyChange(PROP_END, this.end, (this.end = end));
	}

	public void setFile(File file) {
		pcs.firePropertyChange(PROP_FILE, this.file, (this.file = file));
	}

	public void setFunctionHexField(String function_hex_field) {
		pcs.firePropertyChange(PROP_FUNCTION_HEX_FIELD, this.function_hex_field,
				(this.function_hex_field = function_hex_field));
	}

	public void setImage(Image image) {
		pcs.firePropertyChange(PROP_IMAGE, this.image, (this.image = image));
	}

	public void setInputs(HashMap<String, HashMap<String, String>> inputs) {
		pcs.firePropertyChange(PROP_INPUTS, this.inputs, (this.inputs = inputs));
	}

	public void setInputsName(HashMap<String, ArrayList<String>> inputs_name) {
		pcs.firePropertyChange(PROP_INPUTS_NAME, this.inputs_name, (this.inputs_name = inputs_name));
	}

	public void setName(String name) {
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

	public void setNumberOfInputs(String number_of_inputs) {
		pcs.firePropertyChange(PROP_NUMBER_OF_INPUTS, this.number_of_inputs,
				(this.number_of_inputs = number_of_inputs));
	}

	public void setNumberOfOutputs(String number_of_outputs) {
		pcs.firePropertyChange(PROP_NUMBER_OF_OUTPUTS, this.number_of_outputs,
				(this.number_of_outputs = number_of_outputs));
	}

	public void setOutputs(HashMap<String, HashMap<String, String>> outputs) {
		pcs.firePropertyChange(PROP_OUTPUTS, this.outputs, (this.outputs = outputs));
	}

	public void setOutputsName(HashMap<String, ArrayList<String>> outputs_name) {
		pcs.firePropertyChange(PROP_OUTPUTS_NAME, this.outputs_name, (this.outputs_name = outputs_name));
	}

	public void setTitle(String title) {
		titlesIncomingConnection.add(title);
		titlesOutgoingConnection.add(title);
		pcs.firePropertyChange(PROP_TITLE, this.title, (this.title = title));
	}
}