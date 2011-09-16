package de.codepitbull.events;

import org.apache.wicket.ajax.AjaxRequestTarget;

public class TaskSelectedEvent {
	public AjaxRequestTarget target;

	public TaskSelectedEvent(AjaxRequestTarget target) {
		super();
		this.target = target;
	}

}
