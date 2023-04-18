package com.gm.sn.dto;

import com.gm.sn.dto.base.OutputConverter;
import com.gm.sn.entity.AdminRole;
import com.gm.sn.entity.User;
import lombok.Data;
import lombok.ToString;

import java.util.List;
@Data
@ToString
public class UserDTO implements OutputConverter<UserDTO, User> {

    private int id;

    private String username;

    private String name;

    private String phone;

    private String email;

    private boolean enabled;

    private List<AdminRole> roles;

}
