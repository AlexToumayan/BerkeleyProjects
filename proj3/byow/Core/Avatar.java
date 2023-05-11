package byow.Core;


public class Avatar {
    private int x;
    private int y;

    public Avatar(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void updatePosition(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }
}

