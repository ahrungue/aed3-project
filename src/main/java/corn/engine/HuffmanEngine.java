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

    /**
     * <p>
     * Montar a tabela de frequencias de cada caracter, a funçao ira ler o arquivo e retornar uma tabela com o
     * numero de ocorrencias de cada caracter do texto.
     * </p>
     * @param fileName - Nome completo do arquivo.
     * @return Tabela de frequencias da ocorrencia dos caracteres.
     */
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

                //Windows line \r\n, will be considered \n
                if( c =='\r' ){
                    char tmp = ((char) bufferedReader.read());
                    if( tmp == '\n' ){
                        c = '\n';
                    }//end if
                }//end if

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


    /**
     * <p>
     * Montar a arvore de huffman com base na pilha ordenada de nodos {@link corn.utils.HuffmanTree.HuffmanNode}
     * onde cada possui a frequencia de ocorrencia de um caracter no texto lida.
     * </p>
     * @param stack - Pilha Ordenada de nodos HEAD e sempre o menor elemento
     * @return - arvore que representa o codigo a ser trocado pelo caracter.
     */
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

    /**
     * <p>
     * Funçao para a partir da arvore de huffman retornar uma tabela com o caminho de cada caracter na arvore
     * para que a compressao possa ser realizada utilizando como base a tabela retornada.
     * </p>
     * @param huffmanTree - Arvore de huffman
     * @return - tabela com o codigo a ser substituido por cada caracter.
     */
    public Map<Character, String> buildHuffmanCodeTable(HuffmanTree huffmanTree){
        HashMap<Character, String> map = new HashMap<>();
        buildHuffmanTable(map, huffmanTree.getRoot(), "");
        return map;
    }//buildHuffmanCodeTable()

    /**
     * <p>
     * Funçao recursiva para que todos os caminhos da arvore sejam percorridos e a medida que o caminho de cada
     * folha é encontrado esse e salvo na tabela recebida por parametros.
     * </p>
     * @param map - Map com caminho de cada folha
     * @param node - Nodo atual
     * @param path - Caminho acumulado
     */
    private void buildHuffmanTable( Map<Character, String> map, HuffmanTree.HuffmanNode node, String path ){

        if( (node != null) && node.isLeaf() ){
            map.put(node.getChars().charAt(0), path);
        }else if( node != null ){
            buildHuffmanTable(map, node.getLeftNode(),  path + "0" );
            buildHuffmanTable(map, node.getRightNode(), path + "1" );
        }//end if-else

    }//end buildHuffmanTable()

    public void  huffmanCompression( String fileName, Map<Character, String> huffmanCodeMap ){
        //File Object to be readed
        File originalFile = new File(fileName);
        File compressedFile = new File(System.getProperty("user.home") + File.separator + originalFile.getName() + ".compressed");


        //Autocloseable object
        try( BufferedReader bufferedReader = new BufferedReader(new FileReader(originalFile));
                RandomAccessFile randomAccessFile = new RandomAccessFile(compressedFile,"rw") ){

            while( bufferedReader.ready() ){

                //Read one char from the file and check to check the occurrence frequency
                Character c = ((char) bufferedReader.read());

                //Windows line \r\n, will be considered \n
                if( c =='\r' ){
                    char tmp = ((char) bufferedReader.read());
                    if( tmp == '\n' ){
                        c = '\n';
                    }//end if
                }//end if

                String code = huffmanCodeMap.get(c);
                byte[] compressedBytes = new byte[code.length()];
                for( int i = 0; i < code.length(); ++i ){
                    char currentChar = code.charAt(i);

                    compressedBytes[i] = (byte)( currentChar == '0' ? 0 : 1 );

                }//end for(i)

                randomAccessFile.write(compressedBytes);

            }//end while

        }catch( IOException e ){
            e.printStackTrace();
        }//end try-catch

        System.out.printf("%n%nTamanho arquivo original:   %d%n", originalFile.length()/1024);
        System.out.printf("Tamanho arquivo compactado: %d%n", compressedFile.length()/1024);

    }//end huffmanCompression()


}//end class HuffEngine
