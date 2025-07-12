package com.example.advisor_backend.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DeliveryNoteDTO {
    private Long deliveryId;
    private Long orderId;
    private String deliveryStatus;
    private String deliveryDate;

    // Getters and Setters
}
