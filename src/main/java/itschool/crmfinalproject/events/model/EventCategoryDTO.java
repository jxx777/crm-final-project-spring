package itschool.crmfinalproject.events.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Data Transfer Object for Event categories Entity.")
public record EventCategoryDTO(List<String> types) {}