package de.vinado.app.playground.inbound.presentation.ui;

import de.vinado.app.playground.wicket.bootstrap.form.BaseFormControl;
import de.vinado.app.playground.wicket.bootstrap.form.Form;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.string.Strings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class ImportForm extends GenericPanel<ImportForm.Data> {

    private final Form<Data> form;

    public ImportForm(String id, IModel<Data> model) {
        super(id, model);
        this.form = new Form<>("form", model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(form);

        IModel<List<String>> refsModel = LambdaModel.of(getModel(), Data::refs, Data::refs);
        RefsTextArea refsTextArea = new RefsTextArea("refs", refsModel);
        refsTextArea.setLabel(Model.of("Refs"));
        refsTextArea.setRequired(true);
        refsTextArea.debug("Newline-separated list");
        form.add(refsTextArea);

        AjaxSubmitLink importButton = new AjaxSubmitLink("import", form) {
        };
        importButton.setBody(Model.of("Import"));
        form.add(importButton);
    }

    @lombok.Data
    public static class Data implements Serializable {

        private List<String> refs = new ArrayList<>();
    }


    private static class RefsTextArea extends BaseFormControl<String, List<String>> {

        public RefsTextArea(String id, IModel<List<String>> model) {
            super(id, model);
        }

        @Override
        protected FormComponent<String> control(String wicketId) {
            return new TextArea<>(wicketId);
        }

        @Override
        public void convertInput() {
            String input = control().getConvertedInput();

            if (Strings.isEmpty(input)) {
                setConvertedInput(null);
                return;
            }

            List<String> list = Arrays.stream(input.split("\n"))
                .map(String::trim)
                .filter(Predicate.not(Strings::isEmpty))
                .toList();

            setConvertedInput(list);
        }

        @Override
        protected void onBeforeRender() {
            super.onBeforeRender();

            control().setModelObject(String.join("\n", getModelObject()));
        }
    }
}
