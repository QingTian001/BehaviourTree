package state;
import java.util.List;
/** 状态规则的不可变快照；动作和状态 ID 沿用宿主配置。 */
public record StateConfig(int id, boolean isMultiple, boolean isFullBlock,
        List<Integer> disableActions, List<Integer> protectOtherActions, List<Integer> protectStates,
        List<Integer> allowActions, List<Integer> allowStates, List<Integer> interruptSelfActions) {
    public StateConfig {
        disableActions = List.copyOf(disableActions); protectOtherActions = List.copyOf(protectOtherActions);
        protectStates = List.copyOf(protectStates); allowActions = List.copyOf(allowActions);
        allowStates = List.copyOf(allowStates); interruptSelfActions = List.copyOf(interruptSelfActions);
    }
    public int getId() { return id; }
    public boolean getIsMultiple() { return isMultiple; }
    public boolean getIsFullBlock() { return isFullBlock; }
    public List<Integer> getDisableActions() { return disableActions; }
    public List<Integer> getProtectOtherActions() { return protectOtherActions; }
    public List<Integer> getProtectStates() { return protectStates; }
    public List<Integer> getAllowActions() { return allowActions; }
    public List<Integer> getAllowStates() { return allowStates; }
    public List<Integer> getInterruptSelfActions() { return interruptSelfActions; }
}
