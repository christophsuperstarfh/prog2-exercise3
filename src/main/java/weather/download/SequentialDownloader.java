package weather.download;

import tk.plogitech.darksky.forecast.ForecastException;
import tk.plogitech.darksky.forecast.GeoCoordinates;

import java.util.Date;
import java.util.List;

public class SequentialDownloader extends Downloader {

    @Override
    public int process(List<GeoCoordinates> geoCoordinatesList) throws ForecastException {
        long start = new Date().getTime();
        int count = 0;
        for (GeoCoordinates coordinates : geoCoordinatesList) {
            String fileName = save(coordinates);
            if(fileName != null)
                count++;
        }
        long runningTime = new Date().getTime() - start;
        System.out.println("Sequential Downloader running time: " + runningTime);
        return count;
    }
}
