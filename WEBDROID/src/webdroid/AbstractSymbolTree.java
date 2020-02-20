package webdroid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class AbstractSymbolTree {
         static Map<Integer, SymbolTable> html_element;
         static SymbolTable root;
        
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
        
         
         private static void buildSymbolTree(SymbolTable rootelement) {
		SymbolTable html_node = rootelement;
		List<SymbolTable> children = getChildrenById(html_node.getParent_ID());
                
                
                children.forEach( (child) -> System.out.println(child.getTag()));
		html_node.setChildrenElement(children);
                if (children.isEmpty()) {
                    return;
                }

                for (SymbolTable e : children) {
                        buildSymbolTree(e);
                }
	 }
         
          private static void printSymbolTree(SymbolTable rootelement, int level) {
		 for (int i = 0; i < level; i++) {
			 System.out.print("\t");
		 }		 
		 System.out.println(rootelement.getTag());
		 
		 List<SymbolTable> children = rootelement.getChildrenElement();
		 System.out.print(" ");
		 for (SymbolTable e : children) {
			 printSymbolTree(e, level + 1);
		 }
	 }
         
          
        public void Run_Abstract_Symbol_Tree(){
             buildSymbolTree(root);
//            printSymbolTree(root,0);
        //    System.out.println(root.getID()+" "+root.getTag());
                }  
         
}
