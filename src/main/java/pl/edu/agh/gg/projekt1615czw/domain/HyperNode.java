package pl.edu.agh.gg.projekt1615czw.domain;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class HyperNode {
    private final HyperNodeType type;
    private final Set<HyperNodeAttribute> attributes = new HashSet<>();
    private final Point geom;
    private final Color color;

    public HyperNode(Color color, Point geom) {
        this.color = color;
        this.geom = geom;
        this.type = HyperNodeType.VERTEX;
    }

    public HyperNode(HyperNodeAttribute... hyperNodeAttributes) {
        this.type = HyperNodeType.HYPER_EDGE;
        this.geom = null;
        this.color = null;
        this.attributes.addAll(Arrays.asList(hyperNodeAttributes));
    }

    public Point getGeom() {
        return geom;
    }

    public Color getColor() {
        return color;
    }

    public HyperNodeType getType() {
        return type;
    }

    public Set<HyperNodeAttribute> getAttributes() {
        return attributes;
    }
}
