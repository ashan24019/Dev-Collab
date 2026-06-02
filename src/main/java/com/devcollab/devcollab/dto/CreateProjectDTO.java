package com.devcollab.devcollab.dto;


import lombok.Data;

@Data public class CreateProjectDTO {

    private String name;

    private String description;

    private String ownerId;
}
