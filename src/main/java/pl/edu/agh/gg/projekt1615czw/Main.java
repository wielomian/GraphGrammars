package pl.edu.agh.gg.projekt1615czw;

import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import pl.edu.agh.gg.projekt1615czw.application.production.ProductionNotApplicableException;
import pl.edu.agh.gg.projekt1615czw.application.production.ProductionOne;
import pl.edu.agh.gg.projekt1615czw.application.production.ProductionTwo;
import pl.edu.agh.gg.projekt1615czw.application.production.exception.ProductionException;
import pl.edu.agh.gg.projekt1615czw.application.production.reference.ProductionOneReferenceNodeFinder;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNode;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNodeLabel;
import pl.edu.agh.gg.projekt1615czw.infrastructure.GraphAdapter;

import java.util.stream.Collectors;

@Slf4j
@PropertySource(value = "classpath:/application.properties", ignoreResourceNotFound = true)
@ComponentScan(basePackages = "pl.edu.agh.gg.projekt1615czw")
@Configuration
public class Main {
    private final ProductionOne productionOne;
    private final ProductionTwo productionTwo;

    @Autowired
    public Main(ProductionOne productionOne, ProductionTwo productionTwo) {
        this.productionOne = productionOne;
        this.productionTwo = productionTwo;
    }

    public static void main(String[] args) throws ProductionException {
        new AnnotationConfigApplicationContext(Main.class).getBean(Main.class).start();
    }

    private void start() throws ProductionException {
        // do stuff
        log.info("Application started");

        Graph<HyperNode, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultEdge.class);
        graph.addVertex(new HyperNode(HyperNodeLabel.S));
        HyperNode referenceNode = new ProductionOneReferenceNodeFinder().findProductionReferenceNode(graph)
                .orElseThrow(ProductionNotApplicableException::new);
        productionOne.applyProduction(graph, referenceNode);
        HyperNode iNode = findI(graph);
        iNode.setBreakAttribute(1);
        productionTwo.applyProduction(graph, iNode);
        org.graphstream.graph.Graph graphstreamGraph = new GraphAdapter("Graph 1", graph);
        graphstreamGraph.display();
    }
    private HyperNode findI(Graph<HyperNode, DefaultEdge> graph){
        for (HyperNode node : graph.vertexSet()){
            if (node.getLabel().equals(HyperNodeLabel.I))
                return node;
        }
        return null;
    }
}
