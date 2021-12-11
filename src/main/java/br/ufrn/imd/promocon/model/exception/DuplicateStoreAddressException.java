package br.ufrn.imd.promocon.model.exception;

public class DuplicateStoreAddressException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public DuplicateStoreAddressException(String errorMessage) {
		super(errorMessage);
	}
}
