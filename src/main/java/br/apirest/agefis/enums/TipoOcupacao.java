package br.apirest.agefis.enums;

public enum TipoOcupacao {
	
	HORISTA (1,"Horista"),
	MENSALISTA (2,"Mensalista");

	private Integer id;
	private String descricao;
	
	private TipoOcupacao(Integer id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public static TipoOcupacao toEnum(Integer id) {
		if (id == null) {
			return null;
		}
		
		for (TipoOcupacao t : TipoOcupacao.values()) {
			if (id.equals(t.getId())) {
				return t;
			}
		}
		
		throw new IllegalArgumentException("Id invalido:" + id);
	}	
}
