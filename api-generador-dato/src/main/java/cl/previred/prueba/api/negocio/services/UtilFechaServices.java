package cl.previred.prueba.api.negocio.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.previred.prueba.api.vista.resources.vo.InData;
import cl.previred.prueba.api.vista.resources.vo.OutData;

@Service
public class UtilFechaServices {
	
	private static final String FORMAT_FECHA_CL 		= "dd-MM-yyyy";
	private static final String FORMAT_FECHA_US 		= "dd-MM-";
	private static final String FORMAT_FECHA_MESES 		= "MM-yyyy";
	
	private static final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al día
	private static final long MILLSECS_PER_MES = 30; //Milisegundos al día
	
	
	public OutData procesarServicioUno(InData inData) {
		OutData outData = new OutData();
		try {
			outData.setId(				inData.getId());
			outData.setFechaCreacion(	inData.getFechaCreacion());
			outData.setFechaFin(		inData.getFechaFin());
			outData.setFechasFaltantes(	getFechasFaltantes(inData));
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return outData;
	}
	
	private List<String> getFechasFaltantes(InData data){
		List<String> fechas = new ArrayList<>();
		Integer mes  = null;
		
		System.out.println("fechaCreacion	::" + data.getFechaFin());
		System.out.println("fechaFin		::" + data.getFechaCreacion());
		System.out.println("");
		System.out.println("fechaCreacion	::" + getFechaCL(data.getFechaFin()));
		System.out.println("fechaFin		::" + getFechaCL(data.getFechaCreacion()));
		
		
		Long dias  = (getFechaCL(data.getFechaFin()).getTime() - getFechaCL(data.getFechaCreacion()).getTime()) / MILLSECS_PER_DAY;
		Long meses = dias / MILLSECS_PER_MES;
		System.out.println("meses:" + meses);
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		
		Date fechaCreacion = getFechaCL(data.getFechaCreacion());
		mes = Integer.parseInt(sdf.format(fechaCreacion)) - 1;
		for (int i = 0; i < meses; i++) {
			mes += i;
			
			String fechaFaltante = fechaSumaMeses(fechaCreacion, mes);
			if(noExisteFecha(data.getFechas(), fechaFaltante) && i <= 100) {
				fechas.add(fechaFaltante);
			}
		}
		return fechas;
	}
	
	private String fechaSumaMeses(Date fechaCreacion, Integer periodo) {
		String fecha = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_FECHA_CL);

			Calendar cal = Calendar.getInstance(); 
			cal.setTime(fechaCreacion);
			cal.add(Calendar.MONTH, periodo);
			fecha = sdf.format(cal.getTime());
			
			fecha = invertirFecha(fecha, "01");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fecha;
	}
	
	private Date getFechaCL(String fecha) {
		Date fechaNew = null;
		try { 
			fecha = invertirFecha(fecha, null);
			
			SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_FECHA_CL);
			fechaNew = sdf.parse(fecha);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fechaNew;
	}
	
	private String invertirFecha(String fecha, String dia) {
		String fechaNew = null;
		try {
			if(fecha != null) {
				String aux[] = fecha.split("-");
				if(dia != null) {
					fechaNew = aux[2] + "-" + aux[1] + "-" + aux[0]; 					
				}else {
					fechaNew = aux[2] + "-" + aux[1] + "-" + dia;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return fechaNew;
	}
	
	private String getFechaUS(Date fecha) {
		String fechaNew = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_FECHA_CL);
			fechaNew = sdf.format(fecha);
			
			fechaNew = invertirFecha(fechaNew, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fechaNew;
	}
	
	private Boolean noExisteFecha(List<String> fechas, String fechaActual) {
		boolean control = false;
		for (String fecha : fechas) {
			if(!fechaActual.equals(fecha)) {
				control = true;
			}else {
				control = false;
				break;
			}
		}
		return control;
	}
	
}
