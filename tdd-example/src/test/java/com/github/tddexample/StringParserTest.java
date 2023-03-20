package com.github.tddexample;

import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

class StringParserTest {

    @Test
    public void should_get_string_option_if_flag_present() {
        val option = Args.parse(StringOption.class, "-d", "/usr/logs");
        Assertions.assertEquals("/usr/logs", option.directory());
    }

    @Test
    public void should_get_null_if_input_empty_value() {
        val option = Args.parse(StringOption.class, "-d");
        Assertions.assertEquals("", option.directory());
    }

    record StringOption(@Option("d") String directory) {
    }

    // 输入：-d /usr/logs /usr/log2，输出： 异常提示
    @Test
    public void should_not_allow_extra_arguments_for_string_option() {
        Assertions.assertThrows(TooManyArgumentsException.class, () -> new SingleValuedParser("", String::valueOf)
                .parse(Arrays.asList("/user/logs", "/user/log2"), option()));
    }

    @Test
    public void should_set_a_default_value_for_string_option() {
        Assertions.assertEquals("", new SingleValuedParser("", String::valueOf)
                .parse(List.of(), option()));
    }

    static Option option() {
        return new Option() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return Option.class;
            }

            @Override
            public String value() {
                return "d";
            }
        };
    }

}