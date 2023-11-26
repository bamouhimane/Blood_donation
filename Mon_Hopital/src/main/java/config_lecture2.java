import java.io.InputStream;
import java.util.Properties;

public class config_lecture2 {

    public static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = config_lecture2.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static String getProperty(String key) {
        Properties properties = loadProperties();
        return properties.getProperty(key);
    }
}
