package com.avvillas.infrastructure.config;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.jboss.logging.Logger;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class SoapLoggingInterceptor extends AbstractPhaseInterceptor<Message> {

    private static final Logger log = Logger.getLogger(SoapLoggingInterceptor.class);

    public SoapLoggingInterceptor() {
        super(Phase.PRE_STREAM);
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        log.info(">>> OUTBOUND HEADERS:");
        Map<String, List<String>> headers = (Map<String, List<String>>) message.get(Message.PROTOCOL_HEADERS);
        if (headers != null) {
            headers.forEach((key, values) -> log.info(key + ": " + values));
        }

        try {
            final OutputStream os = message.getContent(OutputStream.class);
            if (os != null) {
                message.setContent(OutputStream.class, new FilterOutputStream(os) {
                    @Override
                    public void write(byte[] b, int off, int len) throws IOException {
                        log.info(new String(b, off, len));
                        super.write(b, off, len);
                    }

                    @Override
                    public void write(int b) throws IOException {
                        super.write(b);
                    }
                });
            }
        } catch (Exception e) {
            log.error("Error in SoapLoggingInterceptor", e);
        }
    }
}
