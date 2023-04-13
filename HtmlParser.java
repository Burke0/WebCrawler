import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;


public class HtmlParser 
{
	static Vector<String> images =  new Vector<String>();
	static Vector<String> links  =  new Vector<String>();
	
	//This is the driver code for testing project 2 part a HtmlParser
	/* public static void main(String[] x)
	{
	
		String d = "https://www.newegg.com";	
		String s = "https://www.google.com";
		
		HtmlParser.parseDomain(d);
		System.out.println(HtmlParser.images.size() + " Images Found: \n" + 
				HtmlParser.images + "\nLinks Found:\n"+ HtmlParser.links);
		HtmlParser.saveToFile("images_test", false);
		
		HtmlParser.parseDomain(s);
		HtmlParser.saveToFile("images_test_2.txt",false);
		System.out.println(HtmlParser.images.size() + " Images Found: \n" + 
				HtmlParser.images + "\nLinks Found:\n"+ HtmlParser.links);
	}*/
	
	public static void parseDomain(String domain)
	{
		
		
		URL                         url;
		URLConnection               urlConnection;
		InputStreamReader           isr;
		MyParserCallbackTagHandler  tagHandler = null;
		String                      d;
	
		d=domain;
		
		try
		{
		    url = new URL(d);
		
		    urlConnection = url.openConnection();
		    isr = new InputStreamReader (urlConnection.getInputStream());
		
		    tagHandler = new MyParserCallbackTagHandler(d);
		     
		    new ParserDelegator().parse(isr, tagHandler, true);
		    
	    }
		catch (MalformedURLException mue){
		    System.out.println("parseDomain: Bad URL");
		    mue.printStackTrace();
		}
		catch (IOException ioe){
		    System.out.println("parseDomain: Some IO Exception...");
		    ioe.printStackTrace();
		}

		images=tagHandler.images;
		links=tagHandler.links; 
		
	}//end of parseDomain
	
	public static void saveToFile(String fileName, Boolean append)
	{
		try 
		{
			BufferedWriter bw=new BufferedWriter(new FileWriter(fileName, append));
			for(int i=0; i<images.size(); i++) 
			{
				String t=images.elementAt(i).toString();
				bw.write(t);	
				bw.newLine();
			}
			bw.flush();
			bw.close();
		System.out.println("File save complete");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}//End of saveToFile
	
}//End of HtmlParser Class
		
////////////////////////////////////////////////////////////////////////////////////////

class MyParserCallbackTagHandler extends HTMLEditorKit.ParserCallback
{
	
	String         baseDomain;
	Vector<String> images =  new Vector<String>();
	Vector<String> links  =  new Vector<String>();
	
	public MyParserCallbackTagHandler(String baseDomain)
	{
	
	this.baseDomain = baseDomain;
	}
	
	
	public void handleSimpleTag(HTML.Tag tag, MutableAttributeSet attSet, int pos)
	{ 
	
	Object          attribute;
	//Enumeration<?>  attributeEnum;
	
	// System.out.println("Found a tag: " + tag);
	
	if (tag == HTML.Tag.IMG )
	    {
	    System.out.println("Image Found");
	    attribute = attSet.getAttribute(HTML.Attribute.SRC);
	    if (attribute != null)
	        System.out.println("pos: " + pos + "  " + attribute.toString());
	    	images.add(attribute.toString());
	    }
	if (tag == HTML.Tag.LINK)
		{
			System.out.println("HTML Tag Found: ");
			attribute = attSet.getAttribute(HTML.Attribute.HREF);
	    if (attribute != null)
	        System.out.println("pos: " + pos + "  " + attribute.toString());
	    	links.add(attribute.toString());
		}
	
	}//End of handleSimpleTag

	
	
	
}//End of MyParserCallBackTagHandler
//////////////////////////////////////////////////////////////////////////////////////		
	

