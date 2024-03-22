package com.lec.spring.controller;

import com.lec.spring.domain.Status;
import com.lec.spring.domain.User;
import com.lec.spring.dto.*;
import com.lec.spring.dto.exception.Response;
import com.lec.spring.service.UserService;
import com.lec.spring.service.user.UserInfoService;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserInfoService userInfoService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDTO> signup(@RequestBody UserRequestDTO userRequestDTO){
        return ResponseEntity.ok(userService.signup(userRequestDTO));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody UserRequestDTO userRequestDTO){
        return ResponseEntity.ok(userService.login(userRequestDTO));
    }

    // 비밀번호 확인
    @PostMapping("/passwordCheck")
    public ResponseEntity<?> passwordCheck(@RequestBody UserRequestDTO userRequestDTO) {
        boolean isValid = userService.verifyPassword(userRequestDTO.getUsername(), userRequestDTO.getPassword());

        if (isValid) {
            return ResponseEntity.ok().body(Map.of("isValid", true));
        } else {
            return ResponseEntity.badRequest().body(Map.of("isValid", false, "message", "비밀번호가 일치하지 않습니다."));
        }
    }

    // 회원정보 수정
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody UserRequestDTO userRequestDTO){
        System.out.println(userRequestDTO.getPassword());
        return ResponseEntity.ok(userService.update(userRequestDTO));
    }

    // 비밀번호 수정
    @PutMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody UserRequestDTO userRequestDTO){
        return ResponseEntity.ok(userService.updatePassword(userRequestDTO));
    }

    // 유저정보 불러오기
    @GetMapping("/info")
    public ResponseEntity<UserDTO> getUserInfo() {
        Optional<User> userOptional = userService.getUser();
        System.out.println("userOptional : " + userOptional);
        if (!userOptional.isPresent()) {
            return ResponseEntity.notFound().build(); // 유저 정보가 없을 때 404 상태 코드 반환
        }
        UserDTO userDTO = UserDTO.toDto(userOptional.get());
        System.out.println("userDTO : " + userDTO);
        return ResponseEntity.ok(userDTO); // 유저 정보가 있을 때 해당 정보를 반환
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestHeader(value = "Authorization") String tokenHeader){
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")){
            String accessToken = tokenHeader.substring(7);
            String message = userService.logout(accessToken);
            return ResponseEntity.ok().body(Map.of("message", message));
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid Authorization header."));
        }
    }

    // 회원탈퇴
    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> deleteUser(Authentication authentication){
        String userId = authentication.getName();
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok().body(Map.of("message", "Account deleted successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Failed to delete account."));
        }
    }












    //유저 정보
    @GetMapping("/profile")
    public ResponseEntity<UserDTO> profile(){
        User user = userService.getUser().orElse(null);
        System.out.println(user);
        if(user == null) {
            System.out.println("못찾음");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); //404
        }
        System.out.println("찾ㅇ므");
        System.out.println("-------------------------------------------------");
        System.out.println(UserDTO.toDto(user));
        return new ResponseEntity<>(UserDTO.toDto(user), HttpStatus.OK); // 200
    }

    //판매중, 판매완료, 예약중
    @GetMapping("/product-status")
    public ResponseEntity<Page> getProductList(
            @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
            @RequestParam(name = "status", required = false)String str
            ){
        Status status;
        try {
            status = Status.valueOf(str);
        }catch (IllegalArgumentException e){
            status = null;
        }

        return new ResponseEntity<>(userInfoService.getProductList(page, size, status),HttpStatus.OK);
    }

    //관심 게시글
    @GetMapping("/wish-list")
    public ResponseEntity<Page> getWishList(
            @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size
    ){
        return new ResponseEntity<>(userInfoService.getWishList(page, size),HttpStatus.OK);
    }

    @GetMapping("/test")
    public void test(){
        userInfoService.test();
    }


    //썸네일 변경
    @PostMapping("/change/thumbnail")
    public ResponseEntity<UserDTO> changeImg(@RequestParam("thumbnail") MultipartFile image){
        System.out.println("받았다"+ image);
        System.out.println(image.getName());
        System.out.println(image.getOriginalFilename());
        System.out.println(image.getSize());
        return new ResponseEntity<>(userInfoService.changeImg(image), HttpStatus.CREATED);
    }




}
