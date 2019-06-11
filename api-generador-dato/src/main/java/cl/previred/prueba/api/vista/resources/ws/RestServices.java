package cl.previred.prueba.api.vista.resources.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cl.previred.prueba.api.constant.Constantes;
import cl.previred.prueba.api.negocio.services.UtilFechaServices;
import cl.previred.prueba.api.vista.resources.vo.InData;
import cl.previred.prueba.api.vista.resources.vo.OutData;

@RestController
@RequestMapping("/api-generador-datos")
public class RestServices {
	private static final Logger flog = LoggerFactory.getLogger(RestServices.class);
	
	@Autowired
	private UtilFechaServices service;
	
    @RequestMapping(
    		value 		= "/proceso/nivel1", 
    		method 		= RequestMethod.POST,
    	    consumes	= Constantes.APPLICATION_JSON_UTF8,
    	    produces 	= Constantes.APPLICATION_JSON_UTF8)
	public OutData procesoServicioNivel1(@RequestBody InData inData) {
    	OutData data = new OutData();
    	try {
			System.out.println("Entrada:: " + inData.toString());
			
			data = service.procesarServicioUno(inData);
		} catch (Exception ex) {
			flog.error("Error procesoServicioUno:: ", ex);
		}
    	return data;
	}
	
    @RequestMapping(
    		value 		= "/proceso/nivel3", 
    		method 		= RequestMethod.POST,
    	    consumes	= Constantes.APPLICATION_JSON_UTF8,
    	    produces 	= Constantes.APPLICATION_JSON_UTF8)
	public OutData procesoServicioNivel3(InData inData) {
    	OutData data = new OutData();
    	try {
			System.out.println("Entrada:: " + inData.toString());
			
			data = service.procesarServicioUno(inData);
			data.setFechas(inData.getFechas());
		} catch (Exception ex) {
			flog.error("Error procesoServicioNivel3:: ", ex);
		}
    	return data;
	}
    
}
