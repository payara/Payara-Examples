package fish.payara.views.main;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import org.vaadin.firitin.components.DynamicFileDownloader;
import org.vaadin.firitin.components.button.VButton;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;
import org.vaadin.firitin.components.textfield.VNumberField;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import fish.payara.*;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

@PageTitle("Trip On Budget With ChatGPT")
@Route(value = "budget-journey", layout = ParentAppLayout.class)
public class PointsOfInterestView extends VVerticalLayout {

    @Inject
    private TripsAdvisorService tripsAdvisorService;
    @Inject
    private ReportService reportService;

    private Grid<PointOfInterest> grid;
    private Binder<SearchCriteria> binder;
    private Button searchButton;
    private Button resetButton;
    private DynamicFileDownloader pdfDownload;
    private HorizontalLayout userInputLayout;
    private TextField totalTextField;
    private SearchCriteria searchCriteria;
    private PointsOfInterestResponse response;
    private ComboBox<String> currencyField;
    private static final Map<String, Locale> currencies = new HashMap<>();

    static {
        currencies.put("US Dollar ($)", Locale.US);
        currencies.put("British Pound (\u00a3)", Locale.UK);
        currencies.put("Euro (\u20ac)", Locale.GERMANY);
    }


    @PostConstruct

    private void init() {


        Component logoLayout = ComponentUtil.generateTitleComponent("images/trip_on_budget.png","BudgetJourney");

        // Create the binder for the search criteria
        binder = new Binder<>(SearchCriteria.class);

        userInputLayout = new HorizontalLayout();
        userInputLayout.setDefaultVerticalComponentAlignment(Alignment.END);

        // Create the text fields for the search criteria
        TextField cityField = new TextField("Your next destination:");
        cityField.setWidth("300px");
        cityField.getStyle().set("margin-right", "10px");
        cityField.setPlaceholder("city name");

        binder.forField(cityField)
                .asRequired("City name is required")
                .bind(SearchCriteria::getCity, SearchCriteria::setCity);


        var budgetField = new VNumberField("Your Budget")
                .withMin(1)
                .withPlaceholder("Your budget")
                .withStyle("margin-right", "10px")
                .withWidth("300px")
                .withRequired(true)
                .withTitle("Enter your budget:");

        binder.forField(budgetField)
                .asRequired("Budget is required")
                .withValidator(budget -> budget > 0, "Budget must be greater than zero")
                .withConverter(new DoubleToBigDecimalConverter())
                .bind(SearchCriteria::getBudget, SearchCriteria::setBudget);

        // Create the search button
        searchButton = new Button("Go!");
        searchButton.getStyle().set("margin-top", "10px");
        searchButton.addClickListener(e -> searchPointsOfInterest());
        searchButton.setDisableOnClick(true);

        resetButton = new VButton()
                .withIcon(VaadinIcon.TRASH.create())
                .withStyle("margin-top", "10px")
                .withClickListener(b -> resetFields());

        currencyField = new ComboBox<>();
        currencyField.setItems(currencies.keySet());
        currencyField.setPlaceholder("Pick a currency");
        userInputLayout.add(cityField, budgetField, currencyField, resetButton, searchButton);

        add(logoLayout, userInputLayout);
        totalTextField = new TextField();
        totalTextField.setVisible(false);
        // Create the grid to display the points of interest
        grid = new Grid<>();
        grid.addColumn(PointOfInterest::getName).setHeader("Place").setFlexGrow(1).setSortable(true);
        grid.addColumn(PointOfInterest::getInfo).setHeader("Info").setFlexGrow(2);
        Grid.Column<PointOfInterest> costColumn = grid.addColumn(v -> renderCost(v.getCost()));
        costColumn.setFooter(totalTextField);
        costColumn.setHeader("Cost").setFlexGrow(0).setSortable(true).setTextAlign(ColumnTextAlign.END);
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        add(grid);

        pdfDownload = new DynamicFileDownloader();
        pdfDownload.setText("PDF");
        pdfDownload.setFileName("itinerary_" + LocalDateTime.now(ZoneOffset.UTC) + ".pdf");


    }

    private String renderCost(BigDecimal cost) {
        String formattedCost;
        String value = currencyField.getValue();

        if (value != null && !value.isEmpty()) {

            formattedCost = NumberFormat.getCurrencyInstance(currencies.get(value)).format(cost);
            return formattedCost.replaceAll("\\.00", "");
        }
        formattedCost = NumberFormat.getCurrencyInstance(Locale.US).format(cost);
        return formattedCost.replaceAll("\\.00", "");
    }

    private void resetFields() {
        binder.getFields().forEach(HasValue::clear);
        searchButton.setEnabled(true);
    }

    private void searchPointsOfInterest() {
        // Bind the search criteria to the binder
        searchCriteria = new SearchCriteria();

        if (binder.writeBeanIfValid(searchCriteria)) {
            // Call the suggestPointsOfInterest method and update the grid with the results

            response = tripsAdvisorService
                    .suggestPointsOfInterest(searchCriteria.getCity(), searchCriteria.getBudget());

            if (response.getError() != null) {
                showErrorMessage(String.format("Failed loading data from OpenAI GPT: %n%s", response.getError()));
            } else {

                grid.setItems(response.getPointsOfInterest());
                totalTextField.setVisible(true);
                totalTextField.setValue(renderCost(response.getTotalCost()));
                downloadAsPDF();


            }

            searchButton.setEnabled(true);
        }
    }

    private void downloadAsPDF() {


        pdfDownload.setFileHandler(fh -> {
            response.getPointsOfInterest().forEach(p-> p.setFormattedCost(renderCost(p.getCost())));
            response.setTotalCostOfTrip(renderCost(response.getTotalCost()));

            ReportRequestContext requestContext = new ReportRequestContext();
            requestContext.setResponse(response);
            requestContext.setSearchCriteria(searchCriteria);
            requestContext.setOutputStream(fh);
            reportService.writeAsPdf(requestContext);
        });

        if (!pdfDownload.isAttached()) {
            pdfDownload.addComponentAsFirst(VaadinIcon.DOWNLOAD.create());
            userInputLayout.add(pdfDownload);
        }
    }

    @Getter
    @Setter
    public static class SearchCriteria {
        private String city;
        private BigDecimal budget;
    }

    private void showErrorMessage(String errorMessage) {
        Notification notification = new Notification();
        notification.setText(errorMessage);
        notification.setDuration(10_000); // Set the duration to 10 seconds
        notification.setPosition(Position.TOP_CENTER);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        notification.open();
    }

}
