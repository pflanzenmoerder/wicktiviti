package de.codepitbull.test.helper;

import org.springframework.beans.factory.FactoryBean;
import static org.mockito.Mockito.*;

@SuppressWarnings("rawtypes")
public class MockFactory implements FactoryBean {

	private Class type;

	public void setType(final Class type) {
		this.type = type;
	}

	@SuppressWarnings("unchecked")
	public Object getObject() throws Exception {
		return mock(type);
	}

	public Class getObjectType() {
		return type;
	}

	public boolean isSingleton() {
		return true;
	}
}
