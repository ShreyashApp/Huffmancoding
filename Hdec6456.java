/* Shreyash Appikatla cs610 6456 prp */
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.*;

public class Hdec6456 {

	 static String fileName ;
	private static int totalCod =0;
	private static int[] sam = new int[256];
	private static Nodes6456 root=null;
	static int i;
	static ArrayList<Nodes6456> q = new ArrayList<Nodes6456>();
	

public static void main(String args[]) throws IOException {
    fileName = args[0];
           FileInputStream fileReader = 
           new FileInputStream(fileName);
       File sample = new File(fileName);
       BufferedReader bufferedreader = 
           new BufferedReader(new InputStreamReader(fileReader));
       for(int i=0;i<256;i++)
    	  sam[i]=-1;
       codetabs(bufferedreader.readLine());
       buildhuffman();
       decodingcode();
       
       bufferedreader.close();
       sample.delete();

}

private static void decodingcode() throws IOException {
	// TODO Auto-generated method stub
	String outfile=fileName.substring(0, fileName.length()-4);
  	Nodes6456 temp=root;
  	BufferedInputStream bin=null;
  	BufferedOutputStream bout=null;
  	try{
    	bin= new BufferedInputStream(new FileInputStream(fileName));
    	bout= new BufferedOutputStream( new FileOutputStream(outfile));
       int i=0;
      
        while(bin.read()!='\n' || bin.read()!='\n');
    	int j = 0;
        while(true){ 
        	i=bin.read();
        	for (int k = 128; k>0; k/=2){        
        		if((i & k) !=0){  // Going right
     	            temp=temp.getRight();
     	        }else{ // Going Left
     	            temp=temp.getLeft();
     	        }
        		if(temp.getASCII()!=65535) {
  	             bout.write(temp.getASCII());
  	                temp=root;
 	                if (++j >= totalCod)return;
 	            }
        	}
    	}// While loop ends
    }catch(Exception e){
        System.out.println("IO Error");
        e.printStackTrace();
    }finally{
    	bin.close();
    	bout.close();
    }
}


 	private static void buildhuffman() {
		// TODO Auto-generated method stub
		for(i=0;i<256;i++) {
			Nodes6456 nd = new Nodes6456();
			nd.freq = sam[i];
			nd.symbol = (char)i;
			nd.left = null;
			nd.right = null;
			q.add(nd);
		}
		
		while(q.size()>1) {
			Nodes6456 second = new Nodes6456();
			int x1 = smallest(q,-1);
			Nodes6456 first = q.get(x1);
			q.remove(x1);
			int x2 = smallest(q,x1);
			if(x2==q.size())
				break;
			if(x2<q.size()) {
			second = q.get(x2);
			}
			if(second!=null)
			q.remove(x2);
			if(first!=null&&second!=null) {
			Nodes6456 damn = new Nodes6456();
			damn.freq = first.freq+second.freq;
			damn.symbol = (char) -1;
			damn.left = first;
			damn.right = second;
			root = damn;
			q.add(damn);
			}
		}
	}


	private static int smallest(ArrayList<Nodes6456> q2, int j) {
		// TODO Auto-generated method stub
		i=0;
		while(q2.size()>i) {
			if(q2.get(i).freq!=-1)
				break;
			i++;
		}
		int small=i;
		if(q2.size()>i) {
			if(q2.get(i).symbol==j){	
				i++;		
					while(q2.size()>i){
						if(q2.get(i).freq!=-1)
							break;
						i++;
					}
			small=i;
			}
		}		
		for(int t=0;t<q2.size()&&q.size()>small;t++) {
			if(q2.get(t).freq==-1)
				continue;
			if(q2.get(t).symbol==j)
				continue;
			if(q2.get(t).freq<q2.get(small).freq)
				small = t;
		}
		return small;
	}
  

private static void codetabs(String read) {
	// TODO Auto-generated method stub
	StringBuilder sb=new StringBuilder();
	String[] codes=read.split(";");
	String[] vals;
  	for(int i=0;i<codes.length;i++){
		vals=codes[i].split("\\.");
		sam[Integer.parseInt(vals[0])]=Integer.parseInt(vals[1]);
		sb.append(Integer.parseInt(vals[0])+"."+Integer.parseInt(vals[1])+";");
		totalCod+=Integer.parseInt(vals[1]);
  	} 
	
}
	
	
}
