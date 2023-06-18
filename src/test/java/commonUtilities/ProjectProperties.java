package commonUtilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ProjectProperties {

    private static String filePath = null;
    private static File file = null;
    private static FileInputStream stream = null;
    private static Properties properties = null;

    public static String getProperty(String key){
        if (filePath == null)
        {
            String env = System.getenv("env");
            filePath = "src/test/resources/"+env+".properties";
        }

        if(properties == null){
            try{
                file = new File(filePath);
                stream = new FileInputStream(file);
                properties = new Properties();
                properties.load(stream);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return (properties.containsKey(key)) ? properties.getProperty(key) : null;
    }
}
