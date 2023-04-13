/*
 * The purpose of this class is to keep track of the distance 
 * between each url and the seed url in my WebCrawler class.
 * UrlWithDistance Objects are used as element of the vector "urls" in WebCrawler.
 */
public class UrlWithDistance
{
	String url;
	int distance;
	
	 UrlWithDistance(String url, int distanceFromSeed)
	 {
		 this.url=url;
		 distance=distanceFromSeed;
	 }
	 UrlWithDistance(String url) //Constructor for seed
	 {
		 this.url=url;
		 distance=0;
	 }
	 
}
