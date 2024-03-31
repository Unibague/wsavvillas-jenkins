package com.avvillas.application.mapper;

import com.avvillas.application.dto.ConsultBillAvVillasRequestXml;
import com.avvillas.domain.model.ConsultBillAvVillasRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Mapper de ConsultBillRequestAvVillas
 */
@Mapper(componentModel = "cdi")
public interface IConsultBillRequestMapper {

    /**
     * Convierte un ConsultBillAvVillasRequestXml a ConsultBillAvVillasRequest
     * @param consultRequestXml ConsultBillAvVillasRequestXml para mapear
     * @return ConsultBillAvVillasRequest mapeado
     */
    @Mapping(source = "transactionDate", target = "transactionDate", qualifiedByName = "stringToDate")
    @Mapping(source = "transactionHour", target = "transactionHour", qualifiedByName = "hourToTime")
    @Mapping(source = "compensationDate", target = "compensationDate", qualifiedByName = "stringToDate")
    ConsultBillAvVillasRequest toConsultBillAvVillasRequest(ConsultBillAvVillasRequestXml consultRequestXml);


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
