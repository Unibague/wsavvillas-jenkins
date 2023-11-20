package com.avvillas.infrastructure.persistance.mapper;

import com.avvillas.domain.model.TransactionHistory;
import com.avvillas.infrastructure.persistance.entity.TransactionHistoryEntity;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper de TransactionHistory para su entidad semejante
 */
@Mapper(componentModel = "cdi")
public interface ITransactionHistoryEntityMapper {

    /**
     * Crea una TransactionHistory con base en una TransactionHistoryEntity
     * @param transactionHistoryEntity
     * @return Transaction creada
     */
    TransactionHistory toTransaction(TransactionHistoryEntity transactionHistoryEntity);

    /**
     * Crea una TransactionHistoryEntity con base en una TransactionHistory
     * @param transactionHistory
     * @return TransactionEntity creada
     */
    TransactionHistoryEntity toTransactionEntity(TransactionHistory transactionHistory);

    /**
     * Crea una lista de TransactionHistory con base en una lista de TransactionHistoryEntity
     * @param transactionEntityList
     * @return Lista creada
     */
    List<TransactionHistory> toTransactionList(List<TransactionHistoryEntity> transactionEntityList);

}
