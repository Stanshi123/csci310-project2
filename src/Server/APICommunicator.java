package Server;

import com.google.gson.Gson;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.*;

public class APICommunicator {
	// This is the keyword that we are searching for.
    private String keyword = "Default";

    // Static constants
    private static final int IMAGE_NUMBER = 30;
    private static final int API_REQUEST_THREAD_NUMBER = 4;
    private static final int URL_PROCESS_THREAD_NUMBER = 36;
    private static final int URL_TIMEOUT_LIMIT = 5000;
    private static final int THREAD_NUMBER = 40;
    private static final int SLEEP_TIME = 1000;
    private static final int ELEMENT_PER_REQUEST = 10;
    private static final int SLEEP_TIME_OUT = 20;
    // The constants below are for the request
    private static final String URL_REQUEST_PROPERTY_KEY = "User-Agent";
    private static final String URL_REQUEST_PROPERTY_VALUE = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) " +
            "AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31";
    private static final String REQUEST_URL = "https://www.googleapis.com/customsearch/v1";
    private static final String API_KEY = "AIzaSyDaJ74IGt2X5miRWhriFOImLkBSo1G_dNw";
    private static final String CX = "003668417098658282383:2ym3vezfm44";
    private static final String SEARCH_TYPE = "image";
    private static final String API_REQUEST_PROPERTY_KEY = "Content-Type";
    private static final String API_REQUEST_PROPERTY_VALUE = "application/json";

    // The images vector's add method are overridden, so that it won't exceed IMAGE_NUMBER images.
    // And it needs to be thread-safe. So we use vector.
    protected List<BufferedImage> images = new Vector<BufferedImage>() {
        @Override
        public boolean add(BufferedImage image) {
            return size() < IMAGE_NUMBER && super.add(image);
        }
    };
    // This is the blockingDeque which holds all the URLs
    protected BlockingDeque<URL> urls = new LinkedBlockingDeque<>();

    public APICommunicator() {
    	super();
    }
    /*
     * This is the constructor of the APICommunicator, it takes in one keyword and request Google Custom Search API
     * for result. It will add the result images to the image vector. Then the Servlet will call get images to get the
     * BufferedImages.
     */
    public APICommunicator(String keyword) {
        this.keyword = keyword;

        // Create an ExecutorService for the THREAD_NUBMER threads
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_NUMBER);

        // Assign API_REQUEST_THREAD_NUBMER threads to make API request
        for (int i = 0; i < API_REQUEST_THREAD_NUMBER; i++) {
            executor.submit(makeRequestRunnable(i));
        }

        // Assign URL_PROCESS_THREAD_NUMBER threads to download the urls into Bufferimages.
        for (int i = 0; i < URL_PROCESS_THREAD_NUMBER; i++) {
            executor.submit(makeUrlProcessorRunnable());
        }

        /*
         * Let the main thread count the number of image, if the number of image is not 
         * enough, make the thread sleep for SLEEP_TIME. If the number of image reaches
         * IMAGE_NUMBER, break out the loop and shut down all the thread.
         * Also set a SLEEP_TIME_OUT limit, so that if an error occur, it will not sleep 
         * forever.
         */
        int sleepTime = 0;
        while (images.size() < IMAGE_NUMBER && sleepTime < SLEEP_TIME_OUT) {
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
            }
            sleepTime++;
        }
        // Shut down all the threads.
        executor.shutdownNow();

    }
    
	/*
    // This change the processor to a runnable for multi-threading purpose
    private Runnable makeUrlProcessorRunnable() {
        return () -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    processUrlForImage(urls.takeFirst());
                } catch (InterruptedException e) {
                    return;
                }
            }
        };
    } */
    
    private Runnable makeUrlProcessorRunnable() {
    	UrlProcessorRunnable ur = new UrlProcessorRunnable();
    	return ur;
    }
    
    private class UrlProcessorRunnable implements Runnable{
		@Override
		public void run() {
			while (!Thread.currentThread().isInterrupted()) {
                try {
                    processUrlForImage(urls.takeFirst());
                } catch (InterruptedException e) {
                    return;
                }
			}
    	
		}
	}

    private class RequestRunnable implements Runnable {
    	
    	private int startIndex;
    	public RequestRunnable(int si) {
    		startIndex = si;
    	}
    	@Override
    	public void run() {
    		sendRequestForKeyWord(startIndex * ELEMENT_PER_REQUEST + 1);
    	}
    }
    
    private Runnable makeRequestRunnable(int startIndex) {
    	RequestRunnable rr = new RequestRunnable(startIndex);
    	return rr;
    }
    /*
    // This change the request to a runnable for multi-threading purpose
    private Runnable makeRequestRunnable(int startIndex) {
        return () -> sendRequestForKeyWord(startIndex * ELEMENT_PER_REQUEST + 1);
    } */

    // This method send request for keyword to the Google Custom Search API and retrieve 10 images back.
    protected void sendRequestForKeyWord(int startIndex) {

        // Create Query
        String url = REQUEST_URL;
        Map<String, String> parameters = new HashMap<>();
        parameters.put("q", keyword);
        parameters.put("key", API_KEY);
        parameters.put("cx", CX);
        parameters.put("start", Integer.toString(startIndex));
        parameters.put("searchType",SEARCH_TYPE);

        // Request the API using the query
        try {
            String query = getParamsString(parameters);

            HttpURLConnection connection = (HttpURLConnection) new URL(url + "?" + query).openConnection();
            connection.setRequestProperty(API_REQUEST_PROPERTY_KEY, API_REQUEST_PROPERTY_VALUE);

            connection.setConnectTimeout(URL_TIMEOUT_LIMIT);

            InputStream response = connection.getInputStream();

            // No response.
            if (response == null) return;

            try (Scanner scanner = new Scanner(response)) {
                String responseBody = scanner.useDelimiter("\\A").next();

                // parse the JSON
                Gson gson = new Gson();
                processAPIResponse(gson.fromJson(responseBody, APIResponse.class));
            }
        } catch (IOException e) {
        }
    }

    // This method extract image from response and add to the blocking dequeue
    protected void processAPIResponse(APIResponse apiResponse) {
        // Check NPE
        if (apiResponse.getItems() == null) return;

        // Add URLs to the blockingdeque
        for (Item item : apiResponse.getItems()) {
            try {
                final URL url = new URL(item.getLink());
                urls.add(url);
            } catch (MalformedURLException e) {
                return;
            }
        }
    }

    // Process url and generate image and add to the vector
    protected void processUrlForImage(URL url) {
        try {
            final HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestProperty(URL_REQUEST_PROPERTY_KEY, URL_REQUEST_PROPERTY_VALUE);

            connection.setConnectTimeout(URL_TIMEOUT_LIMIT);
            BufferedImage image = ImageIO.read(url.openStream());

            if (image != null) {
                images.add(image);
            }

        } catch (IOException e) {
        }

    }

    // The getter to get the images
    public List<BufferedImage> getImages() {
        return images;
    }
    
    public String getParamsString(Map<String, String> params)
            throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), java.nio.charset.StandardCharsets.UTF_8.name()));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), java.nio.charset.StandardCharsets.UTF_8.name()));
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }
}
