package bonbombe.exceptions;
import java.lang.Exception;

/**
 * Exception de login existant
 */
public class NomJeuExistantException extends Exception {
	public NomJeuExistantException(String _message){
		super(_message);
	}
}