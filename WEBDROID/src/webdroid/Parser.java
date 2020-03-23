// This is the implementation of Recursive Descent Parser with stack data structure to track the closing of non-void tags
package webdroid;
import java.util.ArrayList; 
import java.util.*;
import webdroid.CharacterScanner.TOKEN_CODE;
import webdroid.KeywordList.HTML_CODE;
import webdroid.SymbolTable.ElementNode;

public class Parser {
    private ArrayList<String> lexeme = new ArrayList<>();
    private ArrayList<TOKEN_CODE> token = new ArrayList<>();
    private int i = 0; //iteration variable to select each lexeme genarated from TokenScanner. Every iteration of i means point to the next lexeme  
    private int id = 0;
    private Stack<ElementNode> html_element_stack  = new Stack<>();  //NV_tag_stack or Non-void tag Stack is the storage of the Non-void tags in a first-in last-out implementation
    private int current_parent_id;    
    KeywordList kw = new KeywordList();
    KeywordList.HTML_CODE kc;    
     
     CharacterScanner HTML_Scanner = new CharacterScanner();
     
       public void setHTMLParser(String s){           
            HTML_Scanner.Run_Character_Scanner(s);
            lexeme = HTML_Scanner.getLexemeStream();
            token  = HTML_Scanner.getTokenStream();
      }
                 
   
       public void PARSE(){
           HTML();
       }
           
       public void HTML(){
           
           if(lexeme.get(i+1).equals("!")){  //lookahead of there's a Doctype declaration
                if( (lexeme.get(i) + lexeme.get(i+1) + lexeme.get(i+2) + " "+lexeme.get(i+3)+lexeme.get(i+4)) . equals("<!DOCTYPE html>"))
                    i+=4;  // proceed to the first element
                else
                    Error(-1,"");
           }
           else i--;  //If no DOCTYPE declaration then deduct one so the checking of element starts with zero
                       
           Element();             
       } 
       
       public void Element(){
                
               if(lexeme.get(i+1).equals("<")){ // lookahead
                 nextLexeme();                 
                 
                 kc = kw.SearchKeyword(lexeme.get(i+1));  //The lookahead assignment
                 switch(kc){                               //The lookahead statement
                   case HTML_NONVOID_PROPERTY:  //special case for style since it is both nonvoid tag and a property
                   case HTML_NONVOID_TAG: //The lookahead statement if the lexeme is a non-void tag
                                            
                       try{
                            current_parent_id = html_element_stack.peek().getID();
                       }catch(EmptyStackException e){
                            current_parent_id = -1; //setting the root
                       }
                       
                       Non_void_element(current_parent_id);
                       break;
                   case HTML_VOID_TAG:  //The lookahead statement if the lexeme is a void tag
                       Void_element();
                         
                       break;  
                   case HTML_SPECIAL_CHARACTER:
                       //Do nothing
                       break;
                   default:
                         
                        Error(0,"");
                        break;   
                    }
                }
               else{
                   //String element
                   nextLexeme(); 
                  
                    HashMap<String, String> Attribute = new HashMap<>();
                    Attribute.put("text", lexeme.get(i));
                    SymbolTable x = new SymbolTable();
                   
                      try{
                         SymbolTable.ElementNode node = x.new ElementNode(id,  "String_element",HTML_CODE.HTML_STRING_ELEMENT, Attribute,html_element_stack.peek().getID() );
                         SymbolTable.AddToTable(node);
                      }
                      catch(EmptyStackException e){
                               Error(5,""); 
                      }


                   
                    id++;
                 }
                  
       }
       public void Non_void_element(int current_parent){
                HashMap<String, String> Attribute = new HashMap<>();
                
                nextLexeme(); //to move to the next lexeme after the tag name
                String current_tag = lexeme.get(i);
                   do{
                    nextLexeme();  
                    kc = kw.SearchKeyword(lexeme.get(i));
                    Attribute.putAll(Attribute());    // Calling of Attribute method and getting the properties
                }while(kc == KeywordList.HTML_CODE.HTML_PROPERTY_NAME || kc == KeywordList.HTML_CODE.HTML_NONVOID_PROPERTY);
                
                //initiate node html tag node properties
                    SymbolTable x = new SymbolTable();
                    SymbolTable.ElementNode node = x.new ElementNode(id, current_tag,kw.SearchKeyword(current_tag), Attribute,current_parent);
 
               // end of initialization
                 SymbolTable.AddToTable(node);
                
                  if(node.getParent_ID()==-1) 
                    SymbolTable.root= node;
              
                html_element_stack.push(node);
                id++;
                
                if(!(lexeme.get(i).equals(">"))) //Move to the next lexeme which can be new element or string element
                        Error(1,current_tag);
                 
                
                while(!(lexeme.get(i)+lexeme.get(i+1)).equals("</")){  //Repeating element lookahead
                        Element();
                }
                
                if(lexeme.get(i).equals("<")){
                        if(lexeme.get(i+1).equals("/")){ //the lookahead to see if it is a closing tag or another element
                        nextLexeme(); //move two lexemes to reach the tag name being closed
                                              
                         // Get the current tag which is on top of the stack 
                       
                        if(current_tag.equals(lexeme.get(i+1))){  // Compare the name of the tag vs the one on top of the stack                      
                              //System.out.println("Pop:  "+html_element_stack.peek().getTag());
                                html_element_stack.pop();           // Removing of the top tag because it has been closed.
                                nextLexeme();
                        }
                        else
                            Error(3,"");
                    }
                          
                           
                }
                if(lexeme.get(i+1).equals(">")){
                    nextLexeme();  //Move to the next lexeme which can be new element or string element
                  }
                else{
                    Error(1,current_tag);
                }    
                 
                 
       }
      
       public void Void_element(){
            HashMap<String, String> Attribute = new HashMap<>();
            nextLexeme();
            String current_tag = lexeme.get(i);  
           
                do{
                    nextLexeme();  
                    kc = kw.SearchKeyword(lexeme.get(i));

                     Attribute.putAll(Attribute());  
                    
                }while(kc == KeywordList.HTML_CODE.HTML_PROPERTY_NAME || kc == KeywordList.HTML_CODE.HTML_NONVOID_PROPERTY);
                
                SymbolTable x = new SymbolTable();
                SymbolTable.ElementNode node = x.new ElementNode(id, current_tag,kw.SearchKeyword(current_tag), Attribute,html_element_stack.peek().getID());
                SymbolTable.AddToTable(node);
                 id++;
            if(!(lexeme.get(i).equals(">"))) 
                 Error(1,current_tag);  //Move to the next lexeme which can be new element or string element
            

       }
       
      
       private HashMap<String, String> Attribute(){
           HashMap<String, String> Attribute = new HashMap<>();
           String property;
           
           if(kc == KeywordList.HTML_CODE.HTML_PROPERTY_NAME || kc == KeywordList.HTML_CODE.HTML_NONVOID_PROPERTY){
               property = lexeme.get(i);   //storing the property name to be passed to the hashmap return key
               
               nextLexeme(); //  Store the property name into the symbol table (pending)
                      // Look for the equal sign if the lexeme is a property  
                  
                        if(lexeme.get(i).equals("=")){                             
                            nextLexeme();                            
                        }                             
                        else
                            Error(2,property);
                       
                         // Checking if the next lexeme is a string
                        if(!(token.get(i)== TOKEN_CODE.STRING)) 
                            Error(6,"");  
                        else{
                            
                            if(property.equals("style"))                                                                  
                                  Attribute.putAll(Styles(lexeme.get(i)));
                            else
                                  Attribute.put(property, lexeme.get(i));
                        }
                            
           }
           
           return Attribute;
        }
       
          private HashMap<String, String> Styles(String style_string){ 
            HashMap<String, String> style_value = new HashMap<>();
            
            CharacterScanner CSS_Scanner = new CharacterScanner();
            CSS_Scanner.Run_Style_Scanner(style_string); 
                                 
              ArrayList<String> style_lexeme = CSS_Scanner.getLexemeStream();
              ArrayList<TOKEN_CODE> style_token = CSS_Scanner.getTokenStream();
            
                int j = 0;
                String style_name = "";
                 KeywordList.HTML_CODE kk; 
                 
               do{
                  kk = kw.SearchKeyword(style_lexeme.get(j));
		  if(kk == KeywordList.HTML_CODE.CSS_PROPERTY) {
                       style_name = style_lexeme.get(j);
                       j++;

                      if(style_lexeme.get(j).equals(":")){
                          j++; 
                          style_value.put(style_name,style_lexeme.get(j) );
                      }                             
                      else 
                          Error(8,style_name);
                      
                  }
                      
                  else
                    Error(7,style_lexeme.get(j));   
                  
                   System.out.println(style_name+" --->"+style_lexeme.get(j)); 
                   j++; 
                     
                   if(style_token.get(j) != TOKEN_CODE.EOF_TOKEN ){
                   
                       if(style_lexeme.get(j).equals(";") )
                             j++;                           
                        else 
                             Error(9,style_name); 
                   } 
                       
                        
                   System.out.println(style_lexeme.get(j)+" <<--"+style_token.get(j));
		 }while(style_token.get(j) != TOKEN_CODE.EOF_TOKEN );  
              
              return style_value;
                                  
        } 
       
       
       
       private void nextLexeme(){ i++; }  //Moving from one lexeme to the next one.
       private void Error(int code, String s){
           
           switch (code){
               //Generic Error Message:
               case -1: WEBDROID.ErrorMessagePopup("Syntax Error ", "Line "+HTML_Scanner.getLine(i) +": Incorrect/Unsupported DOCTYPE declaration."+lexeme.get(i)+lexeme.get(i+1)); break;
               case 0:  WEBDROID.ErrorMessagePopup("Syntax Error 0","Line "+HTML_Scanner.getLine(i) +": Invalid start tag."); break;
               case 1:  WEBDROID.ErrorMessagePopup("Syntax Error 1","Line "+HTML_Scanner.getLine(i) +": Missing closing angle bracket '>' for the tag: "+s); break;
               case 2:  WEBDROID.ErrorMessagePopup("Syntax Error 2","Line "+HTML_Scanner.getLine(i) +": Missing equal sign '=' for an attribute of "+s); break; 
               case 3:  WEBDROID.ErrorMessagePopup("Syntax Error 3","Line "+HTML_Scanner.getLine(i) +": Error: for tag: "+lexeme.get(i)+lexeme.get(i+1)+". Incorrect tag name to close. It should be: "+html_element_stack.peek().getTag()); break; 
               case 4:  WEBDROID.ErrorMessagePopup("Syntax Error 4","Line "+HTML_Scanner.getLine(i) +": Non_void_element: Syntax Error at : "+lexeme.get(i)+lexeme.get(i+1)); break; 
               case 5:  WEBDROID.ErrorMessagePopup("Syntax Error 5","Line "+HTML_Scanner.getLine(i) +": HTML file should start with a non-void tag not random string."); break; 
               case 6:  WEBDROID.ErrorMessagePopup("Syntax Error 6","Line "+HTML_Scanner.getLine(i) +": Property value should be a string enclosed in quotation marks (\") "); break; 
               case 7:  WEBDROID.ErrorMessagePopup("Syntax Error 7","Line "+HTML_Scanner.getLine(i) +": Unsupported style attribute ("+s+")"); break; 
               case 8:  WEBDROID.ErrorMessagePopup("Syntax Error 8","Line "+HTML_Scanner.getLine(i) +": Missing colon ':' for the style "+s); break; 
               case 9:  WEBDROID.ErrorMessagePopup("Syntax Error 9","Line "+HTML_Scanner.getLine(i) +": Missing semi-colon ';' for the style "+s); break; 
           
           }
            WEBDROID.ErrorDetected =true;
       }
         
     
      
}