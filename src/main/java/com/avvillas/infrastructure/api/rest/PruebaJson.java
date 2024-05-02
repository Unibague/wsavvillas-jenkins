package com.avvillas.infrastructure.api.rest;


import com.avvillas.domain.usecase.IBillUseCase;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/restPrueba")
public class PruebaJson {


    @GET()
    public String hello() {
        return "Hola quarkus!";
    }
}
