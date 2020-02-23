package webdroid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class AbstractSymbolTree {
         static Map<Integer, SymbolTable> html_element;
         static SymbolTable root;
         static String string_xml="";
         static String layout_xml="";
         static String color_xml="";
         
         public void set_html_element(Map<Integer, SymbolTable> h,SymbolTable r){
            html_element = h;
            root = r;
         }
               
    	 private static List<SymbolTable> getChildrenById(int id) {
		 List<SymbolTable> children = new ArrayList<>();
		 for (SymbolTable e : html_element.values()) {
                       if (e.getParent_ID() == id) {
                        children.add(e);
                        
                     }
		 }
		 return children;
	 }
        
         
         private static void buildSymbolTree(SymbolTable element) {
		SymbolTable html_node = element;
		List<SymbolTable> children = getChildrenById(html_node.getID());
                
                
                 html_node.setChildrenElement(children);
                if (children.isEmpty()) {
                    return;
                }

                for (SymbolTable e : children) {
                        buildSymbolTree(e);
                }
	 }
         
          private static void printSymbolTree(SymbolTable element, int level) {
		 for (int i = 0; i < level; i++) {
			 System.out.print("\t");
		 }		
                 
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
               
                     switch(element.getElement_type()){
                         case HTML_STRING_ELEMENT:
                             String s = element.getUserDefinedProperties().get("text");
                          //   System.out.println(s);
                             string_xml = string_xml+"<string name=\""+s.trim()+"\">"+s+"</string>\n";
                             break;
                         case HTML_VOID_TAG:
                             
                             break;
                         case HTML_NONVOID_TAG:
                             
                             break;
                         
                         default:
                             break;
                                                      
                     }
                
                 
		 List<SymbolTable> children = element.getChildrenElement();
		 System.out.print(" ");
		 for (SymbolTable e : children) {
			 printSymbolTree(e, level + 1);
		 }
                  
                 
	 }
          
        public void Run_Abstract_Symbol_Tree(){
             buildSymbolTree(root);
             printSymbolTree(root,0);
             string_xml = "<resources>\n"+string_xml+"</resources>";
         //    System.out.println(string_xml);
        }  
         
}
