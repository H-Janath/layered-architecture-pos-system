package dto.tm;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class OrderDetailsTm extends RecursiveTreeObject<OrderDetailsTm> {
    private String OrderId;
    private String  itemCode;
    private int qty;
    private Double unitpice;
}
