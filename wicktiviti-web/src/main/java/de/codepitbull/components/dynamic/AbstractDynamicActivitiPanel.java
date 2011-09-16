package de.codepitbull.components.dynamic;

import de.codepitbull.security.ActivitiAuthenticatedWebSession;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.impl.form.*;
import org.apache.wicket.markup.IMarkupCacheKeyProvider;
import org.apache.wicket.markup.IMarkupResourceStreamProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.StringResourceStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: jmader
 * Date: 15.09.11
 * Time: 18:59
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractDynamicActivitiPanel extends Panel implements IMarkupResourceStreamProvider, IMarkupCacheKeyProvider {

    protected AbstractDynamicActivitiPanel(String id, IModel<?> model) {
        super(id, model);
    }

    protected void fillForm(Form<Map<String, Object>> form, List<FormProperty> formProperties) {
        for (FormProperty formProperty : formProperties) {
            if(formProperty.isWritable()) {
                if(formProperty.getId().equals("userId")) {
                    form.getModelObject().put("userId", ((ActivitiAuthenticatedWebSession) WebSession.get()).getUser().getId());
                }
                else if (formProperty.getType() instanceof LongFormType) {
                    form.add(new TextField<Long>(formProperty.getId()).setRequired(formProperty.isRequired()));
                } else if (formProperty.getType() instanceof StringFormType) {
                    form.add(new TextField<String>(formProperty.getId()).setRequired(formProperty.isRequired()));
                } else if (formProperty.getType() instanceof DateFormType) {
                    form.add(new TextField<Date>(formProperty.getId()).setRequired(formProperty.isRequired()));
                } else if (formProperty.getType() instanceof BooleanFormType) {
                    form.add(new CheckBox(formProperty.getId()).setRequired(formProperty.isRequired()));
                } else if (formProperty.getType() instanceof EnumFormType) {
                    throw new RuntimeException("Enum not supported");
                }
                else {
                    throw new RuntimeException("Unknown formtype for"+formProperty);
                }
            }
            else {
                //remove readonly properties from the list so they don't get submitted
                form.getModelObject().remove(formProperty.getId());
                form.add(new Label(formProperty.getId(), formProperty.getValue()));
            }
        }
    }

    public static IResourceStream createIResourceStreamFromInputStream(InputStream inputStream) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line = null;
            String ret = "";
            while ((line = reader.readLine()) != null) {
                ret = ret + line;
            }
            inputStream.close();
            IResourceStream rStream = new StringResourceStream(ret);

            return rStream;

        } catch (IOException e) {

        }
        return null;
    }
}
