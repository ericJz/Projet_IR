package bonbombe.exceptions;
import java.lang.Exception;

/**
 * Exception de login existant
 */
public class JeuPleinException extends Exception {
	public JeuPleinException(String _message){
		super(_message);
	}
}