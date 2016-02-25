package lc_dfs;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

public class RemoveInvalidParentheses {
	
    public List<String> removeInvalidParentheses(String s) {
    	
    	List<String> res = new ArrayList<String>();
    	
    	if (s == null || s.length() == 0){
    		return res;
    	}
    	
    	int left = 0, right = 0;
    	
    	StringBuilder sb = new StringBuilder();
    	for (int i = 0; i < s.length(); i++){
    		
    		if (s.charAt(i) == '('){
    			left++;
    		}
    		
    		else if (s.charAt(i) == ')'){
    			right++;
    		}
    		
    	}
    	
    	HashSet<String> visited = new HashSet<String>();
    	int b = Math.min(left, right);
    	removeHelper(s, 0, b, b, visited, sb, res);
    	
        return res;
    }
    
    private void removeHelper(String s, int index, int left, int right, HashSet<String> visited, StringBuilder sb, List<String> res){
    	
    	if (visited.contains(s.substring(index, s.length()))){
    		return;
    	}
    	
    	if (s.length() == index){
    		if (left == 0 && right == 0){
    			res.add(sb.toString());
    		}
    		return;
    	}
    	
    	if (left > right){
    		return;
    	}
    	
    	char c = s.charAt(index);
    	if (c != '(' && c != ')'){
    		sb.append(c);
    		removeHelper(s, index + 1, left, right, visited, sb, res);
    		sb.setLength(index + 1);
    	}
    	
    	else{
    		
    		if (c == '('){
    			if (left > 0){
    				
    				sb.append(c);
    				removeHelper(s, index + 1, left - 1, right, visited, sb, res);
    				sb.setLength(index + 1);
    				
    				removeHelper(s, index + 1, left, right, visited, sb, res);
    				
    			}
    			
    		}
    		
    		else if (c == ')'){
    			
    			if (right > 0){
    				
    				sb.append(c);
    				removeHelper(s, index + 1, left, right - 1, visited, sb, res);
    				sb.setLength(index + 1);
    				
    				removeHelper(s, index + 1, left, right, visited, sb, res);
    				
    			}
    			
    		}
    		
    	}
    	
    	
    }

}
