/*
 * Copyright 2008-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package exdev.com.common.controller;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.util.Value;
import com.google.gson.Gson;

import exdev.com.common.ExdevConstants;
import exdev.com.common.service.ExdevBaseService;
import exdev.com.common.service.ExdevCommonService;
import exdev.com.common.vo.SessionVO;
import exdev.com.service.ApprovalService;
import exdev.com.service.ExcelService;
import exdev.com.service.ExdevSampleService;
import exdev.com.service.FileService;

@RestController
public class TESTController {
	
     // 1. 간단 GET - Map으로 응답
    @GetMapping("/hello")
    public Map<String, String> hello() {
        return Map.of("message", "안녕하세요, 태원님!");
    }

    // 2. POST - 문자열 본문 그대로 받기, 문자열 응답
    @PostMapping("/echo")
    public String echoMessage(@RequestBody String message) {
        return "당신이 보낸 메시지: " + message;
    }

    // 3. POST - Map으로 유연하게 받기, 에러 처리 포함, JSON 객체 응답
    @PostMapping("/user")
    public ResponseEntity<?> greetUser(@RequestBody Map<String, Object> userMap) {
        String name = (String) userMap.get("name");
        Integer age = null;

        try {
            Object ageObj = userMap.get("age");
            if (ageObj instanceof Number) {
                age = ((Number) ageObj).intValue();
            } else if (ageObj instanceof String) {
                age = Integer.parseInt((String) ageObj);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "나이 값이 올바르지 않습니다."));
        }

        if (age == null || age < 0) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "나이는 0보다 커야 합니다."));
        }

        return ResponseEntity.ok(Map.of("name", name, "age", age));
    }

    // 4. POST - User DTO 클래스로 받기
    @PostMapping("/user/dto")
    public ResponseEntity<?> greetUserDto(@RequestBody User user) {
        if (user.getAge() == null || user.getAge() < 0) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "나이는 0보다 커야 합니다."));
        }
        return ResponseEntity.ok(user);
    }

    // 5. GET - PathVariable 사용 예제
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        // 예: id가 1이면 존재, 아니면 404
        if (id == 1L) {
            User user = new User("태원", 45);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(404)
                    .body(Map.of("error", "사용자를 찾을 수 없습니다."));
        }
    }

    // 6. GET - Query Parameter 사용 예제
    @GetMapping("/search")
    public Map<String, String> search(@RequestParam String keyword) {
        // 단순 예제: 받은 키워드 그대로 반환
        return Map.of("검색어", keyword);
    }

    // 7. POST - List 형태 받기 (예: 여러 User 객체)
    @PostMapping("/users")
    public ResponseEntity<?> receiveUserList(@RequestBody List<User> users) {
        if (users == null || users.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "사용자 목록이 비어있습니다."));
        }
        return ResponseEntity.ok(Map.of("count", users.size(), "users", users));
    }

    // 8. DELETE - PathVariable로 삭제 예제
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        // 예제: id가 1 이상이면 삭제 성공
        if (id >= 1) {
            return ResponseEntity.ok(Map.of("message", "사용자 삭제 성공: id=" + id));
        } else {
            return ResponseEntity.status(400)
                    .body(Map.of("error", "유효하지 않은 사용자 ID입니다."));
        }
    }

}
