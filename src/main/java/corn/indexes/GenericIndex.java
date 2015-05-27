package corn.indexes;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>
 * Classe para criar um indice para armazenar objetos definos com tamanhos fixos em um arquivo. A classe define um
 * {@link HashMap} para armazenar as keys para acessar a tabela hash com o endereço do objeto no arquivo. A classe
 * armazena uma tupla(key,address) que representa o id do objeto e o endereco dela no arquivo serializado. Além disso
 * pode utilizar a chave nextAddress para armazenar ou recuperar a proxima posiçao vazia.
 * </p>
 *
 * @author rungue
 * @since 05/19/15
 */
@SuppressWarnings("unchecked")
public abstract class GenericIndex<T> {

    TreeMap<String, Long> indexMap;

    private Class<T> persistentClass;

    public File getIndexFile(){
        String fileName = String.format("%s%s%s_Index.ser",
                System.getProperty("user.home"),
                File.separator,
                this.persistentClass.getSimpleName() );

        return new File(fileName);
    }//end getIndexFile()

    public GenericIndex(){
        this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        try {
            ObjectInputStream inputStream = new ObjectInputStream(new BufferedInputStream(
                    new FileInputStream(this.getIndexFile()) ));

            //Read the stream and cast the object
            this.indexMap = ((TreeMap<String, Long>) inputStream.readObject());

        }catch (FileNotFoundException e){
            this.indexMap = new TreeMap<String, Long>();
            this.indexMap.put("nextAddress", (long) 0);
            this.save();
        }catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }//end try-catch

    }//end UserIndex

    public void save(){

        try {

            //Save the object index on the file
            ObjectOutputStream outputStream = new ObjectOutputStream(new BufferedOutputStream(
                    new FileOutputStream(this.getIndexFile())));

            outputStream.writeObject(this.indexMap);
            outputStream.flush();
            outputStream.close();

        }catch(java.io.IOException e) {
            e.printStackTrace();
        }//end try-catch

    }//endSave()

    public void addKeyIndex( String key, Long address){
        this.indexMap.put(key,address);
    }//end addIndex()


    public Long removeKeyIndex( String key){
        return this.indexMap.remove(key);
    }//end removeKeyIndex()

    public void updateKeyAddress( String key, Long address){
        this.removeKeyIndex(key);
        this.addKeyIndex(key,address);
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
        String margin = StringUtils.repeat("*", 50) + System.lineSeparator();
        String toString = "";

        toString += margin;
        toString += String.format("%-36s\t%-8s%n", "Quantidade de Registros ->", (this.indexMap.size() - 1));
        toString += margin;
        toString += String.format("%-36s\t%-8s%n", "Identificador", "Endereço");

        //Add key - value in string
        for( Map.Entry<String,Long> key  : this.indexMap.entrySet() ){
            toString += String.format("%-36s\t%-8s%n", key.getKey(), key.getValue());
        }//end for(iterator)

        toString += margin;
        return toString;
    }//end toString()

}//end class GenericIndex
