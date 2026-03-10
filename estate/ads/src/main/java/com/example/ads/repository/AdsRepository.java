package com.example.ads.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ads.model.Ads;

public interface AdsRepository extends JpaRepository<Ads, Long>{

}
