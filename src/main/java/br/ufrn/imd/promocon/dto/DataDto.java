package br.ufrn.imd.promocon.dto;

public class DataDto {
	private AddressDto[] features;

	public DataDto() {}

	public AddressDto[] getFeatures() {
		return features;
	}

	public void setFeatures(AddressDto[] features) {
		this.features = features;
	}
}
