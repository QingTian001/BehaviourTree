package summon;
import org.junit.jupiter.api.Test;
import summon.generator.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
class GeneratorTest {
    @Test void proportionalAllocationDoesNotOverflowValidIntWeights() {
        var indexes=new EGWeightCalculate().generateIndexes(List.of(new SummonUnit(1,0,1_000_000_000),new SummonUnit(2,0,1_000_000_000)),10);
        assertEquals(5,Collections.frequency(indexes,0)); assertEquals(5,Collections.frequency(indexes,1));
    }
    @Test void roundedRemainderBelongsToLastUnit() {
        assertEquals(List.of(2,2), new EGWeightCalculate().generateIndexes(List.of(new SummonUnit(1, 0, 1), new SummonUnit(2, 0, 1),new SummonUnit(3, 0, 1)),2));
    }
}
