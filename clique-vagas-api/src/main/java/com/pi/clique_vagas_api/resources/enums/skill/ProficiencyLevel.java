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

    public double calculateSkillCompatibility(ProficiencyLevel other) {
        if (this == other) {
            return 1.0;
        } else if (this.ordinal() < other.ordinal()) {
            return 0.6;
        } else {
            return 0.8;
        }
    }

}
