
import static org.objectweb.asm.Opcodes.*;
import java.io.FileOutputStream;
import java.io.IOException;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Label;
import java.io.FileOutputStream;
import java.util.*;
/*
 * @author Ferdinand Yeke
 * Bellarmine University
 * CS-322 Compilers
 * 
 * This class is based of the ARITMETICA regular expression
 * and its DFA. Its Regular Expression is alpha(alpha|beta)*,
 * where alpha represents all the alphabetic characters, following with
 * with zero or more alphabetic characters OR numeric characters. However,
 * this time, this will use opcodes based of the Phase I lexer.
 * 
 * NOTE: all other programs will follow suit with the other
 * regular expressions of the ARITMETICA toy program.
 * 
 */

public class Phase_II_Parser_Opcodes {
	
	static int varcounter = 5;
	static ClassWriter cw;
    static MethodVisitor mv;
    
     //Firstly, making the HashMap for making the variable with the byte-code address.
   static HashMap<String, Integer> LookUpHash = new HashMap<String,Integer>();
    
    /*
     * Makes the second, STATIC HashMap where there is a String for the variable names (like X from INT X, Y from DOUBLE Y, etc), and uses a 
     * boolean to see if it is a valid double or not.
     */
   static HashMap<String, Boolean> isDouble = new HashMap<String, Boolean>();
    
     /*
     * Next, we make a 2D array for the HashMap variables.
     * 
     */
   private static ArrayList<ArrayList<TokenClass>> delimitStatements(ArrayList<TokenClass>progTokens)
   {
   	ArrayList<ArrayList<TokenClass>> delimitedStatements = new ArrayList<ArrayList<TokenClass>>();
   	ArrayList<TokenClass>curStatement = new ArrayList<TokenClass>();
   	
   	for(TokenClass curToken: progTokens)
   	{
   		if (curToken.type.equals("eos"))
   		{
   			delimitedStatements.add(curStatement);
   			curStatement= new ArrayList<TokenClass>();
   		}
   		
   		else
   			curStatement.add(curToken);
   	}
   	return delimitedStatements;
   }//delimitStatements method ends here.
   
    //adds the values for the variableAddressToken like INT 

   /*
    * Declares an INT variable.
    */
   static void declareInt(String variable)
   {
	   //curStatement.get(0);
	   //LookUpHash.get(curStatement);
	   //isDouble.get(curStatement.get(0));
	   
	   if(isDouble.get(variable) == false &&  LookUpHash.get(variable) == 5)
		   
	   {
		   int x=0;
		   mv.visitVarInsn(Opcodes.BIPUSH, x);
		   mv.visitVarInsn(Opcodes.ISTORE, varcounter);//varcounter is 5, so store in location 5 and increment the slots available.
		   varcounter++;
	   }
	   
	   /*
	    * If gets the current Statement
	    */
	   if(isDouble.get(variable) == false &&  LookUpHash.get(variable) == 8)
		   
	   {
		   int z=0;
		   mv.visitVarInsn(Opcodes.BIPUSH, z);
		   mv.visitVarInsn(Opcodes.ISTORE, varcounter+3);//varcounter+3 is 8, so store in location 8 and increment the slots available.
		   varcounter++;
	   }   
	     
   }//declareInt method ends here.
   
   /*
    * Declares a double.
    */
   static void declareDouble(String variable)
   {
	   
	   //LookUpHash.get(curStatement);
	   if(isDouble.get(variable) == true && LookUpHash.get(variable) == 6 && variable == "Y")   
	   {
		   	double y = 0;
		   	mv.visitLdcInsn((Double) y);
			mv.visitVarInsn(Opcodes.DSTORE, varcounter+1);//varcounter+1 is 6, so store in location 6 and increment the slots available.
		   	varcounter++;
	   }
	   
	   if(isDouble.get(variable) == true && LookUpHash.get(variable) == 9 && variable == "U")
	   {
			double u = 0;
		   	mv.visitLdcInsn((Double) u);
			mv.visitVarInsn(Opcodes.DSTORE, varcounter+4);//varcounter+4 is 9, so store in location 9 and increment the slots available.
		   	varcounter++;
	   }
	   
	   
   }//declareDouble method ends here.
   
   /*
    * Prints out the variables.
    */
   static void declarePRINT(String variable)
   {
	   //curStatement.get(0);
	   //LookUpHash.get(curStatement);
	   //isDouble.get(curStatement.get(0));
	   
	   if(variable == "X")
	   {

		   mv.visitVarInsn(Opcodes.ILOAD, 5);
		   mv.visitVarInsn(Opcodes.DLOAD, 9);
		   mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
       	   mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream",  "println", "(I)V", false);
	   }
	   
	   else if(variable == "Y")
	   {
		   mv.visitVarInsn(Opcodes.DLOAD, 6);
		   mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
       	   mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream",  "println", "(D)V", false);
	   }
	   
	   else if(variable == "Z")
	   {
		   mv.visitVarInsn(Opcodes.ILOAD, 8);
		   mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
       	   mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream",  "println", "(I)V", false);
	   }
	   
	   else if(variable == "U")
	   {
		   mv.visitVarInsn(Opcodes.DLOAD, 9);
		   mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
       	   mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream",  "println", "(D)V", false);
	   }
   }
   
   
   /*
    * Declares the input.
    */
   static void declareINPUT()
   {
	   //if(isDouble.get(variable) == false)
	   mv.visitTypeInsn(Opcodes.NEW, "java/util/Scanner");
       mv.visitInsn(Opcodes.DUP);
       mv.visitFieldInsn(GETSTATIC, "java/lang/System", "in", "Ljava/io/InputStream;");  
       mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/util/Scanner",  "<init>", "(Ljava/io/InputStream;)V", false);        
       mv.visitVarInsn(Opcodes.ASTORE, 1);
       
       mv.visitFieldInsn(Opcodes.GETSTATIC,"java/lang/System","out","Ljava/io/PrintStream;");
       mv.visitLdcInsn((String)"Please input a value for a variable X: ");//gets the key from 11, which is X in the lookUpHash
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream","println","(I)V",false);
		
		mv.visitVarInsn(Opcodes.ALOAD, 1);        
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/util/Scanner", "nextInt", "()I;", false);
        mv.visitVarInsn(Opcodes.ISTORE, 5);
        
        
        mv.visitFieldInsn(Opcodes.GETSTATIC,"java/lang/System","out","Ljava/io/PrintStream;");
        mv.visitLdcInsn((String)"Please input a value for a variable Y: ");//gets the key from 11, which is X in the lookUpHash
 		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream","println","(D)V",false);
 		
 		
 		mv.visitVarInsn(Opcodes.ALOAD, 1);        
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/util/Scanner", "nextDouble", "()D;", false);
        mv.visitVarInsn(Opcodes.DSTORE, 6);//Very similar to declareInput!
        
        
        mv.visitFieldInsn(Opcodes.GETSTATIC,"java/lang/System","out","Ljava/io/PrintStream;");
        mv.visitLdcInsn((String)"Please input a value for a variable Z: ");//gets the key from 11, which is X in the lookUpHash
 		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream","println","(I)V",false);
 		
 		
 		mv.visitVarInsn(Opcodes.ALOAD, 1);        
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/util/Scanner", "nextInt", "()I;", false);
        mv.visitVarInsn(Opcodes.DSTORE, 8);//Very similar to declareInput!
        
        
        mv.visitFieldInsn(Opcodes.GETSTATIC,"java/lang/System","out","Ljava/io/PrintStream;");
        mv.visitLdcInsn((String)"Please input a value for a variable U: ");//gets the key from 11, which is X in the lookUpHash
 		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream","println","(D)V",false);
 		
 		
 		mv.visitVarInsn(Opcodes.ALOAD, 1);        
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/util/Scanner", "nextDouble", "()D;", false);
        mv.visitVarInsn(Opcodes.DSTORE, 9);//Very similar to declareInput!
        
		
   }
   
	public static void main(String[] args) throws IOException
	{
		 cw=new ClassWriter(ClassWriter.COMPUTE_FRAMES);
			cw.visit(V11, ACC_PUBLIC+ACC_SUPER, "Phase_II_Parser_Opcodes", null, "java/lang/Object", null);

	        //Create the class
			{
				MethodVisitor mv=cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
				mv.visitCode();
				mv.visitVarInsn(ALOAD, 0); //load the first local variable: this
				mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V",false);
				mv.visitInsn(RETURN);
				mv.visitMaxs(1,1);
				mv.visitEnd();
			}
	        
	        mv=cw.visitMethod(ACC_PUBLIC+ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
	        mv.visitCode();

	        //First off, putting contents in the arrays and hashMap
	        
	       // LookUpHash.put("INT X;",5);//The number is the byte-code value for memory location.
	       // LookUpHash.put("DOUBLE Y;", 6);
	        //LookUpHash.put("INT Z;", 8);
	        //LookUpHash.put("DOUBLE U;", 9);
	        //LookUpHash.put("INPUT X;", 10);
	        //LookUpHash.put("PRINT X;", 11);
	        
	        LookUpHash.put("X",5);
	        LookUpHash.put("Y",6);
	        LookUpHash.put("Z", 8);
	        LookUpHash.put("U", 9);
	        LookUpHash.put("X", 11);
	        LookUpHash.put("X",12);
	        
	        
	        //second HashMap:
	        isDouble.put("X", false);
	        isDouble.put("Y", true);
	        isDouble.put("Z", false);
	        isDouble.put("U", true);
	        isDouble.put("X", false);
	        isDouble.put("X", false);
	        
	        
	       // ArrayList<ArrayList<TokenClass>> Token = new ArrayList<ArrayList<TokenClass>>();
	        ArrayList<TokenClass> curStatement = new ArrayList<TokenClass>();
	        
	        
	        //Separates the two as two tokens
	        curStatement.add(new TokenClass("INT","X;"));
	        curStatement.add(new TokenClass("DOUBLE","Y;"));
	        curStatement.add(new TokenClass("INT","Z;"));
	        curStatement.add(new TokenClass("DOUBLE","U;"));
	        curStatement.add(new TokenClass("INPUT","X;"));
	        curStatement.add(new TokenClass("PRINT","X;"));
	        
	        
	        //Token.get(0).add("INT","X");
	        
	        //For loop that iterates over statements
	        //for  each
	        //if (statement.get(0).value.equals("INT")
	        //declareINT(statement.get(1).value)
	        //...
	        
	        //String delimitedStatements;
	        delimitStatements(curStatement);
	        
	        ArrayList<ArrayList<TokenClass>>PairedStatements = new ArrayList<ArrayList<TokenClass>>();
	        //delimiteedStatements.addAll(varcounter, delimitStatements(curStatement));
	        
	        //NEED AN AVAILABLE 2D arrayLIST!!!
	        PairedStatements.add(new ArrayList<TokenClass>());
	        
	        PairedStatements.get(0).add(new TokenClass("idkey","INT"));
	        PairedStatements.get(0).add(new TokenClass("idkey","X"));
	        PairedStatements.get(0).add(new TokenClass("idkey","DOUBLE"));
	        PairedStatements.get(0).add(new TokenClass("idkey","Y"));
	        PairedStatements.get(0).add(new TokenClass("idkey","INPUT"));
	        PairedStatements.get(0).add(new TokenClass("idkey","X"));
	        PairedStatements.get(0).add(new TokenClass("idkey","PRINT"));
	        PairedStatements.get(0).add(new TokenClass("idkey","X"));
	        
	        
	       // PairedStatements = delimitStatements(PairedStatements);
	        
	        //delimitStatements
	        
	        //Debugging for pairedstatements.
	        //System.out.println(PairedStatements.get(0).indexOf(PairedStatements));
			for(ArrayList<TokenClass>Statement: PairedStatements) {
	        	if(delimitStatements(curStatement).contains("INT"))
	        	{
	        		System.out.println("Working...");
	        		declareInt("X");
	        	}
	        	
	        	else if(delimitStatements(curStatement).contains("DOUBLE"))
	        	{
	        		declareDouble("Y");
	        		declareDouble("Z");
	        	}
	        	
	        	else if(delimitStatements(curStatement).contains("PRINT"))
	        	{
	        		
	        		declarePRINT("X");
	        		declarePRINT("Y");
	        		declarePRINT("Z");
	        		declarePRINT("U");
	        		
	        	}
	        	
	        	//If there is a value that equals INPUT from LookUpHash, then it jumps to declareINPUT, where a user inputs a value to a variable.
	        	else if(delimitStatements(curStatement).contains("INPUT"))
	        	{
	        		declareINPUT();
	        	}
	        }//For loop byte-code precode ends here.
			
			
			//For each loop byte-codee post-code starts here
			for(ArrayList<TokenClass>LookUpHash: PairedStatements)
			{
			 mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
         	 mv.visitLdcInsn((String)"Val"+PairedStatements+"\n");
         	    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream",  "println", "(Ljava/lang/String;)V", false);
         	    
         	    
			}//Byte-code post-code ends here.	
			
			
			mv.visitFieldInsn(Opcodes.GETSTATIC,"java/lang/System","out","Ljava/io/PrintStream;");
		    mv.visitLdcInsn((String)"PRINT X; PRINT Y; PRINT Z;");//gets the key from 11, which is X in the lookUpHash
		    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream","println","(Ljava/lang/String;)V",false);
				
				
				//mv.visitVarInsn(Opcodes.ALOAD, 1);        
		        //mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/util/Scanner", "nextLine", "()Ljava/lang/String;", false);
		        //mv.visitVarInsn(Opcodes.ASTORE, 3);
		        //mv.visitVarInsn(Opcodes.ILOAD, 5);
		        
			
         	  // mv.visitInsn(Opcodes.ILOAD);
          	 // mv.visitInsn(Opcodes.DLOAD);
			
	        
	        mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
			
	        FileOutputStream out=new FileOutputStream("./Phase_II_Parser_Opcodes.class");
			out.write(cw.toByteArray());
			out.close();	        


	        
	        
	        
	}

}
