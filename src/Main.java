import java.util.Map.Entry;

public class Main {

	public static void main(String[] args) {
//		Compact c=new Compact();
//		Extract e=new Extract();
//
//		c.fileCompact("C:\\Users\\steve\\Desktop\\test\\image\\html.png", "C:\\Users\\steve\\Desktop\\test\\image\\1.bin");
//		e.extractFile("C:\\Users\\steve\\Desktop\\test\\image\\1.bin", "C:\\Users\\steve\\Desktop\\test\\image\\1.png");
//		for(Entry<Byte, String> entry : e.tree.code_table.entrySet()) {
//			System.out.println(entry.getValue());
//		}
//		for(Entry<Byte, String> entry : c.tree.code_table.entrySet()) {
//			System.out.println(entry.getValue());
//		}
		System.out.println(myAtoi("21474836460"));
	}
	 public static int myAtoi(String str) {
	        int result=0;
	        int base=0;
	        int sign=0;
	        for(int i=0;i<str.length();i++){
	            if(str.charAt(i)==' '){
	                continue;
	            }
	            else if((int)str.charAt(i)<=57 && (int)str.charAt(i)>=48 || str.charAt(i)=='-' || str.charAt(i)=='+'){
	            	base=i+1;
	            	int j=0;
	            	if(str.charAt(i)!='-'&& str.charAt(i)!='+') {result+=(int)(str.charAt(i)-'0'); sign=1;j=i;}
	            	else {sign=(str.charAt(i)=='-')?-1:1;j=base;}
	            	
		            while(base<str.length()&& str.charAt(base)<='9' && str.charAt(base)>='0'){
		            	System.out.println(result);
		            	if(base-j+1>10) {
		            		return (sign==1)?Integer.MAX_VALUE:Integer.MIN_VALUE;
		            	}
		                int add=(int)(str.charAt(base)-'0');
		               
		                
		               
		                j=(result==0)?base:j;
		                if(base-j==9) {
		                	System.out.println(add);
		                	System.out.println(base);
		                	System.out.println(j);
		                	int tmp=result*sign;
		                	int x=(Integer.MAX_VALUE-add)/10;
		                	int y=(Integer.MIN_VALUE+add)/10;
		                	if(tmp>x) {
		                		return Integer.MAX_VALUE;
		                	}
		                	
		                	if(tmp<y) {
		                		return Integer.MIN_VALUE;
		                	}
		                	
		                }
		                result=result*10+add;
		                base++;
		            }
		            
		            return result*sign;
	            }
	            return 0;
	        }
	        return 0;
	    }
}
