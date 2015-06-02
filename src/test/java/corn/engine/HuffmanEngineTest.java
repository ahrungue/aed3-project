package corn.engine;

import corn.utils.HuffmanTree;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Stack;

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

        Map<Character, Integer> frequencyTable = huffmanEngine.makeFrequencyTable(fileName);
        assertTrue("Tabela nao pode estar vazia.", !frequencyTable.isEmpty());

        System.out.printf("%n%-12s%n", "***** Frequency Table *****");
        for( Map.Entry<Character, Integer> entry : frequencyTable.entrySet() ){
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
    public void testCompressOneFileTree() throws Exception {

        String[] fileNamesToTest = new String[]{
            getTestFile("/huffman/doc01.txt"),
            getTestFile("/huffman/doc02.txt"),
            getTestFile("/huffman/doc03.txt"),
            getTestFile("/huffman/doc04.txt"),
            getTestFile("/huffman/doc05.txt"),
            getTestFile("/huffman/doc06.txt"),
            getTestFile("/huffman/doc07.txt"),
            getTestFile("/huffman/doc08.txt"),
            getTestFile("/huffman/doc09.txt"),
            getTestFile("/huffman/doc10.txt")
        };

        for( String fileNameToTest : fileNamesToTest ){

            List<Stack<HuffmanTree.HuffmanNode>> stacks = huffmanEngine.execute(fileNameToTest);
            HuffmanTree huffmanTree = huffmanEngine.buildTree(stacks.get(0));
            Map<Character, String> map  = huffmanEngine.buildHuffmanCodeTable(huffmanTree);
            Map<String, Long> resultMap = huffmanEngine.huffmanCompression(fileNameToTest, map);

            System.out.printf("%n**************************************************************************************%n");
            System.out.println("Compression using for each file the related tree");
            System.out.printf("%-16s%n", new File(fileNameToTest).getName());
            System.out.printf("%-16s\t%d%n", "Original Size",   resultMap.get("originalSize"));
            System.out.printf("%-16s\t%d%n", "Compressed Size", resultMap.get("compressedSize"));
            System.out.printf("%-16s\t%.3f%n", "Percentage", (double) resultMap.get("compressedSize") / resultMap.get("originalSize"));
            System.out.printf("**************************************************************************************%n");

        }//end for(iterator)

    }//end testBuildTree()

    @Test
    public void testCompressAllFileTree() throws Exception {

        Map<Character, Integer> frequencyTable = huffmanEngine.makeFrequencyTable(
                getTestFile("/huffman/doc01.txt"),
                getTestFile("/huffman/doc02.txt"),
                getTestFile("/huffman/doc03.txt"),
                getTestFile("/huffman/doc04.txt"),
                getTestFile("/huffman/doc05.txt"),
                getTestFile("/huffman/doc06.txt"),
                getTestFile("/huffman/doc07.txt"),
                getTestFile("/huffman/doc08.txt"),
                getTestFile("/huffman/doc09.txt"),
                getTestFile("/huffman/doc10.txt")
        );

        Stack<HuffmanTree.HuffmanNode> stack = huffmanEngine.buildStackFromFrequencyTable(frequencyTable);
        HuffmanTree huffmanTree = huffmanEngine.buildTree(stack);
        Map<Character, String> map  = huffmanEngine.buildHuffmanCodeTable(huffmanTree);

        String[] fileNamesToTest = new String[]{
                getTestFile("/huffman/doc01.txt"),
                getTestFile("/huffman/doc02.txt"),
                getTestFile("/huffman/doc03.txt"),
                getTestFile("/huffman/doc04.txt"),
                getTestFile("/huffman/doc05.txt"),
                getTestFile("/huffman/doc06.txt"),
                getTestFile("/huffman/doc07.txt"),
                getTestFile("/huffman/doc08.txt"),
                getTestFile("/huffman/doc09.txt"),
                getTestFile("/huffman/doc10.txt")
        };

        //Compress all files with the same tree
        for( String fileNameToTest : fileNamesToTest ){
            Map<String, Long> resultMap = huffmanEngine.huffmanCompression(fileNameToTest, map);

            System.out.printf("%n**************************************************************************************%n");
            System.out.println("Compression using a one tree related to the frequency of all files");
            System.out.printf("%-16s%n",new File(fileNameToTest).getName() );
            System.out.printf("%-16s\t%d%n", "Original Size",   resultMap.get("originalSize"));
            System.out.printf("%-16s\t%d%n", "Compressed Size", resultMap.get("compressedSize"));
            System.out.printf("%-16s\t%.3f%n", "Percentage", (double) resultMap.get("compressedSize") / resultMap.get("originalSize"));
            System.out.printf("**************************************************************************************%n");

        }//end for(iterator)

    }//end testBuildTree()


}//end class HuffmanEngineTest