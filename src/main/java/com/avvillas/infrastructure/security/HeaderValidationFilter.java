package com.avvillas.infrastructure.security;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

/**
 * Clase encargada de servir como interceptor en cada peticion HTTP REST
 */
@Provider
@Priority(1)
@ApplicationScoped
public class HeaderValidationFilter implements ContainerRequestFilter {

    /**
     * Cabezeras de la solicitud HTTP REST
     */
    @Context
    private HttpHeaders headers;

    /**
     * Provider de google auth
     */
    @Inject
    GoogleAuthProvider googleAuthProvider;

    /**
     * Implementacion del filtro que se ejecuta en cada peticion HTTP REST
     * @param requestContext request context.
     */
    @Override
    public void filter(ContainerRequestContext requestContext) {
        // Se valida cual ruta se esta protegiendo
        if ("/transactionHistory".equals(requestContext.getUriInfo().getPath())) {

            String headerTokenValue = headers.getHeaderString("token-google");

            // Si retorna false es que el token no es valido
            if (headerTokenValue == null || !googleAuthProvider.validateToken(headerTokenValue)) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                        .entity("No autorizado").build());
            }
        }
    }
}
