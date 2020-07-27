package WeatherTracking.models;

import WeatherTracking.serviceUtils.DateConverter;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("rumeteo")
public class RuMeteoWeatherResource extends WeatherResource {

    @Override
    public List<Weather> getWeather(String city) throws IOException {

        Elements elements = getElementsFromPage("https://ru-meteo.ru/" + city, "wrap_content wide");
        /*
            Extracting the date, temperature, humidity, wind
         */
        Matcher m = Pattern.compile("\\S{2}\\d{1,2}.{1,55}м\\/с.{1,15}%").matcher(elements.text());

        List<Weather> weather = new ArrayList<>();
        String[] temp;  // values from the mather group

        while (m.find()) {
            temp = m.group().split(" ");
            weather.add(new Weather(temp[0].replaceAll("\\W","") + " " + temp[1].toLowerCase(),
                    DateConverter.getDayOfWeekAbbreviation().get(temp[0].replaceAll("\\d", "")), temp[2].substring(0, 3),
                    temp[12].matches("\\d+") ? temp[12] : temp[11], temp[8].matches("\\d+") ? temp[8] : temp[7]));
        }
        return weather;
    }
}
