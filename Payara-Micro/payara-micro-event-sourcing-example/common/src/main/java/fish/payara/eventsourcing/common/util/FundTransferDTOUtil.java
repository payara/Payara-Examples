package fish.payara.eventsourcing.common.util;

import fish.payara.eventsourcing.common.dto.FundTransferDTO;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

public class FundTransferDTOUtil {

    public static String fundTransferDTOToJson(FundTransferDTO fundTransferDTO) {
        Jsonb jsonB = JsonbBuilder.create();
        return jsonB.toJson(fundTransferDTO);
    }

    public static FundTransferDTO jsonToFundTransferDTO(String fundTransferDTOJson) {
        Jsonb jsonb =JsonbBuilder.create();
        return jsonb.fromJson(fundTransferDTOJson, FundTransferDTO.class);
    }
}
