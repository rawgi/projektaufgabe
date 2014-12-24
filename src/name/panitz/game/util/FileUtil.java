package name.panitz.game.util;

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

	public static void main(String[] args) {
		for (String l : readTextLines("test.txt")) {
			System.out.println(l);
		}
	}
}
