package net.uncrash.reviewbot.bot.domain;

import lombok.Data;

import java.util.List;

@Data
public class ReviewMessage {

    /**
     * 提问内容
     */
    private String question;

    /**
     * 结果内容 如果用户的按钮点击回调结果是
     * List 中的任何一个满足，即认为正确
     */
    private List<String> result;

    /**
     * 验证模式，在使用多个验证条件时有效
     *
     */
    private MOD mod = MOD.OR;

    /**
     * 问题答案组
     */
    private List<Button> reviewBtn;

    /**
     * 人工操作按钮组
     */
    private List<Button> controlBtn;

    enum MOD {
        /**
         * 并集，需要满足所有验证条件
         */
        AND,
        /**
         * 交集，满足其中一个验证条件
         */
        OR
    }
}
