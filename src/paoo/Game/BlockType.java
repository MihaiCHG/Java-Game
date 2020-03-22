package paoo.Game;

public enum BlockType {
    GRASS(0), SOIL(1), WATER(2), TREE(3), MOUNTAIN(4), TOWN(5), Monster(6), Rock(7), Cannon( 8), Projectile(9), Aim(10);

    private final int value;

    private BlockType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static BlockType getTypeFromInt(int value) {
        return BlockType.values()[value];
    }
}
