package careercup;

import java.util.ArrayList;

public class BinaryTreeFindSum {
	
	 public static class TreeNode {
	     int val;
	     TreeNode left;
	     TreeNode right;
	     TreeNode(int x) { val = x; }
	 }
	 
	 public ArrayList<ArrayList<Integer>> findSum(TreeNode root, int sum){
		 
		 ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
		 
		 if (root == null){
			 return res;
		 }
		 int N = getDepth(root);
		 int[] path = new int[N];
		 findHelper(root, sum, 0, path, res);
		 
		 return res;
	 }
	 
	 private int getDepth(TreeNode root){
		 
		 if (root == null){
			 return 0;
		 }
		 
		 return Math.max(getDepth(root.left), getDepth(root.right)) + 1;
		 
	 }
	 
	 private void findHelper(TreeNode node, int sum, int level, int[] path, ArrayList<ArrayList<Integer>> res){
		 
		 if (node == null){
			 return;
		 }
		 
		 path[level] = node.val;
		 int cnt = 0;
		 
		 ArrayList<Integer> item = new ArrayList<Integer>();
		 for (int i = level; i >=0; i--){
			 cnt += path[i];
			 item.add(path[i]);
			 if (cnt == sum){
				 res.add(new ArrayLisy<Integer>(item));
			 }
		 }
		 
		 findHelper(node.left, sum, level + 1, path, res);
		 findHelper(node.right, sum, level + 1, path, res);
		 
		 path[level] = Integer.MIN_VALUE;
	 }

}
