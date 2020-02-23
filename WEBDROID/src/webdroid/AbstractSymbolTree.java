package webdroid;

import java.util.ArrayList;
import java.util.HashMap;
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
         
         
          // This is a post-order tree traversal
         
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
                 
		 List<SymbolTable> children = element.getChildrenElement();
		 System.out.print(" ");
		 for (SymbolTable e : children) {
			 printSymbolTree(e, level + 1);
		 }
                  
                 
	 }
        
        private static void GENERATE_XML(SymbolTable element, HashMap<String, String> parent_attribute){
                
            switch(element.getElement_type()){
                         case HTML_STRING_ELEMENT:
                             String s = element.getUserDefinedProperties().get("text");
                             
                             layout_xml=layout_xml+" <TextView\n" +
                                        "        android:id=\"@+id/text"+element.getID()+"\"\n" +
                                        "        android:layout_width=\"wrap_content\"\n" +
                                        "        android:layout_height=\"wrap_content\"\n" +
                                        "        android:text=\"@string/"+s.trim().replaceAll("\\s", "_").replaceAll(":", "").replace(".", "")+"\"\n" +
                                        "        android:textIsSelectable=\"false\"\n" +
                                        "        android:textStyle=\"bold\" />\n";
                             
                             //inserting the string value to the string.xml
                             string_xml = string_xml+"<string name=\""+s.trim().replaceAll("\\s", "_").replaceAll(":", "")+"\">"+s+"</string>\n";
                             break;
                         case HTML_VOID_TAG:
                             
                             if(element.getTag().equals("input")){
                                 String st;
                                 System.out.println(element.getUserDefinedProperties().get("type"));
                                 switch(element.getUserDefinedProperties().get("type")){
                                     case "\"submit\"":
                                     case "\"button\"":
                                         st = element.getUserDefinedProperties().get("value");
                                         if(st.equals(""))
                                             st = "blank";
                                         
                                         layout_xml = layout_xml+"            <Button\n" +
                                                                "                android:id=\"@+id/button"+element.getID()+"\"\n" +
                                                                "                android:layout_width=\"match_parent\"\n" +
                                                                "                android:layout_height=\"wrap_content\"\n" +
                                                                "                android:text=\"@string/"+st+"\n" ;
                                                                 
                                         
                                         break;
                                     case "\"text\"":
                                         try{
                                               st = element.getUserDefinedProperties().get("placeholder");
                                         }catch(NullPointerException e){
                                             st = "blank";
                                         }
                                       
                                         
                                         layout_xml = layout_xml+"            <EditText\n" +
                                                                "                android:id=\"@+id/editText"+element.getID()+"\"\n" +
                                                                "                android:layout_width=\"match_parent\"\n" +
                                                                "                android:layout_height=\"wrap_content\"\n" +
                                                                "                android:hint=\"@string/"+st.replace("\"", "")+"\"\n" +
                                                                "                android:inputType=\"text\"\n" +
                                                                "                android:importantForAutofill=\"no\" />  <!-- Additional attribute if there's no hint or there's any error-->\n";

                                         break;
                                     case "\"password\"":
                                         try{
                                               st = element.getUserDefinedProperties().get("placeholder");
                                         }catch(Exception e){
                                             st = "blank";
                                         }
                                         
                                         layout_xml = layout_xml+"            <EditText\n" +
                                                                "                android:id=\"@+id/editText"+element.getID()+"\"\n" +
                                                                "                android:layout_width=\"match_parent\"\n" +
                                                                "                android:layout_height=\"wrap_content\"\n" +
                                                                "                android:hint=\"@string/"+st.replace("\"", "")+"\"\n" +
                                                                "                android:inputType=\"password\"\n" +
                                                                "                android:importantForAutofill=\"no\" />  <!-- Additional attribute if there's no hint or there's any error-->\n";

                                         break;
                                     default:
                                         break;
                                                 
                                     
                                 }
                                 
                                 
                             }
                             
                             
                             break;
                         case HTML_NONVOID_TAG:
                             
                             
                                List<SymbolTable> children = element.getChildrenElement();
                                for (SymbolTable child_node : children) {
                                        GENERATE_XML(child_node, element.getUserDefinedProperties());
                                }
                  
                             
                             break;
                         
                         default:
                             break;
                                                      
             }
        }          
        
        public static String GetStringElement(SymbolTable element){
            String s = element.getUserDefinedProperties().get("text");
            return s;
        }
        
        public void Run_Abstract_Symbol_Tree(){
             buildSymbolTree(root);
             printSymbolTree(root,0);
             GENERATE_XML(root, root.getUserDefinedProperties());
             
             string_xml = "<resources>\n"+string_xml+"</resources>";
             layout_xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                            "<ScrollView \n" +
                            "        xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
                            "        android:layout_width=\"match_parent\"\n" +
                            "        android:layout_height=\"wrap_content\"\n" +
                            "        android:orientation=\"vertical\">\n" +
                            "        <LinearLayout\n" +
                            "            android:id=\"@+id/parentlinearlayout\"\n" +
                            "            android:layout_width=\"match_parent\"\n" +
                            "            android:layout_height=\"wrap_content\"\n" +
                            "            android:orientation=\"vertical\"\n" +
                            "            android:layout_gravity=\"top\">\n"+layout_xml+
                            "        </LinearLayout>\n" +
                            "    </ScrollView>";
             
             System.out.println(layout_xml);    
             System.out.println(string_xml);
         
        }  
         
}
