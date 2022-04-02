package swing;

import java.io.Serializable;
import java.util.Objects;

public class GameEdge implements Serializable {
    public boolean contain;
    private  GameNode gameNode1;
    private  GameNode gameNode2;
    public boolean contains (GameNode node)
    {
        if (gameNode1.equals(node)) return true;
        if (gameNode2.equals(node)) return true;
        return false;

    }
    public GameEdge(GameNode gameNode1, GameNode gameNode2) {
        this.gameNode1 = gameNode1;
        this.gameNode2 = gameNode2;
    }

    public GameEdge() {

    }

    public GameNode getGameNode1() {
        return gameNode1;
    }

    public void setGameNode1(GameNode gameNode1) {
        this.gameNode1 = gameNode1;
    }

    public GameNode getGameNode2() {
        return gameNode2;
    }

    public void setGameNode2(GameNode gameNode2) {
        this.gameNode2 = gameNode2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameEdge)) return false;
        GameEdge gameEdge = (GameEdge) o;
        return getGameNode1().equals(gameEdge.getGameNode1()) && getGameNode2().equals(gameEdge.getGameNode2());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGameNode1(), getGameNode2());
    }

    @Override
    public String toString() {
        return "GameEdge{" +
                "gameNode1=" + gameNode1 +
                ", gameNode2=" + gameNode2 +
                '}';
    }
}
