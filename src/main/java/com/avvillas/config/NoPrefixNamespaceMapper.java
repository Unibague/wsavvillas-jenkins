package com.avvillas.config;

public class NoPrefixNamespaceMapper extends org.glassfish.jaxb.runtime.marshaller.NamespacePrefixMapper {
    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        if ("http://organizacion.com/wsEstandar/".equals(namespaceUri)) {
            return ""; // sin prefijo para los elementos hijos
        }
        return suggestion;
    }
}
