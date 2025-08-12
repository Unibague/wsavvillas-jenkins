package com.avvillas.infrastructure.config;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;



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
        
        // Agregar namespaces al envelope
        String cleanedXml = xmlContent
            .replaceAll("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">", 
                       "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">")
            // Limpiar prefijos os:
            .replaceAll("<os:", "<")
            .replaceAll("</os:", "</")
            .replaceAll("\\s+xmlns:os=\"[^\"]*\"", "")
            .replaceAll("xmlns:os=\"[^\"]*\"\\s*", "")
            .replaceAll("\\s+>", ">")
            // Agregar xmlns="" a elementos hijos
            .replaceAll("<(codBancoOrigen|codCanal|nroProducto|codOficinaOrigen|codCiudad|fechaTransaccion|horaTransaccion|fechaCompensacion|codRespuesta|mensajeRespuesta|valorTotal|fechaVencimiento|nroAutorizacionRecaudo)>", "<$1 xmlns=\"\">")
            // Agregar namespace al elemento raíz
            .replaceAll("<os_consultarFacturaEstandar>", "<os_consultarFacturaEstandar xmlns=\"http://organizacion.com/wsEstandar/\">")
            .replaceAll("<os_pagarFacturaEstandar>", "<os_pagarFacturaEstandar xmlns=\"http://organizacion.com/wsEstandar/\">");

        originalOs.write(cleanedXml.getBytes("UTF-8"));
        originalOs.flush();
    }
}