 
package webdroid;
import java.util.ArrayList;
import java.util.HashMap;

public class KeywordList {
    
    
    public enum HTML_CODE {NOT_FOUND, HTML_NONVOID_TAG, HTML_VOID_TAG, HTML_PROPERTY_NAME, HTML_FORM_TYPE, HTML_SPECIAL_CHARACTER};
    HashMap<String, HTML_CODE> Keyword = new HashMap<>();
    
    public ArrayList<String> html_kw = new ArrayList<>();
    public ArrayList<HTML_CODE> htmlcodes = new ArrayList<>();
          
     //constructor
     KeywordList(){
            
    //predefined list of keywords in HTML     // and the corresponding token codes
        html_kw.add("/");		        htmlcodes.add(HTML_CODE.HTML_SPECIAL_CHARACTER);
        html_kw.add("<");		        htmlcodes.add(HTML_CODE.HTML_SPECIAL_CHARACTER);
        html_kw.add(">");		        htmlcodes.add(HTML_CODE.HTML_SPECIAL_CHARACTER);
        html_kw.add("a");		        htmlcodes.add(HTML_CODE.HTML_VOID_TAG);
        html_kw.add("action");		        htmlcodes.add(HTML_CODE.HTML_PROPERTY_NAME);
        html_kw.add("body");		        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        html_kw.add("br");		        htmlcodes.add(HTML_CODE.HTML_VOID_TAG);
        html_kw.add("button");		        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        html_kw.add("checkbox");		htmlcodes.add(HTML_CODE.HTML_FORM_TYPE);
        html_kw.add("class");		        htmlcodes.add(HTML_CODE.HTML_PROPERTY_NAME);
        html_kw.add("date");		        htmlcodes.add(HTML_CODE.HTML_FORM_TYPE);
        html_kw.add("div");		        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        html_kw.add("DOCTYPE");		        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        html_kw.add("for");		        htmlcodes.add(HTML_CODE.HTML_PROPERTY_NAME);
        html_kw.add("email");		        htmlcodes.add(HTML_CODE.HTML_FORM_TYPE);
        html_kw.add("form");       		htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        html_kw.add("h1");		        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        html_kw.add("h2");		        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        html_kw.add("h3");		        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        html_kw.add("head");		        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        html_kw.add("html");		        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        html_kw.add("id");		        htmlcodes.add(HTML_CODE.HTML_PROPERTY_NAME);
        html_kw.add("img");		        htmlcodes.add(HTML_CODE.HTML_VOID_TAG);
        html_kw.add("input");		        htmlcodes.add(HTML_CODE.HTML_VOID_TAG);
        html_kw.add("label");		        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        html_kw.add("li");		        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        html_kw.add("name");		        htmlcodes.add(HTML_CODE.HTML_PROPERTY_NAME);
        html_kw.add("nav");		        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        html_kw.add("number");		        htmlcodes.add(HTML_CODE.HTML_FORM_TYPE);
        html_kw.add("ol");		        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        html_kw.add("option");		        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        html_kw.add("p");		        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        html_kw.add("password");		htmlcodes.add(HTML_CODE.HTML_FORM_TYPE);
        html_kw.add("radio");		        htmlcodes.add(HTML_CODE.HTML_FORM_TYPE);
        html_kw.add("select");		        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        html_kw.add("style");		        htmlcodes.add(HTML_CODE.HTML_PROPERTY_NAME);
        html_kw.add("submit");		        htmlcodes.add(HTML_CODE.HTML_FORM_TYPE);
        html_kw.add("table");		        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        html_kw.add("td");		        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        html_kw.add("text");		        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        html_kw.add("textarea");		htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        html_kw.add("time");		        htmlcodes.add(HTML_CODE.HTML_FORM_TYPE);
        html_kw.add("title");		        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        html_kw.add("tr");		        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        html_kw.add("type");		        htmlcodes.add(HTML_CODE.HTML_PROPERTY_NAME);
        html_kw.add("ul");		        htmlcodes.add(HTML_CODE.HTML_NONVOID_TAG);
        html_kw.add("value");		        htmlcodes.add(HTML_CODE.HTML_PROPERTY_NAME);
    }
     
     HTML_CODE SearchKeyword(String key){
     
       int indexOfKeyword = html_kw.indexOf(key);
       
       if(indexOfKeyword>-1)
            return htmlcodes.get(indexOfKeyword);
       else
           return HTML_CODE.NOT_FOUND;

           }
 
     }
     
   
 