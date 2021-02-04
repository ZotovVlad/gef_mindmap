package ru.tcen.pak.workarea.JSON;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import ru.tcen.pak.workarea.model.AbstractMindMapItem;
import ru.tcen.pak.workarea.model.MindMapNode;

public class ControllerJSON implements PropertyChangeListener {
	static public List<AbstractMindMapItem> mindMapNodesAtField = new ArrayList<>();
	static public List<JSONObject> mindMapNodeJSON = new ArrayList<>();
	static public List<MindMapNode> mindMapNodeLib = new ArrayList<>();

	public static final String userDir = System.getProperty("user.dir");
	private static String nodeAllJSON = userDir + File.separator + "files" + File.separator + "properties"
			+ File.separator + "allProperties__" + ".json";
	static String stringJSON = "";

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

	public static void printAllMindMapNodes() {
		for (AbstractMindMapItem mindMapNode : mindMapNodesAtField) {
			System.out.println(mindMapNode);
		}
	}

	public static ArrayList<String> read(MindMapNode mindMapNode, String property) {
		ArrayList<String> properties = new ArrayList<>();
		try {
			Reader reader = new FileReader(mindMapNode.getNodeCustomJSON());
			JSONTokener parser = new JSONTokener(reader);
//			JSONArray array = new JSONArray(parser);
//			for (int i = 0; i < array.length(); i++) {
//				// if ((String) array.get(i))
//				JSONObject root = (JSONObject) array.get(i);
//				if (root.get("name").toString().equals(mindMapNode.getName())) {
//					properties.add((String) root.get(property));
//				}
//			}
			if (parser.more()) {
				JSONObject root = new JSONObject(parser);
				properties.add(root.get(property).toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

	public static List<MindMapNode> readMindMapNodeLib() {
		try {
			Reader reader = new FileReader(nodeAllJSON);
			JSONTokener parser = new JSONTokener(reader);
			JSONArray array = new JSONArray(parser);
			for (int i = 0; i < array.length(); i++) {
				JSONObject root = array.getJSONObject(i);
				MindMapNode mmn = new MindMapNode();
				if (root.has("name")) {
					mmn.setName(root.getString("name"));
				}
				if (root.has("description")) {
					mmn.setDescription(root.getString("description"));
				}
				if (root.has("number_of_hex_parameters")) {
					mmn.setNumberOfHexParameters(root.getString("number_of_hex_parameters"));
				}
				if (root.has("function_hex_field")) {
					mmn.setFunctionHexField(root.getString("function_hex_field"));
				}
				if (root.has("hex_parameters")) {
					JSONArray array_hex_parameters = root.getJSONArray("hex_parameters");
					ArrayList<ArrayList<HashMap<String, String>>> hex_parameters = new ArrayList<>();
					for (int j = 0; j < array_hex_parameters.length(); j++) {
						JSONObject root_hex_parameters = array_hex_parameters.getJSONObject(j);
						ArrayList<HashMap<String, String>> hex_parameter = new ArrayList<>();
						for (int k = 0; k < root_hex_parameters.length(); k++) {
							HashMap<String, String> parameter = new HashMap<>();
							parameter.put(root_hex_parameters.names().getString(k),
									root_hex_parameters.getString(root_hex_parameters.names().getString(k)));
							hex_parameter.add(parameter);
						}
						hex_parameters.add(hex_parameter);
					}
					mmn.setHexParameters(hex_parameters);
				}

				if (root.has("parameters")) {
					JSONArray array_parameters = root.getJSONArray("parameters");
					ArrayList<ArrayList<HashMap<String, String>>> parameters = new ArrayList<>();
					for (int j = 0; j < array_parameters.length(); j++) {
						JSONObject root_parameters = array_parameters.getJSONObject(j);
						ArrayList<HashMap<String, String>> parameter = new ArrayList<>();
						for (int k = 0; k < root_parameters.length(); k++) {
							HashMap<String, String> parameterOne = new HashMap<>();
							parameterOne.put(root_parameters.names().getString(k),
									root_parameters.getString(root_parameters.names().getString(k)));
							parameter.add(parameterOne);
						}
						parameters.add(parameter);
					}
					mmn.setParameters(parameters);
				}

				mindMapNodeJSON.add(root);
				mindMapNodeLib.add(mmn);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mindMapNodeLib;
	}

	public static HashMap<String, ArrayList<String>> readName(MindMapNode mindMapNode, String property, String stor) {
		HashMap<String, ArrayList<String>> names = new HashMap<>();
		try {
			Reader reader = new FileReader(mindMapNode.getNodeCustomJSON());
			JSONTokener parser = new JSONTokener(reader);
			if (parser.more()) {
				JSONObject root = new JSONObject(parser);
				JSONObject namesJSON = root.getJSONObject(property);
				for (int j = 1; j < namesJSON.length() + 1; j++) {
					List<Object> arrayOutput = namesJSON.getJSONArray(stor + j).toList();
					ArrayList<String> output = new ArrayList<>();
					for (int k = 0; k < arrayOutput.size(); k++) {
						output.add((String) arrayOutput.get(k));
					}
					names.put(stor + j, output);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return names;
	}

	public static MindMapNode search(String search) {
		if (search.equals("")) {
			return null;
		}
		for (MindMapNode mindMapNode : mindMapNodeLib) {
			if (mindMapNode.getName().equals(search)) {
				return mindMapNode;
			}
		}
		return null;
	}

	public static void writeCustomJSON(MindMapNode mindMapNode) {
		JSONObject root = new JSONObject();

		root.put(MindMapNode.PROP_NAME, mindMapNode.getName() == null ? "" : mindMapNode.getName());
		root.put(MindMapNode.PROP_DESCRIPTION,
				mindMapNode.getDescription() == null ? "" : mindMapNode.getDescription());
		root.put(MindMapNode.PROP_FUNCTION_HEX_FIELD,
				mindMapNode.getFunctionHexField() == null ? "" : mindMapNode.getFunctionHexField());
		root.put(MindMapNode.PROP_NUMBER_OF_INPUTS,
				mindMapNode.getNumberOfInputs() == null ? "" : mindMapNode.getNumberOfInputs());
		root.put(MindMapNode.PROP_NUMBER_OF_OUTPUTS,
				mindMapNode.getNumberOfOutputs() == null ? "" : mindMapNode.getNumberOfOutputs());
		root.put(MindMapNode.PROP_END, mindMapNode.getEnd() == null ? "" : mindMapNode.getEnd());

		JSONObject root_inputs_name = new JSONObject();
		HashMap<String, ArrayList<String>> inputs_name = mindMapNode.getInputsName();
		for (Map.Entry<String, ArrayList<String>> entry : inputs_name.entrySet()) {
			String key = entry.getKey();
			ArrayList<String> value = entry.getValue();
			JSONArray input_number = new JSONArray();
			if (value.get(0) == null) {
				input_number.put("");
			} else {
				for (String string : value) {
					input_number.put(string);
				}
			}
			root_inputs_name.put(key, input_number);
		}
		root.put("inputs_name", root_inputs_name);

		JSONObject root_outputs_name = new JSONObject();
		HashMap<String, ArrayList<String>> outputs_name = mindMapNode.getOutputsName();
		for (Map.Entry<String, ArrayList<String>> entry : outputs_name.entrySet()) {
			String key = entry.getKey();
			ArrayList<String> value = entry.getValue();
			JSONArray output_number = new JSONArray();
			if (value.get(0) == null) {
				output_number.put("");
			} else {
				for (String string : value) {
					output_number.put(string);
				}
			}
			root_outputs_name.put(key, output_number);
		}
		root.put("outputs_name", root_outputs_name);

		JSONObject root_inputs = new JSONObject();
		HashMap<String, HashMap<String, String>> inputs = mindMapNode.getInputs();
		for (Map.Entry<String, HashMap<String, String>> entry : inputs.entrySet()) {
			String key = entry.getKey();
			JSONObject input = new JSONObject();
			for (Map.Entry<String, String> value : entry.getValue().entrySet()) {
				input.put(value.getKey(), value.getValue());
			}
			root_inputs.put(key, input);
		}
		root.put("inputs", root_inputs);

		JSONObject root_outputs = new JSONObject();
		HashMap<String, HashMap<String, String>> outputs = mindMapNode.getOutputs();
		for (Map.Entry<String, HashMap<String, String>> entry : outputs.entrySet()) {
			String key = entry.getKey();
			JSONObject output = new JSONObject();
			for (Map.Entry<String, String> value : entry.getValue().entrySet()) {
				output.put(value.getKey(), value.getValue());
			}
			root_outputs.put(key, output);
		}
		root.put("outputs", root_outputs);

		try {
			Writer file = new FileWriter(mindMapNode.getNodeCustomJSON());

			file.write(root.toString(2));
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ControllerJSON() {

	}

	private void change(MindMapNode mindMapNode, String propertyName) {

		ControllerJSON.writeCustomJSON(mindMapNode);
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		MindMapNode mindMapNode = (MindMapNode) event.getSource();
		String propertyName = event.getPropertyName();
		this.change(mindMapNode, propertyName);
	}

}
