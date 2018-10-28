package it.mcella.jcr.oak.upgrade.repository;

public class OakPropertyType implements JcrPropertyType {

    private final int type;

    public OakPropertyType(int type) {
        this.type = type;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OakPropertyType that = (OakPropertyType) o;

        if (type != that.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return type;
    }

    @Override
    public String toString() {
        return "OakPropertyType{" +
                "type=" + type +
                '}';
    }

}
