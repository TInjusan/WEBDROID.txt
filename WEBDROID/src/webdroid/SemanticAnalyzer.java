package webdroid;
import java.util.ArrayList;
import java.util.List;
import webdroid.SymbolTable.ElementNode;

public class SemanticAnalyzer {
  
   private static List<ElementNode> children;
   private   List<ElementNode> removal = new ArrayList<>();
   SemanticAnalyzer(){
           buildSymbolTree(SymbolTable.root);    //First build after Syntax Analysis - Abstract Syntax Tree
           
   }  
   
   public void ExecuteAnalyzer(){
           analyze();
           removeElements();
           printSymbolTable();
          
           buildSymbolTree(SymbolTable.root);    //Second build (improved and specified) after Semantic Analysis Abstract Syntax Tree
           printSymbolTree(SymbolTable.root,0);  //Viewing the hierarchy of each tag

   }
        private  void analyze(){
        
             for (ElementNode e : SymbolTable.get_html_table()) {
                 //Checking the validity of the id name if it doesn't begin with a digit and number of usage should only be one.  
                 if(e.getUDP().get("id")!=null){
                      String ID = e.getUDP().get("id");                      
                      if(  Character.isDigit( ID.charAt(0)))   //check if valid id by getting the first character, it should not be a digit
                            Error(4,ID); 
                   
                     else{
                          if(searchID(ID)>1)                           
                           Error(5,ID);                           
                      }
                  }  
                 
                 //Checking for duplicate for
                     if(e.getUDP().get("for")!=null){
                      String ID = e.getUDP().get("for");                      
                      if(  Character.isDigit( ID.charAt(0)))   //check if valid id by getting the first character, it should not be a digit
                            Error(4,ID); 
                   
                     else{
                          if(searchFOR(ID)>1)                           
                           Error(7,ID);                           
                      }
                  }  
                     
                 //Checking the validity of the class name if it doesn't begin with a digit
                 if(e.getUDP().get("class")!=null){
                      String classname = e.getUDP().get("class");                      
                      if(  Character.isDigit( classname.charAt(0)))   
                           Error(3,classname);                       
                 }
                
                 //specifying input elements - replacing the input tag name with the appropriate one
                 if(e.getTag().equals("input")){
                     if(e.getUDP().get("type")==null || e.getUDP().get("type").equals("text")){
                         e.setTag("textbox");
                         e.addUDP("type", "text");
                     }
                     else{
                         switch(e.getUDP().get("type")){
                             case "email":
                             case "password":
                                    e.setTag("textbox");
                                    break;
                             case "radio":
                                     e.setTag("radiobutton");
                                     e.removeUDP("type");
                                    break;
                             case "checkbox":
                                      e.setTag("checkbox");
                                      e.removeUDP("type");
                                      break;
                             case "button":
                             case "submit":
                                      try{
                                            if (e.getUDP().get("value").equals("")){
                                                Error(6,"");
                                            }
                                            else{ //put the value of value property to text so that the code generator will only take
                                                  //text property in xml
                                                e.addUDP("text", e.getUDP().get("value")); 
                                                e.setTag("button");
                                                e.removeUDP("type");
                                                e.removeUDP("value");
                                            }
                                      
                                      }catch(NullPointerException ee){
                                           Error(6,"");
                                      }
                                      
                                      break;
                            
                             default:
                                       Error(0,e.getUDP().get("type"));
                                       break;
                         }
                     }
                         
                 }
                 if(e.getTag().equals("p") || e.getTag().equals("label") || e.getTag().equals("h1") || e.getTag().equals("h2") || e.getTag().equals("h3") || e.getTag().equals("title")){
                     children = getChildrenById(e.getID());
                     if(children.size()>1){
                         System.out.println("Error. Text Tags should only have one child");
                     }
                     //Setting the text from its child string element
                     else{
                          e.addUDP("text", children.get(0).getUDP().get("text")); 
                          removal.add(children.get(0));
                     }
                     
                       // Assigning the text to the element using the for attribute
                       if(e.getTag().equals("label")){

                           if(e.getUDP().get("for")!=null){
                               String id = e.getUDP().get("for");
                               String text = e.getUDP().get("text");

                              if( searchID(id)>0 ){
                                   setText(id, text);
                                   removal.add(e);
                              }
                              else
                                   Error(1,id);                               
                           }
                       }
                       else if(e.getTag().contains("h")){
                                if(e.getTag().equals("h1"))
                                    e.addUDP("font-size", "25sp");
                                else if(e.getTag().equals("h2"))
                                    e.addUDP("font-size", "20sp");
                                else if(e.getTag().equals("h3"))
                                    e.addUDP("font-size", "15sp");
                                
                                e.addUDP("font-weight", "700");
                      }                           
                 }
                 
                 if(e.getTag().equals("img")){
                     String full_source ="";
                     if(e.getUDP().get("src")==null)
                         Error(8,"");
                     else{
                          full_source = WEBDROID.html_location.getText()+"\\"+e.getUDP().get("src").trim();
                          e.addUDP("src", full_source);
                     }                        
                 }
                 
                 if(e.getTag().equals("select")){
                      ArrayList<String> R = new ArrayList<>();
                      if(e.getUDP().get("name")==null){
                          Error(2,"");
                      }
                      else{
                          String selectname= e.getUDP().get("name");
                          
                            children = getChildrenById(e.getID());
                            for (ElementNode child_node : children)  {
                                R.add(GetChildString(child_node)); 
                                removal.add(child_node);
                            }   
                          SymbolTable.ArrayString.put(selectname, R);
                      }
                 }
                 if(e.getTag().equals("br")){
                     removal.add(e);
                 }
             }    
        }
        
        private void removeElements(){
            for (ElementNode remove : removal)  {
                SymbolTable.RemoveTag(remove);
            }
            
        }
        private int searchID(String ID){
            int i = 0;
            for (ElementNode e : SymbolTable.get_html_table()) {
                 if(e.getUDP().get("id")!=null){
                    if (ID.equals( e.getUDP().get("id") )){
                        i++;
                    }
                 }
            }      
            return i;
        }
        private int searchFOR(String ID){
            int i = 0;
            for (ElementNode e : SymbolTable.get_html_table()) {
                 if(e.getUDP().get("for")!=null){
                    if (ID.equals( e.getUDP().get("for") )){
                        i++;
                    }
                 }
            }      
            return i;
        }        
        
        private void setText(String ID, String Text){
             for (ElementNode e : SymbolTable.get_html_table()) {
                 if(e.getUDP().get("id")!=null){
                    if (ID.equals( e.getUDP().get("id") )){
                      e.addUDP("text", Text);
                    }
                 }
            }  
            
        }
         public String GetChildString(ElementNode element){
                String s = "";
                children = element.getChildrenElement();
                for (ElementNode child_node : children){
                    s = child_node.getUDP().get("text");
                     removal.add(child_node);
                }  
                      
                return s;
        }

        public void buildSymbolTree(ElementNode element) {
		 children = getChildrenById(element.getID());             
                 element.setChildrenElement(children);
                    if (children.isEmpty())  
                        return;                

                    for (ElementNode child_node : children)  
                        buildSymbolTree(child_node);
                 
	 }
        public  List<ElementNode> getChildrenById(int id) {
                List<ElementNode> children_ = new ArrayList<>();
                for (ElementNode e : SymbolTable.get_html_table()) {
                      if (e.getParent_ID() == id) {
                       children_.add(e);

                    }
                }
            return children_;
	 }
        
         private void Error(int code, String s){
           
           switch (code){
               //Generic Error Message:
               
               case 0: WEBDROID.ErrorMessagePopup("Semantic Error","Input type: "+s+" is not a supported type for input tag."); break;
               case 1: WEBDROID.ErrorMessagePopup("Semantic Error","Label attribute \"for: "+s+"\". Doesn't exist"); break;
               case 2: WEBDROID.ErrorMessagePopup("Semantic Error","Please make sure to assign a name to each select tag."); break;
               case 3: WEBDROID.ErrorMessagePopup("Semantic Error","Invalid class: "+s+". First character should not be a digit"); break; 
               case 4: WEBDROID.ErrorMessagePopup("Semantic Error","Invalid id: "+s+". First character should not be a number"); break;
               case 5: WEBDROID.ErrorMessagePopup("Semantic Error","Multiple usage of id: "+s+" is not allowed."); break;
               case 6: WEBDROID.ErrorMessagePopup("Semantic Error","Please make sure to set the value property of a button as it is the text of button itself"); break;
               case 7: WEBDROID.ErrorMessagePopup("Semantic Error","Multiple usage of id ["+s+"] as a value of \"for\" attribute is not allowed."); break;
               case 8: WEBDROID.ErrorMessagePopup("Semantic Error","Image tag should always have a source file."); break;
       
           }
            WEBDROID.ErrorDetected =true;
         
         }
        
        private static void printSymbolTable(){
            for (ElementNode e : SymbolTable.get_html_table()) {
                     String tablerow = "";
                     tablerow = tablerow +  e.getID() +"\t";
                     tablerow = tablerow + e.getTag() + "\t";
                     tablerow = tablerow + e.getElement_type() +"\t";
                     tablerow = tablerow + e.getParent_ID() +"\t";
                     System.out.println(tablerow);
                     
                    }
        }
          // This is a post-order tree traversal printing of tree
         
          private void printSymbolTree(ElementNode element, int level) {
		 for (int i = 0; i < level; i++) {
			 System.out.print("\t");
		 }		
                 
                 // printing the node - extracting data //
              
                     if(element.getTag().equals("button")){
                         System.out.println("button - "+element.getUDP().get("text"));
                     }
                     else
                        System.out.println(element.getTag()); 
                  
                 // end of printing the node //
                 
		 children = element.getChildrenElement();
		 System.out.print(" ");
		 for (ElementNode child_node : children) {
			 printSymbolTree(child_node, level + 1);
		 }                
            }    
   
}
