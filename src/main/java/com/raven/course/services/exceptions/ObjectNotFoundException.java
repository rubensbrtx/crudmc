package com.raven.course.services.exceptions;

public class ObjectNotFoundException extends RuntimeException{

	/**
	 * excecao personalizada criada para tratar de um objeto nao encontrado
	 */
	private static final long serialVersionUID = 1L;

	public ObjectNotFoundException(String msg) {
		super(msg);
	}
	
	public ObjectNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
