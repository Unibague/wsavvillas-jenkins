package com.avvillas.infrastructure.feign;

import com.avvillas.domain.feign.IAtlanteFeign;
import com.avvillas.domain.model.BillRequest;
import com.avvillas.domain.model.BillResponse;
import io.quarkus.logging.Log;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.client.WebClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Implementación para consumir la API de Atlante
 */
@ApplicationScoped
public class AtlanteFeign implements IAtlanteFeign {

    /**
     * URL global de Atlante
     */
    private static final String URL = "*";

    /**
     * Token para conectarse a Atlante
     */
    private static final String TOKEN = "*";

    /**
     * Libreria para realizar peticiones REST
     */
    private final WebClient client;

    @Inject
    public AtlanteFeign(Vertx vertx) {
        this.client = WebClient.create(vertx);
    }

    /**
     * Consume el endpoint de Atlante para consultar la factura
     * @param billRequest Dto con los datos de la factura a consultar
     * @return Dto con la factura consultada
     */
    @Override
    public BillResponse getBill(BillRequest billRequest) {
        try {
            return client.postAbs(URL+"/transactionStatus"+TOKEN)
                    .sendJson(billRequest)
                    .onItem().transform(r -> r.bodyAsJson(BillResponse.class))
                    .await().indefinitely();
        } catch (Exception e) {
            Log.error("Fallo Atlante getBill: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
