package fish.payara;

import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.ConverterTypeVia;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.java.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.InputStream;
import java.util.logging.Level;

@ApplicationScoped
@Log
public class ReportService {

    @Inject
    @ConfigProperty(name = "report.template")
    private String template;

    private InputStream getTemplate() {
        try {

            return ReportService.class.getResourceAsStream(template);
        } catch (Exception e) {
            log.log(Level.SEVERE,  "An exception occurred reading the template ", e);
            return null;
        }

    }

    public void writeAsPdf(final ReportRequestContext requestContext) {


        try {

            // Get template stream (either the default or overridden by the user)
            InputStream in = getTemplate();
            if (in == null) {
                return;
            }

            // Prepare the IXDocReport instance based on the template, using
            // Freemarker template engine
            IXDocReport report = XDocReportRegistry.getRegistry().
                    loadReport(in, TemplateEngineKind.Freemarker);

            // Define what we want to do (PDF file from ODF template)
            Options options = Options.getTo(ConverterTypeTo.PDF).via(
                    ConverterTypeVia.ODFDOM);

            // Add properties to the context
            IContext ctx = report.createContext();
            ctx.put("city", requestContext.getSearchCriteria().getCity().toUpperCase());
            ctx.put("total", requestContext.getResponse().getTotalCostOfTrip());

            // instruct XDocReport to inspect InvoiceRow entity as well
            // which is given as list and iterated in a table
            FieldsMetadata metadata = report.createFieldsMetadata();
            metadata.load("r", PointOfInterest.class, true);
            ctx.put("r", requestContext.getResponse().getPointsOfInterest());

            // Write the PDF file to output stream
            report.convert(ctx, options, requestContext.getOutputStream());
            requestContext.getOutputStream().close();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
