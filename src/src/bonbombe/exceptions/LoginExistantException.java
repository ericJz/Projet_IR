package bonbombe.exceptions;
import java.lang.Exception;

/**
 * Exception de login existant
 */
public class LoginExistantException extends Exception {
	public LoginExistantException(String _message){
		super(_message);
	}
}