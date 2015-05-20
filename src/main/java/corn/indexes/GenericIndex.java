package corn.indexes;

import java.io.*;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Stream;

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

    public GenericIndex(){
        this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        try {
            ObjectInputStream inputStream = new ObjectInputStream(new BufferedInputStream(
                    new FileInputStream(this.persistentClass.getSimpleName() + "_Index.ser")));
            this.indexMap = ((TreeMap<String, Long>) inputStream.readObject());

        }catch (FileNotFoundException e){
            this.indexMap = new TreeMap<>();
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
                            new FileOutputStream(this.persistentClass.getSimpleName() + "_Index.ser")));

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

        toString += String.format("%-36s\t%-8s%n","Quantidade de Registros ->", (this.indexMap.size()-1));
        toString += String.format("%-36s\t%-8s%n","Identificador","Endereço");

        Iterator<Map.Entry<String, Long>> strings = this.indexMap.entrySet()
                .stream()
                .sorted(Comparator.comparingLong(Map.Entry<String, Long>::getValue))
                .;
        


        for( String key : strings ){
            toString += String.format("%-36s\t%-8s%n", key,this.indexMap.get(key).toString());
        }//end for(iterator)

        return toString;
    }//end toString()

}//end class GenericIndex
