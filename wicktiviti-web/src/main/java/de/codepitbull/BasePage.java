package de.codepitbull;

import de.codepitbull.css.BootsrapReferences;
import de.codepitbull.models.UserIdModel;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.resource.PackageResourceReference;

@SuppressWarnings("serial")
@AuthorizeInstantiation(value = { Groups.DEVELOPER, Groups.TESTER })
public abstract class BasePage extends WebPage {

    protected BasePage() {
		add(new Label("userId", new UserIdModel()));
    }

    public void renderHead(IHeaderResponse response) {
		response.renderCSSReference(new PackageResourceReference(BootsrapReferences.class, "bootstrap-1.0.0.css"));
		response.renderJavaScriptReference(new PackageResourceReference(BootsrapReferences.class,"less-1.1.3.min.js"));
		response.renderJavaScriptReference(new PackageResourceReference(BootsrapReferences.class,"bootstrap.less"));
        response.renderJavaScriptReference(new PackageResourceReference(BootsrapReferences.class,"forms.less"));
        response.renderJavaScriptReference(new PackageResourceReference(BootsrapReferences.class,"patterns.less"));
        response.renderJavaScriptReference(new PackageResourceReference(BootsrapReferences.class,"preboot.less"));
        response.renderJavaScriptReference(new PackageResourceReference(BootsrapReferences.class,"reset.less"));
        response.renderJavaScriptReference(new PackageResourceReference(BootsrapReferences.class,"scaffolding.less"));
        response.renderJavaScriptReference(new PackageResourceReference(BootsrapReferences.class,"tables.less"));
        response.renderJavaScriptReference(new PackageResourceReference(BootsrapReferences.class,"type.less"));
	}

}
