package de.codepitbull.test;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebPage;

@SuppressWarnings("serial")
public abstract class TestPage extends WebPage{

	public static final String componentId="componentToBeTested";
	
	public TestPage() {
		super();
		add(getComponentToBeTested(componentId));
	}
	
	public abstract Component getComponentToBeTested(String id);
}
