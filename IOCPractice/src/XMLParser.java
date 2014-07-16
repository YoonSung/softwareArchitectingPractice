import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class XMLParser {
     
    @Element
    private String sortName;
     
    public XMLParser() {
        super();
    }
 
    public XMLParser(String sortName) {
        super();
        this.sortName = sortName;
    }
 
    public String getSortName() {
        return sortName;
    }
}