package com.minnow.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecurityUser implements UserDetails {

    private Integer id;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 权限：1管理员/2普通用户/3尊贵用户
     */
    private Integer status;

    private Set<String> perms = new HashSet<>();
    //TODO 用户名下卡号

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public Set<String> getPerms() {

        HashSet<String> goodBerms = new HashSet<>();



        this.perms.forEach(perm->{
            if (perm.contains(",")) {
                String[] splits = perm.split(",");
                for (String split : splits) {
                    goodBerms.add(split);
                }
            }else {
                goodBerms.add(perm);
            }
        });

        return goodBerms;
    }
}
