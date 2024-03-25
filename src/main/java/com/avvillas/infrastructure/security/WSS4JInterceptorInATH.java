package com.avvillas.infrastructure.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.wss4j.common.ConfigurationConstants;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.dom.WSConstants;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Interceptor para las peticiones de ENTRADA de los controladores soap para ATH
 */
@ApplicationScoped
@Named("interceptorInATH")
public class WSS4JInterceptorInATH extends WSS4JInInterceptor {

    public WSS4JInterceptorInATH() {
        final CallbackHandler passwordCallback = new CallbackHandler() {
            @Override
            public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
                for (Callback callback : callbacks) {
                    if (callback instanceof WSPasswordCallback) {
                        final WSPasswordCallback pc = (WSPasswordCallback) callback;
                     /*   if (username.equals(pc.getIdentifier())) {
                            pc.setPassword(password);
                        }
                        return;
                        */
                    }
                }
            }
        };
        final Map<String, Object> props = new HashMap<>();

        props.put(ConfigurationConstants.ACTION, "UsernameToken Timestamp Signature Encrypt");

        props.put(ConfigurationConstants.USER, "rinbancoS");
        props.put(ConfigurationConstants.PASSWORD_TYPE, WSConstants.PASSWORD_TEXT);
        props.put(ConfigurationConstants.PW_CALLBACK_REF, passwordCallback);

        props.put(ConfigurationConstants.SIG_ALGO, "http://www.w3.org/2000/09/xmldsig#rsa-sha1");
        props.put(ConfigurationConstants.SIG_PROP_FILE, "configs/banksign.config");

        props.put(ConfigurationConstants.DEC_PROP_FILE, "configs/unibague.config");
        props.put(ConfigurationConstants.ENC_KEY_TRANSPORT, "http://www.w3.org/2001/04/xmlenc#rsa-1_5");
        props.put(ConfigurationConstants.ENC_SYM_ALGO, "http://www.w3.org/2001/04/xmlenc#tripledes-cbc");
        setProperties(props);
    }

}
