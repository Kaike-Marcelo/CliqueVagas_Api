package com.pi.clique_vagas_api.resources.enums.skill;

public enum TypeSkill {
    HARD_SKILL("hard_skill"),
    SOFT_SKILL("soft_skill");

    private String type;

    TypeSkill(String type) {
        this.type = type;
    }

    public String getTypeSkill() {
        return type;
    }

}
