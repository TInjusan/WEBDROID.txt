 
package webdroid;
import java.util.ArrayList; 
import java.util.*;
import webdroid.TokenScanner.TOKEN_CODE;

public class DF_ReD_Parser {
    
    private ArrayList<String> lexemes = new ArrayList<>();
    private ArrayList<TOKEN_CODE> tokens = new ArrayList<>();
    private int i = 0; //iteration variable to select each lexeme genarated from TokenScanner
    
    Stack<String> NV_tag_stack  = new Stack<>();  //Storage of the Non-void tags in a first-in last-out implementation
    
    KeywordList kw = new KeywordList();
    KeywordList.HTML_CODE kc;
    TokenScanner scan = new TokenScanner();
    TokenScanner.TOKEN_CODE t;
     
       public void setLexeme(String s){
         

        scan.setSource(s);
               
                do{
                    t = scan.next_token();
                    kc = kw.SearchKeyword(scan.getLexeme());
                    lexemes.add(scan.getLexeme());
                    tokens.add(scan.getToken());
                    
                   }while(t != TokenScanner.TOKEN_CODE.EOF_TOKEN);
      }
       
       public void HTML(){
           
           if( (lexemes.get(i) + lexemes.get(i+1) + lexemes.get(i+2) + " "+lexemes.get(i+3)+lexemes.get(i+4)) . equals("<!DOCTYPE html>"))
               i+=5;
            
                                 
           if(lexemes.get(i).equals("<"))  //The lookahead statement
               Element(); 
       } 
       
       public void Element(){
                
               kc = kw.SearchKeyword(lexemes.get(i));
               
               switch(kc){                //The lookahead statement
                   
                   case HTML_NONVOID_TAG: //The lookahead statement if the lexeme is a non-void tag
                       NV_tag_stack.push(lexemes.get(i));  //Pushing a new Non void tag on top of the stack
                       i++;
                       Non_void_element();
                       break;
                   case HTML_VOID_TAG:  //The lookahead statement if the lexeme is a void tag
                       i++;
                       Void_element();
                       break;  
                   default:
                       Error(0);
                       
               }
                             
           
       }
       
       
       public void Non_void_element(){
                
    
                Attribute();
                
                if(lexemes.get(i).equals("<")){
                    i++;
                    if(lexemes.get(i).equals("/")){
                        NV_tag_stack.pop();  // Removing of the top tag because it has been closed.
                    }
                    else          
                        Element(); //if this is not closing an element then it must be a new one,  a child of the one on top of the stack  
                    i++;
                }
                    
                else
                    String_element();
                
                
                 
                  
       }
       
       public void Attribute(){
           kc = kw.SearchKeyword(lexemes.get(i));
         
           if(kc == KeywordList.HTML_CODE.HTML_PROPERTY_NAME){
                 i++; //  Store the property name into the symbol table (pending)
                      // Look for the equal sign if the lexeme is a property          
                        if(lexemes.get(i).equals('='))
                            i++;
                        else
                            Error(0);


                         // Checking if the next lexeme is a string
                        if(tokens.get(i)== TOKEN_CODE.STRING){

                            //Store the value of the property here (pending)
                            i++;
                        }
                        else 
                            Error(0);

               Attribute();
              
               
           }
           else if(lexemes.get(i).equals('>')){
               i++;
           }
                      
       }
       
       public void String_element(){
           
           
                if(lexemes.get(i).equals("<"))
                    i++;
                else
                    { i++; String_element(); }
       }
       
       public void Void_element(){
           
           
       }
       
            
       public void Error(int code){
           
           switch (code){
               //Generic Error Message:
               case 0: System.out.println("Syntax Error");
               
           }
                   
       }
}
