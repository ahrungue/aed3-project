package corn.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rungue
 * @since 05/30/15
 */
public class HuffmanTree {

    public static class HuffmanNode{

        private int frequency;

        private String chars;

        private HuffmanNode leftNode;

        private HuffmanNode rightNode;

        public HuffmanNode(int frequency, String chars){
            this(frequency, chars, null, null);
        }//end HuffmanNode

        public HuffmanNode(int frequency, String chars, HuffmanNode leftNode, HuffmanNode rightNode) {
            this.frequency = frequency;
            this.chars = chars;
            this.leftNode = leftNode;
            this.rightNode = rightNode;
        }//end HuffmanNode()

        public boolean isLeaf(){
            return ( (leftNode == null) && (rightNode == null) );
        }//end isLeaf()

        public int getFrequency() {
            return frequency;
        }

        public void setFrequency(int frequency) {
            this.frequency = frequency;
        }

        public String getChars() {
            return chars;
        }

        public void setChars(String chars) {
            this.chars = chars;
        }

        public HuffmanNode getLeftNode() {
            return leftNode;
        }

        public void setLeftNode(HuffmanNode leftNode) {
            this.leftNode = leftNode;
        }

        public HuffmanNode getRightNode() {
            return rightNode;
        }

        public void setRightNode(HuffmanNode rightNode) {
            this.rightNode = rightNode;
        }
    }//end class HuffmanNode

    private HuffmanNode root;

    public HuffmanTree( HuffmanNode leftNode, HuffmanNode rightNode ){
        this.root = new HuffmanNode(
                leftNode.getFrequency() + rightNode.getFrequency(),
                leftNode.getChars() + rightNode.getChars(),
                leftNode,
                rightNode
        );
    }//end HuffmanNode()

    public Map<String, String> buildHuffmanCodeTable(){

        HashMap<String, String> map = new HashMap<String, String>();
        buildHuffmanTable(map, this.getRoot(), "");
        return map;
    }//buildHuffmanCodeTable()

    private void buildHuffmanTable( Map<String, String> map, HuffmanNode node, String path ){

        if( (node != null) && node.isLeaf() ){
            map.put(node.getChars(), path);
        }else if( node != null ){
           buildHuffmanTable(map, node.getLeftNode(),  path + "0" );
           buildHuffmanTable(map, node.getRightNode(), path + "1" );
        }

    }//end buildHuffmanTable()

    public HuffmanNode getRoot() {
        return root;
    }

    public void setRoot(HuffmanNode root) {
        this.root = root;
    }
}//end class HuffmanTree
