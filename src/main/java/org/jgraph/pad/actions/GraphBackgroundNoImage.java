package org.jgraph.pad.actions;

import org.jgraph.GPGraphpad;

import java.awt.event.ActionEvent;

/**
 * @author sven.luzar
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window Preferences Java Templates.
 * To enable and disable the creation of type comments go to
 * Window Preferences Java Code Generation.
 */
public class GraphBackgroundNoImage extends AbstractActionDefault {

	/**
	 * Constructor for GraphBackgroundNoImage.
	 * @param graphpad
	 */
	public GraphBackgroundNoImage(GPGraphpad graphpad) {
		super(graphpad);
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
			graphpad.getCurrentGraph() .setBackgroundImage(null);
			graphpad.getCurrentGraph() .repaint();
	}

}
