package fish.payara.views.main;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.lumo.LumoUtility;

import jakarta.annotation.PostConstruct;

public class ParentAppLayout extends AppLayout {

	@PostConstruct
	private void init() {
		UI.getCurrent().getElement().setAttribute("theme", Lumo.LIGHT);

		createHeader();
		createDrawer();
	}


	private void createHeader() {
		H1 logo = new H1("Jakarta EE GPT");
		logo.addClassNames(
				LumoUtility.FontSize.LARGE,
				LumoUtility.Margin.MEDIUM);

		var header = new HorizontalLayout(new DrawerToggle(), logo );

		header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
		header.setWidthFull();
		header.addClassNames(
				LumoUtility.Padding.Vertical.NONE,
				LumoUtility.Padding.Horizontal.MEDIUM);

		addToNavbar(header);

	}

	private void createDrawer() {
		var verticalLayout = new VerticalLayout();
		var pointOfViewLink = generateComponent("Trip", VaadinIcon.AIRPLANE.create(), PointsOfInterestView.class);
		var imageGen =generateComponent("AI Image", VaadinIcon.CAMERA.create(), GptImageGenerator.class);
		var home =generateComponent("Home", VaadinIcon.HOME_O.create(), HomePage.class);
		verticalLayout.add(home, pointOfViewLink, imageGen);
		addToDrawer(verticalLayout);
	}

	private Component generateComponent(final String caption, final Icon icon, final Class<? extends Component> clazz) {

		icon.getStyle().set("box-sizing", "border-box")
				.set("margin-inline-end", "var(--lumo-space-m)")
				.set("margin-inline-start", "var(--lumo-space-xs)")
				.set("padding", "var(--lumo-space-xs)");

		var imageGen = new RouterLink();
		imageGen.add(icon, new Span(caption));
		imageGen.setTabIndex(-1);
		imageGen.setRoute(clazz);

		return imageGen;
	}

}
