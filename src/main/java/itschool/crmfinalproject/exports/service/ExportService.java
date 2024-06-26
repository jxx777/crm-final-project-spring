package itschool.crmfinalproject.exports.export;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

public interface ExportService {
    ResponseEntity<byte[]> exportData(String entityType, String format) throws JsonProcessingException;
}