package com.yosa.theopengalaxy.dtos;

import com.yosa.theopengalaxy.domain.RateType;

public class ReviewReadDto {
    private String text;
    private RateType rateType;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public RateType getRateType() {
        return rateType;
    }

    public void setRateType(RateType rateType) {
        this.rateType = rateType;
    }
}
