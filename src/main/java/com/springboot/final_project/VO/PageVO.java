package com.springboot.final_project.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageVO {
    private int id;
    private String username;
    private int page;
    private int limit;
    private String sort;
}
