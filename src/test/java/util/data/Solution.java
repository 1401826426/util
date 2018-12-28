package util.data;

import java.util.Arrays;
import java.util.Comparator;

class Solution {
    public String largestNumber(int[] nums) {
    	String[] ss = new String[nums.length] ; 
    	for(int i = 0;i < nums.length;i++){
    		ss[i] = String.valueOf(nums[i]) ; 
    	}
        Arrays.sort(ss,new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				String s1 = o1 + o2 ; 
				String s2 = o2 + o1 ; 
				for(int i = 0;i < s1.length();i++){
					if(s1.charAt(i) != s2.charAt(i)){
						return s2.charAt(i) - s1.charAt(i) ; 
					}
				}
				return 0 ; 
			}
		});
        StringBuilder sb = new StringBuilder("") ; 
        for(String s:ss){
        	sb.append(s) ; 
        }
        String t = sb.toString() ; 
        int pos = -1; 
        for(int i = 0;i < t.length();i++){
        	if(t.charAt(i) != '0'){
        		pos = i ; 
        		break ; 
        	}
        }
        if(pos < 0){
        	return "0" ; 
        }
    	return t.substring(pos) ; 
    }
}



