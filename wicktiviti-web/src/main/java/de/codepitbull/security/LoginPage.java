/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.codepitbull.security;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.apache.wicket.authroles.authentication.panel.SignInPanel;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;


/**
 * Simple example of a sign in page.
 * 
 * @author Jonathan Locke
 */

@Configurable
@SuppressWarnings("serial")
public class LoginPage extends WebPage {

    @Autowired
    private transient IdentityService identityService;

	/**
	 * Constructor
	 * 
	 * @param parameters
	 *            The page parameters
	 */
	public LoginPage(final PageParameters parameters) {
		add(new SignInPanel("signInPanel"));
	}

    @Override
    protected void onInitialize() {
        super.onInitialize();

         if (identityService.createUserQuery().list().size() == 0) {
			// TODO: remove asap
			User newUser = identityService.newUser("jochen");
			newUser.setPassword("jochen");
			identityService.saveUser(newUser);
			newUser = identityService.newUser("kirk");
			newUser.setPassword("kirk");
			identityService.saveUser(newUser);
			newUser = identityService.newUser("spock");
			newUser.setPassword("spock");
			identityService.saveUser(newUser);
			newUser = identityService.newUser("bones");
			newUser.setPassword("bones");
			identityService.saveUser(newUser);

			Group newGroup = identityService.newGroup("DEVELOPER");
			identityService.saveGroup(newGroup);
			newGroup = identityService.newGroup("TESTER");
			identityService.saveGroup(newGroup);

			identityService.createMembership("jochen", "DEVELOPER");
			identityService.createMembership("kirk", "TESTER");
			identityService.createMembership("spock", "DEVELOPER");
			identityService.createMembership("bones", "TESTER");
		}
    }
}
