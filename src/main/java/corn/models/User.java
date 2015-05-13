package corn.models;

import corn.annotations.SerialAttribute;
import corn.annotations.SerialClass;
import corn.interfaces.BytesConvertable;
import org.apache.commons.lang3.ArrayUtils;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author rungue
 * @since 05/11/15
 */
@SerialClass(size = 336)
public class User implements Serializable, BytesConvertable<User> {

    private static final long serialVersionUID = 9204172974644360629L;

    @SerialAttribute(size = 36)
    private String id;

    @SerialAttribute(size = 200)
    private String name;

    @SerialAttribute(size = 100)
    private String password;

    public User(){
        this(null, null, null);
    }//end User()

    public User( String id, String name, String password ){
        this.id       = id;
        this.name     = name;
        this.password = password;
    }//end User()

    public User(byte[] bytes) {
        this.id       = new String(ArrayUtils.subarray(bytes, 0, 36)).trim();
        this.name     = new String(ArrayUtils.subarray(bytes, 36, 236)).trim();
        this.password = new String(ArrayUtils.subarray(bytes, 236, 336)).trim();
    }//end User()

    @Override
    public byte[] toBytes(){

        byte[] objectBytes = ArrayUtils.EMPTY_BYTE_ARRAY;

        try {
            int idByteSize          = User.class.getDeclaredField("id").getAnnotation(SerialAttribute.class).size();
            int nameByteSize        = User.class.getDeclaredField("name").getAnnotation(SerialAttribute.class).size();
            int passwordByteSize    = User.class.getDeclaredField("password").getAnnotation(SerialAttribute.class).size();

            byte[] idByte           = Arrays.copyOf(id.getBytes(), idByteSize);
            byte[] nameBytes        = Arrays.copyOf(name.getBytes(), nameByteSize);
            byte[] passwordBytes    = Arrays.copyOf(password.getBytes(), passwordByteSize);

            objectBytes = ArrayUtils.addAll(objectBytes, idByte);
            objectBytes = ArrayUtils.addAll(objectBytes, nameBytes);
            objectBytes = ArrayUtils.addAll(objectBytes, passwordBytes);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return objectBytes;

    }//end toBytes()

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        return !(password != null ? !password.equals(user.password) : user.password != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }//end toString()

}//end class User
