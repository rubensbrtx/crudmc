package com.raven.course.domain.enums;

public enum TipoCliente {

	/***
	 * Implementacao de enum para tipo de clientes
	 * para que seja possivel o mapeamento do tipo de cliente atravez 
	 * de codigo, foi criado essa classe
	 */
	
	PESSOAFISICA(1, "Pessoa Fisica"),
	PESSOAJURIDICA(2, "Pessoa Juridica");
	
	private Integer cod;
	private String descricao;
	
	private TipoCliente(Integer cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public Integer getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoCliente toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(TipoCliente x : TipoCliente.values()) {
			if(x.getCod() == cod) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id invalido: " + cod);
	}
	
	
	
}
