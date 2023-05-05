package lllll;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class abc {

	public static void main(String[] args) throws IOException {
		String text="";
		Path fileName = Path.of("D://angular/co_trigger.sql");
		for(int i=1;i<=5000;i++) {
			text+= " INSERT INTO elig_dtls VALUES("+i+",350,"+i+",NULL,'abc"+i+"',998398311,'2023-06-15','SNAP','2023-03-16','accepted');\n";
//			text+= "INSERT INTO co_trigger VALUES("+i+","+i+",NULL,'pending');\n";
		}
		Files.writeString(fileName, text);
		String file_content = Files.readString(fileName);
		System.out.println(file_content);
	}
}
