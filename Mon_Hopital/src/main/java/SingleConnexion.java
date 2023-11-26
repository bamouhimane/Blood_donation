import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Base64;

public class SingleConnexion {
    static String db;
    static String user;
    static String pwdEncrypted;
    static String url;

    static {
        try {
            db = Config_lecture.getProperty("db");
            user = Config_lecture.getProperty("user");
            pwdEncrypted = Config_lecture.getProperty("pwdEncrypted"); // Mettez à jour le nom de la propriété
            url = "jdbc:mysql://localhost:3306/" + db;
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static String decrypt(String encryptedValue, String key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedValue));
        return new String(decryptedBytes);
    }

    public static Connection getConnection() throws Exception {
        try {
            // Déchiffrement du mot de passe
            String encryptionKey = Conf_lecture_key.getEncryptionKey();
            String decryptedPwd = decrypt(pwdEncrypted, encryptionKey);

            // Utilisation du mot de passe déchiffré pour la connexion
            return DriverManager.getConnection(url, user, decryptedPwd);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la connexion à la base de données.", e);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
