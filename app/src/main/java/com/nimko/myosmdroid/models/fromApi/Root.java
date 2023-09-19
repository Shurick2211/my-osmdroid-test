package com.nimko.myosmdroid.models.fromApi;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Root {
    @SerializedName("Builds")
    public ArrayList<Build> builds;
    @SerializedName("ClubNewsStatuses")
    public ArrayList<ClubNewsStatus> clubNewsStatuses;
    @SerializedName("ClubStatuses")
    public ArrayList<ClubStatus> clubStatuses;
    @SerializedName("ClubTypes")
    public ArrayList<ClubType> clubTypes;
    @SerializedName("CoordinatorStatuses")
    public ArrayList<CoordinatorStatus> coordinatorStatuses;
    @SerializedName("Districts")
    public ArrayList<District> districts;
    @SerializedName("EventCategories")
    public ArrayList<Object> eventCategories;
    @SerializedName("EventHoldingStatuses")
    public ArrayList<EventHoldingStatus> eventHoldingStatuses;
    @SerializedName("ExerciseCategories")
    public ArrayList<ExerciseCategory> exerciseCategories;
    @SerializedName("ExerciseDifficultyLevels")
    public ArrayList<ExerciseDifficultyLevel> exerciseDifficultyLevels;
    @SerializedName("ExerciseSubcategories")
    public ArrayList<ExerciseSubcategory> exerciseSubcategories;
    @SerializedName("FileTypes")
    public ArrayList<FileType> fileTypes;
    @SerializedName("FitnessEquipment")
    public ArrayList<FitnessEquipment> fitnessEquipment;
    @SerializedName("InfoPageSections")
    public ArrayList<InfoPageSection> infoPageSections;
    @SerializedName("InfoPageStatuses")
    public ArrayList<InfoPageStatus> infoPageStatuses;
    @SerializedName("NewsStatuses")
    public ArrayList<NewsStatus> newsStatuses;
    @SerializedName("OwnershipTypes")
    public ArrayList<OwnershipType> ownershipTypes;
    @SerializedName("Regions")
    public ArrayList<Region> regions;
    @SerializedName("SportEventStatuses")
    public ArrayList<SportEventStatus> sportEventStatuses;
    @SerializedName("SportEventTypes")
    public ArrayList<SportEventType> sportEventTypes;
    @SerializedName("SportsgroundAccessTypes")
    public ArrayList<SportsgroundAccessType> sportsgroundAccessTypes;
    @SerializedName("SportsgroundCapacities")
    public ArrayList<SportsgroundCapacity> sportsgroundCapacities;
    @SerializedName("SportsgroundConditions")
    public ArrayList<SportsgroundCondition> sportsgroundConditions;
    @SerializedName("SportsgroundStatuses")
    public ArrayList<SportsgroundStatus> sportsgroundStatuses;
    @SerializedName("SportsgroundTypes")
    public ArrayList<SportsgroundType> sportsgroundTypes;
    @SerializedName("SupportTicketStatuses")
    public ArrayList<SupportTicketStatus> supportTicketStatuses;
    @SerializedName("UserRoles")
    public ArrayList<UserRole> userRoles;
    @SerializedName("VideoStatuses")
    public ArrayList<VideoStatus> videoStatuses;
    @SerializedName("TimeZone")
    public ArrayList<Integer> timeZone;
}
