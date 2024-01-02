package org.cmo.cmoportal.state;

import groovyjarjarpicocli.CommandLine;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class State {
    HashMap<String, Object> store;

    public State() {
        this.store = new HashMap<>();
        this.store.put("autoAssignSubmissions", true);
        this.store.put("examinerNotification", "");
        this.store.put("candidateNotification", "");
    }

    public boolean getAutoAssign() {
        return (boolean) this.store.get("autoAssignSubmissions");
    }

    public void setAutoAssign(String name, boolean value) {
        this.store.put(name, value);
    }

    public String getExaminerNotification() {
        return (String) this.store.get("examinerNotification");
    }

    public void setExaminerNotification(String name, String value) {
        this.store.put(name, value);
    }

    public String getCandidateNotification() {
        return (String) this.store.get("candidateNotification");
    }

    public void setCandidateNotification(String name, String value) {
        this.store.put(name, value);
    }

}
