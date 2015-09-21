package bonbombe.exceptions;
import java.lang.Exception;

/**
 * Exception de login existant
 */
public class JeuInexistantException extends Exception {
	public JeuInexistantException(String _message){
		super(_message);
	}
}