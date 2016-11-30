# lyric search
Lyric search project

# TA recommendation
- Given that there are 3 of you and you’re a ragtag group of smart lads, we’d like you to really try to implement the YouTube links functionality (i.e., no longer “potentially”)
- How specifically will the user interact with your program?  i.e., command line input and output?  Java Swing gui?  Some other gui
- Look into JSoup as a potential solution for scraping.

# Project Plan
- Scrape a lyric website to create a database of lyrics. Using a search, users can match a query to generate a list of songs that contain the lyrics
. Return youtube links to the songs.
- Use JSoup to scrape a lyric website. 
	- Sameer tested it out and it doesn't seem too difficult to get scrape lyrics
- Use Lucene for search algorithms http://lucene.apache.org/core/
- Java serialization to store the data http://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html
	
# Jobs breakdown:
- scraping information (JSoup)
- convert stored data to Java serializable 		
- user input and search (lucene)
- output results // link to youtube video 	
- Testing

# Things to figure out
- figure out how to navigate the website for scraping
	- www.azlyrics.com
	- www.azlyrics.com/a.html // alphabetized by artist name 
	- www.alyrics.com/a/aaliyah.html // artist page -- contains a list of all their songs in the data base
	- www.azlyrics.com/lyrics/aaliyah/intro.html // the song page. the url changes format a bit  
		- in html: ArtistName = "AALIYAH"; 
		- SongName = "intro";
		- lyrics are after a line of text in the html saying Usage of azlyrics.com content by any third-party lyrics provider is prohibited by our liscensing agreement. Sorry about that. 
		- lyrics end then a line of html with " MxM banner" is after it. 

# Ideas/thoughts
- Decide if we want a gui or command line.
- Can we build a simple search bar similar to google search using Java Swing?

#JSoup

#Lucene
-https://lucene.apache.org/core/6_3_0/core/overview-summary.html



