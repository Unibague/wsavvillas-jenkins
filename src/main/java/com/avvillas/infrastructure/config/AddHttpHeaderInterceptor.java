package com.avvillas.infrastructure.config;

import org.apache.cxf.interceptor.AbstractOutDatabindingInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.Phase;

import java.util.*;

public class AddHttpHeaderInterceptor extends AbstractOutDatabindingInterceptor {

    public AddHttpHeaderInterceptor() {
        super(Phase.PRE_STREAM);
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        System.out.println("✅ AddHttpHeaderInterceptor ejecutado");

        // Código de estado HTTP explícito (opcional)
        message.put(Message.RESPONSE_CODE, 200);

        // Headers HTTP
        Map<String, List<String>> headers = (Map<String, List<String>>) message.get(Message.PROTOCOL_HEADERS);
        if (headers == null) {
            headers = new HashMap<>();
            message.put(Message.PROTOCOL_HEADERS, headers);
        }

        headers.put("X-Custom-Header", Collections.singletonList("ValorHeader123"));
        headers.put("X-Powered-By", Collections.singletonList("Quarkus-CXF"));
        headers.put("Cache-Control", Collections.singletonList("no-cache"));
    }
}
