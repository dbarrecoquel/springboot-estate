package com.example.ads.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
@Entity
@Table(name = "ads")
public class Ads {
	 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 
	@NotBlank(message = "Name is required")
    private String name;
    private String description;
    private Integer rooms;
    private Integer surface;
    private Double price;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adstype_id", insertable = false, updatable = false)
    private AdsType adsType;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public AdsType getAdsType() {
		return adsType;
	}

	public void setAdsType(AdsType adsType) {
		this.adsType = adsType;
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


    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
}
