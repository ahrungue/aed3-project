package corn.engine;

import corn.utils.HuffmanTree;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author rungue
 * @since 05/30/15
 */
public class HuffmanEngineTest {

    private HuffmanEngine huffmanEngine;

    @Before
    public void initHuffmanEngine(){
        this.huffmanEngine = new HuffmanEngine();
    }//end initHuffmanEngine()

    public String getTestFile(String fileName){
        return this.getClass().getResource(fileName).getFile();
    }//end getTestFile()

    @Test
    public void testMakeFrequencyTable() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        String fileName = getTestFile("/huffman/doc11.txt");

        Method methodMakeFrequencyTable = huffmanEngine.getClass().getDeclaredMethod("makeFrequencyTable", String.class);
        methodMakeFrequencyTable.setAccessible(true);

        Object invoke = methodMakeFrequencyTable.invoke(huffmanEngine, fileName );
        assertThat("Objeto de ser do Tipo HashMap", invoke, instanceOf(HashMap.class) );


        System.out.printf("%n%-12s%n", "***** Frequency Table *****");
        for( Map.Entry<Character, Integer> entry : ((HashMap<Character, Integer>) invoke).entrySet() ){
            System.out.printf("%3s:\t%-9s%n", (entry.getKey() == '\n' ? "\\n" : entry.getKey()   ), entry.getValue());
        }//end for(iterator)

    }//end testMakeFrequencyTable()

    @Test
    public void testMakeStacks(){
        String fileName = getTestFile("/huffman/doc11.txt");
        List<Stack<HuffmanTree.HuffmanNode>> stacks = huffmanEngine.execute( fileName );
        assertTrue("Stacks must not be empty.", !stacks.isEmpty());
    }//testExecute()


    @Test
    public void testHuffmanCode() throws Exception {
        String fileName = getTestFile("/huffman/doc11.txt");
        List<Stack<HuffmanTree.HuffmanNode>> stacks = huffmanEngine.execute(fileName);
        HuffmanTree huffmanTree = huffmanEngine.buildTree(stacks.get(0));
        Map<Character, String> map = huffmanEngine.buildHuffmanCodeTable(huffmanTree);
        assertTrue("Map must not be empty", !map.isEmpty());

        System.out.printf("%n%-12s%n", "***** Huffman Code Table *****");
        for( Map.Entry<Character, String> entry : map.entrySet( ) ){
            System.out.printf("%3s:\t%-9s%n", (entry.getKey() == '\n' ? "\\n" : entry.getKey()), entry.getValue());
        }//end for(iterator)
    }//end testBuildTree()

    @Test
    public void testCompress() throws Exception {

        String fileNameToTest = getTestFile("/huffman/doc11.txt");
        List<Stack<HuffmanTree.HuffmanNode>> stacks = huffmanEngine.execute(fileNameToTest);
        HuffmanTree huffmanTree = huffmanEngine.buildTree(stacks.get(0));

        Map<Character, String> map = huffmanEngine.buildHuffmanCodeTable(huffmanTree);
        huffmanEngine.huffmanCompression(fileNameToTest, map);
    }//end testBuildTree()


}//end class HuffmanEngineTest