package common;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.Vector;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
//import com.cucumber.listener.Reporter;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.google.common.io.CharStreams;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.ChannelSftp.LsEntry;

public class SSHUtil {
	static String host = "**Linux Machine IP or Hostname***";
	static String user = "**Linux machine User***";
	static String password = "** Linux machine password***";	
	static int port = 22;
	static String filePath = "** Linux file Path***";	
	private static String path = System.getProperty("user.dir");
	
	 
	public static void LoadConnectionDeatils()
	{
		try {
//			filePath = CommonUtil.GetXMLData(
//					Paths.get(path.toString(), "src", "test", "java", "SSHSettings.xml").toString(), filePath);
			host = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "SSHSettings.xml").toString(), "SSH_Host");
			user = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "SSHSettings.xml").toString(), "SSH_User");
			password = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "SSHSettings.xml").toString(), "SSH_Password");
			System.out.println("************************************************"+host+"  "+user+"  "+password );
			ExtentCucumberAdapter.addTestStepLog("************************************************"+host+"  "+user+"  "+password );
		} catch (Exception ex) {
			throw new CustomException("DBSettings.xml file does not exist");
		}
	}
	
	/*public static String GetDataFromFile(String filepath,String key)
	{
		filePath=filepath;
		LoadConnectionDeatils();
        //String remoteFile = "/home/centos/IDMTesting/newfile.txt";
        //remoteFile = "/home/centos/IDMTesting/TestFile.json";    
        ArrayList list = new ArrayList(50);
        String value="";
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("Establishing Connection...");
            session.connect();
            System.out.println("Connection established.");
            System.out.println("Crating SFTP Channel.");
            ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();
            System.out.println("SFTP Channel created.");
			Reporter.addStepLog("Connected to the host: '" + host + "'");
            InputStream inputStream = sftpChannel.get(filePath);
			Reporter.addStepLog("Reading the data from file: '" + filePath + "'"); 
            try (Scanner scanner = new Scanner(new InputStreamReader(inputStream))) {
                while (scanner.hasNextLine()) {
                	String line = scanner.nextLine();                	
                     
                	 JSONParser parser = new JSONParser();
					   JSONObject json = (JSONObject) parser.parse(line);
					  value= json.get(key).toString();                	
                    System.out.println(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		Reporter.addStepLog("Value read from file for the key("+key+") is: '" + value + "'");
        return value;
	}*/
	

	public static String GetListOfFiles(String filepath)
	{
		filePath=filepath;
		LoadConnectionDeatils();
        //String SFTPPRIVATEKEY = "/path/to/xxxxxxxxx.pem";
        Session     session     = null;
        Channel     channel     = null;
        ChannelSftp channelSftp = null;
       // ArrayList list = new ArrayList(50);
	   String list="";
        try{
            JSch jsch = new JSch();
           // File privateKey = new File(SFTPPRIVATEKEY);
            //if(privateKey.exists() && privateKey.isFile())
              //  jsch.addIdentity(SFTPPRIVATEKEY);
            session = jsch.getSession(user,host,port);
            session.setPassword(password);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            ExtentCucumberAdapter.addTestStepLog("Connected to the host: '" + host + "'");
            channelSftp = (ChannelSftp)channel;
            channelSftp.cd(filePath);
            Vector filelist = channelSftp.ls("*");
            for(int i=0; i<filelist.size();i++){
                LsEntry entry = (LsEntry) filelist.get(i);
               // list.add(entry.getFilename().toString());
			   list=list+entry.getFilename()+",";
                System.out.println(entry.getFilename());
            }
        }catch(Exception ex){
            ex.printStackTrace();
        } finally {
            if(session != null) session.disconnect();
            if(channel != null) channel.disconnect();
        }
        return list;
	}
	
	public static boolean runCommands(String commands){
		String result="";
		boolean res = false;
		StringBuilder outputBuffer = new StringBuilder();
		try {
			System.out.println("COMMANDS##################-->"+commands);	
			String[] splitValue=commands.split("---");
			if(splitValue.length>1)
			{
			System.out.println("SPLIT@@@@@@@@@@@@@@ VALUE1*******"+splitValue[0]);
			System.out.println("SPLIT@@@@@@@@@@@@@@ VALUE2*******"+splitValue[1]);
			System.out.println("SPLIT@@@@@@@@@@@@@@ VALUE3*******"+splitValue[2]);
			System.out.println("SPLIT@@@@@@@@@@@@@@ VALUE3*******"+splitValue[3]);
	if(splitValue.length>4)
	{
		System.out.println("SPLIT@@@@@@@@@@@@@@ VALUE1*******"+splitValue[4]);
	}
			JSch jsch = new JSch();
		    Session session = setupSshSession();
		    //Session session = jsch.getSession(username, ip, 22);
		    //session.setPassword(password);
		    //setUpHostKey(session);
		    session.connect();

		    Channel channel=session.openChannel("shell");//only shell  
		    channel.setOutputStream(System.out); 
		    PrintStream shellStream = new PrintStream(channel.getOutputStream());  // printStream for convenience 
		    channel.connect(); 
		    
		   
		    String[] commands1=splitValue[0].split("&&");
			
			   
		    for(String cmd : commands1)
		    {
		    	System.out.println("FOR_EACH-------CMD"+cmd);
		    	if(cmd.contains("_delay_"))
		    	{
		    		String[] cmds=cmd.split("_delay_");
		    		shellStream.println(cmds[0]);
		    		ExtentCucumberAdapter.addTestStepLog("Executed command : "+cmds[0]);
		    		
		             shellStream.flush();
		             
		           // result =   result+readChannelOutput(channel);
			    	 //shellStream.flush()
		             
			    Thread.sleep(Long.parseLong(cmds[1].trim()));
			    	 
		    	}
		    	else
		    	{
		    	 shellStream.println(cmd.trim());
		    	 ExtentCucumberAdapter.addTestStepLog("Executed command : "+cmd.trim());
		    	 shellStream.flush();
		    	
		    	 Thread.sleep(2000);
		    	}
		    	System.out.println("#@#@#@#@#@#@#@#INSIDE RUN COMMAND FOR LOOP****");
		    }

        String verifyText=DbHelper.queryCopiedText(splitValue[2]).toUpperCase().trim();
	System.out.println("COPIED@@@@@@@@@@@@@@ VALUE*******"+verifyText);	
String fileoutput="";	
			if(splitValue.length>4)
			{
				fileoutput = GetListOfFiles(splitValue[1]).toUpperCase();
			}
			else{
		     fileoutput = GetDataFromFile(splitValue[1]).toUpperCase();
			}
		   // System.out.println("OUTSIDE FOR_EACH-------CMD");
		    System.out.println("FILEOUTPUT DATA_______-------"+fileoutput);
		  //  result =   readChannelOutput(channel);
		    if(splitValue[3].equals("verifydisplayed")){
 System.out.println("Copied text is"+verifyText+" verified"+fileoutput.contains(verifyText.trim()));
// if(fileoutput.contains(verifyText.toUpperCase()))
                    if(fileoutput.contains(verifyText.trim()))		   
            {
		    System.out.println("Inside VERIFIED DISPLAYED________****true****");
            res=true;
            }
		    else
	    	 {
		System.out.println("Inside VERIFIED DISPLAYED________****false****");
		           
	    		 res= false;
	    	 }
		    }
		    else if(splitValue[3].equals("verifynotdisplayed"))
		    {
		    	 if(fileoutput.contains(verifyText.toUpperCase()))
		            {
		 System.out.println("Inside VERIFIED NOT DISPLAYED________****false****");
		             	    		 
		            res=false;
		            }
		    	 else
		    	 {
		 System.out.println("Inside VERIFIED NOT DISPLAYED________****true****");
		  		   		    		 
		    		 res= true;
		    	 }
		    }
		    
		    ExtentCucumberAdapter.addTestStepLog("Value read from file : '" + fileoutput+ "' , Copied value to be verified : '"+verifyText+"' , Verification type : '"+splitValue[3]+"'");
		    channel.disconnect();
		    session.disconnect();
			}
			else
			{
	System.out.println("---------------------------------------Run only command --------------------------------------------------");

			JSch jsch2 = new JSch();
			    Session session2 = setupSshSession();
			    //Session session = jsch.getSession(username, ip, 22);
			    //session.setPassword(password);
			    //setUpHostKey(session);
			    session2.connect();

			    Channel channel2=session2.openChannel("shell");//only shell  
			    channel2.setOutputStream(System.out); 
			    PrintStream shellStream2 = new PrintStream(channel2.getOutputStream());  // printStream for convenience 
			    channel2.connect(); 
			    
			   
			    String[] commands1=commands.split("&&");
				
				   
			    for(String cmd : commands1)
			    {
			    	System.out.println("FOR_EACH---------------------------------CMD"+cmd);
			    	if(cmd.contains("_delay_"))
			    	{
			    		String[] cmds=cmd.split("_delay_");
			    		shellStream2.println(cmds[0]);
			    		ExtentCucumberAdapter.addTestStepLog("Executed command : "+cmds[0]);
			    		
			             shellStream2.flush();
			             
			           /* result =   result+readChannelOutput(channel);
				    	 shellStream.flush();
			             if(result.contains("FATAL"))
						 {
							 res=false;
							 break;
						 }*/
				    Thread.sleep(Long.parseLong(cmds[1].trim()));
				    	 
			    	}
			    	else
			    	{
			    	 shellStream2.println(cmd.trim());
			    	 ExtentCucumberAdapter.addTestStepLog("Executed command : "+cmd.trim());
			    	 shellStream2.flush();
			    	
			    	 Thread.sleep(2000);
			    	}
			    	
				 channel2.disconnect();
			    session2.disconnect();
			    }
	res=true;
			}
		
		    
		} catch (Exception e) { 
			System.out.println("EXCEPTION BLOCK-------######********--------"+e);
		   // e.printStackTrace();
		}
		return res;
		}
			

private static boolean containsIgnoreCase(List<String> list, String soughtFor) {
    for (String current : list) {
        if (current.equalsIgnoreCase(soughtFor)) {
            return true;
        }
    }
    return false;
}
	
	public static String GetDataFromFile(String filepath)
	{
		filePath=filepath;
		LoadConnectionDeatils();
        //String remoteFile = "/home/centos/IDMTesting/newfile.txt";
        //remoteFile = "/home/centos/IDMTesting/TestFile.json";    
        //ArrayList list = new ArrayList(50);
		String list = "";
        String value="";
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("Establishing Connection...");
            session.connect();
            System.out.println("Connection established.");
            System.out.println("Crating SFTP Channel.");
			//filePath="ibc/sites/ATT03/lhost.yml";
            ExtentCucumberAdapter.addTestStepLog("Connected to the host: '" + filePath + "'");
			System.out.println("Connected to the host: '" + filePath + "'");
            ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();
            System.out.println("SFTP Channel created.");
            ExtentCucumberAdapter.addTestStepLog("Connected to the host: '" + host + "'");
            InputStream inputStream = sftpChannel.get(filePath);
            ExtentCucumberAdapter.addTestStepLog("Reading the data from file: '" + filePath + "'"); 
            try (Scanner scanner = new Scanner(new InputStreamReader(inputStream))) {
                while (scanner.hasNextLine()) {
                	String line = scanner.nextLine();                	
                     
                	list=list+line;   	
                    System.out.println(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	//	Reporter.addStepLog("Value read from file for is: '" + list + "'");
        return list;
	}

	
	private static String readChannelOutput(Channel channel){

	    byte[] buffer = new byte[1024];
	    String line = "";

	    try{
	        InputStream in = channel.getInputStream();
	      
	        while (true){
	   System.out.println("#@#@#@#@#@#@#@#INSIDE READCHANNEL WHILE_1 LOOP****");
	 		   	       	
	        	//boolean z = false;
	            while (in.available() > 0) {
	    System.out.println("#@#@#@#@#@#@#@#INSIDE READCHANNEL WHILE-2 LOOP****");     	
	                int i = in.read(buffer, 0, 1024);
	                if (i < 0) {
	                    break;
	                }
	                line = new String(buffer, 0, i);
System.out.println("Read output ************************************************"+line+"########**************");
	             //  z= true;    
	            }
	   System.out.println("#@#@#@#@#@#@#@#OUTSIDE READCHANNEL WHILE_2 LOOP****");
	            if(line.contains("use_https:")){
	                             System.out.println("LINE CONTAINS USE_HTTPS------BREAK");       	
	                break;
	            }

	            if (channel.isClosed()){
	                break;
	            }
	            try {
	                Thread.sleep(1000);
	            } catch (Exception ee){}
	           
	  /*   if(z) {
	  System.out.println("#@#@#@#@#@#@#@#BREAK READCHANNEL WHILE_1 LOOP****");	    	 
	         break;
	         }*/
	        }
System.out.println("#@#@#@#@#@#@#@#OUTSIDE READCHANNEL WHILE_1 LOOP****");	        
	    }catch(Exception e){
	        System.out.println("Error while reading channel output: "+ e);
	    }
	    
 return line;
	}

	
	public static String runCommand(String command) {
		Session session =null;
		ChannelExec channel = null;
        try {
        	session = setupSshSession();
            session.connect();
            ExtentCucumberAdapter.addTestStepLog("Connected to the host: '" + host + "'");
            StringBuilder outputBuffer = new StringBuilder();
            channel = (ChannelExec) session.openChannel("exec");
            ((ChannelExec)channel).setCommand(command);
            InputStream commandOutput = channel.getInputStream();
            channel.connect();
            int readByte = commandOutput.read();
            ExtentCucumberAdapter.addTestStepLog("Executed the command on Remote machine");
            while(readByte != 0xffffffff)
            {
               outputBuffer.append((char)readByte);
               readByte = commandOutput.read();
            }
            ExtentCucumberAdapter.addTestStepLog("Command output: '" + outputBuffer.toString() + "'");
            System.out.println(outputBuffer.toString());
            return outputBuffer.toString();

        } catch(Exception ex){
            closeConnection(channel, session);
            throw new RuntimeException(ex);

        } finally {
            closeConnection(channel, session);
        }
    }
	
	 private static Session setupSshSession() {
		 Session session=null;
		 try{
			 LoadConnectionDeatils();
	        session = new JSch().getSession(user, host, port);
	        session.setPassword(password);
	        java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
	        //session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
	        //session.setConfig("StrictHostKeyChecking", "no"); // disable check for RSA key
	       
		 }
		 catch(Exception ex){
	            ex.printStackTrace();
	        }
		 return session;
	    }

	    private static void closeConnection(ChannelExec channel, Session session) {
	        try {
	            channel.disconnect();
	        } catch (Exception ignored) {
	        }
	        session.disconnect();
	    }
	

}
