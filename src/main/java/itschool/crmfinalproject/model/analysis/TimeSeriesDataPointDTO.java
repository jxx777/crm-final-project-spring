package itschool.crmfinalproject.model.analysis;

import java.time.LocalDateTime;

public record TimeSeriesDataPointDTO(LocalDateTime timestamp, Double value) {
}
