package de.codepitbull.events;

import org.apache.wicket.ajax.AjaxRequestTarget;

public class TaskFinished {
	public AjaxRequestTarget target;

	public TaskFinished(AjaxRequestTarget target) {
		super();
		this.target = target;
	}

}
