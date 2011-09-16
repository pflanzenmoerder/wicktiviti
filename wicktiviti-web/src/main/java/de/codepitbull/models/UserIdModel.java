package de.codepitbull.models;

import org.apache.wicket.model.AbstractReadOnlyModel;

import de.codepitbull.security.ActivitiAuthenticatedWebSession;

@SuppressWarnings("serial")
public class UserIdModel extends AbstractReadOnlyModel<String> {

	@Override
	public String getObject() {
		return ((ActivitiAuthenticatedWebSession) ActivitiAuthenticatedWebSession
				.get()).getUser().getId();
	}

}
