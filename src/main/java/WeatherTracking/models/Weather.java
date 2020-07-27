package WeatherTracking.models;

public class Weather {
    private String date;
    private String dayOfWeak;
    private String temperature;
    private String humidity;
    private String wind;

    public Weather(String date, String dayOfWeak, String temperature, String humidity, String wind) {
        this.date = date;
        this.dayOfWeak = dayOfWeak;
        this.temperature = temperature;
        this.humidity = humidity;
        this.wind = wind;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDayOfWeak() {
        return dayOfWeak;
    }

    public void setDayOfWeak(String dayOfWeak) {
        this.dayOfWeak = dayOfWeak;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "date='" + date + '\'' +
                ", dayOfWeak='" + dayOfWeak + '\'' +
                ", temperature='" + temperature + '\'' +
                ", humidity='" + humidity + '\'' +
                ", wind='" + wind + '\'' +
                '}';
    }
}
