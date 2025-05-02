/**
 * 
 */

/**
 * @author Ferdinand Yeke
 *
 */

/*
 * This class is just a class of having many types of tokens,
 * with a type and value from a lexer.
 */
public class TokenClass {
	
	public String type;
	public String value;
	
	
	/*
	 * This sets up a preferred constuctor, where
	 * it gets a type and value of a lexer.
	 */
	public TokenClass(String type, String value)
	{
		this.type = type;
		this.value = value;
	}

	/*
	 * This will have a toString method for returning
	 * the type and value of a lexer string in a fashion of
	 * <type>,<value>.
	 */
	
	/*
	 * @Override
	 */
	public String toString()
	{
		return "<"+type+","+value+">";
	}
}
