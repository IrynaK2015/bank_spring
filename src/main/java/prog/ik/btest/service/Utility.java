package prog.ik.btest.service;

import org.apache.commons.lang3.RandomStringUtils;

public class Utility {
    public static String getRandomIban() {
        return "UA" + RandomStringUtils.random(27, false, true);
    }
}
