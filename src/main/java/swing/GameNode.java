package swing;

import java.io.Serializable;
import java.util.Objects;

public class GameNode implements Serializable {
    private double coordX;
    private double coordY;
    private int player=0;

    public GameNode(double coordX, double coordY, int player) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.player = player;
    }

    public GameNode(double coordX, double coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public double getCoordX() {
        return coordX;
    }

    public void setCoordX(double coordX) {
        this.coordX = coordX;
    }

    public double getCoordY() {
        return coordY;
    }

    public void setCoordY(double coordY) {
        this.coordY = coordY;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameNode)) return false;
        GameNode gameNode = (GameNode) o;
        return Double.compare(gameNode.getCoordX(), getCoordX()) == 0 && Double.compare(gameNode.getCoordY(), getCoordY()) == 0 && getPlayer() == gameNode.getPlayer();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCoordX(), getCoordY(), getPlayer());
    }
}
