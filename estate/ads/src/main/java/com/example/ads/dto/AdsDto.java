package com.example.ads.dto;

import java.time.LocalDateTime;

public class AdsDto {
	private Long id;
    private String name;
    private String description;
    private Integer rooms;
    private Integer surface;
    private Double price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private AdsTypeDto adsTypeDto;
    
    public AdsTypeDto getAdsTypeDto() {
		return adsTypeDto;
	}
	public void setAdsTypeDto(AdsTypeDto adsTypeDto) {
		this.adsTypeDto = adsTypeDto;
	}
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
	public Integer getRooms() {
		return rooms;
	}
	public void setRooms(Integer rooms) {
		this.rooms = rooms;
	}
	public Integer getSurface() {
		return surface;
	}
	public void setSurface(Integer surface) {
		this.surface = surface;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}
