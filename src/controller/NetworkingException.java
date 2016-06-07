package controller;

public class NetworkingException extends RuntimeException {

	public NetworkingException(Throwable cause) {
		super(cause);
	}

	public NetworkingException(String message, Throwable cause) {
		super(message, cause);
	}

}
