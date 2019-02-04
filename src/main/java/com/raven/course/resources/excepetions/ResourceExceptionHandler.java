package com.raven.course.resources.excepetions;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.raven.course.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	/***
	 * Controlador de execoes do pacote resource
	 * a anotacao ExceptionHandler indica que eh um tratador para excecoes do tipo objectNotFoundException
	 * instancia um StandartError que informa o Http status, a mensagem de erro e a hora do acontecimento
	 * 
	 * Importante: esse metodo mapeia qualquer excecao do seu tipo que ocorra no codigo
	 */
	
	
	@ExceptionHandler(ObjectNotFoundException.class) // 
	public ResponseEntity<StandartError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
		
		StandartError err = new StandartError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
}
