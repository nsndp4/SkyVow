package com.project.skyvow.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "incident")
public class Incident extends Ticket {

    @NotBlank
    @Column(name = "on_behalf_of", nullable = false)
    private String onBehalfOf;

    @NotBlank
    @Column(nullable = false)
    private String category;

    @NotBlank
    @Column(nullable = false)
    private String subcategory;

    @Column(name = "support_agreement_name")
    private String supportAgreementName;

    @Column(name = "platform")
    private String platform;

    @Column(name = "impacted_lob")
    private String impactedLOB;

    @Column(name = "jira_reference")
    private String jiraReference;

    @Column(name = "impact")
    private String impact;

    @Column(name = "urgency")
    private String urgency;

    @Column(name = "priority")
    private String priority;

    @Column(name = "reported_by")
    private String reportedBy;

    @Column(name = "regulatory_impact")
    private String regulatoryImpact;

    @Column(name = "total_impact_duration")
    private String totalImpactDuration;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Override
    public String toString() {
        return "Incident{" +
                "onBehalfOf='" + onBehalfOf + '\'' +
                ", category='" + category + '\'' +
                ", subcategory='" + subcategory + '\'' +
                ", supportAgreementName='" + supportAgreementName + '\'' +
                ", platform='" + platform + '\'' +
                ", impactedLOB='" + impactedLOB + '\'' +
                ", jiraReference='" + jiraReference + '\'' +
                ", impact='" + impact + '\'' +
                ", urgency='" + urgency + '\'' +
                ", priority='" + priority + '\'' +
                ", reportedBy='" + reportedBy + '\'' +
                ", regulatoryImpact='" + regulatoryImpact + '\'' +
                ", totalImpactDuration='" + totalImpactDuration + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
