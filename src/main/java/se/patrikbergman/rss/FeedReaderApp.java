package se.patrikbergman.rss;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * It Reads and prints any RSS/Atom feed type.
 * <p>
 *
 * @author Alejandro Abdelnur
 */
public class FeedReaderApp {

    private static List<String> urls =
            Arrays.asList(
                    "https://feeds.expressen.se/nyheter/",
                    "https://feeds.expressen.se/sport/",
                    "https://feeds.expressen.se/noje/",
                    "https://feeds.expressen.se/debatt/",
                    "https://feeds.expressen.se/ledare/",
                    "https://feeds.expressen.se/kultur/",
                    "https://feeds.expressen.se/halsoliv/",
                    "https://feeds.expressen.se/motor/",
                    "https://feeds.expressen.se/res/",
                    "https://feeds.expressen.se/gt/",
                    "https://feeds.expressen.se/kvallsposten/"
            );

//    private static List<String> urls =
//            Arrays.asList(
//                    "https://feeds.expressen.se/nyheter/",
//                    "https://feeds.expressen.se/gt/",
//                    "http://kvp.se/rss/nyheter",
//                    "http://expressen.se/rss/sport",
//                    "http://expressen.se/rss/noje",
//                    "http://expressen.se/rss/debatt",
//                    "http://expressen.se/rss/ledare",
//                    "http://expressen.se/rss/kultur",
//                    "http://expressen.se/rss/ekonomi",
//                    "http://expressen.se/rss/halsa",
//                    "http://expressen.se/rss/levabo",
//                    "http://expressen.se/rss/motor",
//                    "http://expressen.se/rss/res",
//                    "http://expressen.se/rss/dokument"
//            );

    static class FeedReader {
        public URL url(String s) {
            try {
                return new URL(s);
            } catch (MalformedURLException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }

        public XmlReader xmlReader(URL url) {
            try {
                return new XmlReader(url);
            } catch (IOException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }

        public SyndFeed syndFeed(XmlReader xmlReader) {
            try {
                SyndFeedInput input = new SyndFeedInput();
                return input.build(xmlReader);
            } catch (FeedException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }

        public static void main(String[] args) {
            try {

                FeedReader feedReader = new FeedReader();

                List foo = urls.stream()
                        .map(feedReader::url)
                        .map(feedReader::xmlReader)
                        .map(feedReader::syndFeed)
                        .map(feed -> feed.getEntries())
                        .flatMap(Collection::stream)
                        .sorted(Comparator.comparing(SyndEntry::getPublishedDate))
                        .map(entry -> entry.getTitle())
                        .distinct()
                        .collect(Collectors.toList());

                String stop = "";
//                URL feedUrl = new URL("https://feeds.expressen.se/nyheter");
//                XmlReader xmlReader = feedReader.xmlReader(feedUrl);
//                SyndFeed feed = feedReader.syndFeed(xmlReader);
//                System.out.println(feed.getEntries().size());



            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("ERROR: " + ex.getMessage());
            }
        }
    }
}
