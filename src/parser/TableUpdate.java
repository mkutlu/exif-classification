package parser;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.HashMap;

public class TableUpdate {
	
	public static void main(String[] args) {
		organize("C:\\Users\\MesutKutlu\\Desktop\\Flick_rPhotos\\brand\\apple\\iphone_6\\27996606078.jpg.txt");
	}
	public static void organize(String fileLocation) {
		String[] photoId = fileLocation.split("\\\\");
		String photoid = photoId[photoId.length-1].substring(0,photoId[photoId.length-1].indexOf("."));
		ExifParser ep = new ExifParser();
		try {
			ep.markerParser(fileLocation);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HashMap<String,String> allSegments = ep.all_segments;
		for (String key : allSegments.keySet()) {
			if(key.equals("APP1")) {
				//System.out.println(key+"\n");
				ep.APP1_parser(allSegments.get(key));
				DBManager dbm = new DBManager();
				Connection connection = dbm.openConnection("user", "1");
				int columnNumber = dbm.getColumnNumber(connection);
				int attrNumber = ep.map.size();
				System.out.println(columnNumber+"  "+attrNumber);
				if(columnNumber<attrNumber) {
					for(int i=1;i<=attrNumber-columnNumber;i++) {
						String column = "s"+Integer.toString((columnNumber+i));
						dbm.addColumn(connection, column);
						System.out.println("added  :s"+Integer.toString((columnNumber+i)));
					}
				}
				dbm.addNewRow(connection, photoid);
				int count = 1;
				for (String val : ep.map) {
					dbm.insertHeaderData(connection, "s"+String.valueOf(count), val,photoid);
					count++;
				}
			}
			
		}
	}
}
