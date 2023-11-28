package com.avvillas.application.mapper;

import com.avvillas.application.dto.PmtNotificationResponseXml;
import com.avvillas.domain.model.PmtNotificationResponse;
import org.mapstruct.Mapper;

/**
 * Mapper de PtmNotificationRequest
 */
@Mapper(componentModel = "cdi")
public interface IPmtNotificationResponseMapper {

    /**
     * Convierte un PmtNotificationResponseXml a PmtNotificationResponse
     * @param pmtXml PmtXml para mapear
     * @return PmtNotificationResponse mapeado
     */
    PmtNotificationResponse toPmtNotificationResponse(PmtNotificationResponseXml pmtXml);

    /**
     * Convierte un PmtNotificationResponse a PmtNotificationResponseXml
     * @param pmt Pmt para mapear
     * @return PmtNotificationResponseXml mapeado
     */
    PmtNotificationResponseXml toPmtNotificationResponseXml(PmtNotificationResponse pmt);

}
