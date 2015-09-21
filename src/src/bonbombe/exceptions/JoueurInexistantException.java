package bonbombe.exceptions;
import java.lang.Exception;

/**
 * Exception de login existant
 */
public class JoueurInexistantException extends Exception {
	public JoueurInexistantException(String _message){
		super(_message);
	}
}