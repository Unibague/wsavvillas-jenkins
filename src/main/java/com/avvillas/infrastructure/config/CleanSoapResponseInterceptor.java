package com.avvillas.infrastructure.config;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Iterator;

public class CleanSoapResponseInterceptor extends AbstractSoapInterceptor {

    public CleanSoapResponseInterceptor() {
        super(Phase.PRE_STREAM);
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        try {
            // Remover header X-Global-Transaction-ID
            removeTransactionIdHeader(message);
            
            // Limpiar prefijos en el XML
            cleanXmlPrefixes(message);
            
        } catch (Exception e) {
            throw new Fault(e);
        }
    }

    private void removeTransactionIdHeader(SoapMessage message) {
        Iterator<Header> headerIterator = message.getHeaders().iterator();
        while (headerIterator.hasNext()) {
            Header header = headerIterator.next();
            if ("X-Global-Transaction-ID".equals(header.getName().getLocalPart())) {
                headerIterator.remove();
                break;
            }
        }
    }

    private void cleanXmlPrefixes(SoapMessage message) throws Exception {
        OutputStream originalOs = message.getContent(OutputStream.class);
        if (originalOs == null) return;

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        message.setContent(OutputStream.class, buffer);
        message.getInterceptorChain().doIntercept(message);

        String xmlContent = buffer.toString("UTF-8");
        
        // Limpiar prefijos os: directamente en el string
        String cleanedXml = xmlContent
            .replaceAll("<os:", "<")
            .replaceAll("</os:", "</")
            .replaceAll("\\s+xmlns:os=\"[^\"]*\"", "")
            .replaceAll("xmlns:os=\"[^\"]*\"\\s*", "")
            .replaceAll("\\s+>", ">");

        originalOs.write(cleanedXml.getBytes("UTF-8"));
        originalOs.flush();
    }
}