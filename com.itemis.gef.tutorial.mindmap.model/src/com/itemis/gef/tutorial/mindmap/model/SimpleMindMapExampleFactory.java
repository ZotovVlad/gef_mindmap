package com.itemis.gef.tutorial.mindmap.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.eclipse.gef.geometry.planar.Rectangle;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class SimpleMindMapExampleFactory {

	private static final double WIDTH = 170;
	private static final double HEIGHT = 170;

	public SimpleMindMap createComplexExample() {
		SimpleMindMap mindMap = new SimpleMindMap();

		MindMapNode center = new MindMapNode();
		center.setTitle("The Core Idea");
		center.setDescription("This is my Core idea");
		center.setColor(Color.GREENYELLOW);
		center.setBounds(new Rectangle(250, 50, WIDTH, HEIGHT));
		try {
			center.setImage(new Image(new FileInputStream("Icons/" + "icon0" + ".png")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mindMap.addChildElement(center);

		MindMapNode child = null;
		for (int i = 0; i < 5; i++) {
			child = new MindMapNode();
			child.setTitle("Association #" + i);
			child.setDescription("I just realized, this is related to the core idea!");
			child.setColor(Color.ALICEBLUE);
			try {
				child.setImage(new Image(new FileInputStream("Icons/" + "icon0" + ".png")));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			child.setBounds(new Rectangle(50 + (i * 200), 250, WIDTH, HEIGHT));
			mindMap.addChildElement(child);

			MindMapConnection conn = new MindMapConnection();
			conn.connect(center, child, null);

			mindMap.addChildElement(conn);
		}

		MindMapNode child2 = new MindMapNode();
		child2.setTitle("Association #4-2");
		child2.setDescription("I just realized, this is related to the last idea!");
		child2.setColor(Color.LIGHTGRAY);
		child2.setBounds(new Rectangle(250, 550, WIDTH, HEIGHT));
		try {
			child2.setImage(new Image(new FileInputStream("Icons/" + "icon0" + ".png")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mindMap.addChildElement(child2);

		MindMapConnection conn = new MindMapConnection();
		conn.connect(child, child2, null);
		mindMap.addChildElement(conn);

		return mindMap;
	}

	public SimpleMindMap createSingleNodeExample() {
		SimpleMindMap mindMap = new SimpleMindMap();

		MindMapNode center = new MindMapNode();
		center.setTitle("The Core Idea");
		center.setDescription("This is my Core idea. I need a larger Explanation to it, so I can test the warpping.");
		center.setColor(Color.GREENYELLOW);
		center.setBounds(new Rectangle(20, 50, WIDTH, HEIGHT));
		try {
			center.setImage(new Image(new FileInputStream("Icons/" + "icon0" + ".png")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mindMap.addChildElement(center);

		return mindMap;
	}
}