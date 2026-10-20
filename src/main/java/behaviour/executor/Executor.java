package behaviour.executor;

import behaviour.Atom;

public abstract class Executor<A extends Atom<?>> {
    private final A atom;

    protected Executor(A atom) {
        this.atom = atom;
    }

    public final A getAtom() {
        return atom;
    }
}
