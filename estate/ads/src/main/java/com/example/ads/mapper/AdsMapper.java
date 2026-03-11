package com.example.ads.mapper;
import org.springframework.stereotype.Component;

import com.example.ads.dto.AdsDto;
import com.example.ads.model.Ads;
@Component
public class AdsMapper {
	
	private final AdsTypeMapper adsTypeMapper;
	
	public AdsMapper(AdsTypeMapper adsTypeMapper)
	{
		this.adsTypeMapper = adsTypeMapper;
	}
	
	public AdsDto toDto(Ads ads){
		
		if (ads == null)
			return null;
		
		AdsDto adsDto = new AdsDto();
		adsDto.setId(ads.getId());
		adsDto.setName(ads.getName());
		adsDto.setDescription(ads.getDescription());
		adsDto.setPrice(ads.getPrice());
		adsDto.setRooms(ads.getRooms());
		adsDto.setSurface(ads.getSurface());
		adsDto.setCreatedAt(ads.getCreatedAt());
		adsDto.setAdsTypeDto(adsTypeMapper.toDto(ads.getAdsType()));
		adsDto.setUpdatedAt(ads.getUpdatedAt());
		return adsDto;
	}
}
