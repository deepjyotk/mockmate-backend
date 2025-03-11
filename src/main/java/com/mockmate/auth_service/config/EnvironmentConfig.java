package com.mockmate.auth_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.lang.management.ManagementFactory;
import java.util.Arrays;

@Configuration
public class EnvironmentConfig {
    @Autowired
    private Environment environment;

    // Check if running in local profile
    public boolean isLocalProfile() {
        return Arrays.asList(environment.getActiveProfiles()).contains("local");
    }

    // Check if running from IntelliJ debugger
    public boolean isRunningLocally() {
        return ManagementFactory.getRuntimeMXBean()
                .getInputArguments()
                .stream()
                .anyMatch(arg -> arg.contains("-agentlib:jdwp"));
    }

    // Determine environment based on multiple factors
    public String getActiveEnvironment() {
        if (isLocalProfile()) return "LOCAL";
        if (isRunningLocally()) return "LOCAL_DEBUG";

        // Additional environment checks
        if (environment.acceptsProfiles("dev")) return "DEV";
        if (environment.acceptsProfiles("prod")) return "PRODUCTION";

        return "UNKNOWN";
    }
}
