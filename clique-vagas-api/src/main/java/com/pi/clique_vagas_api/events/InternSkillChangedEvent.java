package com.pi.clique_vagas_api.events;

import org.springframework.context.ApplicationEvent;

public class InternSkillChangedEvent extends ApplicationEvent {
    private final Long userId;

    public InternSkillChangedEvent(Object source, Long userId) {
        super(source);
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}