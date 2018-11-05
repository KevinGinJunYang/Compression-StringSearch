package code.src;

public class HuffNode implements Comparable<Object> {
	 private HuffNode leftNode;
	 private HuffNode rightNode;
	 private char parent;
	 private int frequency;


	/**
   * @return the leftNode
   */
  public HuffNode getLeftNode() {
    return leftNode;
  }


  /**
   * @param leftNode the leftNode to set
   */
  public void setLeftNode(HuffNode leftNode) {
    this.leftNode = leftNode;
  }


  /**
   * @return the rightNode
   */
  public HuffNode getRightNode() {
    return rightNode;
  }


  /**
   * @param rightNode the rightNode to set
   */
  public void setRightNode(HuffNode rightNode) {
    this.rightNode = rightNode;
  }


  /**
   * @return the parent
   */
  public char getParent() {
    return parent;
  }


  /**
   * @param parent the parent to set
   */
  public void setParent(char parent) {
    this.parent = parent;
  }


  /**
   * @return the frequency
   */
  public int getFrequency() {
    return frequency;
  }


  /**
   * @param frequency the frequency to set
   */
  public void setFrequency(int frequency) {
    this.frequency = frequency;
  }


  public HuffNode(char parent, int frequency, HuffNode leftNode, HuffNode rightNode) {
		this.parent = parent;
		this.frequency = frequency;
		this.leftNode = leftNode;
		this.rightNode = rightNode;
	}


	@Override
	public int compareTo(Object o) {
		return frequency -  ((HuffNode)o).frequency;
	}


	public static void main(String[] args) {
		HuffmanCoding test = new HuffmanCoding("RA RARARARA SSSRARASA RARAaa");
		String encoded = test.encode("RA RARARARA SSSRARASA RARAaa");
		System.out.println(encoded);
		String decoded = test.decode(encoded);
		System.out.println(decoded);
	}



}
