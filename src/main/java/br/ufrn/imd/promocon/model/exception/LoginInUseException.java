package br.ufrn.imd.promocon.model.exception;

public class LoginInUseException extends Exception {
	private static final long serialVersionUID = 1L;

	public LoginInUseException(String errorMessage) {
        super(errorMessage);
    }
}
