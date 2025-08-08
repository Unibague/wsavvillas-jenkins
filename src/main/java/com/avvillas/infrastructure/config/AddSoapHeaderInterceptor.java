package com.avvillas.infrastructure.config;

import java.util.List;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Element;

public class AddSoapHeaderInterceptor extends AbstractSoapInterceptor {

    public AddSoapHeaderInterceptor() {
        super(Phase.PREPARE_SEND); // Antes de enviar el mensaje
    }

    @Override
    public void handleMessage(SoapMessage message) {
        System.out.println(">> Interceptor AddSoapHeaderInterceptor ejecutado");

        try {
            List<Header> headers = message.getHeaders();

            // Crear un nodo XML para el header personalizado
            Element transactionId = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .newDocument()
                .createElementNS("http://organizacion.com/wsEstandar/", "X-Global-Transaction-ID");
            transactionId.setTextContent(UUID.randomUUID().toString());

            headers.add(new Header(new javax.xml.namespace.QName("http://organizacion.com/wsEstandar/", "X-Global-Transaction-ID"), transactionId));

        } catch (Exception e) {
            throw new RuntimeException("Error al agregar encabezado SOAP", e);
        }
    }
}
