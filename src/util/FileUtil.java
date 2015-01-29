package util;

import java.io.*;
import java.util.*;

public class FileUtil {
	public static String[] readTextLines(String fileName) {
		try {
			return readTextLines(new FileReader(fileName));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static String[] readTextLines(InputStream fileStream) {
		return readTextLines(new InputStreamReader(fileStream));
	}

	public static String[] readTextLines(Reader fileReader) {
		try {
			List<String> result = new LinkedList<>();
			BufferedReader in = new BufferedReader(fileReader);
			String line = null;
			while ((line = in.readLine()) != null) {
				result.add(line);
			}
			return result.toArray(new String[0]);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static void writeFile(String fileName, String[] content){
		try(FileWriter fw = new FileWriter(new File(fileName))){
			for(String curLine: content){
				fw.write(curLine+"\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<File> getFileList() throws IOException{
		return getFileList(new File("."));
	}
	
	public static List<File> getFileList(String fileName) throws IOException{
		return getFileList(new File(fileName));
	}
	
	public static List<File> getFileList(File startFile) throws IOException{		
		return getFileList(startFile, new ArrayList<File>());
	}
	
	public static List<File> getFileList(File startFile, List<File> result) throws IOException{

		if(startFile.isDirectory()){
			for(File file: startFile.listFiles()){
				getFileList(file, result);
			}
		}
		result.add(startFile);
		
		return result;
	}
	
	public static String[] getFileListAsStringArray(String startFile) throws IOException{
		List<File> files = getFileList(new File(startFile), new ArrayList<File>());
		String[] names = new String[files.size()-1];
		for(int x = 0; x < files.size()-1; x++){
			names[x] = files.get(x).getName();
		}
		return names;
	}
}


















