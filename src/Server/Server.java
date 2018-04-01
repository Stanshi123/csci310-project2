package Server;

import java.util.*;
import java.sql.Timestamp;

import data.Constants;
import data.Result;
import data.ResultType.ResultType;
import java.awt.image.BufferedImage;

public class Server {

    private static Server server_instance;

    private Server() {}

    static{
    	server_instance = new Server();
    }

    public static Server getInstance(){
        return server_instance;
    }
	
	Map<String,Result> savedResults = new HashMap<String,Result>();
	
	public Result getResultForKeyword(String keyword,String shape, int width, int height, String filterString,
			boolean rotation, boolean border) {
		Result result = null;
//		if (checkIfResultExistsForKeyword(keyword)) {
//			result = retrieveResultForKeyword(keyword);
//		} else {
			
			result = createResultForResponse(keyword, shape, width, height, filterString, rotation, border);
			
		//}
		saveResultForKeyword(keyword, result);
		return result;
	}
	
	//saveResultForKeyword
	private void saveResultForKeyword(String keyword, Result result) {
			savedResults.put(keyword, result);
		}
	
	//checkIfResultExistsForKeyword
	private boolean checkIfResultExistsForKeyword(String keyword) {
		if (savedResults.containsKey(keyword)) {
			return true;
		}
		else return false;
	}
	//retriveResultForKeyword
	private Result retrieveResultForKeyword(String keyword) {
		return savedResults.get(keyword);
	}
	
	//createResultForResponse
	private Result createResultForResponse(String keyword,String shape, int width, int height, String filterString,
			boolean rotation, boolean border) {
		Timestamp requestTime = new Timestamp(System.currentTimeMillis());
		System.out.println(keyword + " requested: " + requestTime);
		APICommunicator comm = new APICommunicator(keyword);
		//checksufficient
		List<BufferedImage> images = new ArrayList<>();
		images = comm.getImages();
		Filter filter = null;
		switch(filterString)
		{
			case "No filter":
			{
				filter = null;
				break;
			}
			case "Black and White":
			{
				filter = Filter.BLACK_AND_WHITE;
				break;
			}
			case "Grayscale":
			{
				filter = Filter.GREY_SCALE;
				break;
			}
			case "Sepia":
			{
				filter = Filter.SEPIA;
				break;
			}
		}
		
		
		boolean suff = false;
		if (images.size() == 30) {
			suff = true;
		}
		if (suff == true) {
		//send to collage builder
			CollageBuilder cb = new CollageBuilder(images,shape);
			BufferedImage resultImage = cb.createCollageWithImages(width, height, border, rotation, filter);
			Result succsessfulResult = new Result(ResultType.success, keyword, resultImage);
			return succsessfulResult;
		}
		else {
		 	Result failureResult = new Result(ResultType.failure, keyword, Constants.ERROR_MESSAGE);
		 	return failureResult;
		 }
		 
	}
	
}