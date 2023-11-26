import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Conf_lecture_key {

    private static final String PROPERTIES_FILE = "key.properties";
    private static final String ENCRYPTION_KEY_PROPERTY = "encryptionKey";

    private static Properties properties;

    static {
        loadProperties();
    }

    private static void loadProperties() {
        properties = new Properties();
        try (InputStream input = Conf_lecture_key.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                System.out.println("Désolé, le fichier de propriétés " + PROPERTIES_FILE + " n'a pas pu être trouvé.");
                return;
            }
            // charge les propriétés depuis le fichier
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getEncryptionKey() {
        if (properties == null) {
            loadProperties();
        }
        return properties.getProperty(ENCRYPTION_KEY_PROPERTY);
    }
}
