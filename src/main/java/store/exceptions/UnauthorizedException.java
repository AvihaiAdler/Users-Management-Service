package store.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException{
	private static final long serialVersionUID = 3073429734711677310L;
	
	public UnauthorizedException() { }
	
	public UnauthorizedException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public UnauthorizedException(String msg) {
		super(msg);
	}
	
	public UnauthorizedException(Throwable cause) {
		super(cause);
	}
}
