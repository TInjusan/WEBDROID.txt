package webdroid;
import java.util.List;
import webdroid.KeywordList.HTML_CODE;
 

//Symbol Table is the collection of element, its properties and how it they are constructed as trees

public class SymbolTable { 
    String ID;
    String Name;
    String Tag;
    String Class;
    HTML_CODE Element_type;
    String Style;
    String Type;
    String For;
    String Action;
    String Src;
    String Parent_ID;
    List<SymbolTable> Children_element;
    
                            //Constructor
        public SymbolTable(){    
                    try{   //initialization
                            this.ID = null;	        
                            this.Name = null;
                            this.Tag = null;
                            this.Class = null;	        
                            this.Element_type = null;
                            this.Style = null;	        
                            this.Type = null;
                            this.For = null;	        
                            this.Action = null;
                            this.Src = null;
                            this.Parent_ID = null;
                    }
                    catch (Exception e){
                                     System.err.println("Exception creating element:" + e);
                    }
        }
        
     // Setters and Getters for each element properties
    //---------------------------------------------------------------------------//
    void    setID(String id)    {  this.ID = id;  }               
    String  getID()             {  return ID;     }
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
    
    void   setParent_ID(String Parent_ID){ this.Parent_ID = Parent_ID; }
    String getParent_ID() {        return Parent_ID;     }
   //-------------------------------------------------------------------------// 
    
}
