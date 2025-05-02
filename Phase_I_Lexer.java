import java.util.ArrayList;
import java.util.Scanner;

/*
 * @author Ferdinand Yeke
 * Bellarmine University
 * CS-322 Compilers
 * 
 * This class is based of the ARITMETICA regular expression
 * and its DFA. Its Regular Expression is alpha(alpha|beta)*,
 * where alpha represents all the alphabetic characters, following with
 * with zero or more alphabetic characters OR numeric characters.
 * 
 * NOTE: all other programs will follow suit with the other
 * regular expressions of the ARITMETICA toy program.
 */
public class Phase_I_Lexer {

	/*
	 * Now, this program starts with a boolean method where there is 
	 * a testString. This testString checks the current position of the string.
	 * 
	 * @param testString tests an inputed string. 
	 */
	public static boolean testKeyID(String testString)
	{
		int state = 0;
		int currentPosition = 0;
		char curChar = testString.charAt(currentPosition);
		while (currentPosition < testString.length())
		{
			switch(state)
			{
			case 0:
				if(Character.isLetter(curChar))
					state= 1;
				else
					return false;
			break;
			
			case 1:
				if(Character.isLetter(curChar) || Character.isDigit(curChar))
					state = 2;
				else
					return false;
			break;
			
			/*
			 * Case 2 here is where it loops to back to itself until there are no more characters.
			 */
			case 2:
				if(Character.isLetter(curChar) || Character.isDigit(curChar))
					state = 2;
				else
					return false;
			break;
			}
		currentPosition++;
		}
		
		/*
		 * This represents the end states of the DFA.
		 */
		if(state == 1 || state == 2)
			return true;
		else
			return false;
	}//TestKeyID method ends here.
	
		/*
	 * Now this is a method to check if there is a integer.
	 */
	public static boolean IntegerLit(String testString)
	{
		int state = 0;
		int currentPosition = 0;
		char curChar = testString.charAt(currentPosition);
		while (currentPosition < testString.length())
		{
			switch(state)
			{
			case 0:
				if(Character.isDigit(curChar))
					state= 2;
				/*
				 * If this Integer is a negative, then it must
				 * go to state 1.
				 */
				else if(curChar == '-')
					state = 1;
				
				else
					return false;
			break;
			
			/*
			 * In this state, this is for the negative sign "-" where it counts as a valid input token.
			 */
			case 1:
				//System.out.println(curChar + " here"); debug
				if(curChar == '-')
				{
					/*
					 * This is where curChar is equal to currentPosition, where currentPosition is moved 
					 * one character up (currentPostion+1) the token stream. If the next character after
					 * the negative sign is indeed a digit, this can go to state 2. Otherwise, return as
					 * false.
					 */
					curChar = testString.charAt(currentPosition+1);
					if(Character.isDigit(curChar))
					state = 2;	
				}		
				else
					return false;
			break;
			
			/*
			 * Case 2 here is where it checks to see if there is one decimal. If there is somehow an decimal, this knows
			 * that this is a double.
			 */
			case 2:
				//curChar = testString.charAt(currentPosition+1);
				if(Character.isDigit(curChar))
					state = 2;
				else
					return false;
				break;
				
			}
			//System.out.println(curChar);
			
			//System.out.println(state);

		currentPosition++;
		}
		
		/*
		 * This represents the end states of the DFA.
		 */
		if(state == 2)
			return true;
		else
			return false;
	}//IntegerLit method ends here.
	
	public static boolean DoubleLit(String testString)
	{
		int state = 0;
		int currentPosition = 0;
		char curChar = testString.charAt(currentPosition);
		while (currentPosition < testString.length())
		{
			switch(state)
			{
			case 0:				
				
				/*
				 * If the character is a digit, then go to state 2.
				 */
				if(Character.isDigit(curChar))
					state= 2;
				/*
				 * If this double is a negative, then it
				 * goes to state 1.
				 */
				else if(curChar == '-')
					state = 1;
				else
					return false;
			break;
			
			/*
			 * In this state, this is for the negative sign "-" where it counts as a valid input token.
			 */
			case 1:
				if(curChar == '-')
				{
					/*
					 * This is where curChar is equal to currentPosition, where currentPosition is moved 
					 * one character up (currentPostion+1) the token stream. If the next character after
					 * the negative sign is indeed a digit, this can go to state 2. Otherwise, return as
					 * false.
					 */
					curChar = testString.charAt(currentPosition+1);
					if(Character.isDigit(curChar))
					state = 2;
				}					
				else
					return false;
			break;			
			/*
			 * Case 2 here is where it checks to see if there is one decimal.
			 */
			
			/*
			 * As long as there is not a decimal, case 2 will keep
			 * looping back to itself until there is a decimal. In this loop however,
			 * curChar will keep incrementing itself by one to move to any other possible
			 * digits until there is a decimal token.
			 */
			case 2:
				//System.out.println(curChar); debug code
				if(Character.isDigit(curChar))
				{
					curChar = testString.charAt(currentPosition+1);
					state = 2;
				}	
				else if(curChar == '.')
					state = 3;
				else
					return false;
				break;
				
			case 3:
				if(curChar == '.')
				{
					/*
					 * Moves testString to a next character after a decimal. 
					 * (with an increment of 1).
					 */
					curChar = testString.charAt(currentPosition+1);
					if(Character.isDigit(curChar))
						state = 4;
				}	
				else
					return false;
				break;
				
			case 4:
				if(Character.isDigit(curChar))
					state = 4;
				else
					return false;
				break;
			}
			//Debug Code:
			//System.out.println(curChar +" Current Character");
			//System.out.println(state +" Current State");
		currentPosition++;
		}		
		/*
		 * This represents the end states of the DFA.
		 */
		if(state == 4)
			return true;
		else
			return false;
	}//DoubleLit method ends here.
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	/*
	 * Starting with the main method, we have a program in a string format, which is the programStr variable.
	 * Next, we have the LexerScanner variable that is a instance of Scanner, and LexerScanner scans the programStr
	 * variable. After that, the token list is made to use the TokenClass as its type for the tokens. Lastly, the
	 * CurrentVal variable is made to see the current value of the programStr's string contents.
	 */
		String programStr = "INT X;\n INT Y;\n INT Z;\n X = 5; "
				+ "\n X = -5 * X;\n Y = -10.00 + -5;\n Z = -15.00;";
		Scanner LexerScanner = new Scanner(programStr);
		ArrayList<TokenClass>TokenList = new ArrayList<TokenClass>();
		String currentVal;
		
		/*
		 * Firstly, let's do a while block, where the scan instance
		 * checks by using the next method. In this while loop, there 
		 * should be a integer variable named state, which represents the states
		 * of the Non-Deterministic string above. There should also be an
		 * if else block, where if there is a character in a string that is valid
		 * like 0 to 9, INT, DOUBLE, or other related, then the state variable should
		 * be incremtented. This state variable can also act as a array 
		 * index. If it detects a semi colon, it will know it reached the end of the statment
		 * and goes to the new available statement.
		 * 
		 */
		while(LexerScanner.hasNext())
		{
			currentVal = LexerScanner.next();
			
			//Try block encapsulating if-else blocks
			try {
				/*
				 * If the currentValue of the lexerString contains a semicolon ;, then it should be removed
				 * and the programStr is rewritten in the currentVal string.
				 */
				if(currentVal.charAt(currentVal.length()-1)==';')
				{
					currentVal = currentVal.substring(0,currentVal.length()-1);
					//LexerScanner.equals("test"); Serves as a reference for phase II
				}
				System.out.println(currentVal);
				
				/*
				 * This checks to see any specific tokens like int, double, arithmetic operations, and more.
				 */
				
				/*
				 * If the current Value of the lexerScanner is equal to a string, then it would be an assignment type.
				 */
				if(currentVal.equals("="))
				{
					TokenList.add(new TokenClass("assn", currentVal));
				}
				
				/*
				 * Else, if the next current value is equal to an mathematical operator like +,-,*,/, then it would be a "aop" type. 
				 * (An arithmetic operator type).
				 */
				else if(currentVal.equals("+") || currentVal.equals("-") ||
						currentVal.equals("*") || currentVal.equals("/"))
				{
					TokenList.add(new TokenClass("aop", currentVal));
				}
				
				/*
				 * Else, if the next current value is equal to an relational operator like <,>,<=,>=,== then it would be a "rop" type. 
				 * (An relational operator type).
				 */
				else if(currentVal.equals("==") || currentVal.equals(">") ||
						currentVal.equals("<") || currentVal.equals("<=") ||
						currentVal.equals(">=") || currentVal.equals("!="))
				{
					TokenList.add(new TokenClass("rop",currentVal));
				}
				
				/*
				 * Checks to see any doubles.
				 */
				else if(DoubleLit(currentVal))
				{
					TokenList.add(new TokenClass("Double_Literal",currentVal));
				}
				
				/*
				 * Checks to see any integers
				 */
				else if(IntegerLit(currentVal))
				{
					TokenList.add(new TokenClass("Integer_Literal",currentVal));
				}
				
				/*
				 * Else, if the testKeyID sees that one of the strings in the currentVal is a valid identifier, then it is placed as a
				 * "id_key" type.
				 */
				else if(testKeyID(currentVal))
				{
					TokenList.add(new TokenClass("id_key", currentVal));
				}
				
				/*
				 * Otherwise, if anything else, it would be placed as an other type.
				 */
				else
					TokenList.add(new TokenClass("other", currentVal));
			}//Try block ends here.
			
			//Catch block catching a invalid token in source code.
			catch(Exception e)
			{
				System.out.println("Invalid token in source code." +currentVal);
			}//Catch block ends here.
			
		}//While block ends here.
		
		/*
		 * Now, a for each loop is made to print out all the current tokens in the TokenList array.
		 */
		for(TokenClass currentTokens : TokenList)
		System.out.println(currentTokens);
	}//Main method ends here.
}//Class ends here.
