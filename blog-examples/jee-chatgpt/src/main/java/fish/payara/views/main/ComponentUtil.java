package fish.payara.views.main;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ComponentUtil {

    public static Component generateTitleComponent(final String image, final String caption) {
        HorizontalLayout logoLayout = new HorizontalLayout();
        logoLayout.setWidthFull();
        logoLayout.setAlignItems(FlexComponent.Alignment.BASELINE);

        Label title = new Label(caption);
        title.getStyle().set("font-weight", "bold");
        title.getStyle().set("font-size", "20px");

        Image logo = new Image(image, "logo");
        logo.setMaxWidth("55px");

        logoLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        logoLayout.add(logo, title);

        return logoLayout;
    }
}
