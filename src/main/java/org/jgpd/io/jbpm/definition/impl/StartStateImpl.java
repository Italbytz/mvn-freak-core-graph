package org.jgpd.io.jbpm.definition.impl;

import org.jgpd.UI.PropPanelConfig;
import org.jgpd.UI.tableModels.JGpdTableModel;
import org.jgpd.io.jbpm.definition.StartState;
import org.w3c.dom.Node;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Collection;
import java.util.Iterator;

public class StartStateImpl extends ActivityStateImpl implements StartState {

	/**
	 * <code>nodeType</code> is the string representation of this node type
	 */
	public static final String nodeType = new String("start-state");
		
	/**
	 * 
	 */
	public StartStateImpl() 
	{
		super();
	}
	
	/**
	 * @param node The XML node representing this node type
	 */
	public StartStateImpl(Node node)
	{
		super(node);
	}
	
	/**
	 * @see org.jgpd.io.JGpdModelNode#propPanelModel(JTable[], DefaultTableModel[])
	 */
	public PropPanelConfig propPanelModel(JTable[] table,
	                                      JGpdTableModel[] tableModel)
	{
		PropPanelConfig panelConfig = super.propPanelModel(table,
		                                                   tableModel);
		
		return panelConfig;
	}

	public void applyProperties(DefaultTableModel[] tableModel)
	{
		super.applyProperties(tableModel);
	}

  public String getDisplayedNodeType()
  {
  	return nodeType;
  }
  
  public String writeXML(String indent)
  {
	String xml = indent + "<start-state name=\"" + getName() + "\">\n";
  	
	String local_indent = indent + "  ";
  	
	xml += local_indent + "<description>" + getDescription() + "</description>\n";
	
	xml += local_indent + "<role>" + getActorRoleName() + "</role>\n";

    Collection fields = getFields();

    Iterator iter = fields.iterator();
	while (iter.hasNext())
	{
	  FieldImpl field = (FieldImpl) iter.next();
	  xml += field.writeXML( local_indent );
	}

	iter = leavingTransitions.iterator();
	while ( iter.hasNext() )
	{
		TransitionImpl transition = (TransitionImpl)(iter.next());
		xml += transition.writeXML(local_indent);
	}
	
	xml += indent + "</start-state>\n";   	

  	return xml;
  }
}
