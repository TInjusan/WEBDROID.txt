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
    private Stack<String> NV_tag_stack  = new Stack<>();  //Storage of the Non-void tags in a first-in last-out implementation
    
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
       
       public void HTML(){
           
           if( (lexemes.get(i) + lexemes.get(i+1) + lexemes.get(i+2) + " "+lexemes.get(i+3)+lexemes.get(i+4)) . equals("<!DOCTYPE html>"))
               i+=5;
           else
               Error(-1);
          
            
           Element(); 
       } 
       
       public void Element(){
              
                if(lexemes.get(i).equals("<")){
                 i++;
                 kc = kw.SearchKeyword(lexemes.get(i));
               
                
                switch(kc){                //The lookahead statement
                   
                   case HTML_NONVOID_TAG: //The lookahead statement if the lexeme is a non-void tag
                       NV_tag_stack.push(lexemes.get(i));  //Pushing a new Non void tag on top of the stack
                       Non_void_element();
                       break;
                   case HTML_VOID_TAG:  //The lookahead statement if the lexeme is a void tag
                       Void_element();
                       break;  
                   default:
                       Error(0);
                       
                    }
                }
                else
                    String_element();
                          
          if(tokens.get(i)!=TokenScanner.TOKEN_CODE.EOF_TOKEN)
              Element();
       }
       
       
       public void Non_void_element(){
                i++; //to move to the next lexeme after the tag name
               
                Attribute();
                 
                
                if(lexemes.get(i).equals(">")){
                    i++; //Move to the next lexeme which can be new element or string element
                }
                else{
                    Error(1);
                }
                
                String_element();
                
                if(lexemes.get(i).equals("<")){
                     
                    if(lexemes.get(i+1).equals("/")){ //the lookahead to see if it is a closing tag or another element
                        i+=2; //move two lexemes to reach the tag name being closed
                                               
                        String current_tag = (String) NV_tag_stack.peek(); // Get the current tag which is on top of the stack 
                       
                        if(current_tag.equals(lexemes.get(i))){  // Compare the name of the tag vs the one on top of the stack                      
                                NV_tag_stack.pop();             // Removing of the top tag because it has been closed.
                                System.out.println(lexemes.get(i)+"   pop");
                                i++;
                        }
                        else
                            Error(3);
                    }
                    else{
                        
                        Element(); //if this is not closing an element then it must be a new one,  a child of the one on top of the stack  
                    }          
                          
                }
                if(lexemes.get(i).equals(">")){
                    i++; //Move to the next lexeme which can be new element or string element
                }
                else{
                    Error(2);
                }    
                
       }
      
       public void Void_element(){
            i++;   
            Attribute(); 
           
            if(lexemes.get(i).equals(">")){
                i++; //Move to the next lexeme which can be new element or string element
            }
            else{
                Error(1);
            }  

       }
       
       public void Attribute(){
             
           kc = kw.SearchKeyword(lexemes.get(i));
         
           if(kc == KeywordList.HTML_CODE.HTML_PROPERTY_NAME){
                 i++; //  Store the property name into the symbol table (pending)
                      // Look for the equal sign if the lexeme is a property  
                  
                        if(lexemes.get(i).equals("="))
                            i++;
                        else
                            Error(2);

                       System.out.println(lexemes.get(i)+" is "+tokens.get(i).toString()+ "    Attribute");
                  
                         // Checking if the next lexeme is a string
                        if(tokens.get(i)== TOKEN_CODE.STRING){

                            //Store the value of the property here (pending)
                            i++;
                        }
                        else 
                            Error(2);

               Attribute();
                       
           }
            
       }
       
       public void String_element(){
                 
                if(lexemes.get(i+1).equals("<")){
                   i++;
                }
                    
                else{ 
                    i++; 
                    String_element(); 
                    //Concatenate the string element here separated by space (pending)
                }
       }
       
            
       public void Error(int code){
           
           switch (code){
               //Generic Error Message:
               case -1: System.out.println("DOCTYPE: Syntax Error at"+lexemes.get(i)+lexemes.get(i+1)); break;
               case 0: System.out.println("Element: Syntax Error at"+lexemes.get(i)+lexemes.get(i+1)); break;
               case 1: System.out.println("Void: Syntax Error at"+lexemes.get(i)+lexemes.get(i+1)); break;
               case 2: System.out.println("Attribute: Syntax Error at"+lexemes.get(i)+lexemes.get(i+1)); break; 
               case 3: System.out.println("Non_void_element: Syntax Error at"+lexemes.get(i)+lexemes.get(i+1)+"Incorrect tag name to close"); break; 
               
            }
           System.exit(0);         
       }
}
