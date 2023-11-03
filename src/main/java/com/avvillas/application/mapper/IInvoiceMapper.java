package com.avvillas.application.mapper;

import com.avvillas.application.dto.InvoiceXml;
import com.avvillas.domain.model.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Mapper de Invoice
 */
@Mapper(componentModel = "cdi")
public interface IInvoiceMapper {

    /**
     * Convierte un InvoiceXml a Invoice
     * @param invoiceXml InvoiceXml para mapear
     * @return Invoice mapeado
     */
    Invoice toInvoice(InvoiceXml invoiceXml);

    /**
     * Convierte un Invoice a InvoiceXml
     * @param invoice Invoice para mapear
     * @return InvoiceXml mapeado
     */
    @Mapping(source = "expirationDate", target = "expirationDate", qualifiedByName = "convertToLocalDateTime")
    @Mapping(source = "endPaymentDate", target = "endPaymentDate", qualifiedByName = "convertToLocalDateTime")
    InvoiceXml toInvoiceXml(Invoice invoice);

    /**
     * Convierte una fecha LocalDate a LocalDateTime
     * @param localDate Fecha a convertir
     * @return LocalDateTime convertida
     */
    @Named("convertToLocalDateTime")
    default LocalDateTime convertToLocalDateTime(LocalDate localDate) {
        if (localDate == null || localDate.toString().isEmpty()) {
            return null;
        }
        return localDate.atStartOfDay();
    }
}
