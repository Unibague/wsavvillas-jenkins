package com.avvillas.infrastructure.config;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
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

public class RemoveNamespacePrefixDomInterceptor extends AbstractSoapInterceptor {

    public RemoveNamespacePrefixDomInterceptor() {
        super(Phase.PRE_STREAM); // Interceptor en fase de salida antes del stream
    }

    private void removeAllPrefixes(Element element) {
        String prefix = element.getPrefix();
        if (prefix != null && !prefix.equals("soapenv") && !prefix.equals("soap")) {
            element.setPrefix(null);
            String localName = element.getLocalName();
            element.getOwnerDocument().renameNode(element, null, localName);
        }

        // Eliminar xmlns innecesarios
        for (int i = element.getAttributes().getLength() - 1; i >= 0; i--) {
            String attrName = element.getAttributes().item(i).getNodeName();
            String attrValue = element.getAttributes().item(i).getNodeValue();
            if ((attrName.startsWith("xmlns:") || attrName.equals("xmlns")) && !attrValue.contains("soap")) {
                element.removeAttribute(attrName);
            }
        }

        // Recursivamente procesar hijos
        for (int i = 0; i < element.getChildNodes().getLength(); i++) {
            if (element.getChildNodes().item(i) instanceof Element childElement) {
                removeAllPrefixes(childElement);
            }
        }
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        try {
            OutputStream originalOs = message.getContent(OutputStream.class);
            if (originalOs == null) return;

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            message.setContent(OutputStream.class, buffer);
            message.getInterceptorChain().doIntercept(message);

            byte[] xmlBytes = buffer.toByteArray();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new ByteArrayInputStream(xmlBytes));

            Element envelope = doc.getDocumentElement();
            Element body = (Element) envelope.getElementsByTagNameNS(
                "http://schemas.xmlsoap.org/soap/envelope/", "Body"
            ).item(0);

            if (body != null) {
                NodeList bodyChildren = body.getChildNodes();
                for (int i = 0; i < bodyChildren.getLength(); i++) {
                    if (bodyChildren.item(i) instanceof Element element) {
                        String localName = element.getLocalName();

                        if ("oe_consultarFacturaEstandar".equals(localName)) {
                            NodeList children = element.getChildNodes();
                            for (int j = 0; j < children.getLength(); j++) {
                                if (children.item(j) instanceof Element childElement) {
                                    removeAllPrefixes(childElement);
                                }
                            }
                        } else {
                            removeAllPrefixes(element);
                        }
                    }
                }
            }

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            ByteArrayOutputStream transformedOutput = new ByteArrayOutputStream();
            transformer.transform(new DOMSource(doc), new StreamResult(transformedOutput));

            // Limpieza final por si quedó algún prefijo como ns2:
            String cleanedXml = transformedOutput.toString("UTF-8");
            cleanedXml = cleanedXml.replaceAll("<os:", "<");
            cleanedXml = cleanedXml.replaceAll("</os:", "</");
            cleanedXml = cleanedXml.replaceAll("xmlns:os=\"[^\"]*\"", ""); // eliminar declaración ns2 si existe

            originalOs.write(cleanedXml.getBytes("UTF-8"));
            originalOs.flush();

        } catch (Exception e) {
            throw new Fault(e);
        }
    }
}
