package util.data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class Solution {
	static int[] h = new int[]{1,2,4,8} ; 
	static int[] m = new int[]{1,2,4,8,16,32} ; 
    public List<String> readBinaryWatch(int num) {
        List<String> list = new ArrayList<>() ; 
        dfs(0,num,new LinkedList<>(),list) ; 
        return list ; 
    }
	private void dfs(int pos, int num, LinkedList<Integer> tmp, List<String> list) {
		if(tmp.size() == num){
			String ans = getResult(tmp) ; 
			if(ans != null){
				list.add(ans) ; 
			}
			return ; 
		}
		if(pos == 10){
			return  ; 
		}
		dfs(pos+1,num,tmp,list) ; 
		tmp.add(pos) ; 
		dfs(pos+1,num,tmp,list) ; 
		tmp.removeLast() ; 
	}
	private String getResult(LinkedList<Integer> tmp) {
		int hh = 0 ; 
		int mm = 0 ; 
		for(int v:tmp){
			if(v < 4){
				hh += h[v] ; 
			}else{
				mm += m[v-4] ; 
			}
		}
		if(hh > 11 || mm > 59){
			return null ; 
		}
		String s = "" + hh + ":";
		if(mm < 10){
			s += "0" ; 
		}
		s += mm ; 
		return s;
	}
	
	public static void main(String[] args){
		System.out.println(new Solution().readBinaryWatch(1));
	}
    
    
}