package com.avvillas.application.service;

import com.avvillas.domain.model.TransactionHistory;
import com.avvillas.domain.repository.ITransactionHistoryRepository;
import com.avvillas.domain.usecase.ITransactionHistoryUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

/**
 * Servicio para los registros del historial de transacciones
 */
@ApplicationScoped
public class TransactionHistoryService implements ITransactionHistoryUseCase {

    /**
     * Repositorio para las TransactionHistory
     */
    private final ITransactionHistoryRepository iTransactionHistoryRepository;

    @Inject
    public TransactionHistoryService(ITransactionHistoryRepository iTransactionHistoryRepository) {
        this.iTransactionHistoryRepository = iTransactionHistoryRepository;
    }

    /**
     * Devuelve todos los registros del historial de transacciones
     * @return Los registros consultados
     */
    @Override
    public List<TransactionHistory> getAll() {
        return iTransactionHistoryRepository.findAll();
    }
}
