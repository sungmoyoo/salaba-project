package org.admin.repository;

import org.admin.domain.RefreshToken;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RefreshTokenDao {
    void addRefreshToken(RefreshToken refreshToken);

    int deleteRefreshToken(@Param("refreshToken") String refreshToken);

    RefreshToken findRefreshToken(@Param("refreshToken") String refreshToken);
}
