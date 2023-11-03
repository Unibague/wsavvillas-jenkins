package com.avvillas.infrastructure.feign;

import com.avvillas.domain.dto.BillResponseDto;
import com.avvillas.domain.dto.BillResponseDtoJson;
import com.avvillas.domain.feign.IAtlante;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.client.HttpResponse;
import io.vertx.mutiny.ext.web.client.WebClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;

import java.text.SimpleDateFormat;

@ApplicationScoped
public class AtlanteFeign implements IAtlante {

    private static final String URL = "*";

    private final WebClient client;

    private final ObjectMapper objectMapper;

    @Inject
    public AtlanteFeign(Vertx pVertx, ObjectMapper pObjectMapper) {
        this.client = WebClient.create(pVertx);
        this.objectMapper = pObjectMapper;
    }

    @GET
    @Override
    public BillResponseDto getBill() {
        System.out.println("entré al asincronico");

        // Realizar la solicitud al servicio REST y obtener la respuesta como String
        String jsonResponse = client.getAbs(URL)
                .send()
                .onItem().transform(HttpResponse::bodyAsString)
                .await().indefinitely();

        // Deserializar la respuesta JSON a tu tipo de objeto deseado
        try {
            return objectMapper.readValue(jsonResponse, BillResponseDto.class);
        } catch (Exception e) {
            // Manejar cualquier error de deserialización
            throw new RuntimeException("Error al deserializar la respuesta JSON", e);
        }
    }

}
