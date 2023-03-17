package com.github.tddexample;

import java.util.List;

interface ArgumentParser {
    Object parse(List<String> arguments, Option option);
}
