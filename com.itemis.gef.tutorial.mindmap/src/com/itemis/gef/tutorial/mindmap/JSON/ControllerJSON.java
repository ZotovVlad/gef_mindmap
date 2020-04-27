package com.itemis.gef.tutorial.mindmap.JSON;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.itemis.gef.tutorial.mindmap.model.AbstractMindMapItem;
import com.itemis.gef.tutorial.mindmap.model.MindMapNode;

public class ControllerJSON implements PropertyChangeListener {
	static public ArrayList<AbstractMindMapItem> mindMapNodes = new ArrayList<>();
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
			Reader reader = new FileReader(((MindMapNode) mindMapNodes.get(0)).getNodeAllJSON());
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

	public static void writeExample() {
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
			Writer file = new FileWriter(((MindMapNode) mindMapNodes.get(0)).getNodeAllJSON());
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
