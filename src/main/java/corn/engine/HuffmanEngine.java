package corn.engine;

import java.io.*;
import java.util.*;

/**
 * Created by LibraLynx on 28/05/2015.
 */


public class HuffmanEngine {

    static class HuffmanNode{
        private int value;
        private String chars;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getChars() {
            return chars;
        }

        public void setChars(String chars) {
            this.chars = chars;
        }
    }

    static class HuffmanTree{

    }

    private HashMap<Character, Integer> hashMap;

    public HuffmanEngine(String fileName) {
        this.hashMap = new HashMap<>();
    }

    public void init(){

    }

    public void makeFrequencyTable(String fileName){
        File file = new File(fileName);
        try( RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r") ){

            while( true ){
                char c = randomAccessFile.readChar();
                this.insertChar(c);
            }

        } catch (EOFException eof) {
            eof.printStackTrace();
        } catch ( IOException e ){
            e.printStackTrace();
        }
    }

    public void insertChar( Character c ){
        if( this.hashMap.get(c) != null ){
            Integer frequency = this.hashMap.get(c);
            this.hashMap.remove(c);
            this.hashMap.put(c, ++frequency);
        }else{
            this.hashMap.put(c, 1);
        }
    }

    public List<HuffmanNode> getNodesList(){
        List<HuffmanNode> huffmanNodes = new ArrayList<>();
        Set<Map.Entry<Character, Integer>> entries = this.hashMap.entrySet();
        new ArrayList<HuffmanNode>(){{
            for (Map.Entry<Character, Integer> entry : entries) {
                add(new HuffmanNode(){{
                    setValue(entry.getValue());
                    setChars(entry.getKey().toString());
                }});
            }
        }};


        return huffmanNodes;
    }


}//end class HuffEngine
