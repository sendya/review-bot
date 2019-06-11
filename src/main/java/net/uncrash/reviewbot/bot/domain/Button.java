package net.uncrash.reviewbot.bot.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Button {

    private String title;

    private String value;
}
