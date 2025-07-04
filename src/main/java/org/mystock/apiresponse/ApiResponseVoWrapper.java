package org.mystock.apiresponse;

public class ApiResponseVoWrapper {
	
	public static <T> ApiResponseVo<T> success(String message, T data, Object metadata) {
		return new ApiResponseVo<>("success", message, data, metadata);
	}

	public static <T> ApiResponseVo<T> failure(String message, T data, Object metadata) {
		return new ApiResponseVo<>("failure", message, data, metadata);
	}

	public static <T> ApiResponseVo<T> error(String message, T data, Object metadata) {
		return new ApiResponseVo<>("error", message, data, metadata);
	}

}
