package com.avvillas.infrastructure.api.soap;

import com.avvillas.application.dto.ConsultBillAvVillasRequestXml;
import com.avvillas.application.dto.ConsultBillAvVillasResponseXml;
import com.avvillas.application.dto.PayBillAvVillasRequestXml;
import com.avvillas.application.dto.PayBillAvVillasResponseXml;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.ws.RequestWrapper;
import jakarta.xml.ws.ResponseWrapper;

/**
 * Unified interface for handling both consulting and paying bills
 */
@WebService(
        name = "wsEstandar", // Matches <wsdl:portType name="wsEstandar">
        targetNamespace = "http://organizacion.com/wsEstandar/"
)

@SOAPBinding(
        style = SOAPBinding.Style.DOCUMENT,  // Matches <soap:binding style="document">
        use = SOAPBinding.Use.LITERAL,  // Matches <soap:body use="literal">
        parameterStyle = SOAPBinding.ParameterStyle.BARE  // Eliminates unnecesary extra wrappers from WSDL
)

public interface IBillAvVillasController {

    /**
     * Devuelve la información de una factura solicitada
     * @param consultBillRequest XML con los datos de la factura a consultar
     * @return XML con la factura consultada
     */
    @WebMethod(
            operationName = "consultarFacturaEstandar",
            action = "http://organizacion.com/wsEstandar/consultarFacturaEstandar"   // Matches <soap:operation soapAction>
    )
    @WebResult(
            name = "os_consultarFacturaEstandarResponse",
            targetNamespace = "http://organizacion.com/wsEstandar/")

    public ConsultBillAvVillasResponseXml consultarFacturaEstandar(
            @WebParam(name = "oe_consultarFacturaEstandar", targetNamespace = "http://organizacion.com/wsEstandar/")
            ConsultBillAvVillasRequestXml consultBillRequest
    );


    /**
     * Guarda la notificacion de pago que envia el banco de una factura
     * @param payBillRequest XML con la notificacion de la factura pagada en el banco
     * @return XML con la confirmacion del pago recibido
     */
    @WebMethod(
            operationName = "pagarFacturaEstandar",
            action = "http://organizacion.com/wsEstandar/pagarFacturaEstandar"   // Matches <soap:operation soapAction>
    )
    @WebResult(
            name = "os_pagarFacturaEstandarResponse",
            targetNamespace = "http://organizacion.com/wsEstandar/")

    public PayBillAvVillasResponseXml pagarFacturaEstandar(
            @WebParam(name = "oe_pagarFacturaEstandar", targetNamespace = "http://organizacion.com/wsEstandar/")
            PayBillAvVillasRequestXml payBillRequest
    );


}
