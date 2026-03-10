package com.example.ads.service;

import com.example.ads.model.AdsType;
import com.example.ads.repository.AdsTypeRepository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
@Transactional
public class AdsTypeService {

	private final AdsTypeRepository adsTypeRepository;
	
	public AdsTypeService(AdsTypeRepository adsTypeRepository) {
		this.adsTypeRepository = adsTypeRepository;
	}
	
	public List<AdsType> getAllAdsType() {
		
		return adsTypeRepository.findAll();
	}
	
	public Optional<AdsType> getAdsTypeById(Long id) {
		
		return adsTypeRepository.findById(id);
	}
	
	public AdsType saveAdsType(AdsType adsType) {
		
		return adsTypeRepository.save(adsType);
	}
	
	public void deleteAdsType(Long id) {
		
		adsTypeRepository.deleteById(id);
	}
}
