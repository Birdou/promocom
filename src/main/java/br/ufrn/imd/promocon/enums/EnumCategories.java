package br.ufrn.imd.promocon.enums;

public enum EnumCategories {
	
	ELETRONICOS("Eletrônicos"), INFORMATICA("Informática"), CASA_E_DECORACAO("Casa e decoração"), MODA_E_ACESSORIOS("Moda e acessórios"), SAUDE_E_BELEZA("Saúde e Beleza"), ELETRODOMESTICOS("Eletrodomésticos"), SUPERMERCADO("Sumermercado");
	
	private String description;

	private EnumCategories(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
