

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map.Entry;

public class Compact {
	HuffmanTree tree;
	int fileLenth;
	Extract e=new Extract();
	HashMap<Byte, Integer> frequence;
	public Compact(){
		tree=new HuffmanTree();
		frequence=new HashMap<Byte, Integer>();
		fileLenth=0;
	}
	public void readFile(String path) {
	FileInputStream in = null;
		try {
			in=new FileInputStream(path);
			int tmp=0;
			int count=0;
			while((tmp=in.read())!=-1) {
				fileLenth++;
				count=frequence.getOrDefault((byte)tmp, 0);
				frequence.put((byte) tmp, count+1);
			}
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void buildTree() {
		for(Entry<Byte, Integer> entry : frequence.entrySet()) {
			HuffNode newnode=new HuffNode(entry.getValue());
			newnode.setElement(entry.getKey());
			tree.queue.insert(newnode);
		}
		while(!tree.queue.isEmpty()) {
			tree.mergeTree(tree.queue.extractMin(), tree.queue.extractMin());
		}
		for(Byte key:frequence.keySet()) {
			tree.getCode(key);
		}
	}
	public byte[] intTobyte(int i) {
		byte[] b=new byte[4];
		b[0]=(byte) ((i<<24)& 0xff);
		b[1]=(byte) ((i<<16)& 0xff);
		b[2]=(byte) ((i<<8)& 0xff);
		b[3]=(byte) (i & 0xff);
		return b;
	}
	public void fileCompact(String src, String des) {
		readFile(src);
		buildTree();
		FileInputStream in=null;
		FileOutputStream fout=null;
		try {
			in=new FileInputStream(src);
			fout=new FileOutputStream(des);
			int tmp=0;
			String s=""; //get code for 8 bit
			for(Entry<Byte, Integer> entry : frequence.entrySet()) {
				fout.write((int)entry.getKey());
				fout.write(intTobyte(entry.getValue()));
			}
			fout.write(0);
			fout.write(0);
			while((tmp=in.read())!=-1) {//write into one byte 
				s+=tree.code_table.get((byte)tmp); 
				if(s.length()<8) {
					continue;
				}
				byte codeWrite=0;
				for(int j=0;j<8;j++) {
					codeWrite<<=1;
					if(s.charAt(j)=='1') {
						codeWrite |=1;
					}
				}
				fout.write((int)codeWrite);
				fout.flush();
				if(s.length()>8)s=s.substring(8);
				else s="";
			}
			byte codeWrite=0;//last byte to be written, if the code is less than 8 bit, use 0 to compensate
			for(int j=0;j<8;j++) {
				codeWrite<<=1;
				if(j>=s.length()) {
					continue;
				}
				if(s.charAt(j)=='1') {
					codeWrite |=1;
				}
			}
			fout.write((int)codeWrite);
			fout.flush();
			in.close();
			fout.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
