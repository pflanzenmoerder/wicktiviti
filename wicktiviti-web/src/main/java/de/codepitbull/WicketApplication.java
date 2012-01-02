package de.codepitbull;

import de.codepitbull.security.ActivitiAuthenticatedWebSession;
import de.codepitbull.security.LoginPage;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see de.codepitbull.Start#main(String[])
 */
public class WicketApplication extends AuthenticatedWebApplication {

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	public Class<MainPage> getHomePage() {
		return MainPage.class;
	}

	@Override
	public void init() {
		super.init();
		mountPage("overview", MainPage.class);
	}

	@Override
	protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
		return ActivitiAuthenticatedWebSession.class;
	}

	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		return LoginPage.class;
	}

}
