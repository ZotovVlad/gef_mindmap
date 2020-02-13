package com.itemis.gef.tutorial.mindmap.model.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.itemis.gef.tutorial.mindmap.model.MindMapConnection;
import com.itemis.gef.tutorial.mindmap.model.MindMapNode;
import com.itemis.gef.tutorial.mindmap.model.SimpleMindMapExampleFactory;

@RunWith(JUnitPlatform.class)
class JUnitTestsMindmapModel {

	public class NumericCalculator {
		public int add(int a, int b) {
			return a + b;
		}
	}

	@Test
	void test() {
		MindMapNode mmn1 = new MindMapNode();
		MindMapNode mmn2 = new MindMapNode();

		mmn1.setTitle("12345");
		mmn2.setTitle("12345");

		MindMapConnection mmc1 = new MindMapConnection();
		MindMapConnection mmc2 = new MindMapConnection();

		mmc1.setSource(mmn1);
		mmc1.setTarget(mmn2);

		mmc2.setSource(mmn1);
		mmc2.setTarget(mmn2);

		assertEquals(mmc1.getSource().getTitle(), mmc2.getTarget().getTitle());

	}

	@Test
	@DisplayName("My First Test")
	void test1(TestInfo testInfo) {
		NumericCalculator calc = new NumericCalculator();
		Assertions.assertEquals(2, calc.add(1, 1), "1 + 1 = 2");
		Assertions.assertEquals("My First Test", testInfo.getDisplayName(), () -> "TestInfo is inject correctly");
	}

	@Test
	void test2() {

		SimpleMindMapExampleFactory testObject = new SimpleMindMapExampleFactory();

		testObject.createComplexExample();

		testObject.createSingleNodeExample();
		testObject.createSingleNodeExample();
		testObject.createSingleNodeExample();
		testObject.createSingleNodeExample();

		Assertions.assertEquals(testObject.equals(testObject), true);
		Assertions.assertEquals(true, true);

	}

}
