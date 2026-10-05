package com.telusko.springsecdemo.controller;

import com.telusko.springsecdemo.dao.Students;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class StudentController {
    List<Students> students = new ArrayList<>(Arrays.asList(
            new Students(1, "Serah", "Java"),
            new Students(2, "Nathan", "Python")
    ));
    @GetMapping("csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }


    @GetMapping("students")
    public List<Students> getStudents(){
        return students;
    }

    @GetMapping("student/{id}")
    public List<Students> getById(@PathVariable int id){
        return students.stream().filter((x)->x.getId() == id).toList();
    }

    @PostMapping("students")
    public String addStudent(@RequestBody Students student){
        students.add(student);
        return "Success";
    }

}
