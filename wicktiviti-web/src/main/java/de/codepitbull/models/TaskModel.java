package de.codepitbull.models;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

@SuppressWarnings("serial")
public class TaskModel extends LoadableDetachableModel<Task> {
    @SpringBean
	private TaskService taskService;
    {
		Injector.get().inject(this);
    }

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
