/*
 * This is where we are going to create and put the test cases for our prototype
 * Most of the test cases here are to check each phases of translation is good.
 * The test cases here are created by the programmers, thus the test cases are customized.
 */
package webdroid;


public class Test_Module {
    
    
       public void Extraction_Method(String s){
        
       
        String temp;
        CharacterScanner scan = new CharacterScanner();
        CharacterScanner.TOKEN_CODE t;
        KeywordList kw = new KeywordList();
        KeywordList.HTML_CODE kc;
                
                
                scan.setSource(s);
               
                do{
                    t = scan.next_token();
                    kc = kw.SearchKeyword(scan.getLexeme());
                    temp = scan.getLexeme()+"\t\t\t\t\t"+"- "+scan.getToken()+"\t\t\t\t\t\t"+"- "+ kc;
                    
                     System.out.println(temp);
                   }while(t != CharacterScanner.TOKEN_CODE.EOF_TOKEN);
      }
    
}
