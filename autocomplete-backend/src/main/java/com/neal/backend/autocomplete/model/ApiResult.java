package com.neal.backend.autocomplete.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResult<T> {
  private String stateCode;
  private String message;
  private T data;



  /**
   * failed result
   * @param stateCode
   * @param message
   * @return
   */
  public ApiResult(String stateCode, String message){
    this(stateCode,message,null);
  }

  /**
   * succeeded result
   * @param data
   * @return
   */
  public ApiResult(T data){
    this("200","",data);
  }
}
