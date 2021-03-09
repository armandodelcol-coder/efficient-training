package br.com.armando.efficienttraining.api.model;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class ProjectModel {

   private Long id;
   private String name;
   private String description;
   private OffsetDateTime createdAt;

}
