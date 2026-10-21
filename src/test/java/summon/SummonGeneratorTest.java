package summon;

import org.junit.jupiter.api.Test;
import summon.generator.IEntityIdGenerator;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class SummonGeneratorTest {
    @Test void proportionalAllocationIncludesLastUnitAndKeepsRequestedTotal() {
        List<Integer> indexes = IEntityIdGenerator.create(3).generateIndexes(
                List.of(new SummonUnit(10, 0, 1), new SummonUnit(20, 0, 1)), 10);
        assertEquals(10, indexes.size());
        assertEquals(5, indexes.stream().filter(i -> i == 0).count());
        assertEquals(5, indexes.stream().filter(i -> i == 1).count());
    }

    @Test void proportionalAllocationAssignsRemainderToLastUnit() {
        List<Integer> indexes = IEntityIdGenerator.create(3).generateIndexes(
                List.of(new SummonUnit(10, 0, 1), new SummonUnit(20, 0, 1), new SummonUnit(30, 0, 1)), 10);
        assertEquals(List.of(0, 0, 0, 1, 1, 1, 2, 2, 2, 2), indexes);
        assertEquals(List.of(0, 0, 0), IEntityIdGenerator.create(3).generateIndexes(List.of(new SummonUnit(1, 0, 1)), 3));
    }

    @Test void randomAlgorithmsRespectWeightsAndRequestedCount() {
        List<SummonUnit> units = List.of(new SummonUnit(10, 0, 0), new SummonUnit(20, 0, 1));
        for (int type : List.of(1, 2)) {
            assertEquals(java.util.Collections.nCopies(12, 1), IEntityIdGenerator.create(type).generateIndexes(units, 12));
        }
        List<Integer> batch = IEntityIdGenerator.create(1).generateIndexes(
                List.of(new SummonUnit(10, 0, 1), new SummonUnit(20, 0, 1)), 20);
        assertEquals(1, batch.stream().distinct().count());
        assertThrows(RuntimeException.class, () -> IEntityIdGenerator.create(99));
    }
}
