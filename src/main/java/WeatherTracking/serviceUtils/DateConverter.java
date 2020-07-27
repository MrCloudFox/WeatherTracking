package WeatherTracking.serviceUtils;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class DateConverter {
    private static Map<String, String> dayOfWeekAbbreviation;

    public DateConverter() {
        dayOfWeekAbbreviation = new LinkedHashMap<>();
        initDayOfWeekAbbreviationRus();
    }

    @PostConstruct
    private void initDayOfWeekAbbreviationRus() {
        dayOfWeekAbbreviation.put("пн", "понедельник");
        dayOfWeekAbbreviation.put("вт", "вторник");
        dayOfWeekAbbreviation.put("ср", "среда");
        dayOfWeekAbbreviation.put("чт", "четверг");
        dayOfWeekAbbreviation.put("пт", "пятница");
        dayOfWeekAbbreviation.put("сб", "суббота");
        dayOfWeekAbbreviation.put("вс", "воскресенье");
    }

    public static Map<String, String> getDayOfWeekAbbreviation() {
        return dayOfWeekAbbreviation;
    }
}
