package parser;


import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class ExifParser {
	public HashMap<String,String> IFD1_map = new HashMap<>();
	public HashMap<String,String> IFD0_map = new HashMap<>();
	public HashMap<String,String> subIFD_map = new HashMap<>();
	public HashMap<String,String> makerIFD_map = new HashMap<>();
	public HashMap<String,String> gpsIFD_map = new HashMap<>();
	public HashMap<String,String> interopIFD_map = new HashMap<>();
	public HashMap<String,String> all_segments = new HashMap<>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public void markerParser(String file) {
		StringBuilder sb = new StringBuilder();
		Scanner scanner = null;
		String segment_name;
		String segment_content;
		boolean start = true;
		scanner = new Scanner(new File(file));
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if(line.contains("*** Marker: ")) {
				if(!start) {
					segment_content = sb.toString();
					all_segments.put(segment_name, segment_content);
				}
				segment_name = line.substring(line.indexOf("Marker")+8, line.substring(line.indexOf("Marker")+8).indexOf(" "));
				
			}
			else {
				if(!start) {
					sb
				}
			}
		}
	}

}
