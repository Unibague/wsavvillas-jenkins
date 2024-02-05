package com.avvillas.application.mapper;

import com.avvillas.domain.model.BillRequest;
import com.avvillas.domain.model.BillResponse;
import com.avvillas.domain.model.PmtNotificationRequest;
import com.avvillas.domain.model.PmtNotificationResponse;
import com.avvillas.domain.model.TransactionHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper de TransactionHistory para los objetos de dominio
 */
@Mapper(componentModel = "cdi")
public interface ITransactionHistoryDtoMapper {

    /**
     * Crea una TransactionHistory con base en una BillRequest
     * @param billRequest BillRequest como base
     * @return Transaction creada
     */
    @Mapping(source = "searchType", target = "searchType", qualifiedByName = "convertSearchTypeIntegerToString")
    @Mapping(source = "searchType", target = "numberStatus")
    @Mapping(target = "messageStatus", constant = "Peticion: Consultar factura")
    @Mapping(source = "currentDateTime", target = "requestDate")
    @Mapping(target = "whoSendPetition", constant = "Solicita: Banco AV Villas")
    TransactionHistory toTransaction(BillRequest billRequest);


    /**
     * Crea una TransactionHistory con base en una BillResponse
     * @param billResponse BillResponse como base
     * @return Transaction creada
     */
    @Mapping(source = "status", target = "numberStatus", qualifiedByName = "stringToInteger")
    @Mapping(source = "message", target = "messageStatus")
    @Mapping(source = "currentDateTime", target = "requestDate")
    @Mapping(target = "whoSendPetition", constant = "Responde: Unibague")
    TransactionHistory toTransaction(BillResponse billResponse);

    /**
     * Crea una TransactionHistory con base en una PmtNotificationRequest
     * @param pmtNotificationRequest PmtNotificationRequest como base
     * @return Transaction creada
     */
    @Mapping(source = "currentDateTime", target = "requestDate")
    @Mapping(target = "messageStatus", constant = "Peticion: Pagar factura")
    @Mapping(target = "whoSendPetition", constant = "Solicita: Banco AV Villas")
    TransactionHistory toTransaction(PmtNotificationRequest pmtNotificationRequest);

    /**
     * Crea una TransactionHistory con base en una PmtNotificationResponse
     * @param pmtNotificationResponse PmtNotificationResponse como base
     * @return Transaction creada
     */
    @Mapping(source = "status", target = "numberStatus", qualifiedByName = "stringToInteger")
    @Mapping(source = "message", target = "messageStatus")
    @Mapping(target = "whoSendPetition", constant = "Responde: Unibague")
    TransactionHistory toTransaction(PmtNotificationResponse pmtNotificationResponse);


    /**
     * Convierte el valor de searchType a su cadena representativa
     * @param searchType SearchTypeInteger a convertir
     * @return SearchTypeString convertido
     */
    @Named("convertSearchTypeIntegerToString")
    default String convertSearchTypeIntegerToString(Integer searchType) {
        if (searchType == 2) {
            return "Numero de factura";
        } else if (searchType == 1 || searchType == 3) {
            return "Numero de identificacion";
        }
        return "";
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
