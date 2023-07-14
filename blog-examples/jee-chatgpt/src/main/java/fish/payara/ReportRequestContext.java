package fish.payara;

import fish.payara.views.main.PointsOfInterestView;
import lombok.Getter;
import lombok.Setter;

import java.io.OutputStream;

@Getter
@Setter
public class ReportRequestContext {

	private PointsOfInterestView.SearchCriteria searchCriteria;
	private PointsOfInterestResponse response;
	private OutputStream outputStream;

}
