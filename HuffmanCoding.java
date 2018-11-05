package code.src;

import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * A new instance of HuffmanCoding is created for every run. The constructor is
 * passed the full text to be encoded or decoded, so this is a good place to
 * construct the tree. You should store this tree in a field and then use it in
 * the encode and decode methods.
 */
public class HuffmanCoding {

	private HashMap<Character, Integer> frequency;
	private HashMap<Character, String> unCodedText = new HashMap<>();
	private HashMap<String, Character> codedText = new HashMap<>();
	private HuffNode root;

	/**
	 * This would be a good place to compute and store the tree.
	 */
	public HuffmanCoding(String text) {
		//Get frequency of chars
		frequency = getFrequency(text.toCharArray());
		//add chars to queue
		PriorityQueue<HuffNode> queue = new PriorityQueue<HuffNode>();
		for (Character c : frequency.keySet()) {
			queue.offer(new HuffNode(c, frequency.get(c), null, null));
		}
		//check all nodes and build tree
		while (queue.size() > 1) {
			HuffNode leftNode = queue.poll();
			HuffNode rightNode = queue.poll();
			int frequency = leftNode.getFrequency() + rightNode.getFrequency();
			HuffNode node = new HuffNode('\0', frequency , leftNode, rightNode);
			queue.offer(node);
		}
		// get root and build from root assigning 0 , 1 to each side of the nodes
		root = queue.poll();
		traverse(root, new String());

		for (char c : unCodedText.keySet()) {
			System.out.println("{ " + c + " encoded : " + unCodedText.get(c)+ " }");
		}

	}

	/**
	 * Gets the frequency of each character in the text.
	 * @param charc
	 * @return
	 */
	private HashMap<Character, Integer> getFrequency(char[] charc) {
		HashMap<Character, Integer> frequency = new HashMap<Character, Integer>();
		for (char c : charc) {
			if (frequency.containsKey(c)) {
				frequency.put(c,frequency.get(c) + 1);
			} else {
				frequency.put(c, 1);
			}
		}
		return frequency;
	}

	/**
	 * Goes through the the nodes adding 0 or 1 from root else adds on.
	 * @param root
	 * @param string
	 *
	 */
	private void traverse(HuffNode node, String string) {
		if (node.getParent() != '\0') {
			codedText.put(string, node.getParent());
			unCodedText.put(node.getParent(), string);
		} else {
			traverse(node.getRightNode(), string + '1');
			traverse(node.getLeftNode(), string + '0');

		}
	}

	/**
	 * Take an input string, text, and encode it with the stored tree. Should
	 * return the encoded text as a binary string, that is, a string containing
	 * only 1 and 0.
	 */
	public String encode(String text) {
		StringBuilder encoded = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			encoded.append(unCodedText.get(text.charAt(i)));
		}
		return encoded.toString();
	}

	/**
	 * Take encoded input as a binary string, decode it using the stored tree,
	 * and return the  text as a text string.
	 */
	public String decode(String encoded) {
		StringBuilder decoded = new StringBuilder();
		HuffNode node = root;

		for (char c : encoded.toCharArray()) {
			// left node 
			if (c == '0') {
				node = node.getLeftNode();
				//right node 
			} else {				
				node = node.getRightNode();
			}			
			if (node.getParent() != '\0') {
				decoded.append(node.getParent());
				node = root;
			}		
		}
		return decoded.toString();
	}

	/**
	 * The getInformation method is here for your convenience, you don't need to
	 * fill it in if you don't wan to. It is called on every run and its return
	 * value is displayed on-screen. You could use this, for example, to print
	 * out the encoding tree.
	 */
	public String getInformation() {
		return "";
	}

}
