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
        private static void printSymbolTable(){
            for (ElementNode e : SymbolTable.entry) {
                     String tablerow = "";
                     tablerow = tablerow +  e.getID() +"\t";
                     tablerow = tablerow + e.getTag() + "\t";
                     tablerow = tablerow + e.getElement_type() +"\t";
                     tablerow = tablerow + e.getParent_ID() +"\t";
                     System.out.println(tablerow);
                     
                    }
        }
        
        private  void analyze(){
             for (ElementNode e : SymbolTable.entry) {
                 //Checking the validity of the id name if it doesn't begin with a digit and number of usage should only be one.  
                 if(e.getUDP().get("id")!=null){
                      String ID = e.getUDP().get("id");                      
                      if(  Character.isDigit( ID.charAt(0))){  //check if valid id by getting the first character, it should not be a digit
                         System.out.println("Error! Invalid id: "+ID+". First character should not be a number");
                         System.exit(0);
                      }
                     else{
                          if(searchID(ID)>1){
                           System.out.println("Error! Multiple usage of id: "+ID+" is not allowed.");
                           System.exit(0); 
                          }
                      }
                  }  
                 //Checking the validity of the class name if it doesn't begin with a digit
                 if(e.getUDP().get("class")!=null){
                      String classname = e.getUDP().get("class");                      
                      if(  Character.isDigit( classname.charAt(0))){   
                         System.out.println("Error! Invalid class: "+classname+". First character should not be a digit");
                         System.exit(0);
                      }
                 }
                
                 //specifying input elements
                 if(e.getTag().equals("input")){
                     if(e.getUDP().get("type")==null || e.getUDP().get("type").equals("text")){
                         e.setTag("textbox");
                         e.addUDP("type", "text");
                     }
                     else{
                         switch(e.getUDP().get("type")){
                             case "email":
                             case "password":
                             case "date":
                                    e.setTag("textbox");
                                    break;
                             case "radio":
                                     e.setTag("radiobutton");
                                     break;
                             case "checkbox":
                                      e.setTag("checkbox");
                                      break;
                             case "button":
                             case "submit":
                                      e.setTag("button");
                                      break;
                             default:
                                       System.out.println("Error. Unsupported type for input tag.");
                                       System.exit(0);
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
                        //  SymbolTable.entry.remove(children.get(0));
                          removal.add(children.get(0));
                     }
                     
                       // Assigning the text to the element using the for attribute
                       if(e.getTag().equals("label")){

                           if(e.getUDP().get("for")!=null){
                               String id = e.getUDP().get("for");
                               String text = e.getUDP().get("text");

                              if( searchID(id)>0 ){
                                   setText(id, text);
                               //    SymbolTable.entry.remove(e);
                                   removal.add(e);
                              }
                              else{
                                   System.out.println("Error! Label for: "+id+" not found.");
                                   System.exit(0);
                              }
                           }
                       }
                 }
                 if(e.getTag().equals("select")){
                      ArrayList<String> R = new ArrayList<>();
                      if(e.getUDP().get("name")==null){
                          System.out.println("Error. Please make sure to assign a name to the select tag.");
                          System.exit(0);
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
             }    
        }
        
        private void removeElements(){
            for (ElementNode remove : removal)  {
                SymbolTable.entry.remove(remove);
            }
            
        }
        private int searchID(String ID){
            int i = 0;
            for (ElementNode e : SymbolTable.entry) {
                 if(e.getUDP().get("id")!=null){
                    if (ID.equals( e.getUDP().get("id") )){
                        i++;
                    }
                 }
            }      
            return i;
        }
        private void setText(String ID, String Text){
             for (ElementNode e : SymbolTable.entry) {
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

        private void buildSymbolTree(ElementNode element) {
		 children = getChildrenById(element.getID());             
                 element.setChildrenElement(children);
                    if (children.isEmpty())  
                        return;                

                    for (ElementNode child_node : children)  
                        buildSymbolTree(child_node);
                 
	 }
        private  List<ElementNode> getChildrenById(int id) {
                List<ElementNode> children_ = new ArrayList<>();
                for (ElementNode e : SymbolTable.entry) {
                      if (e.getParent_ID() == id) {
                       children_.add(e);

                    }
                }
            return children_;
	 }
        
              
          // This is a post-order tree traversal printing of tree
         
          private void printSymbolTree(ElementNode element, int level) {
		 for (int i = 0; i < level; i++) {
			 System.out.print("\t");
		 }		
                 
                 // printing the node - extracting data //
                 if(element.getElement_type() == KeywordList.HTML_CODE.HTML_STRING_ELEMENT){
                     System.out.println(  element.getUDP().get("text"));
                 }
                 else{
                     if(element.getTag().equals("input")){
                        System.out.println(element.getUDP().get("type"));                        
                     }
                     else
                        System.out.println(element.getTag()); 
                 } 
                 // end of printing the node //
                 
		 children = element.getChildrenElement();
		 System.out.print(" ");
		 for (ElementNode child_node : children) {
			 printSymbolTree(child_node, level + 1);
		 }                
            }    
   
}
