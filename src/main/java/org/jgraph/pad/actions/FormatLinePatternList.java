/*
 * @(#)FormatLinePatternList.java	1.2 04.02.2003
 *
 * Copyright (C) 2003 sven.luzar
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */
package org.jgraph.pad.actions;

import org.jgraph.GPGraphpad;
import org.jgraph.graph.CellView;
import org.jgraph.graph.EdgeView;
import org.jgraph.graph.GraphConstants;
import org.jgraph.pad.resources.Translator;
import org.jgraph.utils.Utilities;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 *
 * @author sven.luzar
 * @version 1.0
 *
 */
public class FormatLinePatternList extends AbstractActionListCell {

	protected static Object[] patterns =
		new Object[] {
			new float[] { 4, 2 },
			new float[] { 4, 4 },
			new float[] { 8, 2 },
			new float[] { 8, 4 },
			new float[] { 4, 4, 16, 4 }
	};

	/**
	 * Constructor for FormatLinePatternList.
	 * @param graphpad
	 */
	public FormatLinePatternList(GPGraphpad graphpad) {
		super(graphpad);
		String tmp = Translator.getString("Patterns");
		if (tmp != null) {
			try {
				String[] array = tokenize(tmp);
				patterns = new Object[array.length];
				for (int i = 0; i < array.length; i++) {
					String s = array[i].toString();
					patterns[i] = parsePattern(s);
				}
			} catch (Exception e) {
				// Ignore
			}
		}
	}

	/**
	 * @see AbstractActionListCell#fillCustomItems(Vector)
	 */
	protected void fillCustomItems(Vector items) {
		for (int i = 0; i < patterns.length; i++) {
			EdgeView edge =
				new EdgeView(" ", dummyGraph, dummyGraph.getGraphLayoutCache());
			Map map = GraphConstants.createMap();
			GraphConstants.setPoints(map, this.arrowPoints);
			GraphConstants.setBounds(map, new Rectangle(point, size));
			GraphConstants.setDashPattern(map, (float[]) patterns[i]);
			GraphConstants.setLabelPosition(map, center);
			edge.setAttributes(map);
			items.add(edge);
		}
	}

	/**
	 * @see AbstractActionListCell#fillResetMap(Map)
	 */
	protected void fillResetMap(Map target) {
		target.remove(GraphConstants.DASHPATTERN);
	}

	/**
	 * @see AbstractActionListCell#fillApplyMap(CellView, Map)
	 */
	protected void fillApplyMap(CellView source, Map target) {
		GraphConstants.setDashPattern(
			target,
			GraphConstants.getDashPattern(source.getAttributes()));
	}

	/**
	 * @see AbstractActionListCell#selectAndFillMap(Map)
	 */
	protected void selectAndFillMap(Map target) {
		try {
			String s =
				JOptionPane.showInputDialog(
					Translator.getString("PatternDialog"));
			float[] f = Utilities.parsePattern(s);
			if (f != null) {
				GraphConstants.setDashPattern(target, f);
			}
		} catch (NullPointerException npe) {
			// ignore
		} catch (Exception ex) {
			graphpad.error(ex.toString());
		}
	}

	protected static float[] parsePattern(String pattern) {
		StringTokenizer st = new StringTokenizer(pattern, ",");
		float[] f = new float[st.countTokens()];
		if (f.length > 0) {
			int i = 0;
			while (st.hasMoreTokens())
				f[i++] = Float.parseFloat(st.nextToken());
		}
		return f;
	}

}
