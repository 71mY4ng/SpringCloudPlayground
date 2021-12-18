package com.timyang.playground.api;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = PlayPublicApiApplication.class)
@Profile("dev")
@RunWith(SpringRunner.class)
public class BasicSpringBootTest {
}
