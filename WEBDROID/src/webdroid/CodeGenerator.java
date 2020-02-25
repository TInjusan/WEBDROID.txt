package webdroid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeGenerator {
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
         static KeywordList k = new KeywordList();
         public void set_html_element(Map<Integer, SymbolTable> h,SymbolTable r){
            html_element = h;
            root = r;
         }
               
    	 
      
        private static void GENERATE_XML(SymbolTable element, HashMap<String, String> parent_attribute){
                
            switch(element.getElement_type()){
                         case HTML_STRING_ELEMENT:
                             String s = element.getUserDefinedProperties().get("text");
                             layout_xml=layout_xml+ k.layout_textview(element.getID(),s); //inserting the TextView element to the layout.xml
                             string_xml = string_xml+k.string_element(s, s);  //inserting the string value to the string.xml
                             break;
                             
                         case HTML_VOID_TAG:                             
                             if(element.getTag().equals("input")){
                                 String st;
                                 String type;
                                 if(element.getUserDefinedProperties().get("type") == null)
                                     type="text";
                                 else
                                     type=element.getUserDefinedProperties().get("type");
                                   switch(type){
                                     
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
                                            string_xml = string_xml+k.string_element(st, st);  //inserting the string value to the string.xml
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
        
        public void Run_Code_Generator(){
             
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
