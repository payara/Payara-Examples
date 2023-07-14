package fish.payara.views.main;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;

@PageTitle("Jakarta GPT Home")
@Route(value = "", layout = ParentAppLayout.class)
public class HomePage extends VerticalLayout {

	@PostConstruct
	private void init() {
		Div div = new Div();
		H1 h1 = new H1("Hello and Welcome to Jakarta EE GPT");

		Paragraph paragraph = new Paragraph(
				"A Jakarta EE application that incorporates OpenAI's GPT API. The app is deployed and running on Payara Cloud, the first fully automated Jakarta EE and MicroProfile cloud deployment platform.");
		Paragraph secondParagraph = new Paragraph(
				"The app has two functions. Budget Journey takes a city and budget and gives you itinerary suggestions.");
		Paragraph thirdParagraph = new Paragraph(
				"The second function takes a prompt and generates an image based on the prompt. You can right-click on the image to save it to your machine.");
		Paragraph fourthParagraph = new Paragraph(
				"The application is a pure, vanilla Jakarta EE application that uses the OpenAi-Java library to talk to the OpenAI service. It is deployed to Payara Cloud.");

		div.add(h1, paragraph, secondParagraph, thirdParagraph, fourthParagraph);

		add(div);
	}
}
