package com.itemis.gef.tutorial.mindmap.JSON;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.itemis.gef.tutorial.mindmap.model.MindMapNode;

public class ControllerJSON {
	static ArrayList<MindMapNode> mindMapNodes = new ArrayList<>();

	private static void parseCar(JSONObject mindMapNode) {
		String transmission = (String) car.get("transmission");
		String horsepower = (String) car.get("horsepower");
		String model = (String) car.get("model");
		String brand = (String) car.get("brand");
		JSONArray colors = (JSONArray) car.get("colors");

		List<Object> color = colors.toList();

		cars.add(new Car(brand.toString(), model.toString(), horsepower.toString(), transmission.toString(), color));
	}

	public static void printAllCars() {
		for (MindMapNode mindMapNode : mindMapNodes) {
			System.out.println(mindMapNode);
		}
	}

	public static void read() {
		try {
			Reader reader = new FileReader("src\\files\\car.json");
			JSONTokener parser = new JSONTokener(reader);
			JSONArray root = new JSONArray(parser);

			root.forEach(car -> parseCar((JSONObject) car));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void write() {
		JSONArray root = new JSONArray();

		for (MindMapNode mindMapNode : mindMapNodes) {
			JSONObject obj = new JSONObject();
			obj.put("brand", car.brand);
			obj.put("model", car.model);
			obj.put("horsepower", car.horsepower);
			obj.put("transmission", car.transmission);
			JSONArray colors = new JSONArray();
			for (Object color : car.colors) {
				colors.put(color);
			}
			obj.put("colors", colors);

			root.put(obj);
		}
		try {
			Writer file = new FileWriter("src\\files\\car.json");
			file.write(root.toString());
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
