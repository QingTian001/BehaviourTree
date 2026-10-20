package behaviour.runtime;

import behaviour.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BlackBoardTest {
    private BlackBoard blackBoard;

    @BeforeEach
    void setUp() {
        blackBoard = new BlackBoard(null);
    }

    @Test
    @DisplayName("写入后可以读取并转换为数值类型")
    void putValueThenGetValue() {
        blackBoard.putValue("score", 10);

        assertEquals(Integer.valueOf(10), blackBoard.<Integer>getValue("score"));
        assertEquals(10, blackBoard.getIntValue("score"));
        assertEquals(10L, blackBoard.getLongValue("score"));
    }

    @Test
    @DisplayName("读取默认值后会保存默认值")
    void getValueWithDefaultStoresDefaultValue() {
        assertEquals(7, blackBoard.getIntValue("score", 7));
        assertEquals(7, blackBoard.getIntValue("score", 99));
    }

    @Test
    @DisplayName("指定 key 的监听器可以收到变化")
    void keyListenerReceivesValueChange() {
        List<String> events = new ArrayList<>();
        BlackBoard.IBBListener<Integer> listener = (key, value) -> events.add(key + ":" + value);

        blackBoard.getValueAndRegisterListener("mood", 0, listener);
        blackBoard.putValue("mood", 1);

        assertEquals(List.of("mood:1"), events);
    }

    @Test
    @DisplayName("注销监听器后不再收到变化")
    void unregisterListenerStopsNotifications() {
        List<Integer> events = new ArrayList<>();
        BlackBoard.IBBListener<Integer> listener = (key, value) -> events.add(value);

        blackBoard.getValueAndRegisterListener("mood", 0, listener);
        blackBoard.unregisterListener("mood", listener);
        blackBoard.putValue("mood", 1);

        assertTrue(events.isEmpty());
    }

    @Test
    @DisplayName("全局监听器可以收到不同 key 的变化")
    void globalListenerReceivesDifferentKeyChanges() {
        List<String> events = new ArrayList<>();
        BlackBoard.IBBListener<Object> listener = (key, value) -> events.add(key + ":" + value);

        blackBoard.addGlobalListener(listener);
        blackBoard.putValue("score", 10);
        blackBoard.putValue("mood", "happy");
        blackBoard.removeGlobalListener(listener);
        blackBoard.putValue("score", 20);

        assertEquals(List.of("score:10", "mood:happy"), events);
    }

    @Test
    @DisplayName("clear 会同时清理值和监听器")
    void clearRemovesValuesAndListeners() {
        List<Integer> events = new ArrayList<>();
        BlackBoard.IBBListener<Integer> listener = (key, value) -> events.add(value);

        blackBoard.getValueAndRegisterListener("score", 0, listener);
        blackBoard.clear();
        blackBoard.putValue("score", 1);

        assertTrue(events.isEmpty());
        assertEquals(1, blackBoard.getIntValue("score"));
    }

    @Test
    @DisplayName("空 key 会抛出非法参数异常")
    void emptyKeyThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> blackBoard.putValue("", 1));
    }
}
