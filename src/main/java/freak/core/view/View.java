/*
 * This file is part of RFrEAK. For licensing and copyright information
 * please see the file COPYING in the root directory of this
 * distribution or contact <robin.nunkesser@udo.edu>.
 * 
 * This file is a modification of the original file distributed with
 * FrEAK (http://sourceforge.net/projects/freak427/).
 * Last modification: 06/28/2007
 */

package freak.core.view;

import freak.core.modulesupport.Module;
import freak.core.observer.Observer;

import javax.swing.*;

/**
 * A view in the model-view paradigm that displays data provided by an observer.
 * @author  Patrick, Dirk, Stefan
 */

public interface View extends Module, ViewInterface {

	/**
	 * Creates the output panel for this view.
	 * 
	 * @return the view's new output panel.
	 */
	public JPanel createPanel();

	/**
	 * Updates the view with the specified data.
	 * 
	 * @param data the data the view is to be updated with. 
	 */
	public void update(Object data);

	/**
	 * Returns an identification number that is used to match the view with its
	 * corresponding window in the GUI after the view has been deserialized.
	 * 
	 * @return the unique identification number of the view.
	 */
	public int getIdentificationNumber();

	/**
	 * Returns the observer this view is associated with or <code>null</code>
	 * if this view is not associated with an observer.
	 * 
	 * @return the view's observer or <code>null</code>. 
	 */
	public Observer getObserver();
}
