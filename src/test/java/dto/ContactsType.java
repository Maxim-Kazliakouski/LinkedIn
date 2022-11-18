package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ContactsType {
    String type1;
    String type2;
    String type3;
    String type4;
    String type5;
}