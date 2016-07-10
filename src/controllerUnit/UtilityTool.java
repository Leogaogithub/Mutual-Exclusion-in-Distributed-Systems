package controllerUnit;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author leo 
 *  email: longhaogao@gmail.com
 *
 * @date Apr 12, 2016
 * @version 1.0
 * 
 * @date June 8, 2016
 * @version 1.1
 */

public class UtilityTool {
	static String oneBlank = " ";
	static String twoBlanks = "  ";
	
	public static String getWordsAfter(String str, String where){
		int startIdx = str.indexOf(where);
		int endIdx = str.length();
		String midStr = str.substring(startIdx+where.length(), endIdx);
		return midStr.trim();
	}
	

	public static String getMatcher(String regex, String source) {  
        String result = "";  
        Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(source);  
        while (matcher.find()) {  
            result = matcher.group(1); 
        }  
        return result;  
    } 
	
	public static boolean checkStringNotEmpty(String item){
		if(item==null)
			return false;
		if(item.isEmpty())
			return false;
		if(item.equals(""))
			return false;
		return true;
	}	

	 
	public static String regularingLine(String str){		

		str = str.replaceAll("\n", oneBlank);
		str = str.replaceAll("\t", oneBlank);		
		if(str.contains("#")){
			str = str.substring(0, str.indexOf("#"));
		}
		//remove extral blank
		str = removeExtralBlank(str);		
		return str; 
	}
	
	
	public static String removeExtralBlank(String str){
		str = str.trim();
		while(str.contains(twoBlanks)){
			str = str.replaceAll(twoBlanks,oneBlank);
		}		
		return str;
	}
	
	
	public  static void line(String cont){
		System.out.println(cont);
	}
	
	public static boolean isValidLine(String str){		
		str = str.trim();
		if(!checkStringNotEmpty(str)) return false;		
		if(str.startsWith("#")) return false;
		return true;		
	}

	public static String byteToString(ByteBuffer byteBuffer, int MESSAGE_SIZE)	
	{		
		byteBuffer.flip();
		byteBuffer.position(0);
		byteBuffer.limit(MESSAGE_SIZE);
		byte[] bufArr = new byte[byteBuffer.remaining()];		
		for(int i = 0; i < byteBuffer.remaining(); i++){
			bufArr[i] = '\0';
		}
		byteBuffer.get(bufArr);
		String result = new String(bufArr);
		result = result.substring(0, result.indexOf('\0'));		
		return result;
	}
	
	//greater id is client
	public static boolean preIsClient(int preId, int postId){
		if(preId > postId){
			return true;
		}
		return false;
	}       
}
