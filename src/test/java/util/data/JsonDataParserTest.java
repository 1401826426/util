package util.data;

import org.junit.Test;

public class JsonDataParserTest {
	
	@Test
	public void test(){
		String ss = "{\"a\":1,\"b\":\"2\"}"; 
		NodeParser nodeParser = new JsonNodeParser() ; 
		System.out.println(nodeParser.parse(ss).toString(0));
	}
	
	@Test
	public void test1(){
		String ss = "{\"a\":1,\"b\":2,\"c\":[],\"def\":{\"a\":\"1\",\"bbb\":\"2\",\"ccc\":\"3\",\"ddd\":[1,2,3,4],\"eee\":{}}}"; 
		NodeParser nodeParser = new JsonNodeParser() ; 
		System.out.println(nodeParser.parse(ss).toString(0));
	}
	
	@Test
	public void test2(){
		String ss = "{\"a\":1,\"b\":2,\"c\":[{\"a\":1,\"b\":2,\"c\":3},{\"d\":1,\"e\":2,\"f\":[{\"a\":1,\"b\":2},{\"c\":3}]}],\"def\":{\"a\":\"1\",\"bbb\":\"2\",\"ccc\":\"3\",\"ddd\":[1,2,3,4],\"eee\":{}}}"; 
		NodeParser nodeParser = new JsonNodeParser() ; 
		System.out.println(nodeParser.parse(ss).toString(0));
	}
	
	@Test
	public void test3(){
		String ss = "[1,2,3,4]"; 
		NodeParser nodeParser = new JsonNodeParser() ; 
		System.out.println(nodeParser.parse(ss).toString(0));
	}
	
	@Test
	public void test4(){
		String ss = "[1,2,3,4]"; 
		NodeParser nodeParser = new JsonNodeParser() ; 
		System.out.println(nodeParser.parse(ss).toString(0));
	}
	
	@Test
	public void test5(){
		String ss = "{\"a\":1,\"b\":2,\"c\":[{\"a\":1,\"b\":2,\"c\":3},{\"d\":1,\"e\":2,\"f\":[{\"a\":1,\"b\":2},{\"c\":3}]}],\"def\":{\"a\":[1,2,3,4],\"b\":[\"1\",\"2\",\"3\",\"4\"],\"cccc\":{\"e\":123}}}"; 
		NodeParser nodeParser = new JsonNodeParser() ; 
		System.out.println(nodeParser.parse(ss).toString(0));
	}
}
