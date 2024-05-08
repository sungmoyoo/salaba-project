package org.admin.service;

import org.admin.domain.RefreshToken;

public interface RefreshTokenService {
    void addRefreshToken(RefreshToken refreshToken);

    int deleteRefreshToken(String refreshToken);

    RefreshToken findRefreshToken(String refreshToken);
}
