package com.github.tddexample;

import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ArgsTest {
    // -l -p 8080 -d /usr/logs
    // happy path
    // TODO 输入：-l， 输出：options.logging() = true
    // TODO 输入：-p 8080，输出： options.port()=8080
    // TODO 输入：-d /usr/logs，输出： options.directory()=/usr/logs
    // TODO 输入：-l -p 8080 -d /usr/logs，输出：options（logging=true，port=8080，directory="/usr/logs"）

    //sad path
    // TODO 输入：-l t f，输出：异常提示
    // TODO 输入：-p 8080 8081，异常提示
    // TODO 输入：-d /usr/logs /usr/log2，输出： 异常提示
    // default value
    // TODO -p 默认=0
    // TODO -d 默认=""
    // TODO -l 默认=false

    // -g this is a list -d 1 2 -3 5

    // 根据标志位，把内容进行分割
    @Test
    @Disabled
    public void example_1() {
        // 输入-l -p 8080 -d /usr/logs，将内容进行分割，并提取参数
        Options options = Args.prase(Options.class, "-l", "-p", "8080", "-d", "/usr/logs");
        Assertions.assertTrue(options.logging());
        Assertions.assertEquals(8008, options.port());
        Assertions.assertEquals("/usr/logs", options.directory());

    }

    @Test
    @Disabled
    public void example_2() {
        // 输入g this is a list -d 1 2 -3 5，将内容进行分割，并提取参数列表
        val listOptions = Args.prase(ListOptions.class, "-g", "this", "is", "a", "list", "-d", "1", "2", "3");
        Assertions.assertEquals(new String[]{"this", "is", "a", "list"}, listOptions.group());
        Assertions.assertEquals(new int[]{1, 2, 3}, listOptions.decimals());

    }

    static record Options(@Option("l") boolean logging, @Option("p") int port, @Option("d") String directory) {
    }

    static record ListOptions(@Option("g") String[] group, @Option("d") int[] decimals) {
    }
}
