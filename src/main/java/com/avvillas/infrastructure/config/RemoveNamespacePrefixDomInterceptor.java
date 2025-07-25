package com.avvillas.infrastructure.config;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class RemoveNamespacePrefixDomInterceptor extends AbstractSoapInterceptor {

    public RemoveNamespacePrefixDomInterceptor() {
        super(Phase.PRE_STREAM);
    }

    private void removeAllPrefixes(Element element) {
        String prefix = element.getPrefix();
        // Mantén el prefijo "os" solo en la etiqueta raíz
        if (element.getParentNode() != null && element.getParentNode() instanceof Document) {
            // Es la raíz, deja el prefijo "os"
        } else if (prefix != null && !prefix.equals("soapenv") && !prefix.equals("soap")) {
            element.setPrefix(null);
            String localName = element.getLocalName();
            element.getOwnerDocument().renameNode(element, null, localName);
        }

        // Elimina todos los atributos xmlns excepto los de soap/soapenv
        for (int i = element.getAttributes().getLength() - 1; i >= 0; i--) {
            String attrName = element.getAttributes().item(i).getNodeName();
            String attrValue = element.getAttributes().item(i).getNodeValue();
            if (attrName.startsWith("xmlns:") && !attrValue.contains("soap")) {
                element.removeAttribute(attrName);
            }
        }

        for (int i = 0; i < element.getChildNodes().getLength(); i++) {
            if (element.getChildNodes().item(i) instanceof Element) {
                removeAllPrefixes((Element) element.getChildNodes().item(i));
            }
        }
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

            byte[] xmlBytes = baos.toByteArray();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputStream is = new ByteArrayInputStream(xmlBytes);
            Document doc = db.parse(is);

            removeAllPrefixes(doc.getDocumentElement());

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            transformer.transform(new DOMSource(doc), new StreamResult(outputStream));

            byte[] outputBytes = outputStream.toByteArray();

            os.write(outputBytes);
            os.flush();

            message.setContent(OutputStream.class, os);
        } catch (Exception e) {
            throw new Fault(e);
        }
    }
}
