package com.github.tddexample;

import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

class BooleanParserTest {

    // happy path 输入：-l 输出：true
    @Test
    public void should_get_boolean_option_to_true_if_flag_present() {
        val option = Args.parse(BooleanOption.class, "-l");
        Assertions.assertTrue(option.logging());
    }

    record BooleanOption(@Option("l") boolean logging) {
    }

    // 输入：-l t f，输出：异常提示
    @Test
    public void should_not_allow_extra_arguments_for_bool_option() {
        Assertions.assertThrows(TooManyArgumentsException.class,
                () -> new ArgumentParsers(false, (it) -> Objects.equals(it, "-l"), 0)
                        .parse(Arrays.asList("-l", "t", "f"), option()));
    }

    // 未输入：-l 默认值false
    @Test
    public void should_set_a_default_value_for_bool_option() {
        Assertions.assertFalse((Boolean) new ArgumentParsers(false,
                (it) -> Objects.equals(it, "-l"), 0)
                .parse(List.of(), option()));
    }

    @Test
    public void should_get_boolean_option_to_false_if_not_flag_present() {
        val option = Args.parse(BooleanOption.class);
        Assertions.assertFalse(option.logging());
    }


    static Option option() {
        return new Option() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return Option.class;
            }

            @Override
            public String value() {
                return "l";
            }
        };
    }
}