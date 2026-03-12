package com.example.ads.service;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ads.model.Ads;
import com.example.ads.repository.AdsRepository;
import java.util.Optional;

@Service
@Transactional
public class AdsService {
	
	private final AdsRepository adsRepository;
	
	public AdsService(AdsRepository adsRepository) {
		this.adsRepository = adsRepository;
	}
	
	public List<Ads> getAllAds() {
		
		return adsRepository.findAll();
	}
	
	public Optional<Ads> getAdsById(Long id) {
		
		return adsRepository.findById(id);
	}
	
	public Ads saveAds(Ads ads) {
		
		return adsRepository.save(ads);
	}
	
	public void deleteAds(Long id) {
		
		 adsRepository.deleteById(id);
	}
	public Page<Ads> getAllAds(Pageable pageable) {
	    return adsRepository.findAll(pageable);
	}
	public Page<Ads> findWithFilters(String search, Long adstypeId,
            Double minPrice, Double maxPrice,
            Pageable pageable) {
		return adsRepository.findWithFilters(search, adstypeId, minPrice, maxPrice, pageable);
	}
}
