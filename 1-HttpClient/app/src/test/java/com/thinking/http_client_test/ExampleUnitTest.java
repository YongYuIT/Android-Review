package com.thinking.http_client_test;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void getChinese() {
        Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]+");
        Matcher m = pattern.matcher("abc你好def大家");
        if (m.find()) {
            String chinese = m.group(0);
            System.out.println(chinese + "-->" + m.start() + "-->" + m.end() + "-->" + m.groupCount());
        } else {
            System.out.println("failed");
        }
    }

    @Test
    public void getChineseLoop() {
        convert("abc你好def大家");
    }

    private String convert(String str) {
        Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]+");
        Matcher m = pattern.matcher(str);
        if (m.find()) {
            String chinese = m.group(0);
            System.out.println(chinese + "-->" + m.start() + "-->" + m.end() + "-->" + m.groupCount());
            str = str.replace(chinese, "aa");
            convert(str);
        } else {
            System.out.println("failed");
        }
        return str;
    }
}