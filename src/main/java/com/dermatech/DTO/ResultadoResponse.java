package com.dermatech.DTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ResultadoResponse {
	public boolean success;
	public String mensaje;
	
    public static ResultadoResponse exito(String mensaje) {
        return new ResultadoResponse(true, mensaje);
    }
    
    public static ResultadoResponse error(String mensaje) {
        return new ResultadoResponse(false, mensaje);
    }
}
