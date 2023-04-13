import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.*;

public class WebCrawler extends JPanel 
{
	JList<String> 				list;
	DefaultListModel<String> 	model;
	
	JTextField 	urlTextField;
	JLabel 		urlLabel;
	JButton 	goButton;
	JScrollPane pane;
	JSpinner spin;
	Vector<UrlWithDistance> urls = new Vector<UrlWithDistance>();

	
	public WebCrawler() 
	{
		
		model= 			new DefaultListModel<String>();
	    list=			new JList<String>(model);
	    pane=			new JScrollPane(list);
	    spin=			new JSpinner();
	    urlLabel=		new JLabel("Enter Seed URL");
	    urlTextField=	new JTextField();
	    urlTextField.setText("https://www.Newegg.com/");
	    SeedHandler seedHandler= new SeedHandler();
	    goButton=		new JButton("GO!");
	    goButton.addActionListener(seedHandler);
	    setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	    pane.setPreferredSize(new Dimension(1000,600));
	    add(pane);
	    add(urlLabel);
	    add(urlTextField);
	    add(spin);
	    add(goButton);
		
	}
	/* This class makes sure the radius is 1 to 4 when the go button is pressed. 
	 * Resets the default list model
	 * and calls the startCrawl method.
	*/
	private class SeedHandler implements ActionListener //
	{
		
		public void actionPerformed(ActionEvent e)
		{
			String url;
			int spinNum;
			model.removeAllElements();

			url = urlTextField.getText();
			spinNum=(int) spin.getValue();
			
			if(spinNum<1 || spinNum>5)
			{
				JOptionPane.showMessageDialog(null, "Please try radius of 1 to 4");
				spin.setValue(1);
				
			}
			else 
			{
				System.out.println("Seed Url: " + urlTextField.getText()
												+ "\nRadius: "+ spinNum);
				startCrawl(url,spinNum);
			}
		}
	}//End of SeedHandler
	
	/* startCrawl First adds the seed web address to the urls vector.
	 * Then, it calls parseDomain from my HtmlParser class and add new links to the urls vector
	 * Next, it adds the images to the list model and links to the urls vector.
	 * It checks for duplicate links but not duplicate images, and continues to loop 
	 * until it runs out of links within the given radius 
	 * 
	 * There's something wrong with my do while loop because 
	 * it keeps throwing an ArrayIndexOutOfBoundsException
	 */
	public void startCrawl(String url, int radius)
	{
		try
		{
				
			int UrlsCurrentIndex	=0;
			UrlWithDistance uwd =new UrlWithDistance(url);
			
			urls.add(uwd);
			Iterator<UrlWithDistance> i = urls.iterator();
			do
			{
				String 	tempUrl			=	urls.elementAt(UrlsCurrentIndex).url;
				int 	tempDistance	=	urls.elementAt(UrlsCurrentIndex).distance;
				
				System.out.println("current index: " + UrlsCurrentIndex + " size: " + urls.size()+" distance: "+tempDistance);
				
				UrlsCurrentIndex++;
				
				if(tempDistance <=radius) 
				{
					HtmlParser.parseDomain(tempUrl);
					
					System.out.println("\nFinished Checking: "+ tempUrl+" \n"+tempDistance+" links away from seed\n"+
							HtmlParser.images.size() + " Images Found: \n" + HtmlParser.images + 
							"\n" + HtmlParser.links.size()+" Links Found:\n"+ HtmlParser.links);
					
					for(int n=0; n<HtmlParser.images.size(); n++)
					{
						model.addElement(HtmlParser.images.elementAt(n));;
					}
					for(int n=0; n<HtmlParser.links.size(); n++)
					{
						UrlWithDistance tempUWD = new UrlWithDistance(HtmlParser.links.elementAt(n).toString(),tempDistance);
						if(urls.contains(tempUWD)==false)
						{
							tempUWD.distance++;
							urls.addElement(tempUWD);
						}
					}
				}
			}while(i.hasNext());
		}
		catch(ArrayIndexOutOfBoundsException oops)
		{
			System.out.println("I think my do while loop is causing this problem");
			oops.printStackTrace();
		}
		
	
	}//End of startCrawl
		
	public static void setupFrame()
	{
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new WebCrawler());
		frame.setSize(1000,700);
		frame.setVisible(true);
	}
	
	public static void main (String args[])
	{
		WebCrawler.setupFrame();
	}

}//End Of WebCrawler Class


