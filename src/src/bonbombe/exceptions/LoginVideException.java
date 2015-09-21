package bonbombe.exceptions;
import java.lang.Exception;

/**
 * Exception de connexion avec login vide
 */
public class LoginVideException extends Exception {
	public LoginVideException(String _message){
		super(_message);
	}
}