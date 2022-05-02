package weather.ctrl;


import tk.plogitech.darksky.api.jackson.DarkSkyJacksonClient;
import tk.plogitech.darksky.forecast.*;
import tk.plogitech.darksky.forecast.model.DailyDataPoint;
import tk.plogitech.darksky.forecast.model.DataPoint;
import tk.plogitech.darksky.forecast.model.Forecast;
import tk.plogitech.darksky.forecast.model.HourlyDataPoint;

import java.util.Comparator;
import java.util.List;

public class WeatherController {
    
    private String apiKey = "ab5c55091bfde0864c41b337f1c66af5";
    

    public void process(GeoCoordinates location) throws ForecastException {
        //System.out.println("process "+location); //$NON-NLS-1$

		
		//TODO implement Error handling 
		
		//TODO implement methods for
		// highest temperature 
		// average temperature 
		// count the daily values
		
		// implement a Comparator for the Windspeed 

        try {
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

            double avgTemp = (maxTemp + minTemp)/2;

            System.out.println("max weekly Temp: "+maxTemp);
            System.out.println("min weekly Temp: "+minTemp);
            System.out.println("avg weekly Temp: "+avgTemp);

            List<HourlyDataPoint> forecastWindList = data.getHourly().getData();

            double maxWindspeed = forecastWindList.stream()
                    .max(Comparator.comparing(DataPoint::getWindSpeed))
                    .get()
                    .getWindSpeed();

            System.out.println("max daily windspeed:" + maxWindspeed);
        }
        catch (ForecastException fe){
                fe.printStackTrace();
        }
	}
    
    
    public Forecast getData(GeoCoordinates location) throws ForecastException {
		ForecastRequest request = new ForecastRequestBuilder()
                .key(new APIKey(apiKey))
                .location(location)
                .build();

        DarkSkyJacksonClient client = new DarkSkyJacksonClient();

        Forecast forecast = client.forecast(request);

        //System.out.println("The current weather: " + forecast.getCurrently().getSummary());
        return forecast;
    }
}
