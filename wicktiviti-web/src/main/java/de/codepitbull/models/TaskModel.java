package de.codepitbull.models;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.wicket.model.LoadableDetachableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
@SuppressWarnings("serial")
public class TaskModel extends LoadableDetachableModel<Task> {
    @Autowired
	private transient TaskService taskService;

    private String taskId;

    public TaskModel() {
        super();
    }

	public TaskModel(String taskId) {
		super();
		this.taskId = taskId;
	}

	@Override
	protected Task load() {
		if (taskId == null)
			return null;
		return taskService.createTaskQuery().taskId(taskId).singleResult();
	}

    @Override
    public void setObject(Task object) {
        super.setObject(object);
        if(object != null)
            taskId = object.getId();
    }
}
