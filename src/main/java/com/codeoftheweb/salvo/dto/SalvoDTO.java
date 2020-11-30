package com.codeoftheweb.salvo.dto;

import com.codeoftheweb.salvo.model.Salvo;

import java.util.LinkedHashMap;
import java.util.Map;

public class SalvoDTO {
    public static Map<String,Object> makeSalvoDTO(Salvo salvo){
        Map<String,Object> dto = new LinkedHashMap<>();
        dto.put("id", salvo.getId());
        dto.put("turn",salvo.getTurn());
        dto.put("locations",salvo.getLocations());
        return dto;
    }
}
