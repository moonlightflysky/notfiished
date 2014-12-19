package chapter11;

public class SearchAsendingOrderMatrix {
	
	public static class Cord{
		
		int row;
		int col;
		
		public Cord(int row, int col){
			this.row = row;
			this.col = col;
		}
		
		public boolean inBound(int[][] matrix){
			
			int M = matrix.length;
			int N = matrix[0].length;
			
			return (row >= 0 && row < M && col >= 0 && col < N);
			
		}
		
		public boolean isBefore(Cord q){
			
			return (row <= q.row) && (col <= q.col);
			
		}
	}
	
	private Cord getMidCord(Cord lo, Cord hi){
		
		int midRow = lo.row + (hi.row - lo.row)/2;
		int midCol = lo.col + (hi.col - lo.col)/2;
		
		return new Cord(midRow, midCol);
		
	}
	
	public Cord findElement(int[][] matrix, int target){
		
		if (matrix == null || matrix.length == 0){
			return null;
		}
		
		int M = matrix.length;
		int N = matrix[0].length;
		
		Cord leftTop = new Cord(0, 0);
		Cord rightBottom = new Cord(M - 1, N - 1);
		
		return findHelper(matrix, target, leftTop, rightBottom);
		
	}
	
	private Cord findHelper(int[][] matrix, int target, Cord leftTop, Cord rightBottom){
		
		if (!leftTop.inBound(matrix) || !rightBottom.inBound(matrix)){
			return null;
		}
		
		if (!leftTop.isBefore(rightBottom)){
			return null;
		}
		
		int diagDist = Math.min(rightBottom.row - leftTop.row, rightBottom.col - leftTop.col);
		
		Cord start = new Cord(leftTop.row, leftTop.col);
		Cord end = new Cord(start.row + diagDist, start.col + diagDist);
		
		
		while (start.isBefore(end)){
			
			Cord mid = getMidCord(start, end);
			
			if (matrix[mid.row][mid.col] == target){
				return mid;
			}
			
			else if (matrix[mid.row][mid.col] < target){
				start.row = mid.row + 1;
				start.col = mid.col + 1;
			}
			
			else{
				end.row = mid.row - 1;
				end.row = mid.col - 1;
			}
			
		}
		
		Cord pivot = end;
		
		Cord lowerLeftTopLeft = new Cord(pivot.row, leftTop.col);
		Cord lowerLeftBottomRight = new Cord(rightBottom.row, pivot.col - 1);
		
		Cord upperRightTopLeft = new Cord(leftTop.row, pivot.col);
		Cord upperRightBottomRight = new Cord(pivot.row + 1, rightBottom.col);
		
		Cord lowerLeftRes = findHelper(matrix, target, lowerLeftTopLeft, lowerLeftBottomRight);
		if (lowerLeftRes != null){
			return lowerLeftRes;
		}
		
		else{
			Cord upperRightRes = findHelper(matrix, target, upperRightTopLeft, upperRightBottomRight);
			return upperRightRes;
		}
	}
	
	
	public static void main (String[] argv){
		int[][] matrix = {{15,20,70,85},{20,35,85,95},{30,55,95,105},{40,80,120,120}};
		SearchAsendingOrderMatrix test = new SearchAsendingOrderMatrix ();
		Cord res = test.findElement(matrix, 80);
		if (res != null){
			System.out.println(res.row);
			System.out.println(res.col);
		}
		
		
	}

}
