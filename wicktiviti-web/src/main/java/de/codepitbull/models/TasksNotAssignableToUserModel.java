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
public class TasksNotAssignableToUserModel extends
		LoadableDetachableModel<List<Task>> {
	private final IModel<String> roleModel;
	@Autowired
	private transient TaskService taskService;

	public TasksNotAssignableToUserModel(IModel<String> roleModel) {
		this.roleModel = roleModel;
	}

	@Override
	protected List<Task> load() {
		return taskService
				.createTaskQuery()
				.taskCandidateGroup(roleModel.getObject())
                .list();
	}

	@Override
	protected void onDetach() {
		super.onDetach();
		roleModel.detach();
	}

}