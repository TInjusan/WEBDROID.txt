 
package webdroid; 
import java.util.HashMap;

public class KeywordList {
    
    
    public enum HTML_CODE {NOT_FOUND, HTML_NONVOID_TAG,HTML_NONVOID_PROPERTY, HTML_VOID_TAG, HTML_PROPERTY_NAME, HTML_FORM_TYPE, HTML_SPECIAL_CHARACTER, HTML_STRING_ELEMENT};
    //initializing the collection of keywords in key value pair of hashmap
    HashMap<String, HTML_CODE> Keyword = new HashMap<>();
     
     //constructor initializing the keywords
     KeywordList(){
            
            Keyword.put("/",HTML_CODE.HTML_SPECIAL_CHARACTER);
            Keyword.put("<",HTML_CODE.HTML_SPECIAL_CHARACTER);
            Keyword.put(">",HTML_CODE.HTML_SPECIAL_CHARACTER);
            Keyword.put("a",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("action",HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("body",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("br",HTML_CODE.HTML_VOID_TAG);
            Keyword.put("link",HTML_CODE.HTML_VOID_TAG);
            Keyword.put("button",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("checkbox",HTML_CODE.HTML_FORM_TYPE);
            Keyword.put("color",HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("class",HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("date",HTML_CODE.HTML_FORM_TYPE);
            Keyword.put("div",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("font",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("DOCTYPE",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("for",HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("placeholder",HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("src",HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("alt",HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("height",HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("href",HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("width",HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("email",HTML_CODE.HTML_FORM_TYPE);
            Keyword.put("form",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("font",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("h1",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("h2",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("h3",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("head",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("html",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("id",HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("img",HTML_CODE.HTML_VOID_TAG);
            Keyword.put("input",HTML_CODE.HTML_VOID_TAG);
            Keyword.put("label",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("li",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("name",HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("nav",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("number",HTML_CODE.HTML_FORM_TYPE);
            Keyword.put("ol",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("option",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("p",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("password",HTML_CODE.HTML_FORM_TYPE);
            Keyword.put("radio",HTML_CODE.HTML_FORM_TYPE);
            Keyword.put("select",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("style",HTML_CODE.HTML_NONVOID_PROPERTY);
            Keyword.put("submit",HTML_CODE.HTML_FORM_TYPE);
            Keyword.put("table",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("td",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("text",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("textarea",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("time",HTML_CODE.HTML_FORM_TYPE);
            Keyword.put("title",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("tr",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("type",HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("ul",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("value", HTML_CODE.HTML_PROPERTY_NAME);
   }
     
     HTML_CODE SearchKeyword(String key){ 
                   
          if(Keyword.containsKey(key.toLowerCase())) 
            return  Keyword.get(key.toLowerCase());                
          else
            return HTML_CODE.NOT_FOUND;      
     }
  }
     
   
 