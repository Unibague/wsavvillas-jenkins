package com.avvillas.domain.repository;

import com.avvillas.domain.model.TransactionHistory;
import io.smallrye.mutiny.Uni;

import java.util.List;

/**
 * Contrato para un TransactionHistoryRepository
 */
public interface ITransactionHistoryRepository {

    /**
     * Devuelve una Transaction dada su ID
     * @param id Id de una Transaction
     * @return Transaction encontrada
     */
    public TransactionHistory findById(Long id);

    /**
     * Devuelve todas las Transaction que existen
     * @return Lista de Transactions encontradas
     */
    public List<TransactionHistory> findAll();

    /**
     * Guarda asincronicamente una Transaction
     * @param transaction Transaction a guardar
     * @return Objeto asincronico Uni sin contenido
     */
    public  Uni<Void> insert(TransactionHistory transaction);
}
