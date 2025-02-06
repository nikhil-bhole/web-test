package enums;

import java.time.Duration;

public enum WaitTime {

    DEFAULT(Duration.ofSeconds(0)),
    BRIEF(Duration.ofSeconds(5)),
    MEDIUM(Duration.ofSeconds(10)),
    LARGE(Duration.ofSeconds(20)),
    EXTRA_LARGE(Duration.ofSeconds(30));

    private final Duration duration;

    WaitTime(Duration duration) {
        this.duration = duration;
    }

    public Duration getDuration() {
        return duration;
    }
}
