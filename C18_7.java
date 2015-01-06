package careercup;

import java.util.ArrayList;

public class Tries {
	
	private static final int R = 128;
	
	public static class Node{
		
		int val;
		Node[] next;
		
		public Node(){
			val = -1;
			next = new Node[R];
		}
		
	}
	
	private Node root;
	
	public void put (String s){
		root = put(root, s, -1, 0);
	}
	
	private Node put (Node x, String s, int value,  int d){
		
		if (x == null){
			x = new Node();
		}
		
		if (d == s.length()){
			x.val = value;
			return x;
		}
		
		char c = s.charAt(d);
		
		x.next[c] = put(x.next[c], s, value, d + 1);
		
		return x;
		
	}
	
	public int get(String s){
		
		Node x = get(root, s, 0);
		if (x == null){
			return -1;
		}
		
		else{
			return x.val;
		}
	}
	
	private Node get(Node x, String s, int d){
		
		if (x == null){
			return null;
		}
		
		if (d == s.length()){
			return x;
		}
		
		char c = s.charAt(d);
		return get(x.next[c], s, d + 1);
		
	}
	
	public ArrayList<Integer> indexWithPrefix(String prefix){
		
		
		
	}
	
	
	private void collect(Node x, ArrayList<Integer> res){
		
		if (x == null){
			return;
		}
		
		if (x.val == true){
			
		}
		
	}

}
