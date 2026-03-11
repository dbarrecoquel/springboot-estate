package com.example.frontrest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ads.dto.AdsDto;
import com.example.ads.mapper.AdsMapper;
import com.example.ads.model.Ads;
import com.example.ads.service.AdsService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ads")
@CrossOrigin(origins = "*")
public class AdsController {

	private final AdsService adsService;
	private final AdsMapper adsMapper;
	public AdsController(AdsService adsService, AdsMapper adsMapper) {
		this.adsService = adsService;
		this.adsMapper = adsMapper;
	}
	
	@GetMapping
	public ResponseEntity<List<AdsDto>> getAllAds(){
		List<AdsDto> list = adsService.getAllAds()
				.stream()
				.map(adsMapper::toDto)
				.collect(Collectors.toList());
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AdsDto> getAdsById(@PathVariable Long id) {
		
		Optional<Ads> ads = adsService.getAdsById(id);
		return ads.map(a -> {
			return ResponseEntity.ok(adsMapper.toDto(a));
		}).orElse(ResponseEntity.notFound().build());
		
	}
}
