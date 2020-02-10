 
package webdroid;
import java.util.ArrayList;

public class KeywordList {
    
    
     public enum HTML_CODE {NOT_FOUND, HTML_NONVOID_TAG, HTML_VOID_TAG, HTML_PROPERTY_NAME, HTML_FORM_TYPE, HTML_SPECIAL_CHARACTER};
     
     ArrayList<String> html_kw = new ArrayList<>();
     ArrayList<HTML_CODE> htmlcodes = new ArrayList<>();
     
     
     //constructor
     KeywordList(){
        //predefined list of keywords in HTML
        html_kw.add("/");
        html_kw.add("<");
        html_kw.add(">");
        html_kw.add("a");
        html_kw.add("action");
        html_kw.add("body");
        html_kw.add("button");
        html_kw.add("br");
        html_kw.add("checkbox");
        html_kw.add("class");
        html_kw.add("date");
        html_kw.add("div");
        html_kw.add("DOCTYPE");
        html_kw.add("email");
        html_kw.add("form");       
        html_kw.add("h1");
        html_kw.add("h2");
        html_kw.add("h3");
        html_kw.add("head");
        html_kw.add("html");
        html_kw.add("id");
        html_kw.add("img");
        html_kw.add("input");
        html_kw.add("label");
        html_kw.add("li");
        html_kw.add("nav");
        html_kw.add("number");
        html_kw.add("ol");
        html_kw.add("option");
        html_kw.add("password");
        html_kw.add("radio");
        html_kw.add("select");
        html_kw.add("submit");
        html_kw.add("table");
        html_kw.add("td");
        html_kw.add("text");
        html_kw.add("textarea");
        html_kw.add("time");
        html_kw.add("title");
        html_kw.add("tr");
        html_kw.add("type");
        html_kw.add("ul");
        html_kw.add("value");

        
        // and the corresponding token codes
        htmlcodes.add(HTML_CODE.HTML_SPECIAL_CHARACTER);
        htmlcodes.add(HTML_CODE.HTML_SPECIAL_CHARACTER);
        htmlcodes.add(HTML_CODE.HTML_SPECIAL_CHARACTER);
        htmlcodes.add(HTML_CODE.HTML_VOID_TAG);
        htmlcodes.add(HTML_CODE.HTML_PROPERTY_NAME);
        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        htmlcodes.add(HTML_CODE.HTML_VOID_TAG);
        htmlcodes.add(HTML_CODE.HTML_FORM_TYPE);
        htmlcodes.add(HTML_CODE.HTML_PROPERTY_NAME);
        htmlcodes.add(HTML_CODE.HTML_FORM_TYPE);
        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        htmlcodes.add(HTML_CODE.HTML_FORM_TYPE);
        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        htmlcodes.add(HTML_CODE.HTML_PROPERTY_NAME);
        htmlcodes.add(HTML_CODE.HTML_VOID_TAG);
        htmlcodes.add(HTML_CODE.HTML_VOID_TAG);
        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        htmlcodes.add(HTML_CODE.HTML_FORM_TYPE);
        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        htmlcodes.add(HTML_CODE.HTML_FORM_TYPE);
        htmlcodes.add(HTML_CODE.HTML_FORM_TYPE);
        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        htmlcodes.add(HTML_CODE.HTML_FORM_TYPE);
        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        htmlcodes.add(HTML_CODE.HTML_FORM_TYPE);
        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        htmlcodes.add(HTML_CODE.HTML_PROPERTY_NAME);
        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        htmlcodes.add(HTML_CODE.HTML_PROPERTY_NAME);


     }
     
     HTML_CODE SearchKeyword(String key){
     //perform binary search
      
      int bottom = 0;
      int top = html_kw.size();
      int mid = 0;
      
      boolean found = false;
            while (bottom <= top && found == false){

                mid = (bottom + top) / 2;

                  if(html_kw.get(mid).equals(key)){
                    found = true;

                }
                else if(html_kw.get(mid).compareTo(key) >= 0 ){
                    top = mid - 1;
                }
                else
                    bottom = mid + 1;
            }
            if(found)
                return htmlcodes.get(mid);
            else
                return HTML_CODE.NOT_FOUND;

           }
 
              
     }
     
   
 