package fish.payara;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class PointsOfInterestResponse implements Serializable {
	private List<PointOfInterest> pointsOfInterest;
	private String totalCostOfTrip;

	public BigDecimal getTotalCost() {
		return pointsOfInterest
				.stream()
				.map(PointOfInterest::getCost)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private String error;

}
