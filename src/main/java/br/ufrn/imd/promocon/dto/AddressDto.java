package br.ufrn.imd.promocon.dto;

public class AddressDto {
	private GeometryDto geometry;

	public AddressDto() {}

	public GeometryDto getGeometry() {
		return geometry;
	}

	public void setGeometry(GeometryDto geometry) {
		this.geometry = geometry;
	}
}
