package careercup;


public class LCAForBST {
	
	 public static class TreeNode {
	     int val;
	     TreeNode left;
	     TreeNode right;
	     TreeNode(int x) { val = x; }
	 }
	 
	 public static class Result{
		 
		 boolean found;
		 TreeNode node;
		 
		 public Result(boolean found, TreeNode node){
			 this.found = found;
			 this.node = node;
		 }
		 
	 }
	 
	 public TreeNode getLCAforBST(TreeNode root, TreeNode p, TreeNode q){
		 
		 Result res = LCAHelper(root, p, q);
		 if (res.found){
			 return res.node;
		 }
		 
		 else{
			 return null;
		 }
		 
	 }
	 
	 
	 private Result LCAHelper(TreeNode root, TreeNode p, TreeNode q){
		 
		 if (root == null){
			 return new Result(false, null);
		 }
		 
		 
		 if (root == p && root == q){
			 return new Result(true, root);
		 }
		 
		 
		 if (p.val > q.val){
			 return LCAHelper(root, q, p);
		 }
		 
		 Result left = LCAHelper(root.left, p, q);
		 Result right = LCAHelper(root.right, p, q);
		 
		 int val = root.val; 
		 
		 if (val < p.val){
			 return left;
		 }
		 
		 if (val > q.val){
			 return right;
		 }
		 
		 if (val == p.val){
			 if (right.node != null){
				 return new Result(true, root);
			 }
			 
			 else{
				 return new Result(false, root);
			 }
		 }
		 
		 if (val == q.val){
			 if (left.node != null){
				 return new Result(true, root);
			 }
			 
			 else{
				 return new Result(false, root);
			 }
		 }
		 
		 else{
			 if (left.node != null && right.node != null){
				 return new Result(true, root);
			 }
			 
			 else if (left.node != null){
				 
				 return left;
			 }
			 
			 else{
				 return right;
			 }
		 }
	 }
	 

}
