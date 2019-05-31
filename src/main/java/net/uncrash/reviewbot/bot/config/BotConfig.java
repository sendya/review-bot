package net.uncrash.reviewbot.bot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;

/**
 * @author Sendya
 */
@Data
@Component
@ConfigurationProperties(prefix = "robot")
public class BotConfig {

    private String name;

    private String token;

    private ProxyConfig proxy;

    public DefaultBotOptions getDefaultBotOption() {
        if (proxy.isEnable()) {
            DefaultBotOptions options = new DefaultBotOptions();
            options.setProxyHost(proxy.getHost());
            options.setProxyPort(proxy.getPort());
            options.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);
            return options;
        }
        return new DefaultBotOptions();
    }
}
