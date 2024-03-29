package ktsnwt_tim8.demo.helper;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import org.apache.commons.io.FileUtils;

public class Helper {

	public static String fromFileToBase64(String pathToFile) {
		if (pathToFile == null) {
			return null;
		}
		
		byte[] fileContent;
		try {
			fileContent = FileUtils.readFileToByteArray(new File(pathToFile));
		} catch (IOException e) {
			return null;
		}
		return Base64.getEncoder().encodeToString(fileContent);
	}
	
	
}
