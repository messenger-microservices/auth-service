package ru.pulsarmn.messenger.auth.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties("integration")
public class RestClientConfigurationProperties {

    private UserService userService;

    public static class UserService {
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
