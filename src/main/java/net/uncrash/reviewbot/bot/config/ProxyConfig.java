package net.uncrash.reviewbot.bot.config;

import lombok.Data;

@Data
public class ProxyConfig {

    private boolean enable;

    private String host;

    private Integer port;
}
