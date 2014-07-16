import java.io.File;
import java.lang.reflect.Constructor;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class SortFactory {
	public static Sort getInstance() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Serializer serializer = new Persister();
		File source = new File("./etc/setting.xml");
		XMLParser parser = null;
		
		Constructor<?> constructor = null;
		Sort instance = null;
		try {
			parser = serializer.read(XMLParser.class, source);
			constructor = Class.forName(parser.getSortName()).getDeclaredConstructor(new Class[0]);
			constructor.setAccessible(true);
			instance = (Sort) constructor.newInstance(new Object[0]); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return instance;
	}
}