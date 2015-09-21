package bonbombe.exceptions;
import java.lang.Exception;

/**
 * Exception de bombe existant
 */
public class BombeExistantException extends Exception {
	public BombeExistantException(String _message){
		super(_message);
	}
}
