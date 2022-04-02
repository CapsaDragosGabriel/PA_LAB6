package swing;

import java.io.Serializable;
import java.util.Objects;

public class GameNode implements Serializable {
    private int coordX;
    private int coordY;
    private int player = 0;

    public GameNode(int coordX, int coordY, int player) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.player = player;
    }

    public GameNode(int coordX, int coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public int getCoordX() {
        return coordX;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public void setCoordY(int coordY) {
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
        return Double.compare(gameNode.getCoordX(), getCoordX()) == 0 && Double.compare(gameNode.getCoordY(), getCoordY()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCoordX(), getCoordY(), getPlayer());
    }

    @Override
    public String toString() {
        return "GameNode{" +
                "coordX=" + coordX +
                ", coordY=" + coordY +
                ", player=" + player +
                '}';
    }
}
