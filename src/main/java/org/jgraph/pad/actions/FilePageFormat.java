/*
 * @(#)FilePageFormat.java	1.2 30.01.2003
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

import java.awt.event.ActionEvent;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;

/**
 * Open a dialog to select the page setup.
 * 
 * @author sven.luzar
 * @version 1.0
 *
 */
public class FilePageFormat extends AbstractActionDefault {

	/**
	 * Constructor for FilePageFormat.
	 * @param graphpad
	 */
	public FilePageFormat(GPGraphpad graphpad) {
		super(graphpad);
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
			try {
				PageFormat f =
					PrinterJob.getPrinterJob().pageDialog(
						getCurrentGraph().getPageFormat());
				getCurrentGraph().setPageFormat(f);
				graphpad.getCurrentDocument().updatePageFormat();
			} catch (Exception ex) {
				graphpad.error(ex.getMessage());
			}
	}

}
