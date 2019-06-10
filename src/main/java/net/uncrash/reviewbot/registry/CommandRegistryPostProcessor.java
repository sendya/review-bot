package net.uncrash.reviewbot.registry;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.uncrash.reviewbot.annotation.BotCommand;
import net.uncrash.reviewbot.api.Command;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Sendya
 */
@Slf4j(topic = "CommandRegistryPostProcessor")
@Component
@RequiredArgsConstructor
public class CommandRegistryPostProcessor implements ApplicationRunner {

    private final CommandHub         commandHub;
    private final ApplicationContext applicationContext;

    @Override
    public void run(ApplicationArguments args) {
        Map<String, Object> commandsBean = applicationContext.getBeansWithAnnotation(BotCommand.class);
        commandsBean.values().stream()
                .filter(command -> Command.class.isAssignableFrom(command.getClass()))
                .forEach(command -> {
                    BotCommand botCommand = AnnotationUtils.findAnnotation(command.getClass(), BotCommand.class);
                    if (!botCommand.joinProcess() || !botCommand.leaveProcess()) {
                        String[] commands = botCommand.command();
                        log.debug("Bean: {}, Commands: {}", command.getClass().getName(), commands);
                        commandHub.register(commands, (Command) command);
                    }
                });
    }
}
