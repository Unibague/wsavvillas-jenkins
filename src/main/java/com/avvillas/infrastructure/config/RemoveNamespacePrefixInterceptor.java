package com.avvillas.infrastructure.config;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import org.apache.cxf.interceptor.StaxInInterceptor;
import org.apache.cxf.interceptor.StaxOutInterceptor;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.binding.soap.interceptor.SoapOutInterceptor;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class RemoveNamespacePrefixInterceptor extends AbstractSoapInterceptor {

    public RemoveNamespacePrefixInterceptor() {
        super(Phase.PRE_STREAM);
        addAfter(StaxOutInterceptor.class.getName());
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        try {
            OutputStream os = message.getContent(OutputStream.class);
            if (os == null) {
                return;
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            message.setContent(OutputStream.class, baos);

            message.getInterceptorChain().doIntercept(message);

            String xml = baos.toString("UTF-8");
            // Eliminar prefijo ns2
            xml = xml.replaceAll("<ns2:", "<");
            xml = xml.replaceAll("</ns2:", "</");

            os.write(xml.getBytes("UTF-8"));
            os.flush();
            message.setContent(OutputStream.class, os);
        } catch (Exception e) {
            throw new Fault(e);
        }
    }
}
