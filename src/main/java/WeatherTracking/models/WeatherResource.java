package WeatherTracking.models;

import WeatherTracking.models.Weather;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public abstract class WeatherResource {

    public Elements getElementsFromPage(String url, String className) throws IOException {
        Document doc = Jsoup.connect(url).get();

        return doc.getElementsByClass(className);
    }

    public abstract List<Weather> getWeather(String city) throws IOException;
}
