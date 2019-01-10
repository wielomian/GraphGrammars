package pl.edu.agh.gg.projekt1615czw.application.production.reference;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNode;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNodeLabel;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNodeType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductionFiveReferenceNodeFinder implements ProductionReferenceNodeFinder {

    @Override
    public Optional<HyperNode> findProductionReferenceNode(Graph<HyperNode, DefaultEdge> graph) {
        List<HyperNode> matchingNodes = graph.vertexSet()
                .stream()
                .filter(node -> node.getType() == HyperNodeType.HYPER_EDGE)
                .filter(node -> node.getAttributes().contains(HyperNodeLabel.I))
                .filter(node -> graph.edgesOf(node).size() == 4)
                .filter(node -> node.getBreakAttribute() == 0)
                .collect(Collectors.toList());

        if (matchingNodes.size() != 1) {
            return Optional.empty();
        }

        return Optional.of(matchingNodes.get(0));
    }
}
