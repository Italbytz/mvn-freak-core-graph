/*
 * @(#)DefaultGraphModelProvider.java	1.0 17.02.2003
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
package org.jgraph.pad;

import org.jgpd.jgraph.*;
import org.jgraph.graph.*;
import org.jgraph.pad.resources.Translator;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Map;

/**Graph model provider for the default graph model.
 *
 * @author luzar
 * @version 1.0
 */
public class DefaultGraphModelProvider implements GraphModelProvider {

	/** reference to the default xml file format
	 */
	DefaultGraphModelFileFormatXML defaultGraphModelFileFormatXML;

	/**
	 * Constructor for DefaultGraphModelProvider.
	 */
	public DefaultGraphModelProvider() {
		defaultGraphModelFileFormatXML = new DefaultGraphModelFileFormatXML();
	}

	/**Returns <tt>Default Graph Model</tt>
	 *
	 * @see GraphModelProvider#getPresentationName()
	 */
	public String getPresentationName() {
		return "Default Graph Model";
	}

	/**Creates the Cells for the Default graph model.
	 *
	 * @see DefaultEdge
	 * @see EllipseCell
	 * @see ActivityCell
	 * @see DecisionCell
	 * @see StartCell
	 * @see EndCell
	 * @see SplitCell
	 * @see JoinCell
	 * @see DefaultPort
	 * @see TextCell
	 * @see DefaultGraphCell
	 * @see GraphModelProvider#createCell(GraphModel , int , Object , Map )
	 */
	public Object createCell(
		GraphModel model,
		int type,
		Object userObject,
		Map map) {

		switch (type) {
			case CELL_EDGE_DEFAULT :
				return new DefaultEdge(userObject);
			case CELL_VERTEX_ACTIVITY :
				return new ActivityCell(userObject);
			case CELL_VERTEX_DECISION :
				return new DecisionCell(userObject);
			case CELL_VERTEX_START :
				return new StartCell(userObject);
			case CELL_VERTEX_END :
				return new EndCell(userObject);
			case CELL_VERTEX_SPLIT :
				return new SplitCell(userObject);
			case CELL_VERTEX_JOIN :
				return new JoinCell(userObject);
			case CELL_PORT_DEFAULT :
				return new DefaultPort(userObject);
			case CELL_VERTEX_TEXT :
				return new TextCell(userObject, true);
		}
		return new DefaultGraphCell(userObject);
	}

	/**
	 * Creates a clean default graph model.
	 *
	 * @see DefaultGraphModel
	 * @see GraphModelProvider#createCleanGraphModel()
	 */
	public GraphModel createCleanGraphModel() {
		return new DefaultGraphModel();
	}

	/** Currently returns every time false.
	 *
	 * @see GraphModelProvider#isMutateAbleTo(Class)
	 */
	public boolean isMutateAbleTo(Class otherGraphModelClass) {
		return false;
	}

	/**
	 * Currently returns every time null.
	 *
	 * @see GraphModelProvider#mutateTo(GraphModel, Class)
	 */
	public GraphModel mutateTo(
		GraphModel sourceGraphModel,
		Class otherGraphModelClass) {
		return null;
	}

	/**
	 * Returns the graph model file format objects for this provider
	 *
	 * @see DefaultGraphModelFileFormatXML
	 * @see GraphModelProvider#getGraphModelFileFormats()
	 */
	public GraphModelFileFormat[] getGraphModelFileFormats() {
		return new GraphModelFileFormat[] {
			defaultGraphModelFileFormatXML,
			//	defaultGraphModelFileFormatSerial
		};
	}

	/**Adds a Port to the model specific vertex object
	 *
	 * @see GraphModelProvider#addPort(Object, Object)
	 */
	public void addPort(Object vertex, Object port) {
		if (port instanceof DefaultPort) {
			if (vertex instanceof DefaultMutableTreeNode) {
				((DefaultMutableTreeNode) vertex).add((DefaultPort) port);
			}
		}
	}

	/**
	 * Creates a new clean graph for this model provider
	 *
	 * @see GraphModelProvider#createCleanGraph(GraphModel model)
	 */
	public GPGraph createCleanGraph(GraphModel model) {
		GPGraph graph = new GPGraph(model);
		// TODO: Remove this code when VM is fixed on Mac OS X
		if (System.getProperty("os.name").equals("Mac OS X")) {
			String s = Translator.getString("doubleBufferedOnMacOSX");
			if (s != null)
				graph.setDoubleBuffered(Boolean.getBoolean(s));
		}
		return graph;
	}

}
