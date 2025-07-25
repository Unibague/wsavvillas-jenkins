package com.avvillas.infrastructure.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;

import jakarta.xml.ws.Endpoint;

import com.avvillas.infrastructure.config.RemoveNamespacePrefixDomInterceptor;
import com.avvillas.infrastructure.api.soap.controller.BillAvVillasController;
import com.avvillas.domain.usecase.IConsultBillAvVillasUseCase;
import com.avvillas.domain.usecase.IPayBillAvVillasUseCase;
import com.avvillas.config.NoPrefixNamespaceMapper;

@ApplicationScoped
public class CXFConfig {

    @Inject
    Bus bus;

    @Inject
    ProductorJAXBConfig productorJAXBConfig;

    @Inject
    IConsultBillAvVillasUseCase consultUseCase;

    @Inject
    IPayBillAvVillasUseCase payUseCase;

    @Produces
    public Endpoint endpointBillAvVillas() {
        BillAvVillasController controller = new BillAvVillasController(consultUseCase, payUseCase);
        EndpointImpl endpoint = new EndpointImpl(bus, controller);
        endpoint.publish("/wsEstandar");
        endpoint.getOutInterceptors().add(new RemoveNamespacePrefixDomInterceptor());
        endpoint.getProperties().put("jaxb.namespacePrefixMapper", new NoPrefixNamespaceMapper());

        return endpoint;
    }
}
