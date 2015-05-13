package corn.generics;

import corn.annotations.SerialClass;
import corn.indexes.UserIndex;
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
 * @author rungue
 * @since 05/11/15
 */
@Repository
@SuppressWarnings("unchecked")
public abstract class GenericFileDAO<T extends Serializable> {

    private Class<T> persistentClass;

    public GenericFileDAO() {
        this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }//end GenericFileDAO()


    private RandomAccessFile getRandomAccessFile(){

        try {
            String fileName = this.persistentClass.getSimpleName()+".ser";
            File file = new File(fileName);

            return new RandomAccessFile(file,"rw");

        }catch( IOException e ){
            e.printStackTrace();
            System.err.println("Erro ao abrir o arquivo.");
        }//end try-catch

        return null;
    }//end init()


    public void save( T entity ){

        RandomAccessFile outputStream = this.getRandomAccessFile();
        UserIndex userIndex = new UserIndex();

        try{

            Field id = entity.getClass().getDeclaredField("id");
            id.setAccessible(true);
            String objectId = UUID.randomUUID().toString();
            id.set(entity, objectId);

            byte[] bytes = ((BytesConvertable) entity).toBytes();
            long nextAddress = userIndex.getNextAddress();
            outputStream.seek(nextAddress);

            //Add the new object on the index
            userIndex.addKeyIndex(objectId, nextAddress);

            //Write bytes on the file after seek the specific position
            outputStream.write(bytes);
            nextAddress += bytes.length;

            userIndex.setNextAddress(nextAddress);
            userIndex.save();

            outputStream.close();

        }catch( NoSuchFieldException | IllegalAccessException | IOException e ){
            e.printStackTrace();
        }//end try-catch


    }//end save()

    public void update(T entity ){

        RandomAccessFile outputStream = this.getRandomAccessFile();
        UserIndex userIndex = new UserIndex();


        try {
            Field fieldId = entity.getClass().getDeclaredField("id");
            fieldId.setAccessible(true);

            //User.id to look on the index
            String id = fieldId.get(entity).toString();



            //Address of the user to update
            Long addressByIndex = userIndex.getAddressByIndex(id);
            outputStream.seek(addressByIndex);
            outputStream.write( ((BytesConvertable) entity).toBytes() );

            outputStream.close();

        } catch (NoSuchFieldException | IllegalAccessException | IOException e) {
            e.printStackTrace();
        }
    }//end save()

    public T byId( String id ){
        RandomAccessFile randomAccessFile = this.getRandomAccessFile();
        UserIndex userIndex = new UserIndex();
        T t = null;
        try {
            int classByteSize = this.persistentClass.getAnnotation(SerialClass.class).size();
            byte[] classBytes = new byte[classByteSize];

            Long address = userIndex.getAddressByIndex(id);
            randomAccessFile.seek(address);
            randomAccessFile.read(classBytes);

            //Get the Constructor(byte[] bytes) from Object T
            Constructor<T> declaredConstructor = this.persistentClass.getDeclaredConstructor(byte[].class);
            //Create a new instance T from the bytes
            t = declaredConstructor.newInstance(classBytes);

        } catch (IOException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return t;
    }

    public long count(){
        long count = 0;
        RandomAccessFile randomAccessFile = this.getRandomAccessFile();

        if(randomAccessFile == null){
            return count;
        }//end if

        int classByteSize = this.persistentClass.getAnnotation(SerialClass.class).size();
        byte[] classBytes = new byte[classByteSize];

        try{

            while( randomAccessFile.read(classBytes) != -1 ){
                ++count;
                classBytes = new byte[classByteSize];
            }//end while

        }catch(EOFException eof) {
            return count;
        }catch(Exception e) {
            e.printStackTrace();
        }finally{
            try {
                randomAccessFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }//end try-catch-finally

        return count;

    }//end count()

    public List<T> findAll(){
        List<T> list = new ArrayList<T>();

        RandomAccessFile randomAccessFile = this.getRandomAccessFile();

        try {

            T t;
            int classByteSize = this.persistentClass.getAnnotation(SerialClass.class).size();
            byte[] classBytes = new byte[classByteSize];

            while( randomAccessFile.read(classBytes) != -1 ){

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
        } finally {
            try {
                randomAccessFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return ( list.isEmpty() ? null : list);
    }//end findAll()


}//end class GenericFileDAO
