package WeatherTracking.controllers;


import WeatherTracking.models.*;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.*;

@Controller
public class WeatherController {
    private Map<String, WeatherResource> weatherResources;
    private Map<String, List<Weather>> cacheWeather;
    private String dayOfLastCacheUpdate;

    @Autowired
    public WeatherController(Map<String, WeatherResource> weatherResources) {
        this.weatherResources = weatherResources;
        this.cacheWeather = new HashMap<>();
    }

    public Map<String, WeatherResource> getWeatherResources() {
        return weatherResources;
    }

    @GetMapping(value = "/weather")
    @ResponseBody
    public List<Weather> showWhether(@RequestParam(value = "resource", required = false) String resource,
                            @RequestParam(value = "city", required = false) String city) throws IOException {
        checkCacheRelevance();
        if (cacheWeather.containsKey(resource + city)) {
            return cacheWeather.get(resource + city);
        } else {
            cacheWeather.put(resource + city, weatherResources.get(resource).getWeather(city));
            return cacheWeather.get(resource + city);
        }
    }

    private void checkCacheRelevance() {
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        if (dayOfLastCacheUpdate != null && !dayOfLastCacheUpdate.equals(String.format("%1td", c.getTime()))) {
            dayOfLastCacheUpdate = String.format("%1td", c.getTime());
            cacheWeather.clear();
        } else {
            dayOfLastCacheUpdate = String.format("%1td", c.getTime());
        }
    }


}
