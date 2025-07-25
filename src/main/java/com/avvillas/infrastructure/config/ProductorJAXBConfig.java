package com.avvillas.infrastructure.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.CXFBusFactory;

@ApplicationScoped
public class ProductorJAXBConfig {

    @Produces
    public Bus bus() {
        return CXFBusFactory.getDefaultBus();
    }
}
