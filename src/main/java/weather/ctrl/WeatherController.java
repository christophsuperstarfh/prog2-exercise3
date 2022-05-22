package weather.ctrl;


import tk.plogitech.darksky.api.jackson.DarkSkyJacksonClient;
import tk.plogitech.darksky.forecast.*;
import tk.plogitech.darksky.forecast.model.DailyDataPoint;
import tk.plogitech.darksky.forecast.model.DataPoint;
import tk.plogitech.darksky.forecast.model.Forecast;
import tk.plogitech.darksky.forecast.model.HourlyDataPoint;

import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;

public class WeatherController {

    public static String apiKey = "ab5c55091bfde0864c41b337f1c66af5";


    public void process(GeoCoordinates location) throws MyException {
        //System.out.println("process "+location); //$NON-NLS-1$


        //TODO implement Error handling

        //TODO implement methods for
        // highest temperature
        // average temperature
        // count the daily values

        // implement a Comparator for the Windspeed


        Forecast data = getData(location);
        System.out.println("Temperature: " + data.getCurrently().getTemperature());
        List<DailyDataPoint> forecastList = data.getDaily().getData();

        double maxTemp = forecastList.stream()
                .max(Comparator.comparing(DailyDataPoint::getTemperatureHigh))
                .get()
                .getTemperatureMax();

        double minTemp = forecastList.stream()
                .min(Comparator.comparing(DailyDataPoint::getTemperatureLow))
                .get()
                .getTemperatureLow();



        double sumLow = forecastList.stream()
                .mapToDouble(DailyDataPoint::getTemperatureLow)
                .sum();

        double sumHigh = forecastList.stream()
                .mapToDouble(DailyDataPoint::getTemperatureHigh)
                .sum();

        double n = forecastList.stream()
                        .count();

        double avgTemp = (sumLow + sumHigh) / (2*n);

        DecimalFormat df = new DecimalFormat("0.00");

        System.out.println("max weekly Temp: " + df.format(maxTemp) + " °C");
        System.out.println("min weekly Temp:" + df.format(minTemp)+ " °C");
        System.out.println("avg weekly Temp: " + df.format(avgTemp)+ " °C");

        List<HourlyDataPoint> forecastWindList = data.getHourly().getData();

        double maxWindspeed = forecastWindList.stream()
                .max(Comparator.comparing(DataPoint::getWindSpeed))
                .get()
                .getWindSpeed();

        System.out.println("max daily windspeed: " + df.format(maxWindspeed) + " km/h");


    }


    public Forecast getData(GeoCoordinates location) throws MyException {

        ForecastRequest request = new ForecastRequestBuilder()
                .key(new APIKey(apiKey))
                .location(location)
                .build();

        DarkSkyJacksonClient client = new DarkSkyJacksonClient();

        Forecast forecast = null;
        try {
            forecast = client.forecast(request);
        } catch (ForecastException e) {
            throw new MyException("Forecast error");
        }


        return forecast;

        //System.out.println("The current weather: " + forecast.getCurrently().getSummary());

    }
}
