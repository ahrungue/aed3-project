package corn.indexes;

import java.io.*;
import java.util.HashMap;
import java.util.Set;

/**
 * @author rungue
 * @since 05/13/15
 */
public class UserIndex {

    HashMap<String, Long> indexMap;

    public UserIndex(){

        try {
            ObjectInputStream inputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream("User_Index.ser")));
            this.indexMap = ((HashMap<String, Long>) inputStream.readObject());

        }catch (FileNotFoundException e){
            this.indexMap = new HashMap<String, Long>();
            this.indexMap.put("nextAddress", (long) 0);
            this.save();
        }catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }//end UserIndex

    public void save(){

        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    new BufferedOutputStream(new FileOutputStream("User_Index.ser")));

            outputStream.writeObject(this.indexMap);
            outputStream.flush();
            outputStream.close();

        }catch(java.io.IOException e) {
            e.printStackTrace();
        }

    }//endSave()

    public void addKeyIndex( String key, Long address){
        this.indexMap.put(key,address);
    }//end addIndex()

    public Long getAddressByIndex( String key ){
        return this.indexMap.get(key);
    }//end getAddressByIndex()

    public long getNextAddress(){
        return this.indexMap.get("nextAddress");
    }//end getNextAddress()

    public void setNextAddress( Long nextAddress ){
        this.indexMap.remove("nextAddress");
        this.indexMap.put("nextAddress", nextAddress);
    }//end getNextAddress()

    @Override
    public String toString() {
        String toString = "";

        toString += String.format("%-36s\t%-8s%n","Identificador","Endereço");

        Set<String> strings = this.indexMap.keySet();
        for( String key : strings ){
            toString += String.format("%-36s\t%-8s%n", key,this.indexMap.get(key).toString());
        }//end for(iterator)

        return toString;
    }//end toString()
}//end class UserIndex
