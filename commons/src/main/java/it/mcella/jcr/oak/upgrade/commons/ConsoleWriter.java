package it.mcella.jcr.oak.upgrade.commons;

public class ConsoleWriter {

    public <T> void println(T object) {
        System.out.println(object);
    }

    public <T> void print(T object) {
        System.out.print(object);
    }

}
