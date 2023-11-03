package com.avvillas.application.mapper;

import com.avvillas.application.dto.BillRequestXml;
import com.avvillas.domain.model.BillRequest;
import org.mapstruct.Mapper;

/**
 * Mapper de BillRequest
 */
@Mapper(componentModel = "cdi")
public interface IBillRequestMapper {

    /**
     * Convierte un BillRequestXml a BillRequest
     * @param billXml BillRequestXml para mapear
     * @return BillRequest mapeado
     */
    BillRequest toBillRequest(BillRequestXml billXml);

    /**
     * Convierte un BillRequest a BillRequestXml
     * @param bill BillRequest para mapear
     * @return BillRequestXml mapeado
     */
    BillRequestXml toBillRequestXml(BillRequest bill);

}
