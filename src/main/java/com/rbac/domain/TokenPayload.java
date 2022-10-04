package com.rbac.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TokenPayload {
    private String sub;
    private String name;
    private List<Roles> data;

}
