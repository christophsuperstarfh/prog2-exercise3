package weather.download;

import lombok.SneakyThrows;
import tk.plogitech.darksky.forecast.ForecastException;
import tk.plogitech.darksky.forecast.GeoCoordinates;

import java.net.ServerSocket;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Handler;

public class ParallelDownloader extends Downloader{

    @Override
    public int process(List<GeoCoordinates> geoCoordinatesList) throws ForecastException {
        long start = new Date().getTime();
        ExecutorService executor = Executors.newFixedThreadPool(geoCoordinatesList.size());

            executor.execute(() -> {
                int count = 0;
                for (GeoCoordinates coordinates : geoCoordinatesList) {
                    String fileName = null;
                    try {
                        fileName = save(coordinates);
                    } catch (ForecastException e) {
                        e.printStackTrace();
                    }
                    if(fileName != null)
                        count++;
                }

            });

executor.shutdown();
        long runningTime = new Date().getTime() - start;
        System.out.println("Parallel Downloader running time: " + runningTime);
        return 0;



    }
}
