package swing;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import org.jgrapht.Graph;
import org.jgrapht.GraphIterables;
import org.jgrapht.GraphType;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameGraph<V extends GameNode, E extends GameEdge> implements Serializable, Graph<V, E> {
    private int rows = 10;
    private int cols = 12;
    private Set<GameNode> gameNodeSet = new HashSet<>();
    private Set<GameEdge> gameEdgeSet = new HashSet<>();
    private GameNode prevGameNode = null;

    /*public void generateGraph()
    {
        for (int row = 0; row < rows; row++) {
            for (int clmn = 0; clmn < cols-1; clmn++) {
                int x1 = padX+ clmn*cellWidth;
                int y1 = padY + row * cellHeight;
                int x2 = padX + (clmn+1)*cellWidth;
                int y2 = y1;
                if (Math.random() > 0.4) {
                    g.drawLine(x1, y1, x2, y2);
                    GameNode gameNode1=new GameNode(x1,y1);
                    GameNode gameNode2=new GameNode(x2,y2);
                    this.addEdge(gameNode1,gameNode2);
                    this.addVertex(gameNode1);
                    this.addVertex(gameNode2);
                    // sticksHorizontal.add(x2);
                }
            }
        }
        //vertical lines TODO
        for (int clmn = 0; clmn < cols; clmn++) {
            for (int row = 0; row < rows-1; row++) {
                int x1 = padX + clmn * cellWidth;
                int y1 = padY+row*cellHeight;
                int x2 = x1;
                int y2 = padX + (row+1)*cellHeight;

                if (Math.random() > 0.4) {
                    g.drawLine(x1, y1, x2, y2);
                    GameNode gameNode1=new GameNode(x1,y1);
                    GameNode gameNode2=new GameNode(x2,y2);
                    frame.gameGraph.addEdge(gameNode1,gameNode2);
                    frame.gameGraph.addVertex(gameNode1);
                    frame.gameGraph.addVertex(gameNode2);
                    //  sticksHorizontal.add(x2);
                }
            }
        }
    }
   */
    public void depopulateNodes() {
        for (GameNode node : gameNodeSet)
            gameNodeSet.remove(node);
    }

    public boolean isValidNode(GameNode node) {
        if (prevGameNode == null)
            return true;

        if (node.getPlayer() != 0) {
       //     System.out.println("node used");
            return false;
        }

        for (GameEdge edge : this.gameEdgeSet) {
            if (edge.contains(prevGameNode) && edge.contains(node))
                return true;
        }
        return false;
    }

    public GameNode getGameNodeAtCoords(int x, int y, int player) {
        for (GameNode node : gameNodeSet) {
            if (Math.sqrt((x - node.getCoordX()) * (x - node.getCoordX()) + (y - node.getCoordY()) * (y - node.getCoordY())) <= DrawingPanel.STONE_SIZE) {
                if (isValidNode(node)) {
                    node.setPlayer(player);
                    prevGameNode = node;

                    return node;

                }
            }

        }

        return null;
    }

    @Override
    public Set<E> getAllEdges(V v, V v1) {
        Set<E> edgesSet = new HashSet<>();
        for (GameEdge edge : gameEdgeSet) {
            if (edge.getGameNode1().equals(v) && edge.getGameNode2().equals(v1) ||
                    edge.getGameNode1().equals(v1) && edge.getGameNode2().equals(v))
                edgesSet.add((E) edge);
        }
        return edgesSet;
    }

    @Override
    public E getEdge(V v, V v1) {
        for (GameEdge edge : gameEdgeSet) {
            if (edge.getGameNode1().equals(v) && edge.getGameNode2().equals(v1) ||
                    edge.getGameNode1().equals(v1) && edge.getGameNode2().equals(v))
                return ((E) edge);
        }
        return null;
    }

    @Override
    public Supplier<V> getVertexSupplier() {
        return null;
    }

    @Override
    public Supplier<E> getEdgeSupplier() {
        return null;
    }

    @Override
    public E addEdge(V v, V v1) {
        GameEdge newEdge = new GameEdge(v, v1);
        this.gameEdgeSet.add(newEdge);
        return ((E) newEdge);
    }

    @Override
    public boolean addEdge(V v, V v1, E e) {
        GameEdge newEdge = new GameEdge(e.getGameNode1(), e.getGameNode2());
        this.gameEdgeSet.add(newEdge);
        return true;
    }

    @Override
    public V addVertex() {
        GameNode newNode = new GameNode(0, 0);
        this.gameNodeSet.add(newNode);
        return ((V) newNode);
    }

    @Override
    public boolean addVertex(V v) {
        GameNode newNode = new GameNode(v.getCoordX(), v.getCoordY());
        this.gameNodeSet.add(newNode);
        return true;
    }

    @Override
    public boolean containsEdge(V v, V v1) {
        for (GameEdge edge : this.gameEdgeSet) {
            if (edge.getGameNode1().equals(v) && edge.getGameNode2().equals(v1) ||
                    edge.getGameNode1().equals(v1) && edge.getGameNode2().equals(v))
                return true;
        }
        return false;
    }

    @Override
    public boolean containsEdge(E e) {
        for (GameEdge edge : this.gameEdgeSet) {
            if (edge.getGameNode1().equals(e))
                return true;
        }
        return false;
    }

    @Override
    public boolean containsVertex(V v) {
        for (GameNode node : this.gameNodeSet)
            if (v.equals(node)) return true;
        return false;

    }

    @Override
    public Set<E> edgeSet() {
        return ((Set<E>) this.gameEdgeSet);
    }

    @Override
    public int degreeOf(V v) {
        int degree = 0;
        for (GameEdge edge : this.gameEdgeSet) {
            if (edge.getGameNode1().equals(v) || edge.getGameNode2().equals(v))
                degree++;
        }
        return degree;
    }

    @Override
    public Set<E> edgesOf(V v) {
        Set<E> newEdgeSet = new HashSet<>();
        for (GameEdge edge : this.gameEdgeSet) {
            if (edge.getGameNode1().equals(v) || edge.getGameNode2().equals(v))
                newEdgeSet.add((E) edge);
        }
        return newEdgeSet;
    }

    @Override
    public int inDegreeOf(V v) {
        return degreeOf(v);
    }

    @Override
    public Set<E> incomingEdgesOf(V v) {
        Set<E> newEdgeSet = new HashSet<>();
        for (GameEdge edge : this.gameEdgeSet) {
            if (edge.getGameNode1().equals(v))
                newEdgeSet.add((E) edge);
        }
        return newEdgeSet;
    }

    @Override
    public int outDegreeOf(V v) {
        int degree = 0;

        for (GameEdge edge : this.gameEdgeSet) {
            if (edge.getGameNode2().equals(v))
                degree++;
        }
        return degree;
    }

    @Override
    public Set<E> outgoingEdgesOf(V v) {
        Set<E> newEdgeSet = new HashSet<>();
        for (GameEdge edge : this.gameEdgeSet) {
            if (edge.getGameNode2().equals(v))
                newEdgeSet.add((E) edge);
        }
        return newEdgeSet;
    }

    @Override
    public boolean removeAllEdges(Collection<? extends E> collection) {
        this.gameEdgeSet.removeAll(collection);
        return true;
    }

    @Override
    public Set<E> removeAllEdges(V v, V v1) {
        Set<E> removeSet = new HashSet<>();
        for (GameEdge edge : this.gameEdgeSet) {
            if (edge.getGameNode1().equals(v) && edge.getGameNode2().equals(v1)
                    || edge.getGameNode1().equals(v1) && edge.getGameNode2().equals(v))
                removeSet.add((E) edge);
        }
        return removeSet;
    }

    @Override
    public boolean removeAllVertices(Collection<? extends V> collection) {
        this.gameNodeSet.removeAll(collection);
        return true;
    }

    @Override
    public E removeEdge(V v, V v1) {
        GameEdge toRemove = new GameEdge();
        for (GameEdge edge : gameEdgeSet) {
            if (edge.getGameNode1().equals(v) && edge.getGameNode2().equals(v1) ||
                    edge.getGameNode1().equals(v1) && edge.getGameNode2().equals(v))
                toRemove = edge;
        }
        this.gameEdgeSet.remove(toRemove);
        return ((E) toRemove);
    }

    @Override
    public boolean removeEdge(E e) {
        for (GameEdge edge : gameEdgeSet) {
            if (edge.equals(e)) {
                this.gameEdgeSet.remove(edge);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeVertex(V v) {
        this.gameNodeSet.remove(v);
        return true;
    }

    @Override
    public Set<V> vertexSet() {
        return ((Set<V>) this.gameNodeSet);
    }

    @Override
    public V getEdgeSource(E e) {
        return (V) e.getGameNode1();
    }

    @Override
    public V getEdgeTarget(E e) {
        return (V) e.getGameNode2();
    }

    @Override
    public GraphType getType() {
        return null;
    }

    @Override
    public double getEdgeWeight(E e) {
        return 0;
    }

    @Override
    public void setEdgeWeight(E e, double v) {

    }

    @Override
    public void setEdgeWeight(V sourceVertex, V targetVertex, double weight) {
        Graph.super.setEdgeWeight(sourceVertex, targetVertex, weight);
    }

    @Override
    public GraphIterables<V, E> iterables() {
        return Graph.super.iterables();
    }

    @Override
    public String toString() {
        return "GameGraph{" +
                "nrRows=" + rows +
                ", noClmns=" + cols +
                ", gameNodeSet=" + gameNodeSet +
                ", gameEdgeSet=" + gameEdgeSet +
                ", anteriorGameNode=" + prevGameNode +
                '}';
    }
}
