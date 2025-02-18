package com.avvillas.application.mapper;

import com.avvillas.domain.model.BillRequest;
import com.avvillas.domain.model.BillResponse;
import com.avvillas.domain.model.ConsultBillAvVillasRequest;
import com.avvillas.domain.model.ConsultBillAvVillasResponse;
import com.avvillas.domain.model.PayBillAvVillasRequest;
import com.avvillas.domain.model.PayBillAvVillasResponse;
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

    @Mapping(source = "productNumber", target = "requestId")
    @Mapping(target = "searchType", constant = "Numero de factura")
    @Mapping(source = "referenceOne", target = "invoiceId")
    @Mapping(target = "numberStatus", constant = "2", qualifiedByName = "stringToInteger" )
    @Mapping(target = "messageStatus", constant = "Peticion: Consultar factura")
    @Mapping(source = "transactionDate", target = "requestDate")
    @Mapping(target = "whoSendPetition", constant = "Solicita: Banco AV Villas")
    TransactionHistory toTransaction(ConsultBillAvVillasRequest consultBillAvVillasRequest);


    @Mapping(source = "productNumber", target = "requestId")
    @Mapping(source = "referenceOne", target = "invoiceId")
    @Mapping(source = "responseCode", target = "numberStatus")
    @Mapping(source = "responseMessage", target = "messageStatus")
    @Mapping(source = "transactionDate", target = "requestDate")
    @Mapping(source = "totalValue", target = "paidValue")
    @Mapping(target = "whoSendPetition", constant = "Responde: Unibague")
    TransactionHistory toTransaction(ConsultBillAvVillasResponse consultBillAvVillasResponse);


    @Mapping(source = "productNumber", target = "requestId")
    @Mapping(source = "referenceOne", target = "invoiceId")
    @Mapping(source = "totalValue", target = "paidValue")
    @Mapping(source = "transactionDate", target = "requestDate")
    @Mapping(target = "messageStatus", constant = "Peticion: Pagar factura")
    @Mapping(target = "whoSendPetition", constant = "Solicita: Banco AV Villas")
    TransactionHistory toTransaction(PayBillAvVillasRequest payBillAvVillasRequest);

    @Mapping(source = "productNumber", target = "requestId")
    @Mapping(source = "transactionDate", target = "requestDate")
    @Mapping(source = "referenceOne", target = "invoiceId")
    @Mapping(source = "responseCode", target = "numberStatus")
    @Mapping(source = "responseMessage", target = "messageStatus")
    @Mapping(target = "whoSendPetition", constant = "Responde: Unibague")
    TransactionHistory toTransaction(PayBillAvVillasResponse payBillAvVillasResponse);


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
