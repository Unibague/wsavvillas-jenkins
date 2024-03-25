package com.avvillas.infrastructure.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.common.ConfigurationConstants;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Interceptor para las peticiones de RESPUESTA de los controladores soap para ATH
 */
@ApplicationScoped
@Named("interceptorOutATH")
public class WSS4JInterceptorOutATH extends WSS4JOutInterceptor {

    public WSS4JInterceptorOutATH() {
        final CallbackHandler passwordCallback = new CallbackHandler() {
            @Override
            public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
                for (Callback callback : callbacks) {
                   /* if (callback instanceof WSPasswordCallback) {
                        ((WSPasswordCallback) callback).setPassword(password);
                    }
                    return;
                    */
                }
            }
        };
        final Map<String, Object> props = new HashMap<>();

        props.put(ConfigurationConstants.ACTION, "Timestamp Signature Encrypt");

        props.put(ConfigurationConstants.SIGNATURE_USER, "wsbancolombia");
        props.put(ConfigurationConstants.SIG_ALGO, "http://www.w3.org/2000/09/xmldsig#rsa-sha1");
        props.put(ConfigurationConstants.PW_CALLBACK_REF, passwordCallback);
        props.put(ConfigurationConstants.SIG_PROP_FILE, "configs/unibague.config");

        props.put(ConfigurationConstants.ENCRYPTION_USER, "cifrado.bancolombia");
        props.put(ConfigurationConstants.ENC_KEY_TRANSPORT, "http://www.w3.org/2001/04/xmlenc#rsa-1_5");
        props.put(ConfigurationConstants.ENC_SYM_ALGO, "http://www.w3.org/2001/04/xmlenc#tripledes-cbc");
        props.put(ConfigurationConstants.ENC_PROP_FILE, "configs/bankcipher.config");
        setProperties(props);
    }
}
