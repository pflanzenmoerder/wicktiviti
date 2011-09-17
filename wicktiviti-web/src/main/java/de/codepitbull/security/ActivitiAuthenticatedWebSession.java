package de.codepitbull.security;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("serial")
@Configurable
public class ActivitiAuthenticatedWebSession extends AuthenticatedWebSession {

	private static final Logger LOG = LoggerFactory.getLogger(ActivitiAuthenticatedWebSession.class);
	@Autowired
	private transient IdentityService identityService;

	private User user;

	public ActivitiAuthenticatedWebSession(Request request) {
		super(request);
	}

	public User getUser() {
		return user;
	}

	@Override
	public boolean authenticate(String username, String password) {
		user = identityService.createUserQuery().userId(username).singleResult();
		if (user != null && password.equals(user.getPassword())) {
			LOG.debug("Authenticated {}", username);
			return true;
		}

		LOG.debug("Failed authenticating {}", username);
		return false;
	}

	@Override
	public Roles getRoles() {
		if (user == null)
			return new Roles();
		List<Group> groups = identityService.createGroupQuery().groupMember(user.getId()).list();
		Set<String> groupNames = new HashSet<String>();
		for (Group group : groups)
			groupNames.add(group.getId());
		return new Roles(groupNames.toArray(new String[] {}));
	}

}
