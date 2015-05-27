package corn.generics;

import corn.annotations.SerialClass;
import corn.indexes.GenericIndex;
import corn.interfaces.BytesConvertable;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * Classe implementa funções para serializar o objeto T, as classe que extendem esta devem implementar a função
 * {@link GenericFileDAO#getGenericIndex()} que deve retornar um classe que extende {@link GenericIndex}.
 * </p>
 *
 * @author rungue
 * @since 05/11/15
 */
@Repository
@SuppressWarnings("unchecked")
public abstract class GenericFileDAO<T extends Serializable> {

    private Class<T> persistentClass;

    protected abstract GenericIndex<T> getGenericIndex();

    public GenericFileDAO() {
        this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }//end GenericFileDAO()


    private RandomAccessFile getRandomAccessFile(){

        try {
            String fileName = String.format("%s%s%s.ser",
                    System.getProperty("user.home"),
                    File.separator,
                    this.persistentClass.getSimpleName() );

            File file = new File(fileName);

            return new RandomAccessFile(file,"rw");

        }catch( IOException e ){
            e.printStackTrace();
            System.err.println("Erro ao abrir o arquivo.");
        }//end try-catch

        return null;
    }//end init()


    public void save( T entity ){

        GenericIndex<T> genericIndex = this.getGenericIndex();

        //Autocloseable object
        try( RandomAccessFile randomAccessFile = this.getRandomAccessFile() ){

            Field id = entity.getClass().getDeclaredField("id");
            id.setAccessible(true);
            String objectId = UUID.randomUUID().toString();
            id.set(entity, objectId);

            byte[] bytes = ((BytesConvertable) entity).toBytes();
            long nextAddress = genericIndex.getNextAddress();

            //Seek the address on the file
            randomAccessFile.seek(nextAddress);

            //Add the new object on the index
            genericIndex.addKeyIndex(objectId, nextAddress);

            //Write bytes on the file after seek the specific position
            randomAccessFile.write(bytes);
            nextAddress += bytes.length;

            genericIndex.setNextAddress(nextAddress);
            genericIndex.save();

        }catch( NoSuchFieldException | IllegalAccessException | IOException e ){
            e.printStackTrace();
        }//end try-catch


    }//end save()

    public void update(T entity ){

        GenericIndex<T> genericIndex = this.getGenericIndex();

        //Autocloseable object
        try( RandomAccessFile randomAccessFile = this.getRandomAccessFile() ){

            Field fieldId = entity.getClass().getDeclaredField("id");
            fieldId.setAccessible(true);

            //Object.id to look on the index
            String id = fieldId.get(entity).toString();

            //Address of the user to update
            Long addressByIndex = genericIndex.getAddressByIndex(id);

            //Seek the old object to overwrite
            randomAccessFile.seek(addressByIndex);
            randomAccessFile.write( ((BytesConvertable) entity).toBytes() );

        }catch(NoSuchFieldException | IllegalAccessException | IOException e) {
            e.printStackTrace();
        }//end try-catch

    }//end update()

    public T byId( String id ){

        GenericIndex<T> genericIndex = this.getGenericIndex();
        T t = null;

        //Autocloseable object
        try( RandomAccessFile randomAccessFile = this.getRandomAccessFile() ){

            int classByteSize = this.persistentClass.getAnnotation(SerialClass.class).size();
            byte[] classBytes = new byte[classByteSize];

            Long address = genericIndex.getAddressByIndex(id);

            //address must be not null. The null address indicate the absence of the object
            if(address != null ){

                //Seek the address on the file
                randomAccessFile.seek(address);
                randomAccessFile.read(classBytes);

                //Get the Constructor(byte[] bytes) from Object T
                Constructor<T> declaredConstructor = this.persistentClass.getDeclaredConstructor(byte[].class);

                //Create a new instance T from the bytes
                t = declaredConstructor.newInstance(classBytes);

            }//end if

        }catch(IOException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }//end try-catch

        return t;
    }//end byId()

    public T delete( String id ){

        GenericIndex<T> genericIndex = this.getGenericIndex();
        T t = null;

        long count = this.count();

        //Autocloseable object
        try( RandomAccessFile randomAccessFile = this.getRandomAccessFile() ){

            //size of bytes that class
            int classByteSize = this.persistentClass.getAnnotation(SerialClass.class).size();

            //Object to remove
            byte[] classBytesToRemove = new byte[classByteSize];
            byte[] classBytes         = new byte[classByteSize];

            //Address of the object to be removed from the file
            Long addressToRemove = genericIndex.getAddressByIndex(id);

            //Find the address of the last object in the file
            Long lastAddressUsed = genericIndex.getNextAddress() - classByteSize;

            System.out.println(genericIndex);

            System.out.printf("Remove Address -> %s%n", addressToRemove);
            System.out.printf("Last Address -> %s%n", lastAddressUsed);

            //check if the address to remove is not the same of last object index otherwise means that the file has only one object
            if( addressToRemove.longValue() != lastAddressUsed.longValue() ){

                //Seek the lastAddressUsed on the file
                randomAccessFile.seek(lastAddressUsed);
                randomAccessFile.read(classBytes);

                //Seek the address to remove and read the object to return
                randomAccessFile.seek(addressToRemove);
                randomAccessFile.read(classBytesToRemove);

                //Seek the address to remove and overwrite with the last Object on the file
                randomAccessFile.seek(addressToRemove);
                randomAccessFile.write(classBytes);

                //Get the Constructor(byte[] bytes) from Object T
                Constructor<T> declaredConstructor = this.persistentClass.getDeclaredConstructor(byte[].class);

                //Create a new instance T from the bytes
                t = declaredConstructor.newInstance(classBytesToRemove);

                //Object that overwrite the object to remove
                T lastObject = declaredConstructor.newInstance(classBytes);

                //Get the field id from the object
                Field fieldId = lastObject.getClass().getDeclaredField("id");
                fieldId.setAccessible(true);

                //Object id to update on the index
                String lastObjectId = fieldId.get(lastObject).toString();

                //Update the index
                genericIndex.updateKeyAddress(lastObjectId, addressToRemove);

            }//end if

            //file size
            long length = randomAccessFile.length();
            //Truncate the file to erase the last object
            randomAccessFile.setLength(length-classByteSize);

            //Remove the key from the index
            genericIndex.removeKeyIndex(id);

            //Define the next free address as the last object address
            genericIndex.setNextAddress(lastAddressUsed);

            //Save the index
            genericIndex.save();

        } catch (IOException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException | NoSuchFieldException e) {
            e.printStackTrace();
        }//end try-catch


        return t;

    }//end delete()

    public long count(){

        long count = 0;

        //Autocloseable object
        try( RandomAccessFile randomAccessFile = this.getRandomAccessFile() ){

            int classByteSize = this.persistentClass.getAnnotation(SerialClass.class).size();
            byte[] classBytes = new byte[classByteSize];

            while( (randomAccessFile != null ? randomAccessFile.read(classBytes) : 0) != -1 ){
                ++count;
                classBytes = new byte[classByteSize];
            }//end while

        }catch(EOFException eof) {
            return count;
        }catch(Exception e) {
            e.printStackTrace();
        }//end try-catch

        return count;

    }//end count()

    public List<T> findAll(){
        List<T> list = new ArrayList<T>();

        //Autocloseable object
        try( RandomAccessFile randomAccessFile = this.getRandomAccessFile() ){

            T t;
            int classByteSize = this.persistentClass.getAnnotation(SerialClass.class).size();
            byte[] classBytes = new byte[classByteSize];

            while( (randomAccessFile != null ? randomAccessFile.read(classBytes) : 0) != -1 ){

                //Get the Constructor(byte[] bytes) from Object T
                Constructor<T> declaredConstructor = this.persistentClass.getDeclaredConstructor(byte[].class);
                //Create a new instance T from the bytes
                t = declaredConstructor.newInstance(classBytes);
                list.add(t);

            }//end while

        } catch (EOFException eof) {
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } //end try-catch

        return ( list.isEmpty() ? null : list);
    }//end findAll()

}//end class GenericFileDAO
