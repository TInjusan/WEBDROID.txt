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
         static String current_select="";
         static List<SymbolTable> children;
         static int radiobuttoncount = 0;
         static int radiobuttonparent = -1;
         static String rb = "";
         public void set_html_element(Map<Integer, SymbolTable> h,SymbolTable r){
            html_element = h;
            root = r;
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
        
         
         private static void buildSymbolTree(SymbolTable element) {
		 SymbolTable html_node = element;
		 children = getChildrenById(html_node.getID());
                                 
                 html_node.setChildrenElement(children);
                if (children.isEmpty()) {
                    return;
                }

                for (SymbolTable child_node : children) {
                        buildSymbolTree(child_node);
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
                 
		 children = element.getChildrenElement();
		 System.out.print(" ");
		 for (SymbolTable child_node : children) {
			 printSymbolTree(child_node, level + 1);
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
                                 try{
                                   System.out.println(element.getUserDefinedProperties().get("type"));
                                   
                                 }catch(NullPointerException e){
                                     System.out.println(element.getTag());
                                 }
                                 
                                 switch(element.getUserDefinedProperties().get("type")){
                                     case "submit":
                                     case "button":
                                         
                                         try{
                                               st = element.getUserDefinedProperties().get("value");
                                         }catch(NullPointerException e){
                                             st = "blank";
                                         }
                                         
                                            if(st==null)
                                                st = "blank";
                                            else
                                                 string_xml = string_xml+"<string name=\""+st.trim().replaceAll("\\s", "_").replaceAll(":", "")+"\">"+st+"</string>\n";

                                            layout_xml = layout_xml+"            <Button\n" +
                                                                   "                android:id=\"@+id/button"+element.getID()+"\"\n" +
                                                                   "                android:layout_width=\"wrap_content\"\n" +
                                                                   "                android:layout_height=\"wrap_content\"\n" +
                                                                   "                android:text=\"@string/"+st+"\"/> \n" ;


                                         break;
                                     case "text":
                                         if(element.getUserDefinedProperties().get("placeholder")==null) {
                                             st = "blank"; 
                                             string_xml = string_xml+"<string name=\"blank\"></string>\n"; 
                                         }
                                         else{
                                               st = element.getUserDefinedProperties().get("placeholder");
                                               string_xml = string_xml+"<string name=\""+st.trim().replaceAll("\\s", "_").replaceAll(":", "")+"\">"+st+"</string>\n";
                                         }
                                         
                                        
                                         
                                         layout_xml = layout_xml+"            <EditText\n" +
                                                                "                android:id=\"@+id/editText"+element.getID()+"\"\n" +
                                                                "                android:layout_width=\"match_parent\"\n" +
                                                                "                android:layout_height=\"wrap_content\"\n" +
                                                                "                android:hint=\"@string/"+st+"\"\n" +
                                                                "                android:inputType=\"text\"\n" +
                                                                "                android:importantForAutofill=\"no\" />  <!-- Additional attribute if there's no hint or there's any error-->\n";

                                         break;
                                     case "password":
                                         try{
                                               st = element.getUserDefinedProperties().get("placeholder");
                                         }catch(NullPointerException e){
                                             st = "blank";
                                         }
                                         
                                         if(st==null)
                                              st = "blank";
                                         else
                                              string_xml = string_xml+"<string name=\""+st.trim().replaceAll("\\s", "_").replaceAll(":", "")+"\">"+st+"</string>\n";
                            
                                         layout_xml = layout_xml+"            <EditText\n" +
                                                                "                android:id=\"@+id/editText"+element.getID()+"\"\n" +
                                                                "                android:layout_width=\"match_parent\"\n" +
                                                                "                android:layout_height=\"wrap_content\"\n" +
                                                                "                android:hint=\"@string/"+st+"\"\n" +
                                                                "                android:inputType=\"textPassword\"\n" +
                                                                "                android:importantForAutofill=\"no\" />  <!-- Additional attribute if there's no hint or there's any error-->\n";

                                         break;
                                     case "checkbox":
                                                            layout_xml=layout_xml+"<CheckBox\n" +
                                                                  "        android:id=\"@+id/spinner"+element.getID()+"\"\n" +
                                                                  "        android:layout_width=\"wrap_content\"\n" +
                                                                  "        android:layout_height=\"wrap_content\"\n" +
                                                                  "        android:text=\"@string/"+element.getUserDefinedProperties().get("id")+"\"/>\n";
                                       
                                         break;
                                     case "radio":
                                             
                                                //add to existing radio button
                                            if(radiobuttonparent==element.getParent_ID() && radiobuttoncount >0){
                                                
                                                StringBuffer layout = new StringBuffer("layout_xml");  
                                                
                                                rb =        "   <RadioButton\n" +
                                                            "            android:id=\"@+id/radioButton"+element.getID()+"\"\n" +
                                                            "            android:layout_width=\"match_parent\"\n" +
                                                            "            android:layout_height=\"wrap_content\"\n" +
                                                            "            android:text=\"@string/"+element.getUserDefinedProperties().get("id")+"\" />\n"+
                                                            "    </RadioGroup>";
                                                 
                                                String original_layout = layout_xml.substring(0, layout_xml.lastIndexOf("</RadioGroup>"));
                                                original_layout = original_layout + rb;
                                                layout_xml = original_layout;
                                                
                                                radiobuttoncount++;
                                                
                                            }
                                            else{
                                                //initialize radio button
                                                radiobuttonparent = element.getParent_ID();
                                                layout_xml=layout_xml+  "    <RadioGroup\n" +
                                                                        "        android:layout_width=\"wrap_content\"\n" +
                                                                        "        android:layout_height=\"wrap_content\" >\n"+
                                                                        "       <RadioButton\n" +
                                                                        "            android:id=\"@+id/radioButton"+element.getID()+"\"\n" +
                                                                        "            android:layout_width=\"match_parent\"\n" +
                                                                        "            android:layout_height=\"wrap_content\"\n" +
                                                                        "            android:text=\"@string/"+element.getUserDefinedProperties().get("id")+"\" />\n"+
                                                                        "    </RadioGroup>";
                                                radiobuttoncount++;
                                            }
                                            
                                            break;
                                     default:
                                         break;
                                 }                                
                                 
                             }
                             
                             break;
                         case HTML_NONVOID_TAG:
                             
                                    if(element.getTag().equals("select")){

                                       current_select = element.getUserDefinedProperties().get("name");
                                       layout_xml=layout_xml+"<Spinner\n" +
                                                                  "        android:id=\"@+id/spinner"+element.getID()+"\"\n" +
                                                                  "        android:layout_width=\"match_parent\"\n" +
                                                                  "        android:layout_height=\"wrap_content\"\n" +
                                                                  "        android:entries=\"@array/"+current_select+"\"/>\n";
                                        String arraylist = "<string-array name=\""+current_select+"\">\n";
                                        
                                        children = element.getChildrenElement();
                                        for (SymbolTable child_node : children) {
                                              arraylist = arraylist+  "<item>"+GetChildString(child_node)+"</item>\n";
                                        }
                                        arraylist = arraylist+"</string-array>\n";
                                        string_xml = string_xml + arraylist;
                                   }
                                  
                                    else if(element.getTag().equals("label") && element.getUserDefinedProperties().get("for")!=null){
                                       
                                              String label_name =  element.getUserDefinedProperties().get("for");
                                              string_xml = string_xml+"<string name=\""+label_name+"\">"+GetChildString(element)+"</string>\n";
                                     }
                                    
                                   else{
                                      children = element.getChildrenElement();
                                    for (SymbolTable child_node : children) {
                                            GENERATE_XML(child_node, element.getUserDefinedProperties());
                                    }
                                   }
                                   
                             
                             break;
                         
                         default:
                             break;
                                                      
             }
        }           
        public static String GetChildString(SymbolTable element){
                String s = "";
                children = element.getChildrenElement();
                for (SymbolTable child_node : children)  
                      s = child_node.getUserDefinedProperties().get("text");
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
