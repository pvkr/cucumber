package com.github.pvkr.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:target/cucumber", "json:target/cucumber.json", "junit:target/cucumber.xml"},
        features = "classpath:features",
        glue = "com.github.pvkr.cucumber.steps")
public class CucumberRunner {
}