package de.codepitbull.components.dynamic;

import de.codepitbull.security.ActivitiAuthenticatedWebSession;
import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.resource.IResourceStream;

import java.util.HashMap;
import java.util.Map;

public class ActivitiTaskFormPanel extends AbstractDynamicActivitiPanel {

    @SpringBean
    private FormService formService;

    @SpringBean
    private TaskService taskService;

    @SpringBean
    private RepositoryService repositoryService;

    public ActivitiTaskFormPanel(final String id, final IModel<Task> model) {
        super(id, model);
        setOutputMarkupId(true);
        final Form<Map<String, Object>> form = new Form<Map<String, Object>>("form", new CompoundPropertyModel<Map<String, Object>>(new HashMap<String, Object>()));
        form.setOutputMarkupId(true);
        add(form);
        form.add(new AjaxFallbackButton("submit", new ResourceModel("submit"), form) {
            @SuppressWarnings("unchecked")
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                taskService.claim(model.getObject().getId(), ((ActivitiAuthenticatedWebSession) WebSession.get()).getUser().getId());
                Map<String, String> submitMap=new HashMap<String, String>();
                for (Map.Entry<String, Object> entry:((Map<String, Object>)form.getDefaultModelObject()).entrySet()) {
                    submitMap.put(entry.getKey(), entry.getValue().toString());
                }
                formService.submitTaskFormData(model.getObject().getId(), submitMap);
                model.setObject(null);
            }

            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(form);
            }
        });
        fillForm(form, formService.getTaskFormData(model.getObject().getId()).getFormProperties());
    }

    public String getCacheKey(MarkupContainer container, Class<?> containerClass) {
        //don't cache for now
        return null;
    }

    public IResourceStream getMarkupResourceStream(MarkupContainer container, Class<?> containerClass) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(((Task) getDefaultModelObject()).getProcessDefinitionId()).singleResult();
        return createIResourceStreamFromInputStream(repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), formService.getTaskFormData(((Task)getDefaultModelObject()).getId()).getFormKey()));

    }
}