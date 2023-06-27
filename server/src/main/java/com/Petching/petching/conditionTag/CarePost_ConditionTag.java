package com.Petching.petching.conditionTag;

import com.Petching.petching.carepost.entity.CarePost;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity @Getter
@Setter
@NoArgsConstructor
public class CarePost_ConditionTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long carePostConditionTagId;


    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "POST_ID")
    private CarePost carePost;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CONDITION_TAG_ID")
    private ConditionTag conditionTag;

    public void setCarePost(CarePost carePost) {
        this.carePost = carePost;
        if(!this.carePost.getPostConditionTags().contains(this)) {
            this.carePost.setCarePost_ConditionTag(this);
        }

    }
    public CarePost_ConditionTag(CarePost carePost, ConditionTag conditionTag) {
        this.carePost = carePost;
        this.conditionTag = conditionTag;
    }
}
