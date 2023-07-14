package fish.payara.views.main;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;

@PWA(name = "Budget Journey With ChatGPT", shortName = "budgetGPT", description = "A Jakarta EE/Vaadin app that takes a city/country "
		+
		"and a budget amount then suggests places to visit based on the budget")
public class AppShell implements AppShellConfigurator {
}
