package com.avvillas.application.mapper;

import com.avvillas.application.dto.ConsultBillAvVillasResponseXml;
import com.avvillas.domain.model.ConsultBillAvVillasResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Mapper de ConsultBillResponseAvVillas
 */
@Mapper(componentModel = "cdi")
public interface IConsultBillResponseMapper {


    /**
     * Convierte un ConsultBillAvVillasResponse a ConsultBillAvVillasResponseXml
     * @param consultResponse ConsultResponse para mapear
     * @return ConsultResponseXml mapeado
     */
    @Mapping(source = "transactionDate", target = "transactionDate", qualifiedByName = "dateToString")
    @Mapping(source = "transactionHour", target = "transactionHour", qualifiedByName = "timeToHour")
    @Mapping(source = "compensationDate", target = "compensationDate", qualifiedByName = "dateToString")
    @Mapping(source = "totalValue", target = "totalValue", qualifiedByName = "doubleToString")
    @Mapping(source = "expirationDate", target = "expirationDate", qualifiedByName = "dateToString")
    ConsultBillAvVillasResponseXml toConsultBillAvVillasResponseXml(ConsultBillAvVillasResponse consultResponse);

    /**
     * Convierte un LocalDateTime a un DateString(YYYYMMDD)
     * @param dateTime Fecha LocalDateTime a convertir
     * @return Fecha String convertida
     */
    @Named("dateToString")
    default String dateToString(LocalDateTime dateTime) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyyMMdd");
        return dateTime.format(formato);
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
     * Convierte un Double a un String
     * @param value Valor a convertir
     * @return String convertido
     */
    @Named("doubleToString")
    default String stringToInteger(Double value) {
        return String.valueOf(value);
    }
}
