package org.mmocore.commons.limiter;

public interface Limiter {
    boolean pass();
    default long getTimeLeft() { return 0; }
    default long getTimeElapsed() { return 0; }

    static final Limiter NOOP = new Limiter() {
        @Override public boolean pass() {
            return true;
        }
    };
}
