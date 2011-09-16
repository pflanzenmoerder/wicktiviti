package de.codepitbull.components.dynamic;

import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.resource.IResourceStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ActivitiStartProcessFormPanel extends AbstractDynamicActivitiPanel {

    private static final Logger LOG = LoggerFactory.getLogger(ActivitiStartProcessFormPanel.class);

    @SpringBean
    private FormService formService;

    @SpringBean
    private RepositoryService repositoryService;

    public ActivitiStartProcessFormPanel(final String id, final IModel<ProcessDefinition> model) {
        super(id, model);
        setOutputMarkupId(true);
        final Form<Map<String, Object>> form = new Form<Map<String, Object>>("form", new CompoundPropertyModel<Map<String, Object>>(new HashMap<String, Object>()));
        form.setOutputMarkupId(true);
        add(form);
        fillForm(form, formService.getStartFormData(model.getObject().getId()).getFormProperties());
        form.add(new AjaxFallbackButton("submit", new ResourceModel("submit"), form) {
            @SuppressWarnings("unchecked")
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                LOG.debug("Trying to start new process for {}", model.getObject().getId());
                Map<String, String> submitMap=new HashMap<String, String>();
                for (Map.Entry<String, Object> entry:((Map<String, Object>)form.getDefaultModelObject()).entrySet()) {
                    submitMap.put(entry.getKey(), entry.getValue().toString());
                }
                ProcessInstance instance = formService.submitStartFormData(model.getObject().getId(), submitMap);
                LOG.debug("Started new process for {}", model.getObject().getId());
            }

            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(form);
            }
        });
    }

    public String getCacheKey(MarkupContainer container, Class<?> containerClass) {
        //don't cache for now
        return null;
    }

    public IResourceStream getMarkupResourceStream(MarkupContainer container, Class<?> containerClass) {
        return createIResourceStreamFromInputStream(repositoryService.getResourceAsStream(((ProcessDefinition) getDefaultModelObject()).getDeploymentId(), formService.getStartFormData(((ProcessDefinition) getDefaultModelObject()).getId()).getFormKey()));
    }

}