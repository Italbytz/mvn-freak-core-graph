/*
 * @(#)HelpAbout.java
 *
 * Copyright (C) 2003 sven.luzar
 * Copyright (C) 2003 David Benson
 *
 * JGpd is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * JGpd is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JGpd; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */
package org.jgpd.UI.Dialogs;

import org.jgraph.GPGraphpad;
import org.jgraph.pad.actions.AbstractActionDefault;
import org.jgraph.pad.resources.Translator;
import org.jgraph.utils.BrowserLauncher;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**Shows the bug website.
 * 
 * @author David Benson
 * @version 1.0
 *
 */
public class HelpSubmitABug extends AbstractActionDefault {

	/** The about to submit a bug help
	 */
	protected JDialog aboutDlg;

	/**
	 * Constructor 
	 * @param graphpad
	 */
	public HelpSubmitABug(GPGraphpad graphpad) {
		super(graphpad);
	}

	/**
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		try {		
			BrowserLauncher.openURL("http://sourceforge.net/tracker/?func=add&group_id=90011&atid=592210");		
		} catch (Exception ex){
			JOptionPane.showMessageDialog(graphpad, ex.toString(), Translator.getString("Error"), JOptionPane.ERROR_MESSAGE );
		}
		
	}
	/** Empty implementation. 
	 *  This Action should be available
	 *  each time.
	 */
	public void update(){};

}
