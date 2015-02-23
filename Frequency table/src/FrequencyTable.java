
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class FrequencyTable {
	
	private static class Node{
		stringKey key;
		Object val;
		Node left, right;
		Node(stringKey k, Object v){
			key = k;   //word string
			val = v;   //frequency number
			left = null; //left child
			right = null; //right child
			}
		}
	private Node Root;
	public static int Comparisons;
	
	public FrequencyTable(){Root = null;}
	
	//insert new word with count =1 or increment a word already in the tree
	public void Click(String Word){
		//case for word not in the tree.
		if (Count(Word) == 0 ){
			stringKey Wordtoadd = new stringKey(Word);
			Node Nodetoadd = new Node(Wordtoadd, 1);
			Add(Nodetoadd);
			}
		else{
			int Newvalue = Count(Word);
			Newvalue++;
			stringKey Wordtoadd = new stringKey(Word);
			Node Nodetoadd = new Node(Wordtoadd, Newvalue);
			Add(Nodetoadd);
			}
		}
	
	//returns the frequency count of the word.
	public int Count(String Word){
		stringKey Wordtocount = new stringKey(Word);
		Object count = Counthelp(Wordtocount, Root);
		return (Integer) count;
		}
	
	private static Object Counthelp(stringKey k, Node tree){
		if (tree == null){
			return 0;
			}
		if (k.equals(tree.key.key)){
			Comparisons++;
			return tree.val;
			}
		if(k.Less(tree.key.key)){
			Comparisons++;
			return Counthelp(k, tree.left);
			}
		return Counthelp(k, tree.right);
		}
	
	//two possible add methods. comment out the one you do not want to use.
	public void Add(Node New){
		Root = Addatroot(New, Root);
		//Root = Addatleaf(New, Root);
		}
	
	private static Node Addatleaf(Node New, Node tree){
		//empty case
		if (tree == null){
			return New;
			}
		//found same key. update the value.
		if (New.key.equals(tree.key.key)){
			Comparisons++;
			tree.key = New.key;
			tree.val = New.val;
			return tree;
			}
		//move to left child
		if (New.key.Less(tree.key.key)){
			Comparisons++;
			tree.left = Addatleaf(New,tree.left);
			}
		//move to right child
		else{
			tree.right = Addatleaf(New, tree.right);
			}
		return tree;
		}
	
	private static Node Addatroot(Node New, Node tree){
		//empty case
		if (tree == null){
			return New;
			}
		//already in the tree case
		if(New.key.equals(tree.key.key)){
			Comparisons++;
			tree.key = New.key;
			tree.val = New.val;
			return tree; 
			}
		//adding left case
		if(New.key.Less(tree.key.key)){
			Comparisons++;
			tree.left = Addatroot(New, tree.left);
			tree = RotateRight(tree);
			}
		//adding right case
		else{
			tree.right = Addatroot(New, tree.right);
			tree = RotateLeft(tree);
			}
		return tree;
		}
	
	private static Node RotateLeft(Node tree){
		Node Root = tree.right;
		tree.right = Root.left;
		Root.left = tree;
		return Root;
		}
	
	private static Node RotateRight(Node tree){
		Node Root = tree.left;
		tree.left = Root.right;
		Root.right = tree;
		return Root;
		}

	private static class stringKey implements Key{
		public String key;
		
		public stringKey(String W){
			key = W.toString();
			}
		
		public boolean equals(String key) {
			boolean equal = false;
			if((this.key.compareToIgnoreCase(key)==0)){
				equal = true;
				}
			return equal;
			}

		public boolean Less(String key) {
			boolean equal = false;
			if((this.key.compareToIgnoreCase(key)) < 0){
				equal = true;
				}
			return equal;
			}
		}
	
	private static class FTIterator implements Iterator{
		//followed along with the implementation in class for in-order traversal//
		Stack<Node> stack;
		//constructor. moves to the farthest left node.
		public FTIterator(Node node){
			stack = new Stack<Node>();
			stack.push(node);
			while(node.left != null){
				node = node.left;
				stack.push(node);
				}
			}
		
		public boolean hasNext() {
			//check to see if the stack is empty
			return !stack.empty();
			}
	
		public Object next() {
			Node node = stack.pop();
			Node temp = node.right;
			while (temp != null){
				stack.push(temp);
				temp = temp.left;
				}	
			return node;
			}
		//not using the remove method.
		public void remove() throws UnsupportedOperationException {}
		
		}
	
		//method that starts an iterator at the root of the tree.
		public Iterator iterator(){
			return new FTIterator(Root);
			}
	
	public static void main(String [] args)throws FileNotFoundException{
		FrequencyTable FT = new FrequencyTable();
		Scanner S = new Scanner(new FileInputStream(args[0]));
		while(S.hasNext()){
			FT.Click(new stringKey(S.next()).key);
			}
		System.out.println("Number of Comparisons: " + Comparisons);
		Iterator<Node> InOrder = FT.iterator();
		while (InOrder.hasNext()){
			Node n = InOrder.next();
			String Word = n.key.key;
			Object frequency = n.val;
			System.out.println(Word + " " + frequency);
			}
		}
	
	
	
	}