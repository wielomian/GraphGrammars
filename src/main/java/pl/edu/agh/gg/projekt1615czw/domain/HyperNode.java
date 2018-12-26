package pl.edu.agh.gg.projekt1615czw.domain;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class HyperNode {
    private final HyperNodeType type;
    private final Set<HyperNodeLabel> attributes = new HashSet<>();
    private final Point geom;
    private final Color color;
    private int breakAttribute;
    private Direction direction;

    public HyperNode(Color color, Point geom) {
        this.color = color;
        this.geom = geom;
        this.type = HyperNodeType.VERTEX;
    }

    public HyperNode(HyperNodeLabel... hyperNodeLabels) {
        this.type = HyperNodeType.HYPER_EDGE;
        this.geom = null;
        this.color = null;
        this.breakAttribute = 0;
        this.attributes.addAll(Arrays.asList(hyperNodeLabels));
    }

    public HyperNode(Direction direction, HyperNodeLabel... hyperNodeLabels) {
        this(hyperNodeLabels);
        this.direction = direction;
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

    public Set<HyperNodeLabel> getAttributes() {
        return attributes;
    }

    public int getBreakAttribute() {
        return breakAttribute;
    }

    public void setBreakAttribute(int breakAttribute) {
        this.breakAttribute = breakAttribute;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "HyperNode{" +
                "type=" + type +
                ", attributes=" + attributes +
                ", geom=" + geom +
                ", color=" + color +
                ", breakAttribute=" + breakAttribute +
                ", direction=" + direction +
                '}';
    }
}
