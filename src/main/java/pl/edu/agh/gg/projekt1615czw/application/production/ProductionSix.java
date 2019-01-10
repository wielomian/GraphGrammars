package pl.edu.agh.gg.projekt1615czw.application.production;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.springframework.stereotype.Component;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNode;

@Component
public class ProductionSix implements Production {

    @Override
    public void applyProduction(Graph<HyperNode, DefaultEdge> graph, HyperNode referenceHyperEdgeNode) {
        referenceHyperEdgeNode.setBreakAttribute(1);
    }
}
