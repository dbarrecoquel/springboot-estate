package com.example.events.model;

import java.time.LocalDateTime;

import org.apache.kafka.common.Uuid;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

public class AdViewEvent {
	 @JsonProperty("event_id")
	 private String eventId;
	 
	 @JsonProperty("ad_id")
	 private Long adId;
	 
	 @JsonProperty("ad_name")
	 private String adName;
	 
	 @JsonProperty("adstype_id")
	 private Long adsTypeId;
	 
	 @JsonProperty("adstype_name")
	 private String adsTypeName;
	 
	 @JsonProperty("timestamp")
	 @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	 @JsonSerialize(using = LocalDateTimeSerializer.class)
	 @JsonDeserialize(using = LocalDateTimeDeserializer.class)
	 private LocalDateTime timestamp;

	 public AdViewEvent() {
		 this.timestamp = LocalDateTime.now();
		 this.eventId = Uuid.randomUuid().toString();
	 }
	 public AdViewEvent(Long adId, String adName, Long adsTypeId, String adsTypeName) {
		 this();
		 this.adId = adId;
		 this.adName = adName;
		 this.adsTypeId = adsTypeId;
		 this.adsTypeName = adsTypeName;
	 }
	 public String getEventId() {
		 return eventId;
	 }

	 public void setEventId(String eventId) {
		 this.eventId = eventId;
	 }

	 public Long getAdId() {
		 return adId;
	 }

	 public void setAdId(Long adId) {
		 this.adId = adId;
	 }

	 public String getAdName() {
		 return adName;
	 }

	 public void setAdName(String adName) {
		 this.adName = adName;
	 }

	 public Long getAdsTypeId() {
		 return adsTypeId;
	 }

	 public void setAdsTypeId(Long adsTypeId) {
		 this.adsTypeId = adsTypeId;
	 }

	 public String getAdsTypeName() {
		 return adsTypeName;
	 }

	 public void setAdsTypeName(String adsTypeName) {
		 this.adsTypeName = adsTypeName;
	 }

	 public LocalDateTime getTimestamp() {
		 return timestamp;
	 }

	 public void setTimestamp(LocalDateTime timestamp) {
		 this.timestamp = timestamp;
	 }
}
