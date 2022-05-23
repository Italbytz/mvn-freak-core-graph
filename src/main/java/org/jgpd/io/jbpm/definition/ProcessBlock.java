package org.jgpd.io.jbpm.definition;

import org.jgraph.graph.GraphModel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

public interface ProcessBlock extends DefinitionObject {
	Collection getNodes();
	HashMap getAttributes();
	public void populateProcessBlock( Object beginCell,
										Object finishCell,
										GraphModel model,
										Vector concurrentBlocks );
}
