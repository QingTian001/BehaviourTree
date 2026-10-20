package behaviour.runtime.composite;

import behaviour.BehaviourStack;
import behaviour.runtime.CompositeRuntime;
import behaviour.runtime.IStackListHolder;

import java.util.List;
import java.util.Set;

public interface CompParallelRuntime extends CompositeRuntime, IStackListHolder {
    Set<Integer> getFinishedIndexes();

    int getSuccessNodeNum();

    void setSuccessNodeNum(int num);

    List<BehaviourStack> getBehaviourStackList();

    @Override
    default List<BehaviourStack> getOwnedStacks() {
        return getBehaviourStackList();
    }
}
