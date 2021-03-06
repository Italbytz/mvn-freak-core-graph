/*
 * @(#)ToolsShowOverview.java	1.2 01.02.2003
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
import org.jgraph.pad.GPExplorer;
import org.jgraph.pad.resources.Translator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * 
 * @author sven.luzar
 * @version 1.0
 *
 */
public class ToolsShowExplorer extends AbstractActionDefault {

	protected transient JFrame explorerDialog;

	/**
	 * Constructor for ToolsShowOverview.
	 * @param graphpad
	 */
	public ToolsShowExplorer(GPGraphpad graphpad) {
		super(graphpad);
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (explorerDialog == null) {
			String title = Translator.getString("ExplorerFrameTitle");
			explorerDialog = new JFrame(title);
			explorerDialog.getContentPane().setLayout(new BorderLayout());
			explorerDialog.getContentPane().add(
				new GPExplorer(getCurrentGraph()),
				BorderLayout.CENTER);
			explorerDialog.pack();
			explorerDialog.setSize(new Dimension(Math.max(320, explorerDialog.getWidth()),
												Math.max(240, explorerDialog.getHeight())));
		}
		explorerDialog.show();
	}

}
