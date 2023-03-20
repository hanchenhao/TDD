package com.github.tddexample;

import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ArgsTest {

    // 输入：-l -p 8080 -d /usr/logs，输出：options（logging=true，port=8080，directory="/usr/logs"）
    @Test
    public void input_multiple_parameter_to_get_ture_option() {
        // 输入-l -p 8080 -d /usr/logs，将内容进行分割，并提取参数
        Options options = Args.parse(Options.class, "-l", "-p", "8080", "-d", "/usr/logs");
        Assertions.assertTrue(options.logging());
        Assertions.assertEquals(8080, options.port());
        Assertions.assertEquals("/usr/logs", options.directory());

    }

    // 输入：-l -p  -d /usr/logs，输出：options（logging=true，port=0，directory="/usr/logs"）
    @Test
    public void input_multiple_parameter2_to_get_ture_option() {
        Options options = Args.parse(Options.class, "-l", "-p", "-d", "/usr/logs");
        Assertions.assertTrue(options.logging());
        Assertions.assertEquals(0, options.port());
        Assertions.assertEquals("/usr/logs", options.directory());

    }

    @ParameterizedTest
    @ValueSource(strings = {"-l -p -d", "-d -p -l", "-p -l -d"})
    public void input_multiple_parameter3_to_get_ture_option(String arguments) {
        Options options = Args.parse(Options.class, arguments.split(" "));
        Assertions.assertTrue(options.logging());
        Assertions.assertEquals(0, options.port());
        Assertions.assertEquals("", options.directory());
    }

    @Test
    public void input_multiple_parameter_to_get_ture_option_by_out_of_order() {
        // 输入-l -p 8080 -d /usr/logs，将内容进行分割，并提取参数
        Options options = Args.parse(Options.class, "-d", "/usr/logs", "-l", "-p", "8080");
        Assertions.assertTrue(options.logging());
        Assertions.assertEquals(8080, options.port());
        Assertions.assertEquals("/usr/logs", options.directory());

    }

    static record Options(@Option("l") boolean logging, @Option("p") int port, @Option("d") String directory) {
    }

    @Test
    @Disabled
    public void example_2() {
        // 输入g this is a list -d 1 2 -3 5，将内容进行分割，并提取参数列表
        val listOptions = Args.parse(ListOptions.class, "-g", "this", "is", "a", "list", "-d", "1", "2", "3");
        Assertions.assertEquals(new String[]{"this", "is", "a", "list"}, listOptions.group());
        Assertions.assertEquals(new int[]{1, 2, 3}, listOptions.decimals());

    }

    static record ListOptions(@Option("g") String[] group, @Option("d") int[] decimals) {
    }
}
