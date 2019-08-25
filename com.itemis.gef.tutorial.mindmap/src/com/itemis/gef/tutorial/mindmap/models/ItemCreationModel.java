package com.itemis.gef.tutorial.mindmap.models;

import java.util.ArrayList;

import org.eclipse.gef.geometry.planar.Point;

import com.itemis.gef.tutorial.mindmap.parts.MindMapNodePart;
import com.itemis.gef.tutorial.mindmap.parts.feedback.CreateConnectionFeedbackPart;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * The {@link ItemCreationModel} is sued to store the creation state in the
 * application.
 *
 */
public class ItemCreationModel {

	public enum Type {
		None, Node, Connection
	};

	private ObjectProperty<Type> typeProperty = new SimpleObjectProperty<>(Type.None);
	private ObjectProperty<MindMapNodePart> sourceProperty = new SimpleObjectProperty<>();

	public ArrayList<Point> getPoints() {
		// TODO Auto-generated method stub
		return CreateConnectionFeedbackPart.points;
	}

	public MindMapNodePart getSource() {
		return sourceProperty.getValue();
	}

	public ObjectProperty<MindMapNodePart> getSourceProperty() {
		return sourceProperty;
	}

	public Type getType() {
		return typeProperty.getValue();
	}

	public ObjectProperty<Type> getTypeProperty() {
		return typeProperty;
	}

	public void setSource(MindMapNodePart source) {
		this.sourceProperty.setValue(source);
	}

	public void setType(Type type) {
		this.typeProperty.setValue(type);
	}
}