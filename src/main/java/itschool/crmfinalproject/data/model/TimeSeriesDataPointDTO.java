package itschool.crmfinalproject.data.model;

import java.time.LocalDateTime;

/**
 * Represents a data point in a time series, useful for trends over time.
 */
public record TimeSeriesDataPointDTO(LocalDateTime timestamp, Double value) {
}
