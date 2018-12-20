package util.alg;

public class Pair implements Comparable<Pair>{
	int x ; 
	int y ;
	int g ; 
	int f ; 
	Pair parentPair ;
	public Pair(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	@Override
	public int compareTo(Pair o) {
		return f - o.f ; 
	} 
	
}