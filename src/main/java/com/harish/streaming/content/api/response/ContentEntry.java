package com.harish.streaming.content.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.harish.streaming.content.enumeration.PriorityLevel;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of={"id","guid","title","media","peg$contentClassification"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContentEntry {
    private String id;
    private String guid;
    private long updated;
    private String title;
    private String description;
    private long added;
    private boolean approved;
    private List<Credit> credits = new ArrayList<>();
    private Map<String, String> descriptionLocalized = new HashMap();
    private String displayGenre;
    private String editorialRating;
    private List<ImageMedia> imageMediaIds = new ArrayList();
    private String isAdult;
    private List<String> languages = new ArrayList();
    private String longDescription;
    private Map<String, String> longDescriptionLocalized = new HashMap();
    private String programType;
    private List<String> ratings = new ArrayList();
    private String seriesEpisodeNumber;
    private String seriesId;
    private String shortDescription;
    private Map<String, String> shortDescriptionLocalized = new HashMap();
    private List<String> tagIds= new ArrayList();
    private List<Tag> tags = new ArrayList();
    private Map<String, Thumbnail> thumbnails = new HashMap();
    private Map<String, String> titleLocalized = new HashMap();
    private String tvSeasonEpisodeNumber;
    private String tvSeasonId;
    private String tvSeasonNumber;
    private int year;
    private List<Medium> media = new ArrayList();
    private String peg$ExclusiveFrench;
    private int peg$arAgeRating;
    private String peg$arContentRating;
    private String peg$availableInSection;
    private String peg$contentClassification;
    private String peg$contractName;
    private String peg$countryOfOrigin;
    private String peg$ISOcountryOfOrigin;
    private PriorityLevel peg$priorityLevel;
    private PriorityLevel peg$priorityLevelActionandAdventure;
    private PriorityLevel peg$priorityLevelArabic;
    private PriorityLevel peg$priorityLevelChildrenandFamily;
    private PriorityLevel peg$priorityLevelComedy;
    private PriorityLevel peg$priorityLevelDisney;
    private PriorityLevel peg$priorityLevelDisneyKids;
    private PriorityLevel peg$priorityLevelDramaKidsAction;
    private PriorityLevel peg$priorityLevelKidsFunandAdventure;
    private PriorityLevel peg$priorityLevelKidsMagicandDreams;
    private PriorityLevel peg$priorityLevelKidsPreschool;
    private PriorityLevel peg$priorityLevelKidsRomance;
    private PriorityLevel peg$priorityLevelKidsThriller;
    private String peg$productCode;
    private String peg$programMediaAvailability;
    private String peg$testDefaultValue;

    public String toString(){
        return new StringBuilder().append("CONTENT CLASSIFICATION : ").append(peg$contentClassification)
                .append("- MEDIA COUNT:").append(media.size())
                .append("MEDIA :").append(media)
                .toString();
    }
}