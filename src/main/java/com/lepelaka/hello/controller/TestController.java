package com.lepelaka.hello.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lepelaka.hello.dto.ResponseDTO;
import com.lepelaka.hello.dto.TestRequestBodyDTO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("test")
@Slf4j
public class TestController {
  // @GetMapping
  public String testController() {
    return "hello world";
  }

  @GetMapping("/testGetMapping")
  public String testControllerWithPath() {
    return "hello world testGetmapping";
  }

  @GetMapping(value = { "{id}", "/" }) // localhost:8080/test
  public String testControllerWithPathVariables(@PathVariable int id) {
    log.info("test");
    return "Hello World id : " + id;
  }

  //
  @GetMapping("requestParam")
  public String testControllerRequestParam(@RequestParam(required = false) int id) {
    return "Hello World id : " + id;
  }

  @GetMapping("requestBody")
  public String testControllerRequestBody(@RequestBody TestRequestBodyDTO testRequestBodyDTO) {
    return "Hello world id : " + testRequestBodyDTO.getId() + ", message : " + testRequestBodyDTO.getMessage();
  }

  @GetMapping("requestDTO")
  public String testControllerDTO(TestRequestBodyDTO testRequestBodyDTO) {
    return "Hello world id : " + testRequestBodyDTO.getId() + ", message : " + testRequestBodyDTO.getMessage();
  }

  // 반환 테스트
  @GetMapping("testResponseBody")
  public ResponseDTO<String> testControllerResponseBody() {
    // List<Object> testList = new ArrayList<String>();
    List<String> list = new ArrayList<>();
    list.add("hello world I'm responseDTO");
    ResponseDTO<String> responseDTO = ResponseDTO.<String>builder().data(list).build();
    return responseDTO;
  }

  @GetMapping("testResponseEntity")
  public ResponseEntity<?> testControllerResponseEntity() {
    List<String> list = new ArrayList<>();
    list.add("hello world I'm responseDTO");
    ResponseDTO<String> responseDTO = ResponseDTO.<String>builder().data(list).build();
    // return ResponseEntity.status(400).body(responseDTO);
    return ResponseEntity.badRequest().body(responseDTO);
  }

}
