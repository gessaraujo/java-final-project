package com.cbfacademy.apiassessment.Model.Dto;

public class AddEpisodeDto {
    private String seasonId;
    private String episodeId;

    public String getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(String seasonId) {
        this.seasonId = seasonId;
    }

    public String getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(String episodeId) {
        this.episodeId = episodeId;
    }
}
