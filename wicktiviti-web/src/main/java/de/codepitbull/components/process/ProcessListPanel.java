package de.codepitbull.components.process;

import org.activiti.engine.repository.ProcessDefinition;
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
public class ProcessListPanel extends Panel {
    public ProcessListPanel(String id, final IModel<List<ProcessDefinition>> listOfProcesses, final IModel<ProcessDefinition> selectedDefinition) {
        super(id, listOfProcesses);
        add(new ListView<ProcessDefinition>("processList", listOfProcesses) {
                @Override
                protected void populateItem(final ListItem<ProcessDefinition> processDefinitionListItem) {
                    processDefinitionListItem.add(new Label("processId", processDefinitionListItem.getModelObject().getId()));
                    processDefinitionListItem.add(new AjaxFallbackLink<ProcessDefinition>("processLink") {
                        @Override
                        public void onClick(AjaxRequestTarget target) {
                            selectedDefinition.setObject(processDefinitionListItem.getModelObject());
                        }
                    }.add(new Label("processName", processDefinitionListItem.getModelObject().getName())));
                }
            });
    }
}
