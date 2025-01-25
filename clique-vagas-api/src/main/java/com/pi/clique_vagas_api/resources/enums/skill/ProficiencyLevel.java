package com.pi.clique_vagas_api.resources.enums.skill;

public enum ProficiencyLevel {
    BASIC("basic"),
    INTERMEDIATE("intermediate"),
    ADVANCED("advanced"),
    EXPERT("expert");

    private String level;

    ProficiencyLevel(String level) {
        this.level = level;
    }

    public String getProficiencyLevel() {
        return level;
    }

}
