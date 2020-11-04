package com.yosa.theopengalaxy.domain;

public enum RateType {
    UP(1), DOWN(-1);

    private long direction;

    RateType(long direction) {
        this.direction = direction;
    }

    public long getDirection() {
        return direction;
    }
}
