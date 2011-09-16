package de.codepitbull.models;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.model.AbstractReadOnlyModel;

@SuppressWarnings("serial")
public class RoleModel extends AbstractReadOnlyModel<String> {

	@Override
	public String getObject() {
		String role = (String) AuthenticatedWebSession.get().getRoles()
				.toArray()[0];
		return role.equals("DEVELOPER") ? "TESTER" : "DEVELOPER";
	}

}
