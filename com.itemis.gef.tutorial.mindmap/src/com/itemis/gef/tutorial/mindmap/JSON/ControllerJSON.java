package com.itemis.gef.tutorial.mindmap.JSON;

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
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.itemis.gef.tutorial.mindmap.model.AbstractMindMapItem;
import com.itemis.gef.tutorial.mindmap.model.MindMapNode;

public class ControllerJSON implements PropertyChangeListener {
	static public List<AbstractMindMapItem> mindMapNodesAtField = new ArrayList<>();
	static public List<JSONObject> mindMapNodeJSON = new ArrayList<>();
	static public List<MindMapNode> mindMapNodeLib = new ArrayList<>();

	public static final String userDir = System.getProperty("user.dir");
	private static String nodeAllJSON = userDir + File.separator + "files" + File.separator + "properties"
			+ File.separator + "allProperties" + ".json";
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
			JSONObject root = new JSONObject(parser);
			properties.add(root.get(property).toString());
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
				mmn.setName(root.getString("name"));
				mmn.setDescription(root.getString("description"));
				mmn.setFunctionHexField(root.getString("function_hex_field"));
				mmn.setNumberOfInputs(root.getString("number_of_inputs"));
				mmn.setNumberOfOutputs(root.getString("number_of_outputs"));
				mmn.setEnd(root.getString("end"));
				JSONObject inputsJSON = root.getJSONObject("inputs");
				HashMap<String, HashMap<String, String>> inputs = new HashMap<>();
				for (int j = 1; j < inputsJSON.length() + 1; j++) {
					HashMap<String, String> input = new HashMap<>();
					Map<String, Object> inputJSON = inputsJSON.getJSONObject("input" + j).toMap();
					for (Entry<String, Object> entry : inputJSON.entrySet()) {
						input.put(entry.getKey(), (String) entry.getValue());
					}
					inputs.put("input" + j, input);
				}
				mmn.setInputs(inputs);
				JSONObject outputsJSON = root.getJSONObject("outputs");
				HashMap<String, HashMap<String, String>> outputs = new HashMap<>();
				for (int j = 1; j < outputsJSON.length() + 1; j++) {
					HashMap<String, String> output = new HashMap<>();
					Map<String, Object> outputJSON = outputsJSON.getJSONObject("output" + j).toMap();
					for (Entry<String, Object> entry : outputJSON.entrySet()) {
						output.put(entry.getKey(), (String) entry.getValue());
					}
					outputs.put("output" + j, output);
				}
				mmn.setOutputs(outputs);
				JSONObject inputsNameJSON = root.getJSONObject("inputs_name");
				HashMap<String, ArrayList<String>> inputsName = new HashMap<>();
				for (int j = 1; j < inputsNameJSON.length() + 1; j++) {
					List<Object> arrayInput = inputsNameJSON.getJSONArray("input" + j).toList();
					ArrayList<String> input = new ArrayList<>();
					for (int k = 0; k < arrayInput.size(); k++) {
						input.add((String) arrayInput.get(k));
					}
					inputsName.put("input" + j, input);
				}
				mmn.setInputsName(inputsName);
				JSONObject outputsNameJSON = root.getJSONObject("outputs_name");
				HashMap<String, ArrayList<String>> outputsName = new HashMap<>();
				for (int j = 1; j < outputsNameJSON.length() + 1; j++) {
					List<Object> arrayOutput = outputsNameJSON.getJSONArray("output" + j).toList();
					ArrayList<String> output = new ArrayList<>();
					for (int k = 0; k < arrayOutput.size(); k++) {
						output.add((String) arrayOutput.get(k));
					}
					outputsName.put("output" + j, output);
				}
				mmn.setOutputsName(outputsName);

				try {
					Writer file = new FileWriter(mmn.getNodeCustomJSON());
					file.write(root.toString());
					file.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}

				mindMapNodeJSON.add(root);
				mindMapNodeLib.add(mmn);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mindMapNodeLib;
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

		root.put(MindMapNode.PROP_NAME, mindMapNode.getName());
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
