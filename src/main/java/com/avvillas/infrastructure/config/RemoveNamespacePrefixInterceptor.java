package com.avvillas.infrastructure.config;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class RemoveNamespacePrefixInterceptor extends AbstractPhaseInterceptor<Message> {
    public RemoveNamespacePrefixInterceptor() {
        super(Phase.PRE_STREAM); // ✅ ¡Debe ser esta!
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        if (Boolean.TRUE.equals(message.get(Message.INBOUND_MESSAGE))) return;

        try {
            OutputStream original = message.getContent(OutputStream.class);
            if (original == null) {
                System.out.println("🚫 OutputStream original no disponible");
                return;
            }

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            message.setContent(OutputStream.class, new FilteredOutputStream(original, buffer));

            System.out.println("✅ Interceptor PRE_STREAM activado y envolviendo salida");
        } catch (Exception e) {
            throw new Fault(e);
        }
    }
}
