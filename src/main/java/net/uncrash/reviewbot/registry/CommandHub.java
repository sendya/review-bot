package net.uncrash.reviewbot.registry;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.uncrash.reviewbot.api.Command;
import net.uncrash.reviewbot.api.ProcessCommand;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Sendya
 */
@Slf4j(topic = "CommandHub")
@Component
@Getter
public class CommandHub {

    private final Map<String, Command>        commands        = new ConcurrentHashMap<>();
    private final Map<String, ProcessCommand> processCommands = new ConcurrentHashMap<>(8);

    public void register(String key, Command command) {
        commands.put(key, command);
        log.info("Hub.register :\t{}", command);
    }

    public void register(String[] key, Command command) {
        if (key.length == 1) {
            this.register(key[0], command);
            return;
        }
        for (String str : key) {
            this.register(str, command);
        }
    }

    public void unregister(String key) {
        commands.remove(key);
    }

}