package com.avvillas.domain.usecase;

import com.avvillas.domain.model.TransactionHistory;

import java.util.List;

/**
 * Caso de uso para el historial de transacciones
 */
public interface ITransactionHistoryUseCase {

    /**
     * Devuelve todos los registros del historial de transacciones
     * @return Los registros consultados
     */
    public List<TransactionHistory> getAll();
}
