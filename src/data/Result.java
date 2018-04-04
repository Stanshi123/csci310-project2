package data;

import java.awt.image.BufferedImage;

import Server.Filter;
import data.ResultType.ResultType;

public class Result {
	ResultType resultType = null;
	public String keyword = null;
	public String errorMessage = null;
	public BufferedImage collageImage = null;
	public int width = -1;
	public int height = -1;
	public Filter filter = null;
	public boolean border = false;
	public boolean rotation = false;
	public String shape = null;

	public Result(ResultType resultType, String keyword, String errorMessage) {
		this.resultType = resultType;
		this.keyword = keyword;
		this.errorMessage = errorMessage;
	}

	public Result(ResultType resultType, String keyword, BufferedImage collageImage) {
		this.resultType = resultType;
		this.keyword = keyword;
		this.collageImage = collageImage;
	}

	public Result(ResultType resultType, String keyword, String errorMessage, int width, int height, Filter filter, boolean border, boolean rotation, String shape) {
		this.resultType = resultType;
		this.keyword = keyword;
		this.errorMessage = errorMessage;
		this.width = width;
		this.height = height;
		this.filter = filter;
		this.border = border;
		this.rotation = rotation;
		this.shape = shape;
	}
	
	public Result(ResultType resultType, String keyword, BufferedImage image, int width, int height, Filter filter, boolean border, boolean rotation, String shape) {
		this.resultType = resultType;
		this.keyword = keyword;
		this.collageImage = image;
		this.width = width;
		this.height = height;
		this.filter = filter;
		this.border = border;
		this.rotation = rotation;
		this.shape = shape;
	}
	
	public String getKeyword() {
		return keyword;
	}
	
	public BufferedImage getCollageImage() {
		return collageImage;
	}
	
	public String getErrorMessage() {		// if success, holds imageFilePath; if failure, holds error message
		return errorMessage;
	}
	
	public boolean isSuccess() {
		return resultType == ResultType.success;
	}

	public ResultType getResultType() {
		return resultType;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Filter getFilter() {
		return filter;
	}

	public boolean isBorder() {
		return border;
	}

	public boolean isRotation() {
		return rotation;
	}

	public String getShape() {
		return shape;
	}

}
