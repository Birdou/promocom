package br.ufrn.imd.promocon.model.exception;

public class InvalidDiscountException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidDiscountException(String message) {
		super(message);
	}
}
