package net.uncrash.reviewbot.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface BotCommand {

    /**
     * 注册 bean 别名
     * @return
     */
    String value() default "";

    /**
     * 监听命令
     * @return
     */
    String[] command() default "";

    /**
     * 是否处理加入群组消息
     */
    boolean joinProcess() default false;

    /**
     * 是否处理离开群组消息
     */
    boolean leaveProcess() default false;
}
