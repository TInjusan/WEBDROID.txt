package webdroid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
public class PropertyChecker {
  private static AstNode root;
  private static List<AstNode> children;
  private static ArrayList<String> id_table = new ArrayList<>();
  private static Map<Integer, AstNode> html_element = new HashMap<>();
  
   public void property_check(){
       runChecker(root);
   }
  
   public void set_tree(Map<Integer, AstNode> h,AstNode r){
            html_element = h;
            root = r;
         }
    
    private static void runChecker(AstNode element) {
 
                 
                 if(element.getUserDefinedProperties().get("id")!=null){
                     //check if the id already exists
                     String tag_id = element.getUserDefinedProperties().get("id");
                     
                     if(  Character.isDigit( tag_id.charAt(0))){  //check if valid id by getting the first character, it should not be a digit
                         System.out.println("Error! Invalid id: "+tag_id+". First character should not be a number");
                         System.exit(0);
                     }
                     else{
                       if(  id_table.indexOf(tag_id) == -1)
                           id_table.add(tag_id);
                       else{
                           System.out.println("Error! ID: "+tag_id+" already exist.");
                           System.exit(0);
                       }
                     }                        
                     
                 }
                 
                 
                 if(element.getTag().equals("select")){
                     if(element.getUserDefinedProperties().get("name")==null){
                         System.out.println("Error! Select tag should always have attribute name");
                         System.exit(0);
                     }
                     
                 }
                 
                  if(element.getUserDefinedProperties().get("class")!=null){
                     //check if the id already exists
                     String tag_class = element.getUserDefinedProperties().get("class");
                      if(  Character.isDigit( tag_class.charAt(0))){  //check if valid id by getting the first character, it should not be a digit
                         System.out.println("Error! Invalid class name: "+tag_class+". First character should not be a number");
                         System.exit(0);
                     }
                  }
                 
		 children = element.getChildrenElement();
		 
		 for (AstNode child_node : children) {
			 runChecker(child_node);
		 }                
            }        
          
   
}
