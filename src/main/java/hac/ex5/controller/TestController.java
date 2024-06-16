package hac.ex5.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class TestController {

    @PostMapping("/test")
    public ResponseEntity<String> handleTestData(@RequestBody TestObject data) {
        System.out.println("Received data: " + data.getMessage());
        return ResponseEntity.ok("Received: " + data.getMessage());
    }

    static class TestObject {
        private String message;

        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }
    }
}