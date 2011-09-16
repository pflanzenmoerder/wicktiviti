package de.codepitbull;

import de.codepitbull.components.dynamic.ActivitiTaskFormPanel;
import de.codepitbull.components.process.ProcessListPanel;
import de.codepitbull.components.dynamic.ActivitiStartProcessFormPanel;
import de.codepitbull.components.tasks.TaskListPanel;
import de.codepitbull.models.*;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;

/**
 * Created by IntelliJ IDEA.
 * User: jmader
 * Date: 15.09.11
 * Time: 17:18
 * To change this template use File | Settings | File Templates.
 */
public class MainPage extends BasePage{

    private IModel<ProcessDefinition> selectedProcessDefinitonModel;
    private IModel<Task> selectedTaskModel;

    public MainPage() {
        selectedProcessDefinitonModel=new ProcessDefinitionModel();
        selectedTaskModel=new TaskModel();
        add(new ProcessListPanel("processSelectionPanel", new AvailableProcessesModel(), selectedProcessDefinitonModel));
        add(new WebMarkupContainer("startProcessPanel").setOutputMarkupId(true));
        add(new TaskListPanel("taskSelectionPanel", new TasksAssignableToUserModel(new UserIdModel()), selectedTaskModel).setOutputMarkupId(true));
        add(new WebMarkupContainer("finishTaskPanel").setOutputMarkupId(true));
    }

    @Override
    public void onEvent(IEvent<?> event) {
        if(event.getPayload() instanceof AjaxRequestTarget) {
            AjaxRequestTarget target = (AjaxRequestTarget)event.getPayload();
            if(selectedProcessDefinitonModel.getObject() != null) {
                target.add(get("startProcessPanel").replaceWith(new ActivitiStartProcessFormPanel("startProcessPanel", selectedProcessDefinitonModel)));
            }
            else {
               target.add(get("startProcessPanel").replaceWith(new WebMarkupContainer("startProcessPanel").setOutputMarkupId(true)));
            }
            if(selectedTaskModel.getObject() != null) {
                target.add(get("finishTaskPanel").replaceWith(new ActivitiTaskFormPanel("finishTaskPanel", selectedTaskModel)));
            }
            else {
                target.add(get("finishTaskPanel").replaceWith(new WebMarkupContainer("finishTaskPanel").setOutputMarkupId(true)));
            }
            target.add(get("taskSelectionPanel"));
        }
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        selectedProcessDefinitonModel.detach();
    }
}
