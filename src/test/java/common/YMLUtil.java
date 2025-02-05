package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileFilter;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

//import com.cucumber.listener.Reporter;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;



public class YMLUtil {
	
	static ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
	// read YAML file
	static Map<String, Object> ymlData;
	
	static String YMLNode="";
	static List<Object> ymlObject = new ArrayList<>();
	//static Object ymlObject;
	static Object ymlObjectRepo;
	static Object ymlpayloadObject;
	
	public static String getYMLData(String key) {
		String output = "";
		
		try {		
		   // System.out.println(ymlObject); 
		   // Map map = (Map)ymlObject;
			 Map map = (Map)ymlObject.get(0);
			 try {
		    output=((Map)map).get(key).toString();
			 }catch(Exception e)
			 {
				 output=((Map)map.get(YMLNode)).get(key).toString(); 
			 }
		    if(output == null || output.isEmpty())
		    {
		    	output=((Map)map.get(YMLNode)).get(key).toString();
		    }	
			//output = ymlData.get(key).toString();
			if (output == null || output.isEmpty()) {
				output = key;
			}
			
			//System.out.println(output);
		} catch (Exception ex) {
			output = key;
		}
		if(output.toLowerCase().contains("multiplefilesearch."))
		{
			String[] splitKey=output.split("filesearch.");
			String mainKey=splitKey[1];
			for(int i=1;i<ymlObject.size();i++)
			{
				try {		
					   // System.out.println(ymlObject); 
					   // Map map = (Map)ymlObject;
					
						 Map map = (Map)ymlObject.get(i);
						 try
						 {
					    output=((Map)map).get(mainKey).toString();
						 }
						 catch(Exception e)
						 {
							 output=((Map)map.get(YMLNode)).get(mainKey).toString(); 
						 }
					    if(output == null || output.isEmpty())
					    {
					    	output=((Map)map.get(YMLNode)).get(mainKey).toString();
					    }	
						//output = ymlData.get(key).toString();
						if (output == null || output.isEmpty()) {
							output = key;
						}
						else
						{
							break;
						}
						
						//System.out.println(output);
					} catch (Exception ex) {
						output = key;
					}	
			}
		}
		return output;

	}
	
	public static String getYMLObjectRepositoryData(String key) {
		String output = "";
		try {
		String[] keys=key.split("[.]",0);
		  // System.out.println(ymlObject);
		  if(keys.length>1)
		{
		   Map map = (Map)ymlObjectRepo;
		   output=((Map)map.get(keys[0])).get(keys[1]).toString();
		  //output=(map.get(keys[1])).toString().trim();
		}
		else
		{
			 Map map = (Map)ymlObjectRepo;
			   output=((Map)map).get(key).toString();
		}

		//output = ymlData.get(key).toString();
		if (output == null || output.isEmpty()) {
		output = key;
		}
		//System.out.println(output);
		} catch (Exception ex) {
		output = key;
		}
		AutoHealUtil.XPath = output;
		AutoHealUtil.XPathKey = key;
		return output;
		}

	public static void updateYML(String key, String value) {
		try {
			if (ymlData.containsKey(key)) {
				ymlData.replace(key, value);
			} else {
				ymlData.put(key, value);
			}
			objectMapper.writeValue(new File("src/test/java/TestData.yml"), ymlData);
		} catch (Exception ex) {

		}
	}

	public static void loadYML(String path,String node) {
		
		File dir = new File(path);
		FileFilter fileFilter = new WildcardFileFilter("*.yml*");
		File[] files = dir.listFiles(fileFilter);
		for(int i=0; i<files.length;i++)
		{
		try {			
			if(files[i].getName().contains("TestData"))
			{
			YamlReader reader = new YamlReader(new FileReader(files[i]));
			ymlObject.add(reader.read());
			//ymlObject=reader.read();
		    YMLNode=node;
			//ymlData = objectMapper.readValue(new File("src/test/resources/TestData.yml"),
			//		new TypeReference<Map<String, Object>>() {
			//		});
			}
		} catch (Exception ex) {
System.out.println(ex);
		}
		}
	}
	public static void loadObjectRepoYML(String file) {
		try {			
			YamlReader reader = new YamlReader(new FileReader(file));
			ymlObjectRepo = reader.read(); 
			//ymlData = objectMapper.readValue(new File("src/test/resources/TestData.yml"),
			//		new TypeReference<Map<String, Object>>() {
			//		});
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
	
	public static void readObjectRepoYML(String... files) {
	    for (String file : files) {
	        String fileName = file.substring(file.lastIndexOf("/") + 1); // Extract the filename
	        try {
	            Yaml yaml = new Yaml();
	            FileInputStream inputStream = new FileInputStream(file);
	            Object data = yaml.load(inputStream);
	            System.out.println(fileName + " file is valid");
	        } catch (FileNotFoundException e) {
	            System.err.println("File not found: " + fileName);
	        } catch (YAMLException e) {
	            System.err.println(fileName + " file is invalid: " + e.getMessage());
	        }
	    }
	}
	
	public static void PayloadYML(String file,String node) {
		
		try {			
			YamlReader reader = new YamlReader(new FileReader(file));
			ymlpayloadObject = reader.read();
			Map map = (Map) ymlpayloadObject;
			int abc = ((Map) map).keySet().size();
			for (Object rule : ((Map) map).keySet()) {
				RestAssuredUtil.apiPayloadDictionary.put(rule.toString(), ((Map) map).get(rule).toString());
			}
			YMLNode = node;
		} catch (Exception ex) {

		}
	}

}
