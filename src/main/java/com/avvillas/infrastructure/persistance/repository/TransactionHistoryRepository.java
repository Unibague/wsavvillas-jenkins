package com.avvillas.infrastructure.persistance.repository;

import com.avvillas.domain.model.MessagesLog;
import com.avvillas.domain.model.TransactionHistory;
import com.avvillas.domain.repository.ITransactionHistoryRepository;
import com.avvillas.infrastructure.persistance.entity.TransactionHistoryEntity;
import com.avvillas.infrastructure.persistance.mapper.ITransactionHistoryEntityMapper;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Repositorio de una TransactionHistory
 */
@ApplicationScoped
public class TransactionHistoryRepository implements ITransactionHistoryRepository {

    /**
     * Conexion a la base de datos Postgresql
     */
    private final PgPool pgClient;

    /**
     * Mapper del TransactionHistoryEntity
     */
    private final ITransactionHistoryEntityMapper iTransactionHistoryEntityMapper;

    @Inject
    public TransactionHistoryRepository(PgPool client, ITransactionHistoryEntityMapper iTransactionMapper) {
        pgClient = client;
        iTransactionHistoryEntityMapper = iTransactionMapper;
    }

    /**
     * Devuelve una Transaction dada su ID
     * @param id Id de una Transaction
     * @return Transaction encontrada
     */
    @Override
    public TransactionHistory findById(Long id) {
        try {
            return iTransactionHistoryEntityMapper.toTransaction(
                    pgClient
                            .preparedQuery("SELECT * FROM transaction_history WHERE id = $1")
                            .execute(Tuple.of(id))
                            .onItem().transform(RowSet::iterator)
                            .onItem().transform(iterator -> iterator.hasNext() ? TransactionHistoryEntity.from(iterator.next()) : null)
                            .await().indefinitely()
            );
        } catch (Exception e) {
            Log.error(MessagesLog.ERROR_DB.getDescription().concat("Metodo findById: " + e.getMessage()));
            return new TransactionHistory();
        }
    }

    /**
     * Devuelve todas las Transaction que existen
     * @return Lista de Transactions encontradas
     */
    @Override
    public List<TransactionHistory> findAll() {
        try {
            return iTransactionHistoryEntityMapper.toTransactionList(
                    pgClient
                            .query("SELECT * FROM transaction_history ORDER BY request_date DESC")
                            .execute()
                            .onItem().transformToMulti(set -> Multi.createFrom().iterable(set))
                            .onItem().transform(TransactionHistoryEntity::from)
                            .collect().asList().await().indefinitely()
            );
        } catch (Exception e) {
            Log.error(MessagesLog.ERROR_DB.getDescription().concat("Metodo findAll: " + e.getMessage()));
            return new ArrayList<>();
        }
    }

    /**
     * Guarda asincronicamente una Transaction
     * @param t Transaction a guardar
     * @return Objeto asincronico Uni sin contenido
     */
    @Override
    public Uni<Void> insert(TransactionHistory t) {
        try {
            String query = "INSERT INTO transaction_history (request_id, search_type, invoice_id, invoice_card, paid_value, number_status, message_status, request_date, who_send_petition, str_web_service, exception) VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11)";
            Tuple tupleElements =
                    Tuple.tuple(Arrays.asList(t.getRequestId(), t.getSearchType(), t.getInvoiceId(), t.getInvoiceCard(),
                            t.getPaidValue(), t.getNumberStatus(), t.getMessageStatus(), t.getRequestDate(),
                            t.getWhoSendPetition(), "AV Villas", t.getException()));

            return Uni.combine().all().unis(pgClient.preparedQuery(query).execute(tupleElements)).discardItems();
        } catch (Exception e) {
            Log.error(MessagesLog.ERROR_DB.getDescription().concat("Metodo insert: " + e.getMessage()));
            return Uni.createFrom().voidItem();
        }
    }
}
