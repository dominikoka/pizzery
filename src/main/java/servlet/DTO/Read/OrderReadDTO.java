package servlet.DTO.Read;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.Data;
import servlet.DTO.Create.ItemCreateDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class OrderReadDTO {
  private int id;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd HH:mm:ss")
  private LocalDateTime date;

  private Boolean is_paid;
  private Double total_price;
  private Set<ItemReadDTO> items;


}
