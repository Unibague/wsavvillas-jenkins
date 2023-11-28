package com.avvillas.application.mapper;

import com.avvillas.application.dto.PaidInvoiceXml;
import com.avvillas.domain.model.PaidInvoice;
import org.mapstruct.Mapper;

/**
 * Mapper de PaidInvoice
 */
@Mapper(componentModel = "cdi")
public interface IPaidInvoiceMapper {

    /**
     * Convierte un PaidInvoiceXml a PaidInvoice
     * @param paidXml PaidXml para mapear
     * @return PaidInvoice mapeado
     */
    PaidInvoice toPaidInvoice(PaidInvoiceXml paidXml);

    /**
     * Convierte un PaidInvoice a PaidInvoiceXml
     * @param paid Paid para mapear
     * @return PaidInvoiceXml mapeado
     */
    PaidInvoiceXml toPaidInvoiceXml(PaidInvoice paid);
}
