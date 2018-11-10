package util.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map; 
public class Solution {
    public ArrayList<ArrayList<Integer>> fourSum(int[] num, int target) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>() ;
        Map<Integer,List<Integer>> map = new HashMap<>() ; 
        Arrays.sort(num) ; 
        Map<Integer,Integer> numMap = new HashMap<>() ; 
        for(int i = 0;i < num.length;i++){
            for(int j = i+1;j < num.length;j++){
                int nn = num[i] + num[j] ;
                List<Integer> list = map.get(nn) ; 
                if(list == null){
                    list = new ArrayList<>() ; 
                    map.put(nn,list); 
                }
                list.add(num[i]) ; 
                list.add(num[j]) ; 
                while(j+1<num.length && num[j+1] == num[j]){
                    j++ ; 
                }
            }
            int nn = 1 ; 
            while(i+1<num.length && num[i+1] == num[i]){
                i++ ; 
                nn++ ; 
            }
            numMap.put(num[i],nn) ; 
        }
        for(Map.Entry<Integer,List<Integer>> entry:map.entrySet()){
            int res = target-entry.getKey() ; 
            if(res > target){
                continue ; 
            }
            List<Integer> list1 = map.get(res) ; 
            List<Integer> list2 = entry.getValue() ; 
            if(list1 != null){
                for(int i = 0;i < list1.size();i+=2){
                    for(int j = 0;j < list2.size();j+=2){
                    	boolean flag = true ;
                        int a = list1.get(i) ; 
                        int b = list1.get(i+1) ;
                        int c = list2.get(j) ; 
                        int d = list2.get(j+1) ; 
                        numMap.put(a,numMap.get(a)-1) ; 
                        numMap.put(b,numMap.get(b)-1) ; 
                        numMap.put(c,numMap.get(c)-1) ; 
                        numMap.put(d,numMap.get(d)-1) ; 
                        if(numMap.get(a) < 0 || numMap.get(b) < 0 || numMap.get(c) < 0 || numMap.get(d) < 0){
                            flag = false ; 
                        }
                        numMap.put(a,numMap.get(a)+1) ; 
                        numMap.put(b,numMap.get(b)+1) ; 
                        numMap.put(c,numMap.get(c)+1) ; 
                        numMap.put(d,numMap.get(d)+1) ; 
                        if(!flag){
                            continue ; 
                        }
                        if(a <= b && b <= c && c <= d){
                            ArrayList<Integer> list = new ArrayList<Integer>(4) ; 
                            list.add(a);
                            list.add(b); 
                            list.add(c);
                            list.add(d) ; 
                            result.add(list) ; 
                        }
                        
                    }
                }
            }
        }
        Collections.sort(result,new Comparator<ArrayList<Integer>>(){
             public int compare(ArrayList<Integer> a,ArrayList<Integer> b){
                 for(int i = 0;i < 4;i++){
                     if(a.get(i) != b.get(i)){
                         return a.get(i) - b.get(i) ; 
                     }
                 }
                 return 0 ; 
             }
        });
        return result ; 
    }
    
    
    public static void main(String[] args){
    	ArrayList<ArrayList<Integer>> lists = new ArrayList<>() ; 
    	int[][] a = new int[][]{{306,414,470,492},{310,439,441,492},{314,435,441,492},{331,440,441,470},{333,439,440,470},{333,387,470,492},{334,421,435,492},{347,408,435,492},{356,421,435,470},{357,414,441,470},{361,408,421,492},{367,435,439,441},{387,421,435,439},{387,414,440,441},{408,414,421,439}} ; 
    	for(int i = 0;i < a.length;i++){
    		ArrayList<Integer> list = new ArrayList<Integer>() ; 
    		lists.add(list) ; 
    		for(int j :a[i]){
    			list.add(j) ; 
    		}
    	}
    	Collections.sort(lists,new Comparator<ArrayList<Integer>>(){
            public int compare(ArrayList<Integer> a,ArrayList<Integer> b){
                for(int i = 0;i < 4;i++){
                    if(a.get(i) < b.get(i)){
                        return -1;  
                    }else if(a.get(i) > b.get(i)){
                    	return 1 ; 
                    }
                }
                return 0 ; 
            }
        });
    	System.out.println(lists);
    }
}








