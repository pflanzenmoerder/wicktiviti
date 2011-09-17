package de.codepitbull.models;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.wicket.model.LoadableDetachableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * Created by IntelliJ IDEA.
 * User: jmader
 * Date: 15.09.11
 * Time: 18:16
 * To change this template use File | Settings | File Templates.
 */
@Configurable
public class ProcessDefinitionModel extends LoadableDetachableModel<ProcessDefinition>{
    @Autowired
    private transient RepositoryService repositoryService;

    private String processDefinitionId;

    public ProcessDefinitionModel() {
    }

    public ProcessDefinitionModel(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    @Override
    protected ProcessDefinition load() {
        if(processDefinitionId == null)
            return null;
        return repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
    }

    @Override
    public void setObject(ProcessDefinition object) {
        super.setObject(object);
        if(object != null)
            processDefinitionId = object.getId();
    }
}
