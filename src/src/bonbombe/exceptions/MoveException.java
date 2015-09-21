package bonbombe.exceptions;
import java.lang.Exception;

public class MoveException extends Exception {
	public MoveException(String _message){
		super(_message);
	}
}