package com.gorent.api.util.search.specification;

import com.gorent.api.model.City;
import com.gorent.api.model.Property;
import com.gorent.api.model.enums.PropertyStatus;
import com.gorent.api.util.search.SearchCriteria;
import com.gorent.api.util.search.SearchType;

public class PropertySpecification {

    private static final String CITY = "city";

    private static final String SURFACE_AREA = "surfaceArea";

    private static final String BEDROOMS = "bedrooms";

    private static final String RENTAL_PRICE = "rentalPrice";

    private static final String STATUS = "status";

    private static final String HAS_BALCONY = "hasBalcony";

    private static final String HAS_GARDEN = "hasGarden";

    private Long cityId;

    private Long minArea;

    private Long maxArea;

    private Long minBedrooms;

    private Long maxBedrooms;

    private Long minPrice;

    private PropertySearchStatus status;

    private Boolean hasBalcony;

    private Boolean hasGarden;

    public PropertySpecification() {
    }

    public PropertySpecification(Long cityId, Long minArea, Long maxArea, Long minBedrooms, Long maxBedrooms, Long minPrice, PropertySearchStatus status, Boolean hasBalcony, Boolean hasGarden) {
        this.cityId = cityId;
        this.minArea = minArea;
        this.maxArea = maxArea;
        this.minBedrooms = minBedrooms;
        this.maxBedrooms = maxBedrooms;
        this.minPrice = minPrice;
        this.status = status;
        this.hasBalcony = hasBalcony;
        this.hasGarden = hasGarden;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getMinArea() {
        return minArea;
    }

    public void setMinArea(Long minArea) {
        this.minArea = minArea;
    }

    public Long getMaxArea() {
        return maxArea;
    }

    public void setMaxArea(Long maxArea) {
        this.maxArea = maxArea;
    }

    public Long getMinBedrooms() {
        return minBedrooms;
    }

    public void setMinBedrooms(Long minBedrooms) {
        this.minBedrooms = minBedrooms;
    }

    public Long getMaxBedrooms() {
        return maxBedrooms;
    }

    public void setMaxBedrooms(Long maxBedrooms) {
        this.maxBedrooms = maxBedrooms;
    }

    public Long getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Long minPrice) {
        this.minPrice = minPrice;
    }

    public PropertySearchStatus getStatus() {
        return status;
    }

    public void setStatus(PropertySearchStatus status) {
        this.status = status;
    }

    public Boolean getHasBalcony() {
        return hasBalcony;
    }

    public void setHasBalcony(Boolean hasBalcony) {
        this.hasBalcony = hasBalcony;
    }

    public Boolean getHasGarden() {
        return hasGarden;
    }

    public void setHasGarden(Boolean hasGarden) {
        this.hasGarden = hasGarden;
    }

    @Override
    public String toString() {
        return "PropertySpecification{" +
                "cityId=" + cityId +
                ", minArea=" + minArea +
                ", maxArea=" + maxArea +
                ", minBedrooms=" + minBedrooms +
                ", maxBedrooms=" + maxBedrooms +
                ", minPrice=" + minPrice +
                ", status=" + status +
                ", hasBalcony=" + hasBalcony +
                ", hasGarden=" + hasGarden +
                '}';
    }

    public GenericSpecification<Property> getSpecification() {
        GenericSpecification<Property> genericSpecification = new GenericSpecification<>();
        genericSpecification.add(new SearchCriteria(CITY, new City(cityId), SearchType.EQUAL));
        if (minArea != null) {
            genericSpecification.add(new SearchCriteria(SURFACE_AREA, minArea, SearchType.GREATER_THAN_EQUAL));
        }

        if (maxArea != null) {
            genericSpecification.add(new SearchCriteria(SURFACE_AREA, maxArea, SearchType.LESS_THAN_EQUAL));
        }

        if (minBedrooms != null) {
            genericSpecification.add(new SearchCriteria(BEDROOMS, minBedrooms, SearchType.GREATER_THAN_EQUAL));
        }

        if (maxBedrooms != null) {
            genericSpecification.add(new SearchCriteria(BEDROOMS, maxBedrooms, SearchType.LESS_THAN_EQUAL));
        }

        if (minPrice != null) {
            genericSpecification.add(new SearchCriteria(RENTAL_PRICE, minPrice, SearchType.GREATER_THAN_EQUAL));
        }

        if (status != PropertySearchStatus.ALL) {
            genericSpecification.add(new SearchCriteria(STATUS, PropertyStatus.valueOf(status.name()), SearchType.EQUAL));
        }

        if (hasBalcony != null) {
            genericSpecification.add(new SearchCriteria(HAS_BALCONY, hasBalcony, SearchType.EQUAL));
        }

        if (hasGarden != null) {
            genericSpecification.add(new SearchCriteria(HAS_GARDEN, hasGarden, SearchType.EQUAL));
        }

        return genericSpecification;
    }
}

enum PropertySearchStatus {
    FURNISHED("FURNISHED"),
    UNFURNISHED("UNFURNISHED"),
    ALL("ALL");

    private final String label;

    PropertySearchStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
