package com.kapuchinka.facultydbproject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.kapuchinka.facultydbproject.utils.StringPatterns;

@SpringBootTest
class FacultydbprojectApplicationTests {

    @Test
    void contextLoads() {
        String num = "2020";
        System.out.println(StringPatterns.isValidNumbers(num));
        String text = "Hello";
        System.out.println(StringPatterns.isValidNumbers(text));
    }

}
