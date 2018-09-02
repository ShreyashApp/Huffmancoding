
/* Shreyash Appikatla cs610 6456 prp */
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.io.*;

public class Henc6456 {
	static int[] freq = new int[256];
	static int i,subtrees=256;
	static ArrayList<Nodes6456> q = new ArrayList<Nodes6456>();
	private static String[] codetabs = new String[256];	
	static Nodes6456 root = null;
	static String filename;
	
	public static void main(String args[]) throws IOException {
		
		filename = args[0];
		for(i=0;i<256;i++) {
			freq[i]=-1;
			codetabs[i]=null;
		}
        FileInputStream fileReader = 
                new FileInputStream(filename);
File sample = new File(filename);
            BufferedInputStream bufferedstream = 
                new BufferedInputStream(fileReader);
int line = 0;
            do{
            	line = bufferedstream.read();
            	if(line!=-1) {
            		if(freq[line]!=-1){
				    freq[line]++;
					}else{
					freq[line]=1;
				}
            	}
            }    while(line !=-1);

		
		buildhuffman();

		printcode(root,"");
		
		Compress5324();
   sample.delete();
	}


    public static StringBuilder serializeFreqToString(){
  	  StringBuilder freqString=new StringBuilder();
  	  for(int i=0;i<256;i++){
  		  if(freq[i]!=-1){
  			  freqString.append(i+"."+freq[i]+";");
  		  }
  	  }
 
  	  return (freqString.append('\n').append('\n'));
    }
    public static void Compress5324() throws IOException{
  	  StringBuilder metaData=new StringBuilder();
  	  metaData=serializeFreqToString();
  	  try(BufferedOutputStream bout= new BufferedOutputStream( new FileOutputStream(filename+".huf"))){
            for(int i=0;i<metaData.length();i++){
                char c = metaData.charAt(i);
                bout.write(c);
            }
            int i;
            String bits="";
            int tempwrite=0;
            int locs=7;
            FileInputStream fileReader = 
                new FileInputStream(filename);
            BufferedInputStream bufferedstream = 
                new BufferedInputStream(fileReader);          
            do{
					i=bufferedstream.read();
					if(i!=-1){
						bits=codetabs[i];
						for(int j=0;j<bits.length();j++){
							char c= bits.charAt(j);
							if(c=='1'){
								tempwrite+=1<<locs;
								}
							locs--;
							if(locs<0){
								locs=7;
								bout.write(tempwrite);
								tempwrite=0;
							} //inner if ends
							
						} // for ends
						
					} // if ends
				}while(i!=-1);
           
            if(locs!=7){
          	  bout.write(tempwrite);
            }
            bufferedstream.close();
      }catch(Exception e){
            System.out.println("IO Error");
            e.printStackTrace();
      }
  }


	private static void printcode(Nodes6456 root, String string) {
		// TODO Auto-generated method stub
		 if (root.left== null&& root.right== null) {
             codetabs[root.symbol] = string;
             //System.out.println(string+" "+root.symbol);
            return;
        }
        if(root.left!=null)printcode(root.left, string + "0");
        if(root.right!=null)printcode(root.right, string + "1");
	
	}


	private static void buildhuffman() {
		// TODO Auto-generated method stub
		for(i=0;i<256;i++) {
			Nodes6456 nd = new Nodes6456();
			nd.freq = freq[i];
			nd.symbol = (char)i;
			nd.left = null;
			nd.right = null;
			q.add(nd);
		}
		int f=0;
		
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
			damn.symbol =(char) -1;
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
	
}
