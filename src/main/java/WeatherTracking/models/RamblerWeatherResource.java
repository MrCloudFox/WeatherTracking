package WeatherTracking.models;


import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("rambler")
public class RamblerWeatherResource extends WeatherResource {

    private Map<String, String> cityAsPartOfUrl;


    public RamblerWeatherResource(){
        cityAsPartOfUrl = new HashMap<>();
        initUrlsMap();
    }
    /*
     Name of city as part of url.
     Name of city come from client on rus lang.
    */
    public void initUrlsMap() {
        cityAsPartOfUrl.put("chelyabinsk", "chelyabinske");
        cityAsPartOfUrl.put("ekaterinburg", "ekaterinburge");
        cityAsPartOfUrl.put("moscow", "moskve");
    }

    @Override
    public List<Weather> getWeather(String city) throws IOException {
        /*
            Extrecting weather on a week ahead
         */
        Elements elements = getElementsFromPage("https://weather.rambler.ru/v-" + cityAsPartOfUrl.get(city) + "/?updated", "DhEW");
        /*
            Today weather placed on separate block, so we extracting it separate
         */
        Elements elementsNowWeather = getElementsFromPage("https://weather.rambler.ru/v-moskve/now/", "_36_6");

        List<Weather> weather = new ArrayList<>();
        String[] temp;

        Matcher mNow = Pattern.compile("\\d{2}:\\d{2}.{1,40}%").matcher(elementsNowWeather.text()); // on today
         /*
            Extracting the date, temperature, humidity, wind
         */
        Date date = new Date();
        if (mNow.find()) {
            String now = String.format("%1$td %1$tB %1$tA", date);
            temp = mNow.group().split(" ");
            weather.add(new Weather(now.split(" ")[0] + " " + now.split(" ")[1],
                    now.split(" ")[2], temp[1].startsWith("-") ? temp[1].substring(0, 3): "+" + temp[1].substring(0, 2), temp[8].substring(0, temp[8].length()-1), temp[4]));
        }

        Matcher m = Pattern.compile("\\S+\\d{1,2}.{1,30}м\\/с").matcher(elements.text()); // tomorrow and further

        /*
            First day (tomorrow) extracting day of week as "завтра"
         */
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);

        String tomorrow = String.format("%1$tA", c.getTime());
        while (m.find()) {
            temp = m.group().split(" ");
            weather.add(new Weather(temp[0].replaceAll("\\W","") + " " + temp[1],
                    temp[0].replaceAll("\\d", "").toLowerCase().equals("завтра")? tomorrow : temp[0].replaceAll("\\d", "").toLowerCase(),
                    temp[2].startsWith("-") ? temp[2].substring(0, 3) : "+" + temp[2].substring(0, 2), temp[3].substring(0, temp[3].length()-1), temp[4]));

        }
        return weather;
    }
}
