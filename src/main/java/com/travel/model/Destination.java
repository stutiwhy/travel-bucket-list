package com.travel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Destination {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Destination name is required")
    private String name;
    @NotBlank(message = "City is required")
    private String city;
    @NotBlank(message = "Country is required")
    private String country;
    private String description;
    private String category = "OTHER";   // BEACH, MOUNTAIN, CITY, HISTORICAL, OTHER
    private String imageUrl;            // optional picture link (blank = auto-fetched from Wikipedia)
    private Integer rating;              // 1-5 stars, optional
    private boolean visited = false;     // false = pending

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;                   // owner of this bucket-list item

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
    public boolean isVisited() { return visited; }
    public void setVisited(boolean visited) { this.visited = visited; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
