package cn.ict.zyq.bestConf.cluster.InterfaceImpl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import weka.core.Instance;
import weka.core.Instances;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;

import ch.ethz.ssh2.Connection;
import cn.ict.zyq.bestConf.cluster.Interface.ConfigWrite;
import cn.ict.zyq.bestConf.cluster.Utils.SFTPUtil;
import cn.ict.zyq.bestConf.data.DataIOFile;

public class TomcatConfigWrite implements ConfigWrite {
	private Connection connection;
	private String server;
	private String username;
	private String password;
	private String remotePath;
	private String localPath;
	private static final String theConfig = "server.xml";

	public TomcatConfigWrite() {

	}

	@Override
	public void initial(String server, String username, String password,
			String localPath, String remotePath) {
		this.server = server;
		this.username = username;
		this.password = password;
		this.localPath = localPath;
		this.remotePath = remotePath;
	}

	private Connection getConnection() {
		try {
			connection = new Connection(server);
			connection.connect();
			boolean isAuthenticated = connection.authenticateWithPassword(
					username, password);
			if (isAuthenticated == false) {
				throw new IOException("Authentication failed...");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return connection;
	}

	private void closeConnection() {
		try {
			if (connection.connect() != null) {
				connection.close();
			}
		} catch (IOException e) {

		} finally {
			connection.close();
		}
	}

	@Override
	public void uploadConfigFile() {
		ChannelSftp sftp = null;
		Session session;
		Channel channel;
		session = null;
		channel = null;
		try {
			removeRemoteConfigFile(theConfig);

			session = SFTPUtil.connect(server, 22, username, password);
			channel = session.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
			SFTPUtil.upload(remotePath, localPath + "/" + theConfig, sftp);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sftp != null)
				sftp.disconnect();
			if (session != null)
				session.disconnect();
		}
	}

	private void removeRemoteConfigFile(String filename) {
		String cmdRemove = "cd " + remotePath + "; rm -f " + filename;
		try {
			ch.ethz.ssh2.Session session = this.getConnection().openSession();
			session.execCommand(cmdRemove);
			System.out.println("Here is SUT start information:");
			if (session != null)
				session.close();
			closeConnection();

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("删除配置文件失败!");
		}

	}
	
	//////////////////////////////////////////////////////////////////////////

	@Override
	public void writetoConfigfile(HashMap hm) {
		String iniConf = "data/server-initial.xml", updatedConf="data/server.xml";
		
		try {
			//we first read in the base line
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dbBuilder;
			dbBuilder = dbFactory.newDocumentBuilder();
			Document doc = dbBuilder.parse(new File(iniConf));
			
			//now we find the place to change
			NodeList connList = doc.getElementsByTagName("Connector");
			boolean done = false;
			for (int i = 0; i < connList.getLength() && !done; i++) {
				Node connNode = connList.item(i);
				if (connNode.getNodeType() == Node.ELEMENT_NODE) {
					 NamedNodeMap map = connNode.getAttributes();
					 for(int j=0;j<map.getLength() && !done;j++){
						 if(map.item(j).getNodeName().equals("port") &&
								 map.item(j).getNodeValue().equals("8080")){
							 /** now we update the config file as needed*/
							 Element ele = (Element)connNode;
							 for(Object obj : hm.entrySet()){
								 Map.Entry ent = (Map.Entry)obj;
								 ele.setAttribute(ent.getKey().toString(), ent.getValue().toString());
							 }
							 done=true;
						 }
					 }
				}
			}
			
			//updated! we write the config out to file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(updatedConf));
			transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException{
		TomcatConfigWrite tcw = new TomcatConfigWrite();
		HashMap hm = new HashMap();
		//trainingBestConf_RRS_7_0_4837.8.arff
		String path = args[0];
		Instance inst = DataIOFile.loadDataFromArffFile(path).get(0);
		int num = inst.numAttributes();
		for(int i=0;i<num;i++){
			hm.put(inst.attribute(i).name(),(long)inst.value(i));
		}
		tcw.writetoConfigfile(hm);
	}
}
