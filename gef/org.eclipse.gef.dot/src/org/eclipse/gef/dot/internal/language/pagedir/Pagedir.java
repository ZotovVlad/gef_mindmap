/*******************************************************************************
 * Copyright (c) 2016 itemis AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tamas Miklossy (itemis AG) - initial API and implementation (bug #461506)
 *
 *******************************************************************************/
package org.eclipse.gef.dot.internal.language.pagedir;

/**
 * Enum representing DOT pageDir.
 * 
 * @author miklossy
 *
 */
public enum Pagedir {

	/**
	 * This value specifies 'BL' page direction.
	 */
	BL("BL"),

	/**
	 * This value specifies 'BR' page direction.
	 */
	BR("BR"),

	/**
	 * This value specifies 'TL' page direction.
	 */
	TL("TL"),

	/**
	 * This value specifies 'TR' page direction.
	 */
	TR("TR"),

	/**
	 * This value specifies 'RB' page direction.
	 */
	RB("RB"),

	/**
	 * This value specifies 'RT' page direction.
	 */
	RT("RT"),

	/**
	 * This value specifies 'LB' page direction.
	 */
	LB("LB"),

	/**
	 * This value specifies 'LT' page direction.
	 */
	LT("LT");

	private final String literalValue;

	private Pagedir(String literalValue) {
		this.literalValue = literalValue;
	}

	@Override
	public String toString() {
		return this.literalValue;
	}
}
