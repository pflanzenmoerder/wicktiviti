package de.codepitbull.components.tasks;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jmader
 * Date: 15.09.11
 * Time: 18:01
 * To change this template use File | Settings | File Templates.
 */
public class TaskListPanel extends Panel {
    public TaskListPanel(String id, final IModel<List<Task>> listOfTasks, final IModel<Task> selectedTask) {
        super(id, listOfTasks);
        add(new ListView<Task>("taskList", listOfTasks) {
                @Override
                protected void populateItem(final ListItem<Task> taskListItem) {
                    taskListItem.add(new Label("taskId", taskListItem.getModelObject().getId()));
                    taskListItem.add(new AjaxFallbackLink<ProcessDefinition>("taskLink") {
                        @Override
                        public void onClick(AjaxRequestTarget target) {
                            selectedTask.setObject(taskListItem.getModelObject());
                        }
                    }.add(new Label("taskName", taskListItem.getModelObject().getName()+" "+taskListItem.getModelObject().getDescription())));
                }
            });

    }
}
