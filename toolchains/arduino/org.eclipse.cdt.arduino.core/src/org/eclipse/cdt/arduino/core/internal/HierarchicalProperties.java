/*******************************************************************************
 * Copyright (c) 2015 QNX Software Systems and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.cdt.arduino.core.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class HierarchicalProperties {

	private String value;
	private Map<String, HierarchicalProperties> children;

	public HierarchicalProperties() {
	}

	public HierarchicalProperties(Properties properties) {
		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			putProperty(key, value);
		}
	}

	public String getProperty(String qualifiedKey) {
		if (children == null) {
			return null;
		}

		int i = qualifiedKey.indexOf('.');
		if (i < 0) {
			HierarchicalProperties child = children.get(qualifiedKey);
			return child != null ? child.getValue() : null;
		} else {
			String key = qualifiedKey.substring(0, i);
			HierarchicalProperties child = children.get(key);
			if (child != null) {
				String childKey = qualifiedKey.substring(i + 1);
				return child.getProperty(childKey);
			} else {
				return null;
			}
		}
	}

	public void putProperty(String qualifiedKey, String value) {
		if (children == null) {
			children = new HashMap<>();
		}

		int i = qualifiedKey.indexOf('.');
		if (i < 0) {
			HierarchicalProperties child = children.get(qualifiedKey);
			if (child == null) {
				child = new HierarchicalProperties();
				children.put(qualifiedKey, child);
				child.setValue(value);
			}
		} else {
			String key = qualifiedKey.substring(0, i);
			HierarchicalProperties child = children.get(key);
			if (child == null) {
				child = new HierarchicalProperties();
				children.put(qualifiedKey, child);
			}
			String childKey = qualifiedKey.substring(i + 1);
			child.putProperty(childKey, value);
		}
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Map<String, HierarchicalProperties> getChildren() {
		return children;
	}

	public HierarchicalProperties getChild(String key) {
		return children != null ? children.get(key) : null;
	}

	public void putChild(String key, HierarchicalProperties node) {
		if (children == null) {
			children = new HashMap<>();
		}
		children.put(key, node);
	}

	public List<HierarchicalProperties> listChildren() {
		int size = 0;
		for (Map.Entry<String, HierarchicalProperties> entry : children.entrySet()) {
			try {
				int i = Integer.parseInt(entry.getKey());
				if (i + 1 > size) {
					size = i + 1;
				}
			} catch (NumberFormatException e) {
				// ignore
			}
		}

		ArrayList<HierarchicalProperties> list = new ArrayList<>(size);
		for (Map.Entry<String, HierarchicalProperties> entry : children.entrySet()) {
			try {
				int i = Integer.parseInt(entry.getKey());
				list.set(i, entry.getValue());
			} catch (NumberFormatException e) {
				// ignore
			}
		}
		return list;
	}

	public void setChildren(List<HierarchicalProperties> list) {
		children.clear();
		for (int i = 0; i < list.size(); i++) {
			HierarchicalProperties node = list.get(i);
			if (node != null) {
				children.put(Integer.toString(i), node);
			}
		}
	}

}
