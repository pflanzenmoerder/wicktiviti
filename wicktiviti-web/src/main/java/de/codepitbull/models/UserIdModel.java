package de.codepitbull.models;

import de.codepitbull.security.ActivitiAuthenticatedWebSession;
import org.apache.wicket.model.AbstractReadOnlyModel;

@SuppressWarnings("serial")
public class UserIdModel extends AbstractReadOnlyModel<String> {

	@Override
	public String getObject() {
		return ((ActivitiAuthenticatedWebSession) ActivitiAuthenticatedWebSession
				.get()).getUser().getId();
	}

}
