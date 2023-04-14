import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;

/*
 * This is a Java program consisting of two classes. The main class is called HtmlParser and it contains two static vectors images and links. 
 * The program is designed to parse images and links from a website by a given URL or domain name. 
 * First, it reads an HTML document from the URL associated with the domain name then uses the MyParserCallbackTagHandler class to parse the HTML code for image and link elements. 
 * The program stores the resulting image and link URLs into the images and links vectors, respectively. These vectors can be used later, for example, saveToFile method can be run to save the URLs to a file. 


* The MyParserCallbackTagHandler class is a helper class that extends HTMLEditorKit.ParserCallback. 
* It implements the handleSimpleTag method, which is called for each HTML tag which checks for the IMG and LINK tags and adds the associated URLs to the vectors.
 */
public class HtmlParser 
{
	static Vector<String> images =  new Vector<String>();
	static Vector<String> links  =  new Vector<String>();	
	
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
	

