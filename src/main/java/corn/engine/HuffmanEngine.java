package corn.engine;

import corn.utils.HuffmanTree;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Álvaro Rungue
 * @since 28/05/2015.
 */
public class HuffmanEngine {

    public List<Stack<HuffmanTree.HuffmanNode>> execute( String ... fileNames ){

        List<Stack<HuffmanTree.HuffmanNode>> stackList = new ArrayList<>();

        for( String fileName : fileNames ){
            Map<Character, Integer> characterIntegerMap = makeFrequencyTable(fileName);
            Stack<HuffmanTree.HuffmanNode> huffmanNodesList = buildStackFromMap(characterIntegerMap);
            stackList.add(huffmanNodesList);
        }//end for(iterator)

        return stackList;

    }//end execute()

    private Map<Character, Integer> makeFrequencyTable(String fileName){

        //Create the hash table that will be inserted the character frequency
        HashMap<Character, Integer> characterFrequencyMap = new HashMap<Character, Integer>();

        //File Object to be readed
        File file = new File(fileName);

        //Autocloseable object
        try( BufferedReader bufferedReader = new BufferedReader( new FileReader(file) ) ){

            while( bufferedReader.ready() ){

                //Read one char from the file and check to check the occurrence frequency
                Character c = ((char) bufferedReader.read());

                //case when the character exists in the map
                if( characterFrequencyMap.get(c) != null ){
                    Integer frequency = characterFrequencyMap.get(c);
                    characterFrequencyMap.remove(c);
                    characterFrequencyMap.put(c, ++frequency);
                //otherwise just put the new character in map
                }else{
                    characterFrequencyMap.put(c, 1);
                }//end if

            }//end while

        }catch( EOFException eof ) {
            eof.printStackTrace();
            return characterFrequencyMap;
        }catch( IOException e ){
            e.printStackTrace();
        }//end try-catch

        return characterFrequencyMap;

    }//end makeFrequencyTable()


    /**
     * <p>
     * Fun&ccedil;&atilde;o para ler um {@link Map} com a tabela de frequencia de caracteres para criar a
     * arvore de huffman.
     * </p>
     * @param map - tabela com as frequencias que cada caracter ocorreu em um texto
     * @return {@link Stack<HuffmanTree.HuffmanNode>}
     */
    private Stack<HuffmanTree.HuffmanNode> buildStackFromMap( Map<Character, Integer> map ){

        //Huffman nodes list be added Stack
        List<HuffmanTree.HuffmanNode> huffmanNodes = new ArrayList<>();

        Stack<HuffmanTree.HuffmanNode> collect = map.entrySet().stream()
                //Mapear os valores do Set.Entry do hashmap para o tipo HuffmanNode
                .map(entry -> new HuffmanTree.HuffmanNode(entry.getValue(), entry.getKey().toString() ) )
                //Ordenar a stream pelo valor de frequencia dos nodos
                .sorted(Comparator.comparing(HuffmanTree.HuffmanNode::getFrequency).reversed())
                .collect(Collectors.toCollection(Stack::new));

        return collect;

    }//end getNodeList()


    public HuffmanTree buildTree( Stack<HuffmanTree.HuffmanNode> stack ){

        HuffmanTree huffmanTree = null;

        while( stack.size() > 1 ){

            //Get two smallers node
            HuffmanTree.HuffmanNode leftNode  = stack.pop();
            HuffmanTree.HuffmanNode rightNode = stack.pop();

            //build a new tree
            huffmanTree = new HuffmanTree(leftNode,rightNode);

            stack.push( huffmanTree.getRoot() );

            stack.sort(Comparator.comparing(HuffmanTree.HuffmanNode::getFrequency).reversed());

        }//end while

        return huffmanTree;

    }//end buildTree()

}//end class HuffEngine
