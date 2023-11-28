package com.avvillas.application.mapper;

import com.avvillas.application.dto.PmtNotificationRequestXml;
import com.avvillas.domain.model.PmtNotificationRequest;
import org.mapstruct.Mapper;

/**
 * Mapper de PtmNotificationRequest
 */
@Mapper(componentModel = "cdi", uses = {IPaidInvoiceMapper.class})
public interface IPmtNotificationRequestMapper {

    /**
     * Convierte un PmtNotificationRequestXml a PmtNotificationRequest
     * @param pmtXml PmtXml para mapear
     * @return PmtNotificationRequest mapeado
     */
    PmtNotificationRequest toPmtNotificationRequest(PmtNotificationRequestXml pmtXml);

    /**
     * Convierte un PmtNotificationRequest a PmtNotificationRequestXml
     * @param pmt Pmt para mapear
     * @return PmtNotificationRequestXml mapeado
     */
    PmtNotificationRequestXml toPmtNotificationRequestXml(PmtNotificationRequest pmt);

}
