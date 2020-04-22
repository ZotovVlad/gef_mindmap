package com.itemis.gef.tutorial.mindmap.JSON;

import java.util.ArrayList;

import org.json.JSONObject;

import com.itemis.gef.tutorial.mindmap.model.MindMapNode;

public class ControllerJSON {
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
}
