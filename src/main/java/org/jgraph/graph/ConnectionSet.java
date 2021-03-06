/*
 * @(#)ConnectionSet.java	1.0 1/1/02
 * 
 * Copyright (c) 2001-2004, Gaudenz Alder 
 * All rights reserved. 
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * - Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 * - Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation 
 *   and/or other materials provided with the distribution.
 * - Neither the name of JGraph nor the names of its contributors may be used
 *   to endorse or promote products derived from this software without specific
 *   prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 */

package org.jgraph.graph;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * An object that represents a set of connections. Connections are equal,
 * if equals returns true. Connections that are added later replace earlier
 * connections.
 *
 * @version 1.0 1/1/02
 * @author Gaudenz Alder
 */

public class ConnectionSet implements Serializable {

	/** Contents of the connection set. */
	protected Set connections = new HashSet();

	/** Set of changed edges for the connection set. */
	protected Set edges = new HashSet();

	/**
	 * Returns a connection set that represents the connection or
	 * disconnection of <code>cells</code> in <code>model</code> based
	 * on <code>disconnect</code>.
	 */
	public static ConnectionSet create(
		GraphModel m,
		Object[] cells,
		boolean disconnect) {
		ConnectionSet cs = new ConnectionSet();
		for (int i = 0; i < cells.length; i++) {
			Object cell = cells[i];
			// Edge
			if (m.isEdge(cell)) {
				if (disconnect)
					cs.disconnect(cell);
				else
					cs.connect(cell, m.getSource(cell), m.getTarget(cell));
			}
			// Port
			Iterator it = m.edges(cell);
			while (it.hasNext()) {
				// Edge At Port
				Object edge = it.next();
				if (m.getSource(edge) == cell)
					connect(cs, edge, cell, true, disconnect);
				else if (m.getTarget(edge) == cell)
					connect(cs, edge, cell, false, disconnect);
			}
		}
		return cs;
	}

	/**
	 * Constructs an empty ConnectionSet.
	 */
	public ConnectionSet() {
	}

	/**
	 * Constructs a ConnectionSet with one Connection.
	 */
	public ConnectionSet(Edge edge, Port port, boolean source) {
		connect(edge, port, source);
	}

	/**
	 * Constructs a ConnectionSet with two Connections (to the
	 * source and target port of the edge).
	 */
	public ConnectionSet(Edge edge, Port source, Port target) {
		connect(edge, source, target);
	}

	/**
	 * Connect or disconnect <code>edge</code> from <code>source</code>
	 * and <code>target</code> in <code>cs</code> based on <code>disconnect</code>.
	 */
	protected static void connect(
		ConnectionSet cs,
		Object edge,
		Object port,
		boolean source,
		boolean disconnect) {
		if (disconnect)
			cs.disconnect(edge, source);
		else
			cs.connect(edge, port, source);
	}

	/**
	 * Adds the connections in <code>views</code> to the connection set.
	 */
	public void addConnections(CellView[] views) {
		for (int i = 0; i < views.length; i++) {
			if (views[i] instanceof EdgeView) {
				EdgeView edgeView = (EdgeView) views[i];
				Object edge = edgeView.getCell();
				CellView sourceView = edgeView.getSource();
				CellView targetView = edgeView.getTarget();
				Object source = null;
				if (sourceView != null)
					source = sourceView.getCell();
				Object target = null;
				if (targetView != null)
					target = targetView.getCell();
				connect(edge, source, target);
			}
		}
	}

	/**
	 * Connect <code>edge</code> to <code>source</code>
	 * and <code>target</code> in the connection set. The
	 * previous connections between <code>edge</code> and
	 * its source and target are replaced in the set.
	 */
	public void connect(Object edge, Object source, Object target) {
		connect(edge, source, true);
		connect(edge, target, false);
	}

	/**
	 * Connect <code>edge</code> to <code>port</code>.
	 * <code>source</code> indicates if <code>port</code> is the
	 * source of <code>edge</code>. The previous connections
	 * between <code>edge</code> and its source or target
	 * in the set is replaced.
	 */
	public void connect(Object edge, Object port, boolean source) {
		Connection c = new Connection(edge, port, source);
		connections.remove(c);
		connections.add(c);
		edges.add(edge);
	}

	/**
	 * Disconnect <code>edge</code> from <code>source</code>
	 * and <code>target</code> in the connection set. The
	 * previous connections between <code>edge</code> and
	 * its source and target are replaced in the set.
	 */
	public void disconnect(Object edge) {
		disconnect(edge, true);
		disconnect(edge, false);
	}

	/**
	 * Disconnect <code>edge</code> from <code>port</code>.
	 * <code>source</code> indicates if <code>port</code> is the
	 * source of <code>edge</code>.  The previous connections
	 * between <code>edge</code> and its source or target
	 * in the set is replaced.
	 */
	public void disconnect(Object edge, boolean source) {
		connections.add(new Connection(edge, null, source));
		edges.add(edge);
	}

	/**
	 * Returns <code>true</code> if the connection set is empty.
	 */
	public boolean isEmpty() {
		return connections.isEmpty();
	}

	/**
	 * Returns the number of (edge, port)-pairs.
	 */
	public int size() {
		return connections.size();
	}

	/**
	 * Returns an <code>Iterator</code> for the connections in this set.
	 */
	public Iterator connections() {
		return connections.iterator();
	}

	/**
	 * Returns a <code>Set</code> for the edges in this connection set.
	 */
	public Set getChangedEdges() {
		return edges;
	}

	/**
	 * Creates a new connection set based on this connection set,
	 * where the edges, and ports are mapped using <code>map</code>.
	 * If a port is not found, the old port is used. If both, the
	 * edge and the port are not in <code>map</code>, the entry
	 * is ignored.<p>
	 * <strong>Note:</strong> Consequently, unselected edges are
	 * only reconnected at the first "paste" after a "cut", because
	 * in this case the ConnectionSet is not cloned.
	 */
	public ConnectionSet clone(Map map) {
		ConnectionSet cs = new ConnectionSet();
		Iterator it = connections();
		while (it.hasNext()) {
			// Shortcut Vars
			Connection c = (Connection) it.next();
			Object edge = map.get(c.getEdge());
			Object port = c.getPort();
			if (port != null)
				port = map.get(port);
			// New Port
			if (edge != null && port != null)
				cs.connect(edge, port, c.isSource());
			// Old Port
			else if (edge != null)
				cs.connect(edge, c.getPort(), c.isSource());
		}
		return cs;
	}

	/**
	 * Object that represents the connection between an edge and a port.
	 */
	public static class Connection implements Serializable {

		/** The edge that will be connected to the port. */
		protected Object edge;

		/** The port that will be connected to the edge. */
		protected Object port;

		/** Indicates if <code>port</code> is the source of <code>edge</code>. */
		protected boolean isSource;

		public Connection() {
			// Empty Constructor
		}

		/**
		 * Constructs a new source or target connection between
		 * <code>edge</code> and <code>port</code> based on the
		 * value of <code>source</code>
		 */
		public Connection(Object edge, Object port, boolean isSource) {
			this.edge = edge;
			this.port = port;
			this.isSource = isSource;
		}

		/**
		 * Returns the edge of the connection.
		 */
		public Object getEdge() {
			return edge;
		}

		/**
		 * Returns the port of the connection.
		 */
		public Object getPort() {
			return port;
		}

		/**
		 *  Returns <code>true</code> if <code>port</code> is the source
		 *  of <code>edge</code>.
		 */
		public boolean isSource() {
			return isSource;
		}

		/**
		 * Two connections are equal if they represent the source
		 * or target of the same edge. That is, if <p>
		 * c1.edge is equal to c2.edge and c1.isSource is equal to c2.isSource.
		 */
		public boolean equals(Object obj) {
			if (obj instanceof Connection) {
				Connection other = (Connection) obj;
				return (
					other.getEdge().equals(edge)
						&& other.isSource() == isSource);
			}
			return false;
		}

		/**
		 * Ensure equality of hashCode wrt. equals().
		 */
		public int hashCode() {
			return edge.hashCode();
		}

		/**
		 * @param object
		 */
		public void setEdge(Object object) {
			edge = object;
		}

		/**
		 * @param b
		 */
		public void setSource(boolean b) {
			isSource = b;
		}

		/**
		 * @param object
		 */
		public void setPort(Object object) {
			port = object;
		}

	}

	/**
	 * @return
	 */
	public Set getConnections() {
		return connections;
	}

	/**
	 * @return
	 */
	public Set getEdges() {
		return edges;
	}

	/**
	 * @param set
	 */
	public void setConnections(Set set) {
		connections = set;
	}

	/**
	 * @param set
	 */
	public void setEdges(Set set) {
		edges = set;
	}

}
