package ted;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Digest {

    public MD5Digest() {
  
    }

    
    
    public String StringToMD5(String convert){
        
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(convert.getBytes());
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                    sb.append(String.format("%02x", b & 0xff));
            }
            convert = sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            convert=null;
        }
        
        return convert;
    }
    
}
