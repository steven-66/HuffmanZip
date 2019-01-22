import java.util.HashMap;
import java.util.Map;

public class HuffmanTree{
	HuffNode root;
	PriorityQueue<HuffNode> queue;
	Map<Byte,HuffNode> tmp;//tmp table to store treeNode;
	Map<Byte,String> code_table;
	public HuffmanTree() {
		this.root=null;
		queue=new PriorityQueue<HuffNode>(256);
		code_table=new HashMap<>();
		tmp=new HashMap<>();
	}
	public void mergeTree(HuffNode e1, HuffNode e2) {
		HuffNode e3=new HuffNode(e1.weight+e2.weight);
		queue.insert(e3);
		root=e3;
		e1.setParent(e3);
		e2.setParent(e3);
		e3.setLeft(e1);
		e3.setRight(e2);
		if(e1.isLeaf()) {
			tmp.put( e1.getInfo(),e1);
		}
		if(e2.isLeaf()) {
			tmp.put(e2.getInfo(),e2);
		}
	}
	public void getCode(Byte key) {
		HuffNode pointer=tmp.get(key);
		StringBuffer code=new StringBuffer();
		while(pointer!=root) { //traverse the tree from bottom to up to get code
			if(pointer.isLeft()) {
				code.append('0');
			}
			else if(pointer.isRight()) {
				code.append('1');
			}
			pointer=pointer.getParent();
		}
		code=code.reverse();
		code_table.put(key,code.toString());
		return;
	}
}
class HuffNode implements Comparable<HuffNode>{
	private HuffNode left;//left child
	private HuffNode right;//right child
	private HuffNode parent;
	private byte info;//the correspoding character from reading file
	int weight;
	public HuffNode(int long1) {
		this.weight=long1;
	}
	public boolean isLeaf() {

		return this.getLeft()==null && this.getRight()==null;
	}
	public HuffNode getParent() {
		return parent;
	}
	public void setParent(HuffNode parent) {
		this.parent=parent;
	}
	public HuffNode getLeft() {
		return left;
	}
	public void setLeft(HuffNode left) {
		this.left = left;
	}
	public HuffNode getRight() {
		return right;
	}
	public void setRight(HuffNode right) {
		this.right = right;
	}
	public byte getInfo() {
		return info;
	}
	public void setElement(byte info) {
		this.info = info;
	}
	public boolean isLeft() {
		return this==this.getParent().getLeft();
	}
	public boolean isRight() {
		return this==this.getParent().getRight();
	}
	@Override
	public int compareTo(HuffNode arg0) {
		
		return this.weight-arg0.weight;
	}
}