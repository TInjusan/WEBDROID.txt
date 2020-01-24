 
package webdroid;
import java.util.ArrayList;

public class KeywordList {
    
    
     public enum HTML_CODE {
       NOT_FOUND, HTML_TAG, HTML_PROPERTY_NAME, HTML_FORM_TYPE, HTML_SPECIAL_CHARACTER };
     
     ArrayList<String> htmltags = new ArrayList<>();
     ArrayList<String> htmlpropertyname = new ArrayList<>();
     ArrayList<String> htmlformtype = new ArrayList<>();
     ArrayList<String> htmlspecialcharacter = new ArrayList<>();
     ArrayList<HTML_CODE> htmlcodes = new ArrayList<>();
     
     
     //constructor
     KeywordList(){
         //predefined list of keywords in HTML
        htmlformtype.add("date");
        htmlformtype.add("email");
        htmlformtype.add("number");
        htmlformtype.add("password");
        htmlformtype.add("radio");
        htmlformtype.add("submit");
        htmlformtype.add("time");
        
        htmlpropertyname.add("action");
        htmlpropertyname.add("class");
        htmlpropertyname.add("id");
        htmlpropertyname.add("type");
        htmlpropertyname.add("value");
        
        htmltags.add("a");
        htmltags.add("body");
        htmltags.add("button");
        htmltags.add("checkbox");
        htmltags.add("div");
        htmltags.add("form");       
        htmltags.add("h1");
        htmltags.add("h2");
        htmltags.add("h3");
        htmltags.add("head");
        htmltags.add("html");
        htmltags.add("img");
        htmltags.add("input");
        htmltags.add("label");
        htmltags.add("li");
        htmltags.add("nav");
        htmltags.add("ol");
        htmltags.add("option");
        htmltags.add("select");
        htmltags.add("table");
        htmltags.add("td");
        htmltags.add("text");
        htmltags.add("textarea");
        htmltags.add("title");
        htmltags.add("tr");
        htmltags.add("ul");

        
        // and the corresponding token codes
        htmlcodes.add(HTML_CODE.HTML_TAG);
        htmlcodes.add(HTML_CODE.HTML_PROPERTY_NAME);
        htmlcodes.add(HTML_CODE.HTML_FORM_TYPE);
        htmlcodes.add(HTML_CODE.HTML_SPECIAL_CHARACTER);
     }
     
     HTML_CODE SearchKeyword(String key){
      //perform binary search
      int i = 1;  //iteration variable to check each one of the html keyword; htmltag, htmlpropertyname, htmlformtype
      
      ArrayList<String> htmllookup = new ArrayList<>();
      htmllookup = htmltags;
      
      do{
      
            int bottom = 0;
            int top = htmllookup.size();
            int mid = 0;

            boolean found = false;
            while (bottom <= top && found == false){

                mid = (bottom + top) / 2;

                  if(htmllookup.get(mid).equals(key)){
                    found = true;

                }
                else if(htmllookup.get(mid).compareTo(key) >= 0 ){
                    top = mid - 1;
                }
                else
                    bottom = mid + 1;
            }
            
             if(found){
               return htmlcodes.get(i-1); 
             }
             
             else if (!found && i == 3){
                 return HTML_CODE.NOT_FOUND;
             }
             else
                 i++;
      }while(i<=3);
      
    switch()
     
 

}
