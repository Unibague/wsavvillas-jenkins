package com.avvillas.application.service;

import com.avvillas.application.dto.BillRequestXml;
import com.avvillas.application.dto.BillResponseXml;
import com.avvillas.application.mapper.IBillRequestMapper;
import com.avvillas.application.mapper.IBillResponseMapper;
import com.avvillas.application.mapper.ITransactionHistoryDtoMapper;
import com.avvillas.domain.feign.IAtlanteFeign;
import com.avvillas.domain.model.BillRequest;
import com.avvillas.domain.model.BillResponse;
import com.avvillas.domain.model.TransactionHistory;
import com.avvillas.domain.repository.ITransactionHistoryRepository;
import com.avvillas.domain.usecase.IBillUseCase;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;

/**
 * Servicio para la consulta de una factura
 */
@ApplicationScoped
public class BillService implements IBillUseCase {

    /**
     * API de Atlante
     */
    private final IAtlanteFeign atlanteFeign;

    /**
     * Repositorio para las TransactionHistory
     */
    private final ITransactionHistoryRepository iTransactionHistoryRepository;

    /**
     * Mapper para BillRequest
     */
    private final IBillRequestMapper iBillRequestMapper;

    /**
     * Mapper para BillResponse
     */
    private final IBillResponseMapper iBillResponseMapper;

    /**
     * Mapper para las TransactionHistory
     */
    private final ITransactionHistoryDtoMapper iTransactionHistoryDtoMapper;

    @Inject
    public BillService(IAtlanteFeign atlanteFeign, IBillRequestMapper iBillRequestMapper, IBillResponseMapper iBillResponseMapper, ITransactionHistoryRepository iTransactionHistoryRepository, ITransactionHistoryDtoMapper iTransactionHistoryDtoMapper) {
        this.atlanteFeign = atlanteFeign;
        this.iBillRequestMapper = iBillRequestMapper;
        this.iBillResponseMapper = iBillResponseMapper;
        this.iTransactionHistoryRepository = iTransactionHistoryRepository;
        this.iTransactionHistoryDtoMapper = iTransactionHistoryDtoMapper;
    }

    /**
     * Devuelve la información de una factura solicitada
     * @param billRequestXml Dto con los datos de la factura a consultar
     * @return Dto con la factura consultada
     */
    @Override
    public BillResponseXml getBill(BillRequestXml billRequestXml) {
        billRequestXml.setCurrentDateTime(LocalDateTime.now());
        BillRequest billDto = iBillRequestMapper.toBillRequest(billRequestXml);

        insertBillRequestHistory(billDto);

        BillResponse billResponseJson = atlanteFeign.getBill(billDto);
        billResponseJson.setRequestId(billRequestXml.getRequestId());
        billResponseJson.setCurrentDateTime(LocalDateTime.now());

        insertBillResponseHistory(billResponseJson);

        return iBillResponseMapper.toBillResponseXml(billResponseJson);
    }


    /**
     * Guarda un log de la peticion en base de datos
     * @param billRequest Peticion a guardar
     */
    @Override
    public void insertBillRequestHistory(BillRequest billRequest) {
        iTransactionHistoryRepository.insert(iTransactionHistoryDtoMapper.toTransaction(billRequest)).subscribe().with(
                result -> Log.info("Resultado exitoso BillRequestHistory: " + result.toString()),
                failure -> Log.error("Fallo al guardar BillRequestHistory: " + failure.getMessage())
        );
    }

    /**
     * Guarda un log de la respuesta en base de datos
     * @param billResponse Respuesta a guardar
     */
    @Override
    public void insertBillResponseHistory(BillResponse billResponse) {
        for (int i = 0; i < billResponse.getInvoices().size(); i++) {
            TransactionHistory transaction = iTransactionHistoryDtoMapper.toTransaction(billResponse);
            transaction.setInvoiceId(billResponse.getInvoices().get(i).getInvoiceId());

            iTransactionHistoryRepository.insert(transaction).subscribe().with(
                    result -> Log.info("Resultado exitoso BillResponseHistory: " + result.toString()),
                    failure -> Log.error("Fallo al guardar BillResponseHistory: " + failure.getMessage())
            );
        }
    }

}
