package parser;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ExifParser {
	public ArrayList<String> map = new ArrayList<>();
	public HashMap<String,String> all_segments = new HashMap<>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*try {
			markerParser("C:\\Users\\MesutKutlu\\Desktop\\Flick_rPhotos\\brand\\apple\\iphone_6\\27996606078.jpg.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for (String key : all_segments.keySet()) {
			if(key.equals("APP1")) {
				//System.out.println(key+"\n");
				APP1_parser(all_segments.get(key));
				for (String val : IFD0_map) {
					System.out.println(val);
				}
			}
			
		}*/
	}
	
	public void markerParser(String file) throws FileNotFoundException {
		StringBuilder sb = new StringBuilder();
		Scanner scanner = null;
		String segment_name="";
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
				String nameres = line.substring(line.indexOf("Marker")+8);
				segment_name = nameres.substring(0,nameres.indexOf(' '));
				sb = new StringBuilder();
				start= false;
			}
			else {
				if(!start) {
					sb.append(line+"\n");
				}
			}
		}
		scanner.close();
	}
	public void APP1_parser(String content) {
		Scanner scanner = null;
		String tag_segment = "";
		String tag_name;
		String tag_value;
		scanner = new Scanner(content);
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if(line.contains("EXIF")) {
				
				String nameres = line.substring(line.indexOf("EXIF")+5);
				tag_segment = nameres.substring(0,nameres.indexOf(' '));
			}
			else {
				if(line.contains("] =")) {
					String nameres = line.substring(line.indexOf("[")+1);
					tag_name = nameres.substring(0,nameres.indexOf(' '));
					tag_value = line.substring(line.indexOf(" = ")+3);
				
						map.add(tag_segment+"$"+tag_name+"$"+tag_value);
				}
			}
		}
		scanner.close();
	}
	
	

}
