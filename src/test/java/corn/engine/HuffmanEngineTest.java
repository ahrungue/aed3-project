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
        Method methodMakeFrequencyTable = huffmanEngine.getClass().getDeclaredMethod("makeFrequencyTable", String.class);
        methodMakeFrequencyTable.setAccessible(true);

        Object invoke = methodMakeFrequencyTable.invoke(huffmanEngine, getTestFile("/huffman/doc01.txt") );

        assertThat("", invoke, instanceOf(HashMap.class) );

    }//end testMakeFrequencyTable()

    @Test
    public void testMakeStacks(){
        List<Stack<HuffmanTree.HuffmanNode>> stacks = huffmanEngine.execute(getTestFile("/huffman/doc01.txt"));
        assertTrue(!stacks.isEmpty());
    }//testExecute()


    @Test
    public void testBuildTree() throws Exception {
        List<Stack<HuffmanTree.HuffmanNode>> stacks = huffmanEngine.execute(getTestFile("/huffman/doc10.txt"));
        HuffmanTree huffmanTree = huffmanEngine.buildTree(stacks.get(0));

        Map<String, String> map = huffmanTree.buildHuffmanCodeTable();
        System.out.println(map.size());
    }//end testBuildTree()


}//end class HuffmanEngineTest