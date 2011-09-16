package de.codepitbull.models;

import java.util.List;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

@SuppressWarnings("serial")
public class TasksAssignableToUserModel extends LoadableDetachableModel<List<Task>> {
	private final IModel<String> userIdModel;
	@SpringBean
	private TaskService taskService;

	public TasksAssignableToUserModel(IModel<String> userIdModel) {
		this.userIdModel = userIdModel;
		Injector.get().inject(this);
	}

	@Override
	protected List<Task> load() {
		return taskService
				.createTaskQuery()
				.taskCandidateUser(userIdModel.getObject())
                .list();
	}

	@Override
	protected void onDetach() {
		super.onDetach();
		userIdModel.detach();
	}
	
	
}