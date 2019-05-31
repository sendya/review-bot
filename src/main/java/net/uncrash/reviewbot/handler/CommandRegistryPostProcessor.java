package net.uncrash.reviewbot.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.uncrash.reviewbot.annotation.BotCommand;
import net.uncrash.reviewbot.api.Command;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

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
    public void run(ApplicationArguments args) throws Exception {
        String[] arr = applicationContext.getBeanNamesForAnnotation(BotCommand.class);
        if (arr.length > 0) {
            for (String beanName : arr) {
                Class<?> clazz = applicationContext.getType(beanName);
                if (clazz == null || !Command.class.isAssignableFrom(clazz)) {
                    continue;
                }

                Command command = (Command) applicationContext.getBean(clazz);
                Annotation[] annotations = clazz.getAnnotations();
                if (annotations != null && annotations.length > 0) {
                    for (Annotation annotation : annotations) {
                        if (annotation instanceof BotCommand) {
                            BotCommand botCommand = (BotCommand) annotation;
                            if (botCommand.joinProcess()) {
                                // TODO register join handle
                                continue;
                            }
                            if (botCommand.leaveProcess()) {
                                // TODO register leave handle
                                continue;
                            }
                            String[] commands = botCommand.command();
                            log.debug("Bean: {}, Commands: {}", beanName, commands);
                            commandHub.register(commands, command);
                        }
                    }
                }
            }
        }
    }
}
