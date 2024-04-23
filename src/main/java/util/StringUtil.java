package util;

import org.apache.logging.log4j.message.ParameterizedMessageFactory;

public class StringUtil {

    public static String format(String message, Object... params) {
        return ParameterizedMessageFactory.INSTANCE.newMessage(message, params).getFormattedMessage();
    }

    public static void main(String[] args) {
        System.getProperties().putIfAbsent("log4j.configurationFile", "../log4j2.xml");
        System.out.println(StringUtil.format("{} {}", "hello world", 0));
    }

    public static String getTableString(int tableNum) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tableNum; i++) {
            sb.append("\t");
        }
        return sb.toString();
    }
}

