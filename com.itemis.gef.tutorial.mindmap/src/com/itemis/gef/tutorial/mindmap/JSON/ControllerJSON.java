package com.itemis.gef.tutorial.mindmap.JSON;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import org.json.JSONObject;

import com.itemis.gef.tutorial.mindmap.model.MindMapNode;

public class ControllerJSON implements PropertyChangeListener {
	static ArrayList<MindMapNode> mindMapNodes = new ArrayList<>();

	private static void parseMindMapNode(JSONObject mindMapNode) {

	}

	public static void printAllMindMapNodes() {
		for (MindMapNode mindMapNode : mindMapNodes) {
			System.out.println(mindMapNode);
		}
	}

	public static void read() {

	}

	public static void write() {

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		this.setDescription((String) evt.getNewValue());
	}

}
