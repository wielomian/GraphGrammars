package pl.edu.agh.gg.projekt1615czw.application.production.reference;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNode;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNodeLabel;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNodeType;

import java.util.*;
import java.util.stream.Collectors;

public class ProductionSixReferenceNodeFinder implements ProductionReferenceNodeFinder {

    @Override
    public Optional<HyperNode> findProductionReferenceNode(Graph<HyperNode, DefaultEdge> graph) {
        List<HyperNode> matchingNodes = graph.vertexSet()
                .stream()
                .filter(node -> node.getType() == HyperNodeType.HYPER_EDGE)
                .filter(node -> node.getLabel().equals(HyperNodeLabel.I))
                .filter(node -> graph.edgesOf(node).size() <= 4)
                .filter(node -> node.getBreakAttribute() == 0)
                .collect(Collectors.toList());
        List<HyperNode> matchingNodes2 = graph.vertexSet()
                .stream()
                .filter(node -> node.getType() == HyperNodeType.HYPER_EDGE)
                .filter(node -> node.getLabel().equals(HyperNodeLabel.I))
                .filter(node -> graph.edgesOf(node).size() <= 3)
                .filter(node -> node.getBreakAttribute() == 1)
                .collect(Collectors.toList());
        List<HyperNode> matchingNodes3 = graph.vertexSet()
                .stream()
                .filter(node -> node.getType() == HyperNodeType.HYPER_EDGE)
                .filter(node -> node.getLabel().equals(HyperNodeLabel.F))
                .filter(node -> graph.edgesOf(node).size() <= 2)
                .collect(Collectors.toList());

        graph.edgesOf(matchingNodes.get(0));
        for (HyperNode big : matchingNodes) {
            for (HyperNode small : matchingNodes2) {
                for (HyperNode f : matchingNodes3) {
                    HashSet<HyperNodeLabel> hyperNodeLabels = new HashSet<>(f.getAttributes());
                    hyperNodeLabels.retainAll(small.getAttributes());
                    hyperNodeLabels.retainAll(big.getAttributes());
                    if (hyperNodeLabels.size() == 2) {
                        return Optional.of(big);
                    }
                }

            }

        }
        return Optional.empty();
    }
}
