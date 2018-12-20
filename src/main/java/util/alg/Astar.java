package util.alg;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Astar {
	
	private final static int NORMAL = 1000 ; 
	private final static int CROSS = 1814;
	private final static char EMPRY_CHAR = '0' ; 
    private static int[] dx = new int[]{-1,-1,-1,0,0,1,1,1} ; 	
	private static int[] dy = new int[]{-1,0,1,-1,1,-1,0,1} ; 
	private static int[] dis = new int[]{CROSS,NORMAL,CROSS,NORMAL,NORMAL,CROSS,NORMAL,CROSS} ; 
	
	private Queue<Pair> que  ; 
	
	private boolean[][] openList ; 
	
	private boolean[][] closeList ;
	
	private char[][] map ; 
	
	private Pair[][] pairs ; 
	
	private int n ; 
	
	private int m ; 
	
	private int ex ; 
	
	private int ey ; 
	
	public Astar(char[][] map,int sx,int sy,int ex,int ey){
		this.n = map.length ; 
		this.m = map[0].length ; 
		this.ex = ex ; 
		this.ey = ey ; 
		this.map = map ; 
		this.que = new PriorityQueue<>() ;
		this.openList = new boolean[n][m] ; 
		this.closeList = new boolean[n][m] ; 
		this.pairs = new Pair[n][m] ; 
		search(sx,sy,ex,ey) ; 
	}
	
	public void search(int sx,int sy,int ex,int ey){
		Pair st = new Pair(sx,sy) ;
		int sh = dis(sx,sy,ex,ey) ; 
		st.f = sh ;
		que.add(st) ; 
		openList[sx][sy] = true ; 
		pairs[sx][sy] = st ; 
		while(!que.isEmpty()){
			Pair now = que.poll() ; 
			if(now.x == ex && now.y == ey){
				break ; 
			}
			openList[now.x][now.y] = false; 
			closeList[now.x][now.y] = true ; 
//			System.out.println(now.x + " " + now.y + " " + now.f + " " + now.g);
			for(int i = 0;i < 8;i++){
				int nx = now.x + dx[i] ; 
				int ny = now.y + dy[i] ;
				if(!pass(nx,ny)){
					continue ; 
				}
				int dd = dis[i] ; 
				int h = dis(nx,ny,ex,ey) ; 
				Pair npair = pairs[now.x][now.y] ; 
				int newf = npair.g + dd + h ; 
				if(!openList[nx][ny]){
					Pair pair = new Pair(nx,ny) ; 
					pair.f = newf ;
					pair.g = npair.g + dd ; 
					pair.parentPair = npair ; 
					pairs[nx][ny] = pair ;
					que.add(pair) ; 
					openList[nx][ny] = true ; 
				}else{
				    if(newf < pairs[nx][ny].f){
				    	Pair pair = new Pair(nx,ny) ; 
						pair.f = newf ; 
						pair.g = npair.g + dd ; 
						pair.parentPair = npair ; 
						que.remove(pairs[nx][ny]) ; 
						que.add(pair) ; 
						pairs[nx][ny] = pair ;
				    }
				}
			}
		}
	}
	
	private List<Pair> getRoadPair(){
		Pair ePair = this.pairs[ex][ey] ; 
		List<Pair> road = new ArrayList<>() ; 
		while(ePair != null){
			road.add(ePair) ; 
			ePair = ePair.parentPair ; 
		}
		return road ; 
	}
	
	public void printXyRoad(){
		System.out.println("==============printXyRoad===============");
		List<Pair> road = getRoadPair() ; 
		for(int i = road.size()-1;i >= 0;i--){
			Pair pair = road.get(i) ; 
			System.out.print("(" + pair.x + "," + pair.y + ")") ; 
			if(i > 0){
				System.out.print("===>");
			}else{
				System.out.println();
			}
		}
		System.out.println(); 
	}
	
	private char[][] getMapCopy(){
		char[][] copy = new char[n][m] ; 
		for(int i = 0;i < n;i++){
			for(int j = 0;j < m;j++){
				copy[i][j] = map[i][j] ; 
			}
		}
		return copy ; 
	}
	
	
	public void printMapRoad(){
		System.out.println("==============printXyRoad===============");
		List<Pair> road = getRoadPair() ; 
		char[][] copy = getMapCopy() ; 
		for(int i = road.size()-1;i >= 0;i--){
			Pair pair = road.get(i) ; 
			copy[pair.x][pair.y] = '1' ; 
		}
		printMap(copy) ; 
	}
	
	
	private static void printMap(char[][] copy){
		int n = copy.length ; 
		int m = copy[0].length ; 
		for(int i = 0;i < n;i++){
			for(int j = 0;j < m;j++){
				System.out.print(copy[i][j]) ;
				if(j == m-1){
					System.out.println();  
				}
			}
		}
		System.out.println(); 
	}
	
	public void printOpenList(){
		System.out.println("==============printOpenList===============");
		for(int i = 0;i < n;i++){
			for(int j = 0;j < m;j++){
				 char ch = openList[i][j] ? '1' : map[i][j] ; 
				 System.out.print(ch) ; 
				 if(j == m-1){
					System.out.println();  
				 }
			}
		}
		System.out.println(); 
	}
	
	public void printSearchList(){
		System.out.println("==============printSearchList===============");
		for(int i = 0;i < n;i++){
			for(int j = 0;j < m;j++){
				 char ch = (openList[i][j] || closeList[i][j]) ? '1' : map[i][j] ; 
				 System.out.print(ch) ; 
				 if(j == m-1){
						System.out.println();  
					 }
			}
		}
	}
	
	private boolean pass(int nx, int ny) {
		return nx >= 0 && nx < n && ny >= 0 && ny < m && map[nx][ny] == EMPRY_CHAR && !closeList[nx][ny];
	}

	private int dis(int nx,int ny,int x,int y){
		int t = 1; 
		int dx = nx - x > 0 ? nx - x : x -nx;
	    int dy = ny - y > 0 ? ny -y : y - ny;
		if(t==1){
		    int disxy = dx - dy > 0 ? dx - dy : dy - dx ; 
		    int minxy = (dx <= dy) ? dx : dy ; 
		    return (int)((minxy*CROSS+disxy*NORMAL)*1.05f) ; 
		}else if(t == 2){
			return (int) Math.sqrt(dx*dx + dy*dy)*NORMAL ; 
		}
		return 0 ; 
	}
	
	public void print(){
		printXyRoad();
		printMapRoad();
		printOpenList();
		printSearchList();
	}
	
	public static void main(String[] args){
		
		char[][] map = generateMap() ;
		printMap(map);
		int n = map.length ; 
		int m = map[0].length ;
		long startTime = System.currentTimeMillis() ; 
		new Astar(map, 0, 0, n-1, m-1).print();
		long endTime = System.currentTimeMillis() ; 
		System.err.println(endTime - startTime );
	}
	
	private static char[][] generateMap(){
		int n = 100 ; 
		int m = 100; 
		char[][] map = new char[n][m] ; 
		for(int i = 0;i < n;i++){
			for(int j = 0;j < m;j++){
				map[i][j] = EMPRY_CHAR ; 
			}
		}
		for(int j = 0;j < m-1;j++){
			map[n/2][j] = '#';
			if(j % 2 ==0){				
				map[1][j] = '#';
				map[0][j+1] = '#';
			}
		}
		map[0][m-1] = EMPRY_CHAR ; 
		for(int i = 1;i < n/2;i++){
			map[i][m-2] = '#';
		}
		
//		String[] strs = new String[]{
//				"##00###",
//				"#0##0##",
//				"#00##0#",
//				"####0##",
//				"#####00"
//		} ; 
//		
//		char[][] map = new char[strs.length][] ;
//		for(int i = 0;i < strs.length;i++){
//			map[i] = strs[i].toCharArray() ;
//		}
		return map ; 
	}
}




















