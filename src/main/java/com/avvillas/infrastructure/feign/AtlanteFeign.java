package com.avvillas.infrastructure.feign;

import com.avvillas.domain.feign.IAtlanteFeign;
import com.avvillas.domain.model.BillRequest;
import com.avvillas.domain.model.BillResponse;
import com.avvillas.domain.model.ConsultBillAvVillasRequest;
import com.avvillas.domain.model.ConsultBillAvVillasResponse;
import com.avvillas.domain.model.PayBillAvVillasRequest;
import com.avvillas.domain.model.PayBillAvVillasResponse;
import com.avvillas.domain.model.PmtNotificationRequest;
import com.avvillas.domain.model.PmtNotificationResponse;
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
    private static final String URL = "http://integra.unibague.edu.co/avVillas";

    /**
     * Token para conectarse a Atlante
     */
    private static final String TOKEN = "?api_token=$2y$10$s/5xSDieUMEvYD/gfNqFAeFzvWXt13jhWuugpJzQ9rZQrbGpBYUqo";

    /**
     * Libreria para realizar peticiones REST
     */
    private final WebClient client;

    @Inject
    public AtlanteFeign(Vertx vertx) {
        this.client = WebClient.create(vertx);
    }

    /**
     * Consume el endpoint de Atlante para consultar la factura solicitada por ATH
     * @param billRequest Dto con los datos de la factura a consultar
     * @return Dto con la factura consultada
     */
    @Override
    public BillResponse getBill(BillRequest billRequest) {
        try {
            return client.postAbs(URL+"/transactionStatusATH"+TOKEN)
                    .sendJson(billRequest)
                    .onItem().transform(r -> r.bodyAsJson(BillResponse.class))
                    .await().indefinitely();
        } catch (Exception e) {
            Log.error("Fallo Atlante getBill: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Consume el endpoint de Atlante para pagar facturas enviadas por ATH
     * @param pmtNotificationRequest Dto con los datos de las facturas a pagar
     * @return Dto con la confirmacion de pago de las facturas
     */
    @Override
    public PmtNotificationResponse sendPmtNotification(PmtNotificationRequest pmtNotificationRequest) {
        try {
            return client.postAbs(URL+"/registerPaymentATH"+TOKEN)
                    .sendJson(pmtNotificationRequest)
                    .onItem().transform(r -> r.bodyAsJson(PmtNotificationResponse.class))
                    .await().indefinitely();
        } catch (Exception e) {
            Log.error("Fallo Atlante sendPmtNotification: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Consume el endpoint de Atlante para consultar la factura solicitada por AvVillas
     * @param billAvVillasRequest Dto con los datos de la factura a consultar
     * @return Dto con la factura consultada
     */
    @Override
    public ConsultBillAvVillasResponse consultBillAvVillas(ConsultBillAvVillasRequest billAvVillasRequest) {
        try {
            return client.postAbs(URL+"/transactionStatusAVVILLAS"+TOKEN)
                    .sendJson(billAvVillasRequest)
                    .onItem().transform(r -> {
                                Log.warn("El JSON que esta llegando de Atlante es: " + r.bodyAsString());
                                return r.bodyAsJson(ConsultBillAvVillasResponse.class);
                    }
                    )
                    .await().indefinitely();
        } catch (Exception e) {
            Log.error("Fallo Atlante consultBillAvVillas: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Consume el endpoint de Atlante para pagar la factura enviada por AvVillas
     * @param payBillAvVillasRequest Dto con los datos de la factura a pagar
     * @return Dto con la confirmacion de pago de la factura
     */
    @Override
    public PayBillAvVillasResponse payBillAvVillas(PayBillAvVillasRequest payBillAvVillasRequest) {
        try {
            return client.postAbs(URL+"/registerPaymentAVVILLAS"+TOKEN)
                    .sendJson(payBillAvVillasRequest)
                    .onItem().transform(r -> {
                        Log.warn("El JSON que esta llegando de Atlante es: " + r.bodyAsString());
                        return r.bodyAsJson(PayBillAvVillasResponse.class);
                    })
                    .await().indefinitely();
        } catch (Exception e) {
            Log.error("Fallo Atlante payBillAvVillas: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
