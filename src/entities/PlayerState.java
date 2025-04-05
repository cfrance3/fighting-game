package entities;

public enum PlayerState {
    IDLE(0), FALL(1), JUMP(2), ATTACK(3);

    private final int value;

    PlayerState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
