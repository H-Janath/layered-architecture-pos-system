package entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Orders {
    private String id;
    private String date;
    private String customerId;
}
