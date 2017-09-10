package com.harish.content;

import com.harish.content.api.ContentFilterAPIShould;
import com.harish.streaming.content.ContentFilterApp;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@RunWith(Suite.class)
@Suite.SuiteClasses(ContentFilterAPIShould.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = ContentFilterApp.class)
@ActiveProfiles("test")
public class TestSuite {
}
