package monster.java.server.net;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageProtocol {
	
	private static Pattern movePattern = Pattern.compile("([a-z]*):(\\d*),(\\d*)");
	
	public static void process(NetworkPlayer client, String line) throws IOException {
		
		for (String msg : line.split(";")) {
			
			if (msg.startsWith("mv:")) {
				
				processMove(client, msg);
				
			}
			
		}
		
	}
	
	private static void processMove(NetworkPlayer client, String mvMsg) throws IOException {
		
		Matcher matcher = movePattern.matcher(mvMsg);
		
		
		
	}

}
