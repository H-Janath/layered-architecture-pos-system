package dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString

public class OrderDto {
    private String Orderid;
    private String date;
    private String custId;
    private List<OrderDetailsDto> dto;
}
