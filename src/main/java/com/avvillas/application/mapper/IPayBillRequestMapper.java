package com.avvillas.application.mapper;

import com.avvillas.application.dto.PayBillAvVillasRequestXml;
import com.avvillas.domain.model.PayBillAvVillasRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Mapper de IPayBillRequestMapper
 */
@Mapper(componentModel = "cdi")
public interface IPayBillRequestMapper {


    /**
     * Convierte un PayBillAvVillasRequestXml a PayBillAvVillasRequest
     * @param payBillAvVillasRequestXml PayBillAvVillasRequestXml para mapear
     * @return PayBillAvVillasRequest mapeado
     */
    @Mapping(source = "transactionDate", target = "transactionDate", qualifiedByName = "stringToDate")
    @Mapping(source = "transactionHour", target = "transactionHour", qualifiedByName = "hourToTime")
    @Mapping(source = "compensationDate", target = "compensationDate", qualifiedByName = "stringToDate")
    @Mapping(source = "cashValue", target = "cashValue", qualifiedByName = "stringToDouble")
    @Mapping(source = "chequeValue", target = "chequeValue", qualifiedByName = "stringToDouble")
    @Mapping(source = "totalValue", target = "totalValue", qualifiedByName = "stringToDouble")
    PayBillAvVillasRequest toPayBillAvVillasRequest(PayBillAvVillasRequestXml payBillAvVillasRequestXml);

    /**
     * Convierte un String a un Double
     * @param value Valor a convertir
     * @return Double convertido
     */
    @Named("stringToDouble")
    default Double stringToDouble(String value) {
        return Double.parseDouble(value);
    }

    /**
     * Convierte un DateString(YYYYMMDD) a un LocalDateTime
     * @param dateString Fecha String a convertir
     * @return Fecha LocalDateTime convertida
     */
    @Named("stringToDate")
    default LocalDateTime stringToDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return LocalDate.parse(dateString, formatter).atStartOfDay();
    }

    /**
     * Convierte un HourString(HHMMSS) a un LocalTime
     * @param hourString Hora String a convertir
     * @return Hora LocalTime convertida
     */
    @Named("hourToTime")
    default LocalTime hourToTime(String hourString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
        return LocalTime.parse(hourString, formatter);
    }
}
