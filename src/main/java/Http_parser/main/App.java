package Http_parser.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {	
    	System.out.println("working ...");
    	Writer writer = null;
	    
    	String output;
    	String responseString;
    	try {
            while (true) {
            	responseString="";
            	StringBuffer response = null;
            	output = new Date().toString();
                
            	try {
                	  response = requestSite();
				} catch (MalformedURLException e) {
					output+=",MalformedULR";
					e.printStackTrace();
				} catch (IOException e) {
					output+=",IOException";
					e.printStackTrace();
				}
                
            	responseString = parseHttp(response);
              	printLn(writer, output + responseString);
              	
            	Thread.sleep(20 * 1000);
            }
        } catch (InterruptedException e) {
        	printLn(writer, "InterruptedException :(");
            e.printStackTrace();
        }        
    }

	private static String parseHttp(StringBuffer response) {
		
		String res = "";
		int start = 0;
		int end = 0;
		
		start = response.indexOf("\"obywatelstwo\"}],") + 16;
		res = response.substring(start);
		
		end = res.indexOf("location\":\"Wroc") - 2;
		res = res.substring(0,end);
		
		return res;
	}

	private static StringBuffer requestSite() throws MalformedURLException, IOException, ProtocolException {
	
		URL url = new URL("http://rezerwacje.duw.pl/app/webroot/status_kolejek/query.php?status");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		
		BufferedReader in = new BufferedReader(
				  new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer content = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
				    content.append(inputLine);
				}
				in.close();
		
		con.disconnect();
		return content;
	}

	private static void printLn(Writer writer, String output) {
		try {
    		String filename = "File.txt";
    		FileOutputStream fs = new FileOutputStream(filename, true);
    	    writer = new BufferedWriter(new OutputStreamWriter(fs));
    
		writer.append(output);
		
		} catch (IOException ex) {
	    	System.out.println("file error");
	    } finally {
	       try {writer.close();} catch (Exception ex) {/*ignore*/}
	    }
	}
}
