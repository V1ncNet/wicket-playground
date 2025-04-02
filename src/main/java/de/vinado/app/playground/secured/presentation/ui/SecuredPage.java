package de.vinado.app.playground.secured.presentation.ui;

import de.vinado.app.playground.wicket.PlaygroundPage;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;

@AuthorizeInstantiation({"USER"})
public class SecuredPage extends PlaygroundPage {
}
