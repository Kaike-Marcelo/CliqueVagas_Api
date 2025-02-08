package com.pi.clique_vagas_api.events;

import org.springframework.context.ApplicationEvent;

public class JobPostingSkillChangedEvent extends ApplicationEvent {
    private final Long jobPostingId;

    public JobPostingSkillChangedEvent(Object source, Long jobPostingId) {
        super(source);
        this.jobPostingId = jobPostingId;
    }

    public Long getJobPostingId() {
        return jobPostingId;
    }
}