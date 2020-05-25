package com.itemis.gef.tutorial.mindmap.model;

import java.io.File;
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
		SimpleMindMap.cleanDirectory(
				new File(System.getProperty("user.dir") + File.separator + "files" + File.separator + "nodes"));

//		List<MindMapNode> child = new ArrayList<>();
//		for (int i = 0; i < 3; i++) {
//			child.add(new MindMapNode());
//			child.get(i).setTitle("Association #" + i);
//			child.get(i).setDescription("I just realized, this is related to the core idea!");
//			child.get(i).setColor(Color.PALEVIOLETRED);
//			try {
//				child.get(i).setImage(new Image(new FileInputStream("Icons/" + "icon0" + ".png")));
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			child.get(i).setBounds(new Rectangle(300 + (i * 300), 250, WIDTH, HEIGHT));
//			mindMap.addChildElement(child.get(i));
//
//			if (i != 0) {
//				MindMapConnection conn = new MindMapConnection();
//				conn.connect(child.get(i - 1), child.get(i), null);
//				mindMap.addChildElement(conn);
//			}
//		}
//
//		MindMapNode child2_1 = new MindMapNode();
//		child2_1.setTitle("Association #3");
//		child2_1.setDescription("I just realized, this is related to the last idea!");
//		child2_1.setColor(Color.PALEVIOLETRED);
//		child2_1.setBounds(new Rectangle(450, 600, WIDTH, HEIGHT));
//		try {
//			child2_1.setImage(new Image(new FileInputStream("Icons/" + "icon0" + ".png")));
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		mindMap.addChildElement(child2_1);
//
//		MindMapConnection conn = new MindMapConnection();
//		conn.connect(child2_1, child2_1, null);
//		mindMap.addChildElement(conn);
//
//		MindMapNode child2_2 = new MindMapNode();
//		child2_2.setTitle("Association #4");
//		child2_2.setDescription("I just realized, this is related to the last idea!");
//		child2_2.setColor(Color.PALEVIOLETRED);
//		child2_2.setBounds(new Rectangle(750, 600, WIDTH, HEIGHT));
//		try {
//			child2_2.setImage(new Image(new FileInputStream("Icons/" + "icon0" + ".png")));
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		mindMap.addChildElement(child2_2);
//
//		MindMapNode start = new MindMapNode();
//		start.setTitle("START");
//		start.setColor(Color.PALEVIOLETRED);
//		start.setBounds(new Rectangle(50, 450, WIDTH, HEIGHT));
//		mindMap.addChildElement(start);
//
//		MindMapNode finish = new MindMapNode();
//		finish.setTitle("FINISH");
//		finish.setColor(Color.PALEVIOLETRED);
//		finish.setBounds(new Rectangle(1150, 450, WIDTH, HEIGHT));
//		mindMap.addChildElement(finish);

//

//		MindMapConnection conn1 = new MindMapConnection();
//		conn1.connect(start, child.get(0), null);
//		mindMap.addChildElement(conn1);
//
//		MindMapConnection conn2 = new MindMapConnection();
//		conn2.connect(child.get(2), finish, null);
//		mindMap.addChildElement(conn2);
//
////		MindMapConnection conn3 = new MindMapConnection();
////		conn3.connect(child.get(0), child2_1, null);
////		mindMap.addChildElement(conn3);
//
//		MindMapConnection conn4 = new MindMapConnection();
//		conn4.connect(start, child2_1, null);
//		mindMap.addChildElement(conn4);
//
//		MindMapConnection conn5 = new MindMapConnection();
//		conn5.connect(start, child2_2, null);
//		mindMap.addChildElement(conn5);
//
//		MindMapConnection conn6 = new MindMapConnection();
//		conn6.connect(child2_2, finish, null);
//		mindMap.addChildElement(conn6);

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