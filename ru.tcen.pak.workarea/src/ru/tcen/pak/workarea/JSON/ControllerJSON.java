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
		} catch (Exception e) {
//			e.printStackTrace();
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
		if (mindMapNode.getFunctionHexField() != null) {
			root.put(MindMapNode.PROP_FUNCTION_HEX_FIELD, mindMapNode.getFunctionHexField());
		}
		if (mindMapNode.getNumberOfHexParameters() != null) {
			root.put(MindMapNode.PROP_NUMBER_OF_HEX_PARAMETERS, mindMapNode.getNumberOfHexParameters());
		}

		ArrayList<ArrayList<HashMap<String, String>>> hexParameters = mindMapNode.getHexParameters();
		if (hexParameters != null) {
			JSONArray hexParametersJSON = new JSONArray();
			for (ArrayList<HashMap<String, String>> hexParameter : hexParameters) {
				JSONObject hexParameterJSON = new JSONObject();
				for (HashMap<String, String> oneParameter : hexParameter) {
					hexParameterJSON.put((String) oneParameter.keySet().toArray()[0],
							oneParameter.values().toArray()[0]);
				}
				hexParametersJSON.put(hexParameterJSON);
			}
			root.put(MindMapNode.PROP_HEX_PARAMETERS, hexParametersJSON);
		}

		ArrayList<ArrayList<HashMap<String, String>>> parameters = mindMapNode.getParameters();
		if (parameters != null) {
			JSONArray parametersJSON = new JSONArray();
			for (ArrayList<HashMap<String, String>> parameter : parameters) {
				JSONObject parameterJSON = new JSONObject();
				for (HashMap<String, String> oneParameter : parameter) {
					parameterJSON.put((String) oneParameter.keySet().toArray()[0], oneParameter.values().toArray()[0]);
				}
				parametersJSON.put(parameterJSON);
			}
			root.put(MindMapNode.PROP_PARAMETERS, parametersJSON);
		}

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
