package google;

import java.util.ArrayList;
import java.util.LinkedList;

public class MinimumSquareNumber {
	
	public ArrayList<Integer> getMinSquaredSum(int n){
		
		if (n <= 0){
			return null;
		}
		
		int N = (int)Math.floor(Math.sqrt(n));
		
		LinkedList<Integer> queue = new LinkedList<Integer>();
		queue.add(0);
		
		ArrayList<Integer>[] res= (ArrayList<Integer>[])new ArrayList[n + 1];
		res[0] = new ArrayList<Integer>();
		
		while (!queue.isEmpty()){
			
			int num = queue.poll();
			
			for (int i = 1; i <= N; i++){
				
				int next = num + i * i;
				
				if (next == n){
					res[num].add(i);
					return res[num];
				}
				
				if (next > n){
					break;
				}
				
				
				if (next < n){
					
					res[next] = new ArrayList<Integer>(res[num]);
					res[next].add(i);
					queue.offer(i);
				}
			}
			
		}
		
		return null;
	}
	
	
	public ArrayList<Integer> getMinSquareSum(int n){
		
		ArrayList<Integer> res = new ArrayList<Integer>();
		
		if (n <= 0){
			return res;
		}
		
		int[] dp = new int[n + 1];
		int[] pv = new int[n + 1];
		
		//int N = (int)Math.floor(Math.sqrt(n));
		
		for (int i = 1; i < n; i++){
			
			dp[i] = Integer.MAX_VALUE;
			
			for (int j = 1; j * j <= i; j++){
				
				if (dp[i - j] != Integer.MAX_VALUE){
					
					if (dp[i - j] + 1 < dp[i]){
						dp[i] = dp[i - j] + 1;
						pv[i] = i - j;
					}
					
				}
				
			}
		}
		
		if (dp[n] == Integer.MAX_VALUE){
			return res;
		}
		
		int q = pv[n];
		int p = n;
		while (q != 0){
			
			res.add(p - q);
			q = pv[q];
			p = q;
			
		}
		
		res.add(p - q);
		
		return res;
		
	}

}
