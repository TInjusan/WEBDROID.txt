// This class is the collection of supported HTML keywords
// It will return a value from a given string that define its HTML_CODE

package webdroid; 
import java.util.HashMap;

public class KeywordList {
    
    
    public enum HTML_CODE {NOT_FOUND, HTML_NONVOID_TAG,HTML_NONVOID_PROPERTY, HTML_VOID_TAG, HTML_PROPERTY_NAME, HTML_SPECIAL_CHARACTER, HTML_STRING_ELEMENT};
    //initializing the collection of keywords in key value pair of hashmap
    HashMap<String, HTML_CODE> Keyword = new HashMap<>();
    HashMap<String, String> html_xml = new HashMap<>();
     //constructor initializing the keywords
     KeywordList(){
            
            Keyword.put("/",HTML_CODE.HTML_SPECIAL_CHARACTER);
            Keyword.put("<",HTML_CODE.HTML_SPECIAL_CHARACTER);
            Keyword.put(">",HTML_CODE.HTML_SPECIAL_CHARACTER);
            Keyword.put("!",HTML_CODE.HTML_SPECIAL_CHARACTER);
            
            Keyword.put("action",HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("color",HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("class",HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("placeholder",HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("for",HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("src",HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("alt",HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("height",HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("href",HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("width",HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("value", HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("id",HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("name",HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("type",HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("style",HTML_CODE.HTML_NONVOID_PROPERTY);             
                  
            Keyword.put("br",HTML_CODE.HTML_VOID_TAG);
            Keyword.put("link",HTML_CODE.HTML_VOID_TAG);
            Keyword.put("input",HTML_CODE.HTML_VOID_TAG);
            Keyword.put("img",HTML_CODE.HTML_VOID_TAG);
            
            Keyword.put("button",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("div",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("a",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("body",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("font",HTML_CODE.HTML_NONVOID_TAG);              
            Keyword.put("head",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("html",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("form",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("font",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("h1",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("h2",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("h3",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("label",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("nav",HTML_CODE.HTML_NONVOID_TAG);            
            Keyword.put("option",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("p",HTML_CODE.HTML_NONVOID_TAG);                        
            Keyword.put("select",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("textarea",HTML_CODE.HTML_NONVOID_TAG);            
            Keyword.put("title",HTML_CODE.HTML_NONVOID_TAG);
             
            
            
            html_xml.put("text", "text");
            html_xml.put("password", "textPassword");
            html_xml.put("email", "textEmailAddress");
            html_xml.put("password", "textPassword");
            
   }
     
     HTML_CODE SearchKeyword(String key){ 
                   
          if(Keyword.containsKey(key.toLowerCase())) 
            return  Keyword.get(key.toLowerCase());                
          else
            return HTML_CODE.NOT_FOUND;      
     }
     
     private String clean(String s){
         return (s.trim().replaceAll("\\s", "_").replaceAll(":", "")).replaceAll("\\.", "").replaceAll("\'", "");
     }
     
     public String string_element(String id, String s){
         id = clean(id);
         
        s = (s.replaceAll("\'", "\\'")).replaceAll("\"", "\\\"");
         
         
         if(s.equals("blank"))
             s ="";
         return "<string name=\""+id+"\">"+s+"</string>\n";
     }
     
     public String layout_textview(int id, String s){
         
         s = clean(s);
         
         return " <TextView\n" +
                "        android:id=\"@+id/text"+id+"\"\n" +
                "        android:layout_width=\"wrap_content\"\n" +
                "        android:layout_height=\"wrap_content\"\n" +
                "        android:text=\"@string/"+s+"\"\n" +
                "        android:textIsSelectable=\"false\"\n" +
                "        android:textStyle=\"bold\" />\n";
     }
     
     public String layout_textbox(int id, String s, String type){
         
         return "            <EditText\n" +
                                                                "                android:id=\"@+id/editText"+id+"\"\n" +
                                                                "                android:layout_width=\"match_parent\"\n" +
                                                                "                android:layout_height=\"wrap_content\"\n" +
                                                                "                android:hint=\"@string/"+s+"\"\n" +
                                                                "                android:inputType=\""+html_xml.get(type)+"\"\n" +
                                                                "                android:importantForAutofill=\"no\" />  <!-- Additional attribute if there's no hint or there's any error-->\n";

                                         
     }
     
     public String layout_xml(String s){
         
         return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
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
                            "            android:layout_gravity=\"top\">\n"+s+
                            "        </LinearLayout>\n" +
                            "    </ScrollView>";
     }
     
  }
     
   
 