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
	static public List<AbstractMindMapItem> mindMapNodes = new ArrayList<>();
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

	private static void parseMindMapNode(JSONObject mindMapNode) {

	}

	public static void printAllMindMapNodes() {
		for (AbstractMindMapItem mindMapNode : mindMapNodes) {
			System.out.println(mindMapNode);
		}
	}

	public static ArrayList<String> read(String property) {
		ArrayList<String> properties = new ArrayList<>();
		try {
			Reader reader = new FileReader(nodeAllJSON);
			JSONTokener parser = new JSONTokener(reader);
			JSONObject root = new JSONObject(parser);
			JSONArray array = (JSONArray) root.get(property);
			for (int i = 0; i < array.length(); i++) {
				properties.add((String) array.get(i));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

	public static void readMindMapNodeLib() {
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

				mindMapNodeLib.add(mmn);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeAllPropertiesJSON() {
		JSONObject root = new JSONObject();

		JSONArray titles = new JSONArray();
		titles.put("Title # 1");
		titles.put("Title # 2");
		titles.put("Title # 3");
		root.put(MindMapNode.PROP_TITLE, titles);

		JSONArray descriptions = new JSONArray();
		descriptions.put("Description # 1");
		descriptions.put("Description # 2");
		descriptions.put("Description # 3");
		root.put(MindMapNode.PROP_DESCRIPTION, descriptions);

		JSONArray colors = new JSONArray();
		colors.put("Color # 1");
		colors.put("Color # 2");
		colors.put("Color # 3");
		root.put(MindMapNode.PROP_COLOR, colors);

		try {
			Writer file = new FileWriter(nodeAllJSON);
			file.write(root.toString());
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public ControllerJSON() {

	}

	private void change(MindMapNode mindMapNode, String propertyName) {
		// TODO change JSON file this node
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		MindMapNode mindMapNode = (MindMapNode) event.getSource();
		String propertyName = event.getPropertyName();
		this.change(mindMapNode, propertyName);
	}

}
