package webdroid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeGenerator {
         static Map<Integer, AstNode> html_element;
         static AstNode root;
         static String string_xml;
         static String layout_xml;
         static String color_xml;
         static String current_select="";
         static List<AstNode> children;
         static int radiobuttoncount = 0;
         static int radiobuttonparent = -1;
         static String rb = "";
         static String App_name="";
         static KeywordList k = new KeywordList();
         public void set_html_element(Map<Integer, AstNode> h,AstNode r){
            html_element = h;
            root = r;
         }
         
         CodeGenerator(){
             string_xml="";
             layout_xml="";
             color_xml="";
         }
    	 
      
        private static void GENERATE_XML(AstNode element, HashMap<String, String> parent_attribute){
                
            switch(element.getElement_type()){
                         case HTML_STRING_ELEMENT:
                             String s = element.getUserDefinedProperties().get("text");
                             
                             
                             layout_xml=layout_xml+ k.layout_textview(element.getID(),s); //inserting the TextView element to the layout.xml
                             if(!string_xml.contains(k.string_element(s, s)))
                                string_xml = string_xml+k.string_element(s, s);  //inserting the string value to the string.xml
                             break;
                             
                         case HTML_VOID_TAG:                             
                             if(element.getTag().equals("input")){
                                //this is the shorthand if statement to assign value to type variable
                                String id = element.getUserDefinedProperties().get("id") == null? element.getUserDefinedProperties().get("id") : "id_"+element.getID();
                                String type = element.getUserDefinedProperties().get("type") == null? element.getUserDefinedProperties().get("type") : "text";
                                String value = element.getUserDefinedProperties().get("value") == null? "blank" : element.getUserDefinedProperties().get("value"); 
                                String placeholder = element.getUserDefinedProperties().get("placeholder") == null? "blank" : element.getUserDefinedProperties().get("placeholder");
                                            
                                   switch(type){
                                     
                                     case "submit":
                                     case "button":                                         
                                         
                                         if(!string_xml.contains(k.string_element(value, value)))
                                              string_xml = string_xml+k.string_element(value, value);  //inserting the string value to the string.xml
                                            layout_xml = layout_xml+"            <Button\n" +
                                                                   "                android:id=\"@+id/button"+element.getID()+"\"\n" +
                                                                   "                android:layout_width=\"wrap_content\"\n" +
                                                                   "                android:layout_height=\"wrap_content\"\n" +
                                                                   "                android:text=\"@string/"+value+"\"/> \n" ;


                                         break;
                                     case "text": 
                                     case "password":
                                     case "email":           
                                         if(!string_xml.contains(k.string_element(placeholder, placeholder)))
                                            string_xml = string_xml+k.string_element(placeholder, placeholder);  //inserting the string value to the string.xml
                                         layout_xml = layout_xml+ k.layout_textbox(id,placeholder, type);
                                                         
                                         break;
                                     case "checkbox":
                                                            layout_xml=layout_xml+"<CheckBox\n" +
                                                                  "        android:id=\"@+id/spinner"+element.getID()+"\"\n" +
                                                                  "        android:layout_width=\"wrap_content\"\n" +
                                                                  "        android:layout_height=\"wrap_content\"\n" +
                                                                  "        android:text=\"@string/"+id+"\"/>\n";
                                       
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
                                                            "    </RadioGroup>\n";
                                                 
                                                String original_layout = layout_xml.substring(0, layout_xml.lastIndexOf("</RadioGroup>\n"));
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
                                                                        "    </RadioGroup>\n";
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
                                            for (AstNode child_node : children) {
                                                  arraylist = arraylist+  "<item>"+GetChildString(child_node)+"</item>\n";
                                            }
                                            arraylist = arraylist+"</string-array>\n";
                                            string_xml = string_xml + arraylist;
                                         
                                   }
                                    else if(element.getTag().equals("title")){
                                        App_name = GetChildString(element);
                                         
                                    }
                                  
                                    else if(element.getTag().equals("label") && element.getUserDefinedProperties().get("for")!=null){
                                       
                                              String label_name =  element.getUserDefinedProperties().get("for");
                                               
                                              if(!string_xml.contains(k.string_element(label_name, GetChildString(element))))
                                                    string_xml = string_xml+k.string_element(label_name, GetChildString(element)); 
                                              
                                     }
                                    
                                   else{
                                      children = element.getChildrenElement();
                                        for (AstNode child_node : children) {
                                                GENERATE_XML(child_node, element.getUserDefinedProperties());
                                    }
                                   }
                                   
                             break;
                         
                         default:
                             break;
                                                      
             }
        }           
        public static String GetChildString(AstNode element){
                String s = "";
                children = element.getChildrenElement();
                for (AstNode child_node : children)  
                      s = child_node.getUserDefinedProperties().get("text");
                return s;
        }
        
        public void Run_Code_Generator(){
             
             GENERATE_XML(root, root.getUserDefinedProperties());
             
             string_xml = "<resources>\n<string name=\"app_name\">"+App_name+"</string>\n"+string_xml+"</resources>";
             layout_xml = k.layout_xml(layout_xml);
        }  
        public String get_layout_xml(){
            return layout_xml;
        }
        public String get_string_xml(){
            return string_xml;
        }
         
}
