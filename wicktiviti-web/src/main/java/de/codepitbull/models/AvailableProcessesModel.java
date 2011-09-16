package de.codepitbull.models;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jmader
 * Date: 15.09.11
 * Time: 17:25
 * To change this template use File | Settings | File Templates.
 */
public class AvailableProcessesModel extends LoadableDetachableModel<List<ProcessDefinition>>{
	@SpringBean
	private RepositoryService repositoryService;

	public AvailableProcessesModel() {
		super();
		Injector.get().inject(this);
	}

	@Override
	protected List<ProcessDefinition> load() {
        return repositoryService.createProcessDefinitionQuery().latestVersion().list();
	}
}
