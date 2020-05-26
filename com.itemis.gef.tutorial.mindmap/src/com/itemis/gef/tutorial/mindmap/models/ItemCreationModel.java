package com.itemis.gef.tutorial.mindmap.models;

import java.util.ArrayList;
import java.util.List;

import com.itemis.gef.tutorial.mindmap.parts.MindMapNodePart;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * The {@link ItemCreationModel} is sued to store the creation state in the
 * application.
 *
 */
public class ItemCreationModel {

	public enum Type {
		// None, Node, Connection
		None(""), Node(""), Connection("");

		public List<String> names = new ArrayList<>();

		private String string;

		private Type(final String string) {
			this.string = string;
		}

		public String getString() {
			return string;
		}

		public void setString(String string) {
			this.string = string;
		}
	};

	private ObjectProperty<Type> typeProperty = new SimpleObjectProperty<>(Type.None);
	private ObjectProperty<MindMapNodePart> sourceProperty = new SimpleObjectProperty<>();

	private String mindMapNodeName = "";

	public String getMindMapNodeName() {
		return mindMapNodeName;
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

	public void setMindMapNodeName(String mindMapNodeName) {
		this.mindMapNodeName = mindMapNodeName;
	}

	public void setSource(MindMapNodePart source) {
		this.sourceProperty.setValue(source);
	}

	public void setType(Type type) {
		this.typeProperty.setValue(type);
	}
}