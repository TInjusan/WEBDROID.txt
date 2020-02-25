// This is the implementation of Recursive Descent Parser with stack data structure to track the closing of non-void tags
package webdroid;
import java.util.ArrayList; 
import java.util.*;
import webdroid.CharacterScanner.TOKEN_CODE;
import webdroid.KeywordList.HTML_CODE;

public class Parser {
    private static HashMap<Integer, SymbolTable> html_element = new HashMap<>();
    private static List<SymbolTable> children;
    private SymbolTable root;
    private ArrayList<String> lexemes = new ArrayList<>();
    private ArrayList<TOKEN_CODE> tokens = new ArrayList<>();
    private int i = 0; //iteration variable to select each lexeme genarated from TokenScanner. Every iteration of i means point to the next lexeme  
    private int id = 0;
    private Stack<SymbolTable> html_element_stack  = new Stack<>();  //NV_tag_stack or Non-void tag Stack is the storage of the Non-void tags in a first-in last-out implementation
    private int current_parent_id;
    
    KeywordList kw = new KeywordList();
    KeywordList.HTML_CODE kc;
    CharacterScanner scan = new CharacterScanner();
    CharacterScanner.TOKEN_CODE t;
     
       public void Set_Parser(String s){
            scan.setSource(s);
               
                do{
                    t = scan.next_token();
                    kc = kw.SearchKeyword(scan.getLexeme());
                     //Printing of token stream // Testing
                     // System.out.println(scan.getLexeme()+"\t\t\t\t\t"+"- "+scan.getToken()+"\t\t\t\t\t\t"+"- "+ kc);
                                 
                    lexemes.add(scan.getLexeme());
                    tokens.add(scan.getToken());
                   
                   }while(t != CharacterScanner.TOKEN_CODE.EOF_TOKEN);
      }
       
      
       public void PARSE(){
           HTML();
           System.out.println("Success! Zero Error found");  //HTML CSS Files are good to go.
           
           buildSymbolTree(root);    // Building of Abstract Syntax Tree
       //  printSymbolTree(root,0);  //Viewing the hierarchy of each tag
       }
    
       
       public void HTML(){
           
           if(lexemes.get(i+1).equals("!")){  //lookahead of there's a Doctype declaration
                if( (lexemes.get(i) + lexemes.get(i+1) + lexemes.get(i+2) + " "+lexemes.get(i+3)+lexemes.get(i+4)) . equals("<!DOCTYPE html>"))
                    i+=4;  // proceed to the first element
                else
                    Error(-1);
           }
           else i--;  //If no DOCTYPE declaration then deduct one so the checking of element starts with zero
                       
           Element(); 
        //   html_element.forEach((key,value) -> System.out.println(key + " = " + value.getTag()));
         
       } 
       
       public void Element(){
                
               if(lexemes.get(i+1).equals("<")){
                 nextLexeme();
                 
                 
                 kc = kw.SearchKeyword(lexemes.get(i+1));  //The lookahead assignment
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
                Attribute.put("text", lexemes.get(i));
                SymbolTable  node = new SymbolTable(id,  "String_element",HTML_CODE.HTML_STRING_ELEMENT, Attribute,html_element_stack.peek().getID() );
                
                
                  //end of initialization
               //  System.out.println(id+" Tag:= "+node.getName()+"  Parent:= "+node.getParent_ID());                
                html_element.put(node.getID(), node);
                id++;
                 }
                  
       }
       public void Non_void_element(int current_parent){
                HashMap<String, String> Attribute = new HashMap<>();
                
                nextLexeme(); //to move to the next lexeme after the tag name
                String current_tag = lexemes.get(i);
                   do{
                    nextLexeme();  
                    kc = kw.SearchKeyword(lexemes.get(i));
                    Attribute.putAll(Attribute());    // Calling of Attribute method and getting the properties
                }while(kc == KeywordList.HTML_CODE.HTML_PROPERTY_NAME || kc == KeywordList.HTML_CODE.HTML_NONVOID_PROPERTY);
                
                //initiate node html tag node properties
                SymbolTable  node = new SymbolTable(id, current_tag,kw.SearchKeyword(current_tag), Attribute,current_parent );
               //  end of initialization
               
                html_element.put(node.getID(), node);
                  if(node.getParent_ID()==-1){
                    root= node;
                }
                html_element_stack.push(node);
                id++;
                
                if(!(lexemes.get(i).equals(">"))) //Move to the next lexeme which can be new element or string element
                        Error(4);
                 
                
                while(!(lexemes.get(i)+lexemes.get(i+1)).equals("</")){  //Repeating element lookahead
                        Element();
                }
                
                if(lexemes.get(i).equals("<")){
                        if(lexemes.get(i+1).equals("/")){ //the lookahead to see if it is a closing tag or another element
                        nextLexeme(); //move two lexemes to reach the tag name being closed
                                              
                         // Get the current tag which is on top of the stack 
                       
                        if(current_tag.equals(lexemes.get(i+1))){  // Compare the name of the tag vs the one on top of the stack                      
                              //System.out.println("Pop:  "+html_element_stack.peek().getTag());
                                html_element_stack.pop();           // Removing of the top tag because it has been closed.
                                nextLexeme();
                        }
                        else
                            Error(3);
                    }
                          
                           
                }
                if(lexemes.get(i+1).equals(">")){
                    nextLexeme();  //Move to the next lexeme which can be new element or string element
                  }
                else{
                    Error(2);
                }    
                 
                 
       }
      
       public void Void_element(){
            HashMap<String, String> Attribute = new HashMap<>();
            nextLexeme();
            String current_tag = lexemes.get(i);  
           
                do{
                    nextLexeme();  
                    kc = kw.SearchKeyword(lexemes.get(i));

                     Attribute.putAll(Attribute());  
                    
                }while(kc == KeywordList.HTML_CODE.HTML_PROPERTY_NAME || kc == KeywordList.HTML_CODE.HTML_NONVOID_PROPERTY);
                
                SymbolTable  node = new SymbolTable(id, current_tag,kw.SearchKeyword(current_tag), Attribute,html_element_stack.peek().getID() );
            
              
                html_element.put(node.getID(), node);
              //   System.out.println(id+" Tag:= "+node.getName()+"  Parent:= "+node.getParent_ID());
                id++;
            if(!(lexemes.get(i).equals(">"))) 
                 Error(1);  //Move to the next lexeme which can be new element or string element
            

       }
       
       public HashMap<String, String> Attribute(){
           HashMap<String, String> Attribute = new HashMap<>();
           String property;
           
           if(kc == KeywordList.HTML_CODE.HTML_PROPERTY_NAME || kc == KeywordList.HTML_CODE.HTML_NONVOID_PROPERTY){
               property = lexemes.get(i);   //storing the property name to be passed to the hashmap return key
               
               nextLexeme(); //  Store the property name into the symbol table (pending)
                      // Look for the equal sign if the lexeme is a property  
                  
                        if(lexemes.get(i).equals("=")){
                             
                            nextLexeme();
                            
                        }
                             
                        else
                            Error(2);

                       
                         // Checking if the next lexeme is a string
                        if(!(tokens.get(i)== TOKEN_CODE.STRING)) 
                            Error(2); //Store the value of the property here (pending)
                        else
                            Attribute.put(property, lexemes.get(i));
           }
           
           return Attribute;
        }
       
      
       private void nextLexeme(){ i++; }  //Moving from one lexeme to the next one.
       private void Error(int code){
           
           switch (code){
               //Generic Error Message:
               case -1: System.out.println("DOCTYPE: Syntax Error at"+lexemes.get(i)+lexemes.get(i+1)); break;
               case 0: System.out.println("Element: Syntax Error at"+lexemes.get(i)+lexemes.get(i+1)); break;
               case 1: System.out.println("Void: Syntax Error at"+lexemes.get(i)+lexemes.get(i+1)); break;
               case 2: System.out.println("Attribute: Syntax Error at"+lexemes.get(i)+lexemes.get(i+1)); break; 
               case 3: System.out.println("Non_void_element: Syntax Error at : "+lexemes.get(i)+lexemes.get(i+1)+" Incorrect tag name to close. It should be: "+html_element_stack.peek().getTag()); break; 
               case 4: System.out.println("Non_void_element: Syntax Error at : "+lexemes.get(i)+lexemes.get(i+1)); break; 
              
            }
           System.exit(0);         
       }
                
        private static void buildSymbolTree(SymbolTable element) {
		 SymbolTable html_node = element;
		 children = getChildrenById(html_node.getID());
                                 
                 html_node.setChildrenElement(children);
                    if (children.isEmpty())  
                        return;                

                    for (SymbolTable child_node : children)  
                        buildSymbolTree(child_node);
                 
	 }
        private static List<SymbolTable> getChildrenById(int id) {
                List<SymbolTable> children_ = new ArrayList<>();
                for (SymbolTable e : html_element.values()) {
                      if (e.getParent_ID() == id) {
                       children_.add(e);

                    }
                }
            return children_;
	 }
        
              
          // This is a post-order tree traversal printing of tree
         
          private static void printSymbolTree(SymbolTable element, int level) {
		 for (int i = 0; i < level; i++) {
			 System.out.print("\t");
		 }		
                 
                 // printing the node - extracting data //
                 if(element.getElement_type() == KeywordList.HTML_CODE.HTML_STRING_ELEMENT){
                     System.out.println(  element.getUserDefinedProperties().get("text"));
                 }
                 else{
                     if(element.getTag().equals("input")){
                        System.out.println(element.getUserDefinedProperties().get("type"));                        
                     }
                     else
                        System.out.println(element.getTag()); 
                 } 
                 // end of printing the node //
                 
		 children = element.getChildrenElement();
		 System.out.print(" ");
		 for (SymbolTable child_node : children) {
			 printSymbolTree(child_node, level + 1);
		 }                
            }
        
          
       public HashMap<Integer, SymbolTable> getHTML_Elements(){
           return html_element;
       }
       public SymbolTable getroot(){
           return root;
       }
       
       
}