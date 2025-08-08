package com.avvillas.infrastructure.config;

import org.apache.cxf.Bus;
import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.EndpointImpl;

import com.avvillas.config.NoPrefixNamespaceMapper;
import com.avvillas.domain.usecase.IConsultBillAvVillasUseCase;
import com.avvillas.domain.usecase.IPayBillAvVillasUseCase;
import com.avvillas.infrastructure.api.soap.controller.BillAvVillasController;

import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Startup
@Singleton
public class CXFConfig {

    @Inject
    Bus bus;

    @Inject
    IConsultBillAvVillasUseCase consultUseCase;

    @Inject
    IPayBillAvVillasUseCase payUseCase;

    @PostConstruct
    public void init() {
        System.out.println("⚙️ CXFConfig.init() ejecutado");
        try {
            publishWsEstandar();
        } catch (Exception e) {
            System.err.println("❌ Error al publicar wsEstandar:");
            e.printStackTrace();
        }
    }

    public void publishWsEstandar() {
        BillAvVillasController controller = new BillAvVillasController(consultUseCase, payUseCase);
        EndpointImpl endpoint = new EndpointImpl(bus, controller);
        endpoint.setAddress("/wsEstandar");

        // Logs básicos REQ_IN y RESP_OUT (útiles para consola de desarrollo)
        endpoint.getInInterceptors().add(new LoggingInInterceptor());
        endpoint.getOutInterceptors().add(new LoggingOutInterceptor());

        // Interceptores personalizados
        endpoint.getOutInterceptors().add(new AddSoapHeaderInterceptor());
        endpoint.getOutInterceptors().add(new AddHttpHeaderInterceptor());
        endpoint.getOutInterceptors().add(new SoapLoggingInterceptor());

        // Eliminación de prefijos XML
        endpoint.getProperties().put("jaxb.namespacePrefixMapper", new NoPrefixNamespaceMapper());

        // Logging de CXF habilitado
        endpoint.getFeatures().add(new LoggingFeature());

        // Publicar servicio
        endpoint.publish();
        System.out.println("✅ Publicado: /soap/wsEstandar");
    }
}
