package com.example.actuator;

import com.example.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MyInfoContributor implements InfoContributor {

    private final Environment env;

    @Override
    public void contribute(Info.Builder builder) {
      builder.withDetail("port", env.getProperty("server.port"));
      builder.withDetail("timestamp", DateTimeUtil.formatattedDateTime());
      builder.withDetail("active profile", env.getActiveProfiles());
    }
}
