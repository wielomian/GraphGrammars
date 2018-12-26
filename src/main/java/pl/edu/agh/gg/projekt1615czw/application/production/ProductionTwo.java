package pl.edu.agh.gg.projekt1615czw.application.production;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import pl.edu.agh.gg.projekt1615czw.application.production.exception.ProductionException;
import pl.edu.agh.gg.projekt1615czw.application.production.exception.production.two.HyperEdgeNotInGraphException;
import pl.edu.agh.gg.projekt1615czw.application.production.exception.production.two.ProductionTwoException;
import pl.edu.agh.gg.projekt1615czw.domain.Direction;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNode;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNodeLabel;

import java.awt.Color;
import java.awt.Point;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductionTwo implements Production {

    private class ProductionTwoElementsOriented {

        HyperNode hyperEdge;
        HyperNode northWestPoint;
        HyperNode northEastPoint;
        HyperNode southWestPoint;
        HyperNode southEastPoint;

        ProductionTwoElementsOriented(Graph<HyperNode, DefaultEdge> graph, HyperNode referenceHyperEdgeNode) {
            this.hyperEdge = referenceHyperEdgeNode;
            Set<DefaultEdge> edges = graph.edgesOf(referenceHyperEdgeNode);
            List<HyperNode> points = edges.stream().map(e -> findPointOnEdge(graph, e)).sorted((HyperNode o1, HyperNode o2) -> {
                Point o1Point = o1.getGeom();
                Point o2Point = o2.getGeom();

                if (o1Point.getY() > o2Point.getY()) {
                    return 1;
                } else if (o1Point.getY() < o2Point.getY()) {
                    return -1;
                } else {
                    return Double.compare(o1Point.getX(), o2Point.getX());
                }
            }).collect(Collectors.toList());
            //todo learn where point (0,0) is and adjust

            this.southWestPoint = points.remove(0);
            this.southEastPoint = points.remove(0);
            this.northWestPoint = points.remove(0);
            this.northEastPoint = points.remove(0);

            //todo test if the order is correct
        }

        HyperNode findPointOnEdge(Graph<HyperNode, DefaultEdge> graph, DefaultEdge edge) {
            HyperNode source = graph.getEdgeSource(edge);
            if (source.getGeom() != null) {
                return source;
            } else {
                return graph.getEdgeTarget(edge);
            }
        }
    }

    @Override
    public void applyProduction(Graph<HyperNode, DefaultEdge> graph, HyperNode referenceHyperEdgeNode) throws ProductionException {
        if (referenceHyperEdgeNode.getBreakAttribute() != 1) {
            throw new ProductionTwoException(String.format("Break equals %d for HyperEdge %s", referenceHyperEdgeNode.getBreakAttribute(), referenceHyperEdgeNode.toString()));
        }

        ProductionTwoElementsOriented elements = new ProductionTwoElementsOriented(graph, referenceHyperEdgeNode);
        HyperNode northEastHyperEdge = new HyperNode(HyperNodeLabel.I);
        HyperNode northWestHyperEdge = new HyperNode(HyperNodeLabel.I);
        HyperNode southEastHyperEdge = new HyperNode(HyperNodeLabel.I);
        HyperNode southWestHyperEdge = new HyperNode(HyperNodeLabel.I);

        HyperNode northHyperEdge = new HyperNode(Direction.N, HyperNodeLabel.F);
        HyperNode southHyperEdge = new HyperNode(Direction.S, HyperNodeLabel.F);
        HyperNode eastHyperEdge = new HyperNode(Direction.E, HyperNodeLabel.F);
        HyperNode westHyperEdge = new HyperNode(Direction.W, HyperNodeLabel.F);

        List<HyperNode> hyperEdges = Arrays.asList(
                northEastHyperEdge, northWestHyperEdge,
                southEastHyperEdge, southWestHyperEdge,
                northHyperEdge, southHyperEdge, eastHyperEdge, westHyperEdge);

        //todo decide how do we round such cases
        //todo throw this into helper

        Point middlePointCoordinates = new Point(
                (int) Math.floor(elements.northWestPoint.getGeom().getX() + elements.northEastPoint.getGeom().getX() / 2),
                (int) Math.floor(elements.northWestPoint.getGeom().getY() + elements.southWestPoint.getGeom().getY() / 2));

        //todo set rgb as true value from the bitmap [bitmap needed]
        HyperNode middlePoint = new HyperNode(new Color(0, 0, 0), middlePointCoordinates);

        if (!graph.removeVertex(referenceHyperEdgeNode)) {
            throw new HyperEdgeNotInGraphException(String.format("No HyperEdge %s in graph", referenceHyperEdgeNode.toString()));
        }

        graph.addVertex(middlePoint);

        for (HyperNode hyperEdge : hyperEdges){
            graph.addVertex(hyperEdge);
            graph.addEdge(middlePoint, hyperEdge);
        }

        graph.addEdge(elements.northEastPoint, northEastHyperEdge);
        graph.addEdge(elements.northWestPoint, northWestHyperEdge);
        graph.addEdge(elements.southEastPoint, southEastHyperEdge);
        graph.addEdge(elements.southWestPoint, southWestHyperEdge);
    }
}
