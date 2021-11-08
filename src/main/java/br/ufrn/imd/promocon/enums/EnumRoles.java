package br.ufrn.imd.promocon.enums;

public enum EnumRoles {

	ADMIN("ROLE_ADMIN", "Administrador"),
	USER("ROLE_USER", "Usuário");
	
	private String code;
	
	private String description;
	
	private EnumRoles(String code, String description) {
		this.code = code;
		this.description = description;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
