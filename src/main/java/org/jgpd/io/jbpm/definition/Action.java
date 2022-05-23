package org.jgpd.io.jbpm.definition;

import java.io.Serializable;

public interface Action  extends Serializable {
  EventType getEventType();
}
