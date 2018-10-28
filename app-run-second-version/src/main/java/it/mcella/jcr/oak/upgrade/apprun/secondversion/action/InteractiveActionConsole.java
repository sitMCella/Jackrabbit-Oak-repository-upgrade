package it.mcella.jcr.oak.upgrade.apprun.secondversion.action;

import it.mcella.jcr.oak.upgrade.commons.ConsoleReader;
import it.mcella.jcr.oak.upgrade.commons.ConsoleWriter;

import java.util.ArrayList;
import java.util.List;

public class InteractiveActionConsole {

    private final ConsoleReader consoleReader;
    private final ConsoleWriter consoleWriter;

    private List<String> questions;

    public InteractiveActionConsole(ConsoleReader consoleReader, ConsoleWriter consoleWriter) {
        this.consoleReader = consoleReader;
        this.consoleWriter = consoleWriter;
        this.questions = new ArrayList<>();
    }

    public void reset() {
        this.questions = new ArrayList<>();
    }

    public void addQuestion(String question) {
        questions.add(question);
    }

    public List<String> retrieveResponses() {
        List<String> responses = new ArrayList<>();
        for (String question : questions) {
            consoleWriter.print(question + ": ");
            responses.add(consoleReader.readLine());
        }
        return responses;
    }

    List<String> getQuestions() {
        return questions;
    }

}
