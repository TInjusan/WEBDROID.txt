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
          
            
           if(lexemes.get(i).equals("<"))  //The lookahead statement
               Element(); 
       } 
       
       public void Element(){
               i++;
               kc = kw.SearchKeyword(lexemes.get(i));
               System.out.println(lexemes.get(i)+" is "+kc.toString()+ "    Element");
               
                if(lexemes.get(i).equals("<")){
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
           
       }
       
       
       public void Non_void_element(){
                
                System.out.println(lexemes.get(i)+" is "+kw.SearchKeyword(lexemes.get(i)).toString()+ "   Non_void element");
               
                Attribute();
                 
                
                if(lexemes.get(i).equals(">")){
                    i++; //Move to the next lexeme which can be new element or string element
                }
                else{
                    Error(1);
                }
                
                
                if(lexemes.get(i).equals("<")){
                    i++;
                    if(lexemes.get(i).equals("/")){
                        i++;
                                               
                        String current_tag = (String) NV_tag_stack.peek(); // Get the current tag which is on top of the stack 
                       
                        if(current_tag.equals(lexemes.get(i)))  // Compare the name of the tag vs the one on top of the stack                      
                                NV_tag_stack.pop();             // Removing of the top tag because it has been closed.
                        else
                            Error(3);
                    }
                    else{
                        i++;
                        Element(); //if this is not closing an element then it must be a new one,  a child of the one on top of the stack  
                    }          
                         i++;
                }
                    
                else  //Store the string element enclosed in the angle brackets (pending)
                    String_element();
                
       }
      
       public void Void_element(){
           System.out.println(lexemes.get(i)+" is "+kw.SearchKeyword(lexemes.get(i)).toString()+ "    Void element");
               
           Attribute();  
       }
       
       public void Attribute(){
           System.out.println(lexemes.get(i)+" is "+kw.SearchKeyword(lexemes.get(i)).toString()+ "    Attribute");
               
           kc = kw.SearchKeyword(lexemes.get(i));
         
           if(kc == KeywordList.HTML_CODE.HTML_PROPERTY_NAME){
                 i++; //  Store the property name into the symbol table (pending)
                      // Look for the equal sign if the lexeme is a property          
                        if(lexemes.get(i).equals('='))
                            i++;
                        else
                            Error(2);


                         // Checking if the next lexeme is a string
                        if(tokens.get(i)== TOKEN_CODE.STRING){

                            //Store the value of the property here (pending)
                            i++;
                        }
                        else 
                            Error(0);

               Attribute();
              
               
           }
            
       }
       
       public void String_element(){
           System.out.println(lexemes.get(i)+" is "+kw.SearchKeyword(lexemes.get(i)).toString()+ "    String");
               
                if(lexemes.get(i).equals("<"))
                    i++;
                else
                    { i++; String_element(); }
       }
       
            
       public void Error(int code){
           
           switch (code){
               //Generic Error Message:
               case -1: System.out.println("DOCTYPE: Syntax Error at"+lexemes.get(i)+lexemes.get(i+1)); break;
               case 0: System.out.println("Element: Syntax Error at"+lexemes.get(i)+lexemes.get(i+1)); break;
               case 1: System.out.println("Non_void_element: Syntax Error at"+lexemes.get(i)+lexemes.get(i+1)); break;
               case 2: System.out.println("Attribute: Syntax Error at"+lexemes.get(i)+lexemes.get(i+1)); break; 
               case 3: System.out.println("Non_void_element: Syntax Error at"+lexemes.get(i)+lexemes.get(i+1)+"Incorrect tag name to close"); break; 
               
            }
           System.exit(0);         
       }
}
