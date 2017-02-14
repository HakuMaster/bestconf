package cn.ict.zyq.bestConf.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TxtFileOperation {

	       
	    public static void writeToFile(String fileName, String content) {  
	        	          
	        File dirFile = null;  
	        try {  
	            dirFile = new File("data/");  
	            if (!(dirFile.exists()) && !(dirFile.isDirectory())) {  
	                boolean creadok = dirFile.mkdirs();  
	                if (creadok) {  
	                    System.out.println(" ok:�����ļ��гɹ��� ");  
	                } else {  
	                    System.out.println(" err:�����ļ���ʧ�ܣ� ");  
	                }  
	            }  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        String fullPath = dirFile + "/" + fileName + ".txt";  
	        write(fullPath, content);  
	    }  
	  
	    /** 
	     * д�ļ� 
	     *  
	     * @param path �ļ�·��
	     * @param content д������
	     */  
	    public static boolean write(String path, String content) {  
	        String s = new String();  
	        String s1 = new String();  
	        BufferedWriter output = null;  
	        try {  
	            File f = new File(path);  
	            if (f.exists()) {  
	            } else {  
	                System.out.println("�ļ������ڣ����ڴ���...");  
	                if (f.createNewFile()) {  
	                    System.out.println("�ļ������ɹ���");  
	                } else {  
	                    System.out.println("�ļ�����ʧ�ܣ�");  
	                }  
	            } 
	            
	            /*BufferedReader input = new BufferedReader(new FileReader(f));  
	            while ((s = input.readLine()) != null) {  
	                s1 += s + "\n";  
	            }  
	            System.out.println("ԭ�ļ����ݣ�" + s1);  
	            input.close(); */ 
	            
	            s1 = content;  
	            output = new BufferedWriter(new FileWriter(f,true));  
	            output.write(s1);  
	            output.flush();  
	            return true;  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	            return false;  
	        } finally {  
	            if (output != null) {  
	                try {  
	                    output.close();  
	                } catch (IOException e) {  
	                    e.printStackTrace();  
	                }  
	            }  
	        }  
	    }  
	    
	    
	    
	    /**
	     * ��ȡTXT�ļ�
	     * @param str	�ļ�·��
	     * @return	�����ַ����б��ֱ��Ӧ �������䡢������������
	     */
	    public static List<String> readTxt(String str) {  
	        File file = new File(str);  
	        String line;
	        int flag = 0;
	        InputStreamReader isr= null; 
            List<String> slist = new ArrayList<String>();
	        try {  
	            isr = new InputStreamReader(new FileInputStream(file), "utf-8");    
	            BufferedReader in = new BufferedReader(isr);// ��װ�ļ��������������ж�ȡ  
	            StringBuilder sb = new StringBuilder();
  
	            while ((line = in.readLine()) != null) {  
	                if(flag<2)
	                	slist.add(line);
	                else{
	                	sb.append(line+"\n");
	                }	
	                flag++;
	            }  
	            
	            slist.add(sb.toString());
	            return slist;  
	        } catch (FileNotFoundException e) {  
	            e.printStackTrace();  
	            return null;  
	        }// �����ļ�������  
	        catch (IOException e) {  
	            e.printStackTrace();  
	            return null;  
	        } finally {  
	            if (isr != null) {  
	                try {  
	                    isr.close();  
	                } catch (IOException e) {  
	                    e.printStackTrace();  
	                }  
	            }  
	        }  
	    }  
	      
	      
	    public static String readFile(String str) {  
	        File file = new File(str);  
	        String line;  
	        InputStreamReader isr= null;  
	        try {  
	            isr = new InputStreamReader(new FileInputStream(file), "utf-8");    
	            BufferedReader in = new BufferedReader(isr);// ��װ�ļ��������������ж�ȡ  
	            StringBuilder sb = new StringBuilder();  
	            while ((line = in.readLine()) != null) {  
	                sb.append(line);  
	                sb.append("\n");
	                  
	            }  
	            return sb.toString();  
	        } catch (FileNotFoundException e) {  
	            e.printStackTrace();  
	            return null;  
	        }// �����ļ�������  
	        catch (IOException e) {  
	            e.printStackTrace();  
	            return null;  
	        } finally {  
	            if (isr != null) {  
	                try {  
	                    isr.close();  
	                } catch (IOException e) {  
	                    e.printStackTrace();  
	                }  
	            }  
	        }  
	    }  

}
