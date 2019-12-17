 
package webdroid;
import java.util.ArrayList;

public class KeywordList {
     public enum KW_CODE {
       NOT_FOUND, HTML, HEAD, BODY, TITLE, H1, H2, H3, FORM, INPUT, TEXTAREA, BUTTON, SELECT, OPTION,
       LABEL, IMG, A, LI, UL, OL, TABLE, TD, TR, DIV, NAV, CHECKBOX, DATE, EMAIL, NUMBER, PASSWORD, 
       RADIO, SUBMIT, TEXT, TIME, ACTION, TYPE, VALUE
     };
     
     ArrayList<String> kwlist = new ArrayList<>();
     ArrayList<KW_CODE> kwcodes = new ArrayList<>();
     
     //constructor
     KeywordList(){
         //predefined list of keywords in HTML
        kwlist.add("a");
        kwlist.add("action");
        kwlist.add("body");
        kwlist.add("button");
        kwlist.add("checkbox");
        kwlist.add("date");
        kwlist.add("div");
        kwlist.add("email");
        kwlist.add("form");       
        kwlist.add("h1");
        kwlist.add("h2");
        kwlist.add("h3");
        kwlist.add("head");
        kwlist.add("html");
        kwlist.add("img");
        kwlist.add("input");
        kwlist.add("label");
        kwlist.add("li");
        kwlist.add("nav");
        kwlist.add("number");
        kwlist.add("ol");
        kwlist.add("option");
        kwlist.add("password");
        kwlist.add("radio");
        kwlist.add("select");
        kwlist.add("submit");
        kwlist.add("table");
        kwlist.add("td");
        kwlist.add("text");
        kwlist.add("textarea");
        kwlist.add("time");
        kwlist.add("title");
        kwlist.add("tr");
        kwlist.add("type");
        kwlist.add("ul");
        kwlist.add("value");
        
        // and the corresponding token codes
        kwcodes.add(KW_CODE.A);
        kwcodes.add(KW_CODE.ACTION);
        kwcodes.add(KW_CODE.BODY);
        kwcodes.add(KW_CODE.BUTTON);
        kwcodes.add(KW_CODE.CHECKBOX);
        kwcodes.add(KW_CODE.DATE);
        kwcodes.add(KW_CODE.DIV);
        kwcodes.add(KW_CODE.EMAIL);
        kwcodes.add(KW_CODE.FORM);
        kwcodes.add(KW_CODE.H1);
        kwcodes.add(KW_CODE.H2);
        kwcodes.add(KW_CODE.H3);
        kwcodes.add(KW_CODE.HEAD);
        kwcodes.add(KW_CODE.HTML);
        kwcodes.add(KW_CODE.IMG);
        kwcodes.add(KW_CODE.INPUT);
        kwcodes.add(KW_CODE.LABEL);
        kwcodes.add(KW_CODE.LI);
        kwcodes.add(KW_CODE.NAV);
        kwcodes.add(KW_CODE.NUMBER);
        kwcodes.add(KW_CODE.OL);
        kwcodes.add(KW_CODE.OPTION);
        kwcodes.add(KW_CODE.PASSWORD);
        kwcodes.add(KW_CODE.RADIO);
        kwcodes.add(KW_CODE.SELECT);
        kwcodes.add(KW_CODE.SUBMIT);
        kwcodes.add(KW_CODE.TABLE);
        kwcodes.add(KW_CODE.TD);
        kwcodes.add(KW_CODE.TEXT);
        kwcodes.add(KW_CODE.TEXTAREA);
        kwcodes.add(KW_CODE.TIME);
        kwcodes.add(KW_CODE.TITLE);
        kwcodes.add(KW_CODE.TR);
        kwcodes.add(KW_CODE.TYPE);
        kwcodes.add(KW_CODE.UL);
        kwcodes.add(KW_CODE.VALUE);


     }
     
     KW_CODE SearchKeyword(String key){
      //perform binary search
      
      int bottom = 0;
      int top = kwlist.size();
      int mid = 0;
      
      boolean found = false;
      while (bottom <= top && found == false){
          
          mid = (bottom + top) / 2;
          
            if(kwlist.get(mid).equals(key)){
              found = true;
              
          }
          else if(kwlist.get(mid).compareTo(key) >= 0 ){
              top = mid - 1;
          }
          else
              bottom = mid + 1;
      }
      if(found)
          return kwcodes.get(mid);
      else
          return KW_CODE.NOT_FOUND;
           
     }
 

}
