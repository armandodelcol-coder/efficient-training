package br.com.armando.efficienttraining.domain.model.enums;

public enum TaskResourceStatus {

    MESSY("Messy"),
    LEARNING("Learning"),
    LEARNED("Learned");

    private String description;

    TaskResourceStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

}
