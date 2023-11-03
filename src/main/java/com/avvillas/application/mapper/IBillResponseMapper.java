package com.avvillas.application.mapper;

import com.avvillas.application.dto.BillResponseXml;
import com.avvillas.domain.model.BillResponse;
import org.mapstruct.Mapper;

/**
 * Mapper de BillResponse
 */
@Mapper(componentModel = "cdi", uses = {IInvoiceMapper.class})
public interface IBillResponseMapper {

    /**
     * Convierte un BillResponseXml a BillResponse
     * @param billXml BillResponseXml para mapear
     * @return BillResponse mapeado
     */
    BillResponse toBillResponse(BillResponseXml billXml);

    /**
     * Convierte un BillResponse a BillResponseXml
     * @param bill BillResponse para mapear
     * @return BillResponseXml mapeado
     */
    BillResponseXml toBillResponseXml(BillResponse bill);
}
