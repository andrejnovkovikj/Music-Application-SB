package com.example.fakefy.model.dto;

public class PlaylistDto {
    private String name;
    private String description;
    private String imageUrl;
    private String username;

    public PlaylistDto(String name, String description, String imageUrl, String username) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.username = username;

    }
    public PlaylistDto(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
