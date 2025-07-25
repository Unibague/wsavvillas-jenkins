package com.avvillas.config;

import org.glassfish.jaxb.runtime.marshaller.NamespacePrefixMapper;

public class NoPrefixNamespaceMapper extends NamespacePrefixMapper {
    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        if ("http://organizacion.com/wsEstandar/".equals(namespaceUri)) {
            return "os";
        }
        return suggestion;
    }
}