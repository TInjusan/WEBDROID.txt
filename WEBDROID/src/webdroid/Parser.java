// This is the implementation of Recursive Descent Parser with depth first algorithm to track the closing of non-void tags
// DF ReD means Depth First Recursive Descent 
package webdroid;
import java.util.ArrayList; 
import java.util.*;
import webdroid.TokenScanner.TOKEN_CODE;

public class Parser {
    
    private ArrayList<String> lexemes = new ArrayList<>();
    private ArrayList<TOKEN_CODE> tokens = new ArrayList<>();
    private int i = 0; //iteration variable to select each lexeme genarated from TokenScanner. Every iteration of i means point to the next lexeme  
    private Stack<String> NV_tag_stack  = new Stack<>();  //NV_tag_stack or Non-void tag Stack is the storage of the Non-void tags in a first-in last-out implementation
    
    KeywordList kw = new KeywordList();
    KeywordList.HTML_CODE kc;
    TokenScanner scan = new TokenScanner();
    TokenScanner.TOKEN_CODE t;
     
       public void Set_Parser(String s){
            scan.setSource(s);
               
                do{
                    t = scan.next_token();
                    kc = kw.SearchKeyword(scan.getLexeme());
                    lexemes.add(scan.getLexeme());
                    tokens.add(scan.getToken());
                    
                   }while(t != TokenScanner.TOKEN_CODE.EOF_TOKEN);
      }
       
       public void PARSE(){
           HTML();
           System.out.println("Success! Zero Error found");
       }
       private void nextLexeme(String c){
           i++;
       }
       
       
       public void HTML(){
           
           if( (lexemes.get(i) + lexemes.get(i+1) + lexemes.get(i+2) + " "+lexemes.get(i+3)+lexemes.get(i+4)) . equals("<!DOCTYPE html>"))
               i+=4;
           else
               Error(-1);
          
            
           Element(); 
       } 
       
       public void Element(){
              
                if(lexemes.get(i+1).equals("<")){
                 nextLexeme("Element");
                 kc = kw.SearchKeyword(lexemes.get(i+1));  //The lookahead 
                     
                     
                switch(kc){                //The lookahead statement
                   
                   case HTML_NONVOID_TAG: //The lookahead statement if the lexeme is a non-void tag
                       NV_tag_stack.push(lexemes.get(i+1));  //Pushing a new Non void tag on top of the stack
                       System.out.println("Push: "+lexemes.get(i+1));
                       Non_void_element();
                       break;
                   case HTML_VOID_TAG:  //The lookahead statement if the lexeme is a void tag
                       Void_element();
                         
                       break;  
                   case HTML_SPECIAL_CHARACTER:
                       //Do nothing
                       break;
                   default:
                         
                        Error(0);
                        break;   
                    }
                }
                else
                    String_element(); 
       }
       
       
       public void Non_void_element(){
                nextLexeme("Non_void_element"); //to move to the next lexeme after the tag name
               
                Attribute();
                 
                
                if(!(lexemes.get(i).equals(">"))) //Move to the next lexeme which can be new element or string element
                    Error(4);
                 
                
                while(!(lexemes.get(i)+lexemes.get(i+1)).equals("</")){  //Repeating element lookahead
                       Element();
                }
                
                if(lexemes.get(i).equals("<")){
                        if(lexemes.get(i+1).equals("/")){ //the lookahead to see if it is a closing tag or another element
                       nextLexeme("Non_void_element"); //move two lexemes to reach the tag name being closed
                                              
                        String current_tag = (String) NV_tag_stack.peek(); // Get the current tag which is on top of the stack 
                       
                        if(current_tag.equals(lexemes.get(i+1))){  // Compare the name of the tag vs the one on top of the stack                      
                                System.out.println("Pop:  "+lexemes.get(i+1));
                                NV_tag_stack.pop();             // Removing of the top tag because it has been closed.
                                
                                nextLexeme("Non_void_element");
                        }
                        else
                            Error(3);
                    }
                          
                           
                }
                if(lexemes.get(i+1).equals(">")){
                    nextLexeme("Non_void_element");  //Move to the next lexeme which can be new element or string element
                  }
                else{
                    Error(2);
                }    
                 
                 
       }
      
       public void Void_element(){
            nextLexeme("Void_element");   
            Attribute(); 
          
            if(!(lexemes.get(i).equals(">"))) 
                 Error(1);  //Move to the next lexeme which can be new element or string element
            

       }
       
       public void Attribute(){
           nextLexeme("Attribute");  
           kc = kw.SearchKeyword(lexemes.get(i));
         
           if(kc == KeywordList.HTML_CODE.HTML_PROPERTY_NAME){
                  nextLexeme("Attribute"); //  Store the property name into the symbol table (pending)
                      // Look for the equal sign if the lexeme is a property  
                  
                        if(lexemes.get(i).equals("="))
                            nextLexeme("Attribute"); 
                        else
                            Error(2);

                       
                         // Checking if the next lexeme is a string
                        if(!(tokens.get(i)== TOKEN_CODE.STRING)) 
                            Error(2); //Store the value of the property here (pending)
                       
                    Attribute();  // the recursion part
                       
           }
            
       }
       
       public void String_element(){
                 
                if(!(lexemes.get(i+1).equals("<") )){
                   
                    nextLexeme("String_element"); 
                    String_element(); 
                    //Concatenate the string element here separated by space (pending)
                }
              
                //nextLexeme("String_element"); 
       }
       
            
       public void Error(int code){
           
           switch (code){
               //Generic Error Message:
               case -1: System.out.println("DOCTYPE: Syntax Error at"+lexemes.get(i)+lexemes.get(i+1)); break;
               case 0: System.out.println("Element: Syntax Error at"+lexemes.get(i)+lexemes.get(i+1)); break;
               case 1: System.out.println("Void: Syntax Error at"+lexemes.get(i)+lexemes.get(i+1)); break;
               case 2: System.out.println("Attribute: Syntax Error at"+lexemes.get(i)+lexemes.get(i+1)); break; 
               case 3: System.out.println("Non_void_element: Syntax Error at : "+lexemes.get(i)+lexemes.get(i+1)+" Incorrect tag name to close. It should be: "+NV_tag_stack.peek()); break; 
               case 4: System.out.println("Non_void_element: Syntax Error at : "+lexemes.get(i)+lexemes.get(i+1)); break; 
              
            }
           System.exit(0);         
       }
}
