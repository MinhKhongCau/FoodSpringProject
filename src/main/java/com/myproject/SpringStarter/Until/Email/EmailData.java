package com.myproject.SpringStarter.Until.Email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailData {
    private String reciptient;
    private String messageBody;
    private String subject;
    
}
