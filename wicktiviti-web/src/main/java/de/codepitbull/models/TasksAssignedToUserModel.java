package de.codepitbull.models;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.List;

@Configurable
@SuppressWarnings("serial")
public class TasksAssignedToUserModel extends LoadableDetachableModel<List<Task>> {
	private final IModel<String> userIdModel;
	@Autowired
	private transient TaskService taskService;

	public TasksAssignedToUserModel(IModel<String> userIdModel) {
		this.userIdModel = userIdModel;
	}

	@Override
	protected List<Task> load() {
		return taskService
				.createTaskQuery()
				.taskAssignee(userIdModel.getObject())
                .list();
	}

	@Override
	protected void onDetach() {
		super.onDetach();
		userIdModel.detach();
	}

}