package dto;

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@ToString
@NoArgsConstructor
public class OrderDto2 {
    private String id;
    private String date;
    private String customerId;
}
