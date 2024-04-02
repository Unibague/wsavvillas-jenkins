package com.avvillas.application.mapper;

import com.avvillas.application.dto.PayBillAvVillasResponseXml;
import com.avvillas.domain.model.PayBillAvVillasResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Mapper de IPayBillRequestMapper
 */
@Mapper(componentModel = "cdi")
public interface IPayBillResponseMapper {


    /**
     * Convierte un PayBillAvVillasResponse a PayBillAvVillasResponseXml
     * @param payBillResponse PayBillAvVillasResponse para mapear
     * @return PayBillAvVillasResponseXml mapeado
     */
    @Mapping(source = "transactionDate", target = "transactionDate", qualifiedByName = "dateToString")
    @Mapping(source = "transactionHour", target = "transactionHour", qualifiedByName = "timeToHour")
    @Mapping(source = "compensationDate", target = "compensationDate", qualifiedByName = "dateToString")
    @Mapping(target = "authorizationNumber", constant = "0", qualifiedByName = "stringToInteger" )
    PayBillAvVillasResponseXml toPayBillAvVillasResponse(PayBillAvVillasResponse payBillResponse);

    /**
     * Convierte un LocalDateTime a un DateString(YYYYMMDD)
     * @param dateTime Fecha LocalDateTime a convertir
     * @return Fecha String convertida
     */
    @Named("dateToString")
    default String dateToString(LocalDateTime dateTime) {
        if (dateTime != null) {
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyyMMdd");
            return dateTime.format(formato);
        }
        return null;
    }

    /**
     * Convierte un LocalTime a un HourString(HHMMSS)
     * @param time Hora LocalTime a convertir
     * @return Hora String convertida
     */
    @Named("timeToHour")
    default String timeToHour(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
        return time.format(formatter);
    }

    /**
     * Convierte un String a un Integer
     * @param value Valor a convertir
     * @return Integer convertido
     */
    @Named("stringToInteger")
    default Integer stringToInteger(String value) {
        return Integer.parseInt(value);
    }
}
