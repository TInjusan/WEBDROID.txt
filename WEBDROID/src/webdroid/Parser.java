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
    private SymbolTable table = new SymbolTable();
    KeywordList kw = new KeywordList();
    KeywordList.HTML_CODE kc;    
    public String CSS ="";
    
       public void setHTMLParser(String s){
            CharacterScanner HTML_Scanner = new CharacterScanner();
            HTML_Scanner.Run_Character_Scanner(s);
            lexeme = HTML_Scanner.getLexemeStream();
            token  = HTML_Scanner.getTokenStream();
      }
                 
   
       public void PARSE(){
           HTML();
                      
           if(!CSS.equals("")){
                System.out.println(CSS);
            CharacterScanner CSS_Scanner = new CharacterScanner();
            CSS_Scanner.Run_Character_Scanner(CSS);
            lexeme.clear();
            token.clear();
            
            lexeme = CSS_Scanner.getLexemeStream();
            token  = CSS_Scanner.getTokenStream(); 
           }
         
       }
           
       public void HTML(){
           
           if(lexeme.get(i+1).equals("!")){  //lookahead of there's a Doctype declaration
                if( (lexeme.get(i) + lexeme.get(i+1) + lexeme.get(i+2) + " "+lexeme.get(i+3)+lexeme.get(i+4)) . equals("<!DOCTYPE html>"))
                    i+=4;  // proceed to the first element
                else
                    Error(-1);
           }
           else i--;  //If no DOCTYPE declaration then deduct one so the checking of element starts with zero
                       
           Element(); 
        //   html_element.forEach((key,value) -> System.out.println(key + " = " + value.getTag()));
         
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
                         
                        Error(0);
                        break;   
                    }
                }
               else{
                   //String element
                   nextLexeme(); 
                   //initiate node html tag node properties
                HashMap<String, String> Attribute = new HashMap<>();
                Attribute.put("text", lexeme.get(i));
                ElementNode node = new ElementNode();
                node.ElementNodeProperties(id,  "String_element",HTML_CODE.HTML_STRING_ELEMENT, Attribute,html_element_stack.peek().getID() );
                
                
               //  end of initialization
               //  System.out.println(id+" Tag:= "+node.getName()+"  Parent:= "+node.getParent_ID());                
                SymbolTable.entry.add(node);
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
                SymbolTable.ElementNode  node = new SymbolTable.ElementNode();
                
                node.ElementNodeProperties(id, current_tag,kw.SearchKeyword(current_tag), Attribute,current_parent );
               // end of initialization
                 SymbolTable.entry.add(node);
                
                  if(node.getParent_ID()==-1) 
                    SymbolTable.root= node;
              
                html_element_stack.push(node);
                id++;
                
                if(!(lexeme.get(i).equals(">"))) //Move to the next lexeme which can be new element or string element
                        Error(4);
                 
                
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
                            Error(3);
                    }
                          
                           
                }
                if(lexeme.get(i+1).equals(">")){
                    nextLexeme();  //Move to the next lexeme which can be new element or string element
                  }
                else{
                    Error(2);
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
                
                ElementNode  node = new ElementNode();
                node.ElementNodeProperties(id, current_tag,kw.SearchKeyword(current_tag), Attribute,html_element_stack.peek().getID() );
            
              
                SymbolTable.entry.add(node);
              //   System.out.println(id+" Tag:= "+node.getName()+"  Parent:= "+node.getParent_ID());
                id++;
            if(!(lexeme.get(i).equals(">"))) 
                 Error(1);  //Move to the next lexeme which can be new element or string element
            

       }
       
      
       public HashMap<String, String> Attribute(){
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
                            Error(2);
                       
                         // Checking if the next lexeme is a string
                        if(!(token.get(i)== TOKEN_CODE.STRING)) 
                            Error(2); //Store the value of the property here (pending)
                        else
                            Attribute.put(property, lexeme.get(i));
           }
           
           return Attribute;
        }
       
       private void nextLexeme(){ i++; }  //Moving from one lexeme to the next one.
       private void Error(int code){
           
           switch (code){
               //Generic Error Message:
               case -1: System.out.println("DOCTYPE: Syntax Error at"+lexeme.get(i)+lexeme.get(i+1)); break;
               case 0: System.out.println("Element: Syntax Error at"+lexeme.get(i)+lexeme.get(i+1)); break;
               case 1: System.out.println("Void: Syntax Error at"+lexeme.get(i)+lexeme.get(i+1)); break;
               case 2: System.out.println("Attribute: Syntax Error at"+lexeme.get(i)+lexeme.get(i+1)); break; 
               case 3: System.out.println("Non_void_element: Syntax Error at : "+lexeme.get(i)+lexeme.get(i+1)+" Incorrect tag name to close. It should be: "+html_element_stack.peek().getTag()); break; 
               case 4: System.out.println("Non_void_element: Syntax Error at : "+lexeme.get(i)+lexeme.get(i+1)); break; 
              
            }
           System.exit(0);         
       }
         
        public void CSS(){ 
            
        } 
      
}