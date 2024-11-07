package com.fedorov.chat_v3;


import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:63342")
public class TestController {
//    @Value("${spring.token}")
//    private String token;
//
//    List<Test> tests = new ArrayList<Test>();
//
//
//    @GetMapping("/get1")
//    public ResponseEntity<?> getUsers(@RequestParam String token) {
//
//        if (token != null && token.equals(this.token)) {
//
//            return ResponseEntity.ok(tests);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//
//    }
//
//    @PostMapping("/registry1")
//    public ResponseEntity<?> registry(@Validated @RequestBody Test test) {
//        if (tests.stream().noneMatch(s -> s.getUsername().equals(test.getUsername()))) {
//
//            tests.add(test);
//
//
//            return ResponseEntity.ok(test);
//
//        }
//        else {
//            System.out.println("Блять");
//            return ResponseEntity.badRequest().body("This username is already in use!");
//        }
//    }
//
//    @PostMapping("/signup1")
//    public ResponseEntity<?> signup(@Validated @RequestBody Test test) {
//
//        if (tests.stream().anyMatch(s -> s.getUsername().equals(test.getUsername()) && s.getPassword().equals(test.getPassword()))) {
//
//            TestResponse response = new TestResponse(test.getUsername(), test.getPassword(), token);
//
//
//            return ResponseEntity.ok(response);
//
//        }
//        else {
//            System.out.println("Блять2");
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//
//
//
//
//    }

}
