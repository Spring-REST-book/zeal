package com.dzone.albanoj2.zeal.test.system;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.dzone.albanoj2.zeal.Application;

import io.cucumber.spring.CucumberContextConfiguration;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@CucumberContextConfiguration
public class CucumberConfiguration {
}
