package webdroid;
import java.util.List;
import webdroid.KeywordList.HTML_CODE;
 

//Symbol Table is the collection of element, its properties and how they are constructed as trees

public class SymbolTable { 
   private int ID;
   private String Name;
   private String Tag;
   private String Class;
   private HTML_CODE Element_type;
   private String Style;
   private String Type;
   private String For;
   private String Action;
   private String Src;
   private int Parent_ID;
   private List<SymbolTable> Children_element;
    
                            //Constructor
        public SymbolTable(){    
                    try{   //initialization
                            this.ID = 0;	        
                            this.Name = null;
                            this.Tag = null;
                            this.Class = null;	        
                            this.Element_type = null;
                            this.Style = null;	        
                            this.Type = null;
                            this.For = null;	        
                            this.Action = null;
                            this.Src = null;
                            this.Parent_ID = 0;
                    }
                    catch (Exception e){
                                     System.err.println("Exception creating element:" + e);
                    }
        }
        
     // Setters and Getters for each element properties
    //---------------------------------------------------------------------------//
    void    setID(int id)    {  this.ID = id;  }               
    int  getID()             {  return ID;     }
    //--------------------------------------------------------------------------//
    void    setName(String name){ this.Name = name; }
    String  getName()           { return Name;      }
    //-------------------------------------------------------------------------//
    
    List<SymbolTable> getChildren_element()         { return Children_element;   }    
    void setChildren_element(List<SymbolTable> c)   { this.Children_element = c; }
    
    //--------------------------------------------------------------------------//
    void    setTag(String tag){ this.Tag = tag; }
    String  getTag()          { return Tag;     }
    //-------------------------------------------------------------------------//
    void    setClassName(String classaame){ this.Class = classaame; }
    String  getClassName()          { return Class;     }
    
    //-------------------------------------------------------------------------//
    void    setElement_type(HTML_CODE element_type){ this.Element_type = element_type; }
    HTML_CODE  getElement_type()          { return Element_type;     }
    
    //-------------------------------------------------------------------------//
    void    setStyle(String style){ this.Style = style; }
    String  getStyle()            { return Style;       }

    //-------------------------------------------------------------------------//
    void    setType(String type) { this.Type = type; }
    String  getType()            { return Type;     }    
    //-------------------------------------------------------------------------//
    void    setFor(String for_name) { this.For = for_name; }
    String  getFor()                { return For;     }    
    
    //-------------------------------------------------------------------------//
    void    setAction(String action) { this.Action = action; }
    String  getAction()                { return Action;     }    

    //-------------------------------------------------------------------------//
    void    setSrc(String src) { this.Src = src; }
    String  getSrc()           { return Src;     }    
    //-------------------------------------------------------------------------//
    
    void   setParent_ID(int Parent_ID){ this.Parent_ID = Parent_ID; }
    int getParent_ID() {        return Parent_ID;     }
   //-------------------------------------------------------------------------// 
    void setChildrenElement (List<SymbolTable>  c){
        this.Children_element = c;
    }
    List<SymbolTable>  getChildrenElement(){
        return Children_element;
    }
}
