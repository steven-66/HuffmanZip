import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

public class Extract {
	HuffmanTree tree;
	HashMap<Byte,Integer> frequence;
	public Extract() {
		tree=new HuffmanTree();
		frequence=new HashMap<>();
	}
	public int byteToint(byte[] b) {
		return (int)((b[0]<<24) | (b[1]<<16) | (b[2]<<8) | (b[3]));
	}
	public String byteTostring(byte b) {
		int shift=0;
		String s="";
		while(shift<8) {
			if(((b<<shift >>7)&1)==1) {
				s+='1';
			}
			else if(((b<<shift >>7)&1)==(byte) 0) {
				s+='0';
			}
			shift++;
		}
		return s;
	}
	public void extractFile(String src, String des) {
		try {
			FileInputStream fin=new FileInputStream(src);
			int tmp=0;
			while((tmp=fin.read())!=-1){
				if(tmp==0 && fin.read()==0) {
					break;
				}
				byte key=(byte) tmp;
				byte[] read=new byte[4];
				fin.read(read);
				frequence.put(key, byteToint(read));
			}
			buildTree();
			HashMap<String, Byte> dic=new HashMap<String,Byte>();//translate the bytecode into indexing byte
			for(Entry<Byte,String> entry: tree.code_table.entrySet()) {
				dic.put(entry.getValue(), entry.getKey());
			}
			HuffNode pointer=tree.root;
			FileOutputStream fout=new FileOutputStream(des);
			String code="";
			int getNextchar=0;
			int fileLenth=0;
			while((tmp=fin.read())!=-1) {
				code+=byteTostring((byte) tmp);
				
				while(getNextchar<code.length()) {
					if(code.charAt(getNextchar)=='1' && pointer.getRight()!=null) {
						pointer=pointer.getRight();
					}
					else if(code.charAt(getNextchar)=='0' && pointer.getLeft()!=null) {
						pointer=pointer.getLeft();
					}
					getNextchar++;
				
					if(pointer.isLeaf()) {
						if(fileLenth==tree.root.weight) {
							break;
						}
						fout.write(pointer.getInfo());
						code=code.substring(getNextchar);
						getNextchar=0;
						pointer=tree.root;
						fileLenth++;
					}
				}
			}
			fin.close();
			fout.close();
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
}
