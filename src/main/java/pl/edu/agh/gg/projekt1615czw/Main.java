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
import pl.edu.agh.gg.projekt1615czw.application.production.*;

import pl.edu.agh.gg.projekt1615czw.application.production.exception.ProductionException;
import pl.edu.agh.gg.projekt1615czw.application.production.reference.ProductionFiveReferenceNodeFinder;
import pl.edu.agh.gg.projekt1615czw.application.production.reference.ProductionOneReferenceNodeFinder;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNode;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNodeLabel;
import pl.edu.agh.gg.projekt1615czw.infrastructure.GraphAdapter;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@PropertySource(value = "classpath:/application.properties", ignoreResourceNotFound = true)
@ComponentScan(basePackages = "pl.edu.agh.gg.projekt1615czw")
@Configuration
public class Main {
    private final ProductionOne productionOne;
    private final ProductionTwo productionTwo;
    private final ProductionFive productionFive;
    @Autowired
    public Main(ProductionOne productionOne, ProductionTwo productionTwo, ProductionFive productionFive) {
        this.productionOne = productionOne;
        this.productionTwo = productionTwo;
        this.productionFive = productionFive;

    }

    public static void main(String[] args) throws ProductionException {
        new AnnotationConfigApplicationContext(Main.class).getBean(Main.class).start();
    }

    private void start() throws ProductionException {
        // do stuff
        Graph<HyperNode, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultEdge.class);

        productionFiveTest(graph);
        org.graphstream.graph.Graph graphstreamGraph = new GraphAdapter("Graph 1", graph);
        graphstreamGraph.display();
    }

    private void productionFiveTest(Graph<HyperNode, DefaultEdge> graph){
        graph.addVertex(new HyperNode(HyperNodeLabel.S));
        HyperNode referenceNode = new ProductionOneReferenceNodeFinder().findProductionReferenceNode(graph)
                .orElseThrow(ProductionNotApplicableException::new);
        productionOne.applyProduction(graph, referenceNode);
        referenceNode = new ProductionFiveReferenceNodeFinder().findProductionReferenceNode(graph)
                .orElseThrow(ProductionNotApplicableException::new);
        System.out.println("Reference node before P5 break = " + referenceNode.getBreakAttribute());
        productionFive.applyProduction(graph, referenceNode);
        System.out.println("Reference node after P5 break = " + referenceNode.getBreakAttribute());

    }

    private void productionSixTest(Graph<HyperNode, DefaultEdge> graph){
        graph.addVertex(new HyperNode(HyperNodeLabel.S));
        HyperNode referenceNode = new ProductionOneReferenceNodeFinder().findProductionReferenceNode(graph)
                .orElseThrow(ProductionNotApplicableException::new);
        productionOne.applyProduction(graph, referenceNode);
        referenceNode = new ProductionFiveReferenceNodeFinder().findProductionReferenceNode(graph)
                .orElseThrow(ProductionNotApplicableException::new);
        System.out.println("Reference node before P5 break = " + referenceNode.getBreakAttribute());
        productionFive.applyProduction(graph, referenceNode);
        System.out.println("Reference node after P5 break = " + referenceNode.getBreakAttribute());
    }
}
